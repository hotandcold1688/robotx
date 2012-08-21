/**
 * Copyright 2012 zili.dengzl. All Rights Reserved
 */
package com.macyou.robot.session;

/**
 * @author zili.dengzl
 * @time 2012-8-15 下午4:55:07
 * 
 */
public interface Session {

	public String getId();

	public long getCreateTime();

	public long getLastAccessedTime();

	public void setMaxInactiveInterval(int interval);

	public int getMaxInactiveInterval();

	public Object getAttribute(String name);

	public void setAttribute(String name, Object value);

	public void removeAttribute(String name);

	public boolean isNew();

}
