/**
 * Copyright 2012 zili.dengzl. All Rights Reserved
 */
package com.macyou.robot.session;

import java.sql.Timestamp;

/**
 * @author zili.dengzl
 * @time 2012-8-15 下午4:55:07
 * 
 */
public interface Session {

	public String getId();

	public Timestamp getCreateTime();

	public Timestamp getLastAccessedTime();

	public void setLastAccessedTime(Timestamp time);

	public int getMaxInactiveInterval();

	public void setMaxInactiveInterval(int interval);

	public Object getAttribute(String name);

	public void setAttribute(String name, Object value);

	public void removeAttribute(String name);

}
