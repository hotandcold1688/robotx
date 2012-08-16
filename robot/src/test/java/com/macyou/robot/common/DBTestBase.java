/**
 * Copyright 2012 zili.dengzl. All Rights Reserved
 */
package com.macyou.robot.common;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author zili.dengzl
 * @time 2012-8-16 下午1:36:58
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "spring/datasource.xml" })
public class DBTestBase {
	protected static final String[] TEST_ID = { "9999997", "9999998", "9999999" };
	protected static final String[] QUESTIONS = { "is_ut_funny", "单元测试你好", "how old r u" };
	protected static final String[] ANSWERS = { "no", "码农你好", "two years old" };
	protected static final Knowledge.ContentType CONTENT_TYPE = Knowledge.ContentType.text;
	protected static final String ROBOT_ID = "robot_jdbc_fetcher_test";

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Before
	public void setUp() {
		String deleteSql = "delete from robot_knowledge where id = ? ";
		jdbcTemplate.update(deleteSql, new Object[] { TEST_ID[0] });
		jdbcTemplate.update(deleteSql, new Object[] { TEST_ID[1] });
		jdbcTemplate.update(deleteSql, new Object[] { TEST_ID[2] });

		String insertSql = "insert into robot_knowledge (id,gmt_create, question,answer,content_type,robot_id) VALUES ( ? , sysdate(), ? ,? ,?, ? )";
		jdbcTemplate.update(insertSql, new Object[] { TEST_ID[0], QUESTIONS[0], ANSWERS[0], CONTENT_TYPE.name(),
				ROBOT_ID });
		jdbcTemplate.update(insertSql, new Object[] { TEST_ID[1], QUESTIONS[1], ANSWERS[1], CONTENT_TYPE.name(),
				ROBOT_ID });
		jdbcTemplate.update(insertSql, new Object[] { TEST_ID[2], QUESTIONS[2], ANSWERS[2], CONTENT_TYPE.name(),
				ROBOT_ID });
	}

}
