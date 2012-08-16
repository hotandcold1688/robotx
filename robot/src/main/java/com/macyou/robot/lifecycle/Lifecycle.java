package com.macyou.robot.lifecycle;

/**
 * 
 * @author zili.dengzl
 * 
 */
public interface Lifecycle {
	/**
	 * throw RuntimeException if necessary
	 */
	void start();

	/**
	 * throw RuntimeException if necessary
	 */
	void stop();
}
