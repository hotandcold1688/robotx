package com.macyou.robot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import com.macyou.robot.config.RobotConfig;
import com.macyou.robot.index.Fetcher;
import com.macyou.robot.index.JavaFetcher;
import com.macyou.robot.index.JdbcFetcher;
import com.macyou.robot.lifecycle.Lifecycle;
import com.macyou.web.SimpleData;

/**
 * robot 的初始化发生在服务启动，或者加入新的robot的时候,get的过程中不触发robot的初始化
 * 
 * 1.robot的容器 2.调用robot的start
 * 
 * @author zili.dengzl
 * @time 2012-8-16 上午11:16:17
 * 
 */
public class RobotManager implements Lifecycle {
	static final String INDEX_DIR = "target/lucene/index/robot/";
	Map<String, Robot> robotMap = new ConcurrentHashMap<String, Robot>();

	public List<Robot> listAllRobot() {
		List<Robot> list = new ArrayList<Robot>();
		for (Entry<String, Robot> entry : robotMap.entrySet()) {
			list.add(entry.getValue());
		}
		return list;
	}

	public synchronized Robot createRobot(String robotId) {
		// TODO:recreate logic
		// TODO : get info from DB,reflect create robot
		RobotConfig config = new RobotConfig();
		config.setIndexPath(INDEX_DIR + robotId);
		JavaFetcher fetcher = new JavaFetcher(SimpleData.knowledges);
		//Fetcher fetcher = new JdbcFetcher(robotId);
		config.setFetcher(fetcher);
		Robot robot = new SimpleRobot(config);
		robot.start();
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

	@Override
	public void start() {
		for (Robot robot : listAllRobot()) {
			robot.start();
		}
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
	}

}
