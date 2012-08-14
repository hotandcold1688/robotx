package com.macyou.robot.common;

import org.apache.lucene.util.Version;

/**
 * @author zili.dengzl
 *
 */
public interface Constants {
	/**
	 * 
	 */
	Version LUCENE_VERSION = Version.LUCENE_36;
	
	
	public static String DEFAULT_NULL_ANSWER= "请重新输入问题，亲";
	
	public static String DEFAULT_ANSWER= "对不起，没找到答案";
}
