package com.macyou.robot.segment;

import java.util.List;
import java.util.Set;

public interface SynonymLexicon {

public Set<String> listAllGroupIds();
	
	/**
	 * 通过词获取同义词词组列表，一个词可能在多个同义词组中出现。
	 * @param word
	 * @return
	 */
	public List<SynonymGroup> getSynonymGroupsByWord(String word);
	
	/**
	 * 通过词获取同义词词组ID列表，一个词可能在多个同义词组中出现。
	 * @param word
	 * @return
	 */
	public List<String> getSynonymGroupsIdByWord(String word);
	
	/**
	 * 通过同义词组ID获取同义词词组
	 * @param groupId
	 * @return
	 */
	public SynonymGroup getSynonymGroupById(String groupId);
	
	/**
	 * 添加一组同义词。
	 * @param synonymGroup
	 */
	public void addSynonymGroup(SynonymGroup synonymGroup);	
	/**
	 * 通过同义词组ID删除一组同义词。
	 * @param groupId
	 */
	public void removeSynonymGroup(String groupId);
	/**
	 * 清除辞典中所有内容，用于重新加载。
	 */
	public void clear();
	
	/**
	 * 
	 */
	public void init();
}
