package com.macyou.robot.segment;

import java.io.Serializable;

public interface SynonymGroup extends Serializable{

	public String getGroupId();
	
	public String[] getWords();
	
	public void setGroupId(String groupId);
	
	public void setWords(String[] words);
	
	public boolean contains(String word);

}
