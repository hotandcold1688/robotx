package com.macyou.robot.segment;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.macyou.robot.util.TextFileUtil;

public class HITSynonymLexicon implements  SynonymLexicon {
    private static final Log log = LogFactory.getLog(HITSynonymLexicon.class);

    private String           hitLexiconFile="src/main/resources/synonym.txt";
    protected DataStruct         data        = new DataStruct();
    protected DataStruct         new_data;

    
    
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
    
    
    
    private class DataStruct {
        private Map<String, HITSynonymGroup> idGroupMap   = new HashMap<String, HITSynonymGroup>();
        private Map<String, ArrayList<String>>    wordGroupMap = new HashMap<String, ArrayList<String>>();
        public Map<String, HITSynonymGroup> getIdGroupMap() {
            return idGroupMap;
        }
        public Map<String, ArrayList<String>> getWordGroupMap() {
            return wordGroupMap;
        }
    }
    


    public Set<String> listAllGroupIds() {
        return data.getIdGroupMap().keySet();
    }

    public List<String> getSynonymGroupsIdByWord(String word) {
        if (!isLegality(word))
            return null;
        return data.getWordGroupMap().get(word);
    }

    public HITSynonymGroup getSynonymGroupById(String groupId) {
        return data.getIdGroupMap().get(groupId);
    }

    public List<HITSynonymGroup> getSynonymGroupsByWord(String word) {
        if (!isLegality(word))
            return null;
        List<String> groupsId = this.getSynonymGroupsIdByWord(word);
        if (groupsId == null)
            return null;
        List<HITSynonymGroup> groups = new LinkedList<HITSynonymGroup>();
        for (String id : groupsId) {
        	HITSynonymGroup group = this.getSynonymGroupById(id);
            if (group != null)
                groups.add(group);
        }
        return groups.isEmpty() ? null : groups;
    }

    public void addSynonymGroup(HITSynonymGroup synonymGroup) {
       addSynonymGroup(synonymGroup, data.getIdGroupMap(), data.getWordGroupMap());
    }

    public void addSynonymGroup(HITSynonymGroup synonymGroup, Map<String, HITSynonymGroup> idMap,
                                Map<String, ArrayList<String>> wordMap) {
        String id = synonymGroup.getGroupId();
        if (idMap.containsKey(id)) {
            return;
        }
        idMap.put(id, synonymGroup);
        String[] words = synonymGroup.getWords();
        for (String word : words) {
        	ArrayList<String> groups = wordMap.get(word);
            if (groups == null) {
                groups = new ArrayList<String>();
                wordMap.put(word, groups);
            }
            groups.add(id);
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



    public String getHitLexiconFile() {
        return hitLexiconFile;
    }

    public void setHitLexiconFile(String hitLexiconFile) {
        this.hitLexiconFile = hitLexiconFile;
    }

}
