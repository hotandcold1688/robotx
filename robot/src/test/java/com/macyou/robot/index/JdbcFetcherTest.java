package com.macyou.robot.index;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;

import com.macyou.robot.common.DBTestBase;
import com.macyou.robot.common.Knowledge;

/**
 * shows spring-test usage
 * 
 * test fetch data from DB use jdbcTemplate of spring
 * 
 * @author zili.dengzl
 * @time 2012-8-12 上午11:51:08
 * 
 */
@ContextConfiguration(locations = { "spring/index.xml" })
public class JdbcFetcherTest extends DBTestBase {

	@Test
	public void testNextPage_onePage() {
		JdbcFetcher jdbcFetcher = new JdbcFetcher(ROBOT_ID);
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
		JdbcFetcher jdbcFetcher = new JdbcFetcher(ROBOT_ID);
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

	@Test(expected = IllegalArgumentException.class)
	public void testNextPage_nullRobotId() {
		new JdbcFetcher(null);
	}
}
