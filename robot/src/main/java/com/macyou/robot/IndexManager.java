package com.macyou.robot;

import com.macyou.robot.index.DefaultIndexBuilderFactory;
import com.macyou.robot.index.Fetcher;
import com.macyou.robot.index.IndexBuilder;
import com.macyou.robot.index.IndexBuilderFactory;
import com.macyou.robot.index.JdbcFetcher;

/**
 * 被定时钟调用或者外部条件触发，进行某个(全部）机器人的全量(增量)的索引建立
 * 
 * @author zili.dengzl
 * 
 */
public class IndexManager {
	RobotManager robotManager;

	public void fullBuildAllRobotIndex() {
		for (Robot robot : robotManager.listAllRobot()) {
			fullBuildOneRobotIndex(robot);
		}
	}

	public void fullBuildOneRobotIndex(Robot robot) {
		try {
			IndexBuilderFactory factory = new DefaultIndexBuilderFactory(robot.getIndexPath());
			JdbcFetcher fetcher = new JdbcFetcher();
			fetcher.setRobotId(robot.getRobotId());
			factory.setFetcher(fetcher);
			factory.setAnalyzer(robot.getAnalyzer());
			IndexBuilder builder = factory.getIndexBuilder(IndexBuilderFactory.IndexType.FULL);
			builder.buildIndex();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// builder.close(); //TODO
		}

	}

	public void setRobotManager(RobotManager robotManager) {
		this.robotManager = robotManager;
	}

}
