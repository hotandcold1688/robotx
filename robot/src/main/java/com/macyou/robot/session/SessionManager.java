/**
 * Copyright 2012 zili.dengzl. All Rights Reserved
 */
package com.macyou.robot.session;


/**
 * @author zili.dengzl
 * @time 2012-8-15 下午4:54:51
 * 
 */
public interface SessionManager {
	static final String SESSION_COOKIE_NAME = "robotSessionId";

	/**
	 * create a session and store it
	 * 
	 * @return
	 */
	Session newSession();

	/**
	 * @param id
	 * @return
	 */
	Session getSession(String id);

}
