package com.macyou.robot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class RobotManager {
	Map<String, Robot> robotMap = new HashMap<String, Robot>();

	public List<Robot> listAllRobot() {
		List<Robot> list = new ArrayList<Robot>();
		for (Entry<String, Robot> entry : robotMap.entrySet()) {
			list.add(entry.getValue());
		}
		return list;
	}
}
