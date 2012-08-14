package com.macyou.robot.index;

public class DefaultIndexDirFactory implements IndexDirFactory{

	public String getIndexDir(String sceneId) {
		//默认返回写死。。
		return "target/lucene/index/SimpleRobotTest/";
	}

}
