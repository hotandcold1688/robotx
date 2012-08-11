package com.macyou.robot.common;

import java.sql.Timestamp;

/**
 * @author zili.dengzl
 * 
 */
public class Entity {

	/**
	 * 
	 */
	protected long id;
	public static final String ID = "id";

	protected Timestamp gmtCreate;
	public static final String GMT_CREATE = "gmt_create";

	protected Timestamp gmtModified;
	public static final String GMT_MODIFIED = "gmt_modified";

	/**
	 * 
	 */
	protected String isDeleted;
	public static final String IS_DELETED = "is_deleted";

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Timestamp getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Timestamp gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public Timestamp getGmtModified() {
		return gmtModified;
	}

	public void setGmtModified(Timestamp gmtModified) {
		this.gmtModified = gmtModified;
	}

	public String getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(String isDeleted) {
		this.isDeleted = isDeleted;
	}
}
