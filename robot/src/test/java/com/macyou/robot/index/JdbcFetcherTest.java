package com.macyou.robot.index;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.macyou.robot.common.Knowledge;

/**
 * shows spring-test usage
 * 
 * @author zili.dengzl
 * @time 2012-8-12 上午11:51:08
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "spring/datasource.xml", "spring/index.xml" })
public class JdbcFetcherTest {

	private static final String[] TEST_ID = { "9999997", "9999998", "9999999" };
	private static final Knowledge.ContentType CONTENT_TYPE = Knowledge.ContentType.text;
	private static final String ROBOT_ID = "robot_jdbc_fetcher_test";

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Autowired
	JdbcFetcher jdbcFetcher;

	@Before
	public void setUp() {
		String deleteSql = "delete from robot_knowledge where id = ? ";
		jdbcTemplate.update(deleteSql, new Object[] { TEST_ID[0] });
		jdbcTemplate.update(deleteSql, new Object[] { TEST_ID[1] });
		jdbcTemplate.update(deleteSql, new Object[] { TEST_ID[2] });

		String insertSql = "insert into robot_knowledge (id,question,answer,content_type,robot_id) VALUES ( ? , 'is_ut_funny','no',?, ? )";
		jdbcTemplate.update(insertSql, new Object[] { TEST_ID[0], CONTENT_TYPE.name(), ROBOT_ID });
		jdbcTemplate.update(insertSql, new Object[] { TEST_ID[1], CONTENT_TYPE.name(), ROBOT_ID });
		jdbcTemplate.update(insertSql, new Object[] { TEST_ID[2], CONTENT_TYPE.name(), ROBOT_ID });
	}

	@After
	public void tearDown() {
		//
	}

	
	@Test
	public void testNextPage_onePage() {
		jdbcFetcher.setRobotId(ROBOT_ID);
		jdbcFetcher.start();
		Assert.assertEquals(true, jdbcFetcher.hasNext());
		List<Knowledge> list = jdbcFetcher.nextPage();
		Assert.assertEquals(3, list.size());
		Knowledge knowledge = list.get(0);
		Assert.assertEquals(TEST_ID[0], String.valueOf(knowledge.getId()));
		Assert.assertEquals(CONTENT_TYPE, knowledge.getContentType());
		Assert.assertEquals(ROBOT_ID, knowledge.getRobotId());
	}
	
	@Test
	public void testNextPage_multiPage() {
		jdbcFetcher.setRobotId(ROBOT_ID);
		jdbcFetcher.setPageSize(2);
		jdbcFetcher.start();
		
		Assert.assertEquals(true, jdbcFetcher.hasNext());
		List<Knowledge> list = jdbcFetcher.nextPage();
		Assert.assertEquals(2, list.size());
		Assert.assertEquals(TEST_ID[0], String.valueOf(list.get(0).getId()));

		Assert.assertEquals(true, jdbcFetcher.hasNext());
		list = jdbcFetcher.nextPage();
		Assert.assertEquals(1, list.size());
		Assert.assertEquals(TEST_ID[2], String.valueOf(list.get(0).getId()));
		Assert.assertEquals(false, jdbcFetcher.hasNext());

	}
}
