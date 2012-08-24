package com.macyou.robot.segment;

import java.util.ArrayList;
import java.util.Collection;

public class HITGroups extends ArrayList<String>{

	private static final long serialVersionUID = -8929128550487072623L;
	
	public HITGroups(){
		super();
	}
	
	public HITGroups(Collection<String> groups){
		super(groups);
	}
	
	public boolean add(String groupID){
		if(!this.contains(groupID))
			return super.add(groupID);
		return true;
	}
	
	public String remove(String groupID){
		int index = this.indexOf(groupID);
		if(index >= 0)
			return super.remove(index);
		return null;
	}
	
	public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String word : this) {
            sb.append(word).append(" ");
        }
        return sb.toString().trim();
    }

}
