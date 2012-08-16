package com.macyou.robot.common;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author zili.dengzl
 *
 */
public class PathHelper {
	static Map<String, String> indexPathMap = new HashMap<String, String>();
	static String DEFAULT_INDEX_DIR = "target/lucene/index/default/";
	static {
		indexPathMap.put("robot1", "target/lucene/index/robot2/");
	}

	public static String getIndexPath(String robotId) {
		String path = indexPathMap.get(robotId);
		if (null == path) {
			return DEFAULT_INDEX_DIR;
		} else {
			return path;
		}
	}
}
