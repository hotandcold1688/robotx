package com.macyou.robot.common;

/**
 * @author zili.dengzl
 * 
 */
public class Entity {

	/**
	 * 
	 */
	protected long id;
	public static final String ID = "ID";

	/**
	 * 
	 */
	protected String isDeleted;
	public static final String IS_DELETED = "IS_DELETED";

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(String isDeleted) {
		this.isDeleted = isDeleted;
	}
}
