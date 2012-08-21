package com.macyou.robot.session;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zili.dengzl
 *
 */
public class HashSessionManager extends AbstractSessionManager {
	Map<String, Session> sessionMap = new ConcurrentHashMap<String, Session>();

	@Override
	public Session getSession(String id) {
		return sessionMap.get(id);
	}

	@Override
	protected void saveSession(Session session) {
		sessionMap.put(session.getId(), session);
	}
}
