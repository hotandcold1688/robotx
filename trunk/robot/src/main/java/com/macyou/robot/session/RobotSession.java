package com.macyou.robot.session;

import java.sql.Timestamp;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zili.dengzl
 * 
 */
public class RobotSession implements Session {

	private String id;
	/**
	 * 单位:秒
	 */
	private int maxInactiveInterval =  60;
	private Timestamp createTime;
	private Timestamp lastAccessTime;
	private Map<String, Object> valueMap = new ConcurrentHashMap<String, Object>();

	public RobotSession(String id, Timestamp createTime, Timestamp lastAccessTime) {
		super();
		this.id = id;
		this.createTime = createTime;
		this.lastAccessTime = lastAccessTime;
	}

	@Override
	public String getId() {

		return id;
	}

	@Override
	public Timestamp getCreateTime() {
		return createTime;
	}

	@Override
	public Timestamp getLastAccessedTime() {
		synchronized (this) { // 是否真的需要同步
			return lastAccessTime;
		}
	}

	@Override
	public void setLastAccessedTime(Timestamp time) {
		synchronized (this) {
			lastAccessTime = time;
		}
	}

	@Override
	public int getMaxInactiveInterval() {
		return maxInactiveInterval;
	}

	@Override
	public void setMaxInactiveInterval(int interval) {
		maxInactiveInterval = interval;
	}

	@Override
	public Object getAttribute(String name) {
		return valueMap.get(name);
	}

	@Override
	public void setAttribute(String name, Object value) {
		valueMap.put(name, value);
	}

	@Override
	public void removeAttribute(String name) {
		valueMap.remove(name);
	}

}
