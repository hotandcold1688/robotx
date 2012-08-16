package com.macyou.robot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

/**
 * robot 的初始化发生在服务启动，或者加入新的robot的时候,get的过程中不触发robot的初始化
 * 
 * 
 * @author zili.dengzl
 * @time 2012-8-16 上午11:16:17
 * 
 */
public class RobotManager {
	ConcurrentHashMap<String, Robot> robotMap;

	public void init() {
//		createRobot("robot1", true);
	}

	public List<Robot> listAllRobot() {
		List<Robot> list = new ArrayList<Robot>();
		for (Entry<String, Robot> entry : robotMap.entrySet()) {
			list.add(entry.getValue());
		}
		return list;
	}

	public synchronized Robot createRobot(String robotId, boolean reCreate) {
		//TODO:recreate logic
		//TODO : get info from DB,reflect create robot
		Robot robot = new SimpleRobot();
		robotMap.put(robotId, robot);
		return robot;
	}

	public Robot getRobot(String robotId) {
		Robot robot = robotMap.get(robotId);
		if (null == robot) {
			robot = robotMap.get("defaultRobot");
		}
		return robot;
	}

	public void setRobotMap(ConcurrentHashMap<String, Robot> robotMap) {
		this.robotMap = robotMap;
	}
	
	
}
