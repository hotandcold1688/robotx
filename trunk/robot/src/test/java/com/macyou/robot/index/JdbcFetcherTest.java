package com.macyou.robot.index;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.macyou.robot.common.Knowledge;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "spring/datasource.xml", "spring/index.xml" })
public class JdbcFetcherTest {
	@Autowired
	Fetcher jdbcFetcher;

	@Test
	public void test() {
		jdbcFetcher.start();
		boolean result = jdbcFetcher.hasNext();
		Assert.assertEquals(true, result);
		List<Knowledge> list = jdbcFetcher.nextPage();
		Assert.assertEquals(2, list.size());
	}
}
