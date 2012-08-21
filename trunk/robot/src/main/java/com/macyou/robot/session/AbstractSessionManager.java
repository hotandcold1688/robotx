package com.macyou.robot.session;

/**
 * @author zili.dengzl
 * 
 */
public abstract class AbstractSessionManager implements SessionManager {

	@Override
	public Session newSession() {
//		Session session = new RobotSession();
//		saveSession(session);
//		return session;
		return null;
	}

	protected abstract void saveSession(Session session);

}
