/**
 * Copyright 2012 zili.dengzl. All Rights Reserved
 */
package com.macyou.robot;

import java.util.List;

import mockit.Mock;
import mockit.MockUp;

import org.apache.lucene.document.Document;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.macyou.robot.common.DBTestBase;
import com.macyou.robot.common.Knowledge;
import com.macyou.robot.index.JavaFetcher;
import com.macyou.robot.index.SearchHelper;

/**
 * @author zili.dengzl
 * @time 2012-8-16 上午11:11:22
 * 
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "index/spring/index.xml" })
public class IndexManagerTest extends DBTestBase {

	String INDEX_DIR = "target/lucene/index/robot1/";

	@Autowired
	IndexManager indexManager;

	@Test
	public void tesFfullBuildAllRobotIndex() throws Exception {

		new MockUp<SimpleRobot>() {
			@Mock
			public String getRobotId() {
				return ROBOT_ID;
			}
		};
		indexManager.fullBuildAllRobotIndex();
		Document doc = SearchHelper.searchFirstDoc(INDEX_DIR, Knowledge.QUESTION, QUESTIONS[0]);
		Assert.assertEquals(ANSWERS[0], doc.getFieldable(Knowledge.ANSWER).stringValue());

	}
}
