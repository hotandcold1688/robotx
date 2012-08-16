/**
 * Copyright 2012 zili.dengzl. All Rights Reserved
 */
package com.macyou.robot;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author zili.dengzl
 * @time 2012-8-16 上午11:11:22
 * 
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "index/spring/datasource.xml", "index/spring/index.xml" })
public class IndexManagerTest {
	@Autowired
	IndexManager indexManager;

	@Test
	public void tesFfullBuildAllRobotIndex() {
		indexManager.fullBuildAllRobotIndex();
	}
}
