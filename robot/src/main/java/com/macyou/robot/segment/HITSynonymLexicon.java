package com.macyou.robot.segment;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.SortedMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.macyou.robot.util.TextFileUtil;

public class HITSynonymLexicon extends AbstractSynonymLexicon {
    private static final Log log = LogFactory.getLog(HITSynonymLexicon.class);

    private String           hitLexiconFile="D:/robotx/robot/src/main/resources/synonym.txt";

    public synchronized void init() {
        if (this.hitLexiconFile == null)
            return;
        List<String> lines = null;
        try {
            lines = TextFileUtil.readLines(new FileInputStream(this.hitLexiconFile));
        } catch (FileNotFoundException e) {
            log.error(this.hitLexiconFile, e);
        } catch (IOException e) {
            log.error(this.hitLexiconFile, e);
        }
        if (lines != null) {
            for (String line : lines) {
                if (line.trim().length() == 0)
                    continue;
                String[] array = line.split("\\s+");
                if (array.length < 2)
                    continue;
                String groupId = array[0];
                HITSynonymGroup group = new HITSynonymGroup(groupId, array, 1);
                this.addSynonymGroup(group);
            }
        }
    }
    
    

    public boolean isLegality(String word) {
        char[] chrs = word.toCharArray();
        for (char ch : chrs) {
            if (ch > 255)
                return true;
        }
        return false;
    }

    static public void loadHIT(SortedMap<String, SynonymGroup> newidGroupMap,
                               SortedMap<String, HITGroups> newWordGroupMap, String lexiconFile)
            throws IOException {
        if (lexiconFile == null)
            return;
        List<String> lines = TextFileUtil.readLines(new FileInputStream(lexiconFile));
        if (lines != null) {

            for (String line : lines) {
                if (line.trim().length() == 0)
                    continue;
                String[] array = line.split("\\s+");
                if (array.length < 2)
                    continue;
                String groupId = array[0];
                HITSynonymGroup group = new HITSynonymGroup(groupId, array, 1);
                addSynonymGroup(group, newidGroupMap, newWordGroupMap);
            }
        }
    }

    static public synchronized void addSynonymGroup(SynonymGroup synonymGroup,
                                                    SortedMap<String, SynonymGroup> newidGroupMap,
                                                    SortedMap<String, HITGroups> newwordGroupMap) {
        String id = synonymGroup.getGroupId();
        if (newidGroupMap.containsKey(id)) {
            return;
        }
        newidGroupMap.put(id, synonymGroup);
        String[] words = synonymGroup.getWords();
        for (String word : words) {
            HITGroups groups = newwordGroupMap.get(word);
            if (groups == null) {
                groups = new HITGroups();
                newwordGroupMap.put(word, groups);
            }
            groups.add(id);
        }
    }

    public short getSynonymLevel(String word1, String word2) {
        if (word1.equals(word2)) {
            return SynonymLevelConfig.LEVEL_EQUALS;
        }
        HITGroups groups1 = data.getWordGroupMap().get(word1);
        HITGroups groups2 = data.getWordGroupMap().get(word2);
        if (groups1 == null || groups2 == null) {
            return SynonymLevelConfig.LEVEL_NONSYNONYM;
        }
        short maxLevel = SynonymLevelConfig.LEVEL_NONSYNONYM;
        for (String group1 : groups1) {
            for (String group2 : groups2) {
                short level = SynonymLevelUtil.getSynonymLevelFromGroupId(group1, group2);
                if (level > maxLevel) {
                    maxLevel = level;
                }
                if (maxLevel == SynonymLevelConfig.LEVEL_ABSOLUTE)
                    return maxLevel;
            }
        }
        return maxLevel;
    }

    public String getHitLexiconFile() {
        return hitLexiconFile;
    }

    public void setHitLexiconFile(String hitLexiconFile) {
        this.hitLexiconFile = hitLexiconFile;
    }

    public static void main(String[] args) {
    	new HITSynonymLexicon().init();
	}
}
