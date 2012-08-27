package com.macyou.robot.segment;

import java.io.Serializable;


public class HITSynonymGroup implements Serializable{

	private static final long serialVersionUID = 5363762438606262208L;
	
	private String groupId;
	
	private String[] words;
	
	public HITSynonymGroup(String groupId, String[] array, int offset){
		this.setGroupId(groupId);
		String[] newarray = new String[array.length-offset];
		System.arraycopy(array, offset, newarray, 0, newarray.length);
		this.setWords(newarray);
	}
	
	public HITSynonymGroup(String groupId, String[] words){
		this.setGroupId(groupId);
		this.setWords(words);
	}
	
	public HITSynonymGroup(String groupId){
		this(groupId, null);
	}
	
	public HITSynonymGroup(){
		this(null, null);
	}

	public boolean contains(String word) {
		for(String term:words){
			if(term.equals(word))
				return true;
		}
		return false;
	}

	public String getGroupId() {
		return this.groupId;
	}

	public String[] getWords() {
		return this.words;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public void setWords(String[] words) {
		this.words = words;
	}
	
	public String toString(){
		StringBuffer sb = new StringBuffer();
		sb.append(this.groupId);
		sb.append(" ");
		for(String word:this.words){
			sb.append(word);
			sb.append(" ");
		}
		return sb.substring(0, sb.length()-1);
	}

}
