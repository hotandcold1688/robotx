package com.macyou.robot.index;

import com.macyou.robot.lifecycle.Lifecycle;

/**
 * @author zili.dengzl
 * 
 */
public interface IndexBuilder extends Lifecycle {

	/**
	 * full build index
	 */
	void fullBuildIndex();

	/**
	 * increment build index
	 */
	void incrementBuildIndex();
}
