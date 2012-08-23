package com.macyou.robot.segment;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class AbstractSynonymLexicon implements SynonymLexicon {

    protected DataStruct         data        = new DataStruct();
    protected DataStruct         new_data;

    private boolean              beginUpdate = false;

    protected SynonymLevelConfig config;

    protected class DataStruct {
        private Map<String, SynonymGroup> idGroupMap   = new HashMap<String, SynonymGroup>();
        private Map<String, HITGroups>    wordGroupMap = new HashMap<String, HITGroups>();

        public Map<String, SynonymGroup> getIdGroupMap() {
            return idGroupMap;
        }

        public Map<String, HITGroups> getWordGroupMap() {
            return wordGroupMap;
        }
    }
    public boolean isLegality(String word) {
        return true;
    }

    public abstract void init();

    public synchronized void refresh() {
        new_data = new DataStruct();
        beginUpdate = true;
        this.init();
        beginUpdate = false;
        data = new_data;
    }

    public Set<String> listAllGroupIds() {
        return data.getIdGroupMap().keySet();
    }

    public List<String> getSynonymGroupsIdByWord(String word) {
        if (!isLegality(word))
            return null;
        return data.getWordGroupMap().get(word);
    }

    public SynonymGroup getSynonymGroupById(String groupId) {
        return data.getIdGroupMap().get(groupId);
    }

    public List<SynonymGroup> getSynonymGroupsByWord(String word) {
        if (!isLegality(word))
            return null;
        List<String> groupsId = this.getSynonymGroupsIdByWord(word);
        if (groupsId == null)
            return null;
        List<SynonymGroup> groups = new LinkedList<SynonymGroup>();
        for (String id : groupsId) {
            SynonymGroup group = this.getSynonymGroupById(id);
            if (group != null)
                groups.add(group);
        }
        return groups.isEmpty() ? null : groups;
    }

    public void addSynonymGroup(SynonymGroup synonymGroup) {
        if (beginUpdate)
            addSynonymGroup(synonymGroup, new_data.getIdGroupMap(), new_data.getWordGroupMap());
        else
            addSynonymGroup(synonymGroup, data.getIdGroupMap(), data.getWordGroupMap());
    }

    public void addSynonymGroup(SynonymGroup synonymGroup, Map<String, SynonymGroup> idMap,
                                Map<String, HITGroups> wordMap) {
        String id = synonymGroup.getGroupId();
        if (idMap.containsKey(id)) {
            return;
        }
        idMap.put(id, synonymGroup);
        String[] words = synonymGroup.getWords();
        for (String word : words) {
            HITGroups groups = wordMap.get(word);
            if (groups == null) {
                groups = new HITGroups();
                wordMap.put(word, groups);
            }
            groups.add(id);
        }
    }

    public synchronized void removeSynonymGroup(String groupId) {
        SynonymGroup synonymGroup = data.getIdGroupMap().remove(groupId);
        if (synonymGroup == null)
            return;
        String[] words = synonymGroup.getWords();
        for (String word : words) {
            HITGroups groups = data.getWordGroupMap().get(word);
            if (groups != null) {
                groups.remove(groupId);
                if (groups.isEmpty()) {
                    data.getWordGroupMap().remove(word);
                }
            }
        }
    }

    public short getSynonymLevel(String word1, String word2) {
        if (!isLegality(word1) || !isLegality(word2))
            return SynonymLevelConfig.LEVEL_NONSYNONYM;
        if (word1.equals(word2)) {
            return SynonymLevelConfig.LEVEL_EQUALS;
        }
        HITGroups groups1 = data.getWordGroupMap().get(word1);
        HITGroups groups2 = data.getWordGroupMap().get(word2);
        if (groups1 == null || groups2 == null) {
            return SynonymLevelConfig.LEVEL_NONSYNONYM;
        }
        for (String group1 : groups1) {
            for (String group2 : groups2) {
                if (group1.equals(group2)) {
                    return SynonymLevelConfig.LEVEL_ABSOLUTE;
                }
            }
        }
        return SynonymLevelConfig.LEVEL_NONSYNONYM;
    }

    public synchronized void clear() {
        data = new DataStruct();
    }

    public void setSynonymLevelConfig(SynonymLevelConfig config) {
        this.config = config;
    }

    public SynonymLevelConfig getSynonymLevelConfig() {
        return this.config;
    }

}
