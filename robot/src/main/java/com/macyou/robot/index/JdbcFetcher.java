package com.macyou.robot.index;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.util.Assert;

import com.macyou.robot.common.Knowledge;
import com.macyou.robot.common.SpringBeanGetter;

/**
 * fetch data from MYSQL DB
 * 
 * <b>NOT thread safe!</b>
 * 
 * @author zili.dengzl
 * 
 */
public class JdbcFetcher implements Fetcher {
	private static final Logger logger = LoggerFactory.getLogger(JdbcFetcher.class);

	private JdbcTemplate jdbcTemplate;

	private SqlRowSet sqlRowSet;

	private int pageSize = 20;

	private String robotId;

	/**
	 * it indicate the current record is valid only, if totalSize can be MOD by pageSize, we will call nextPage one more
	 * time and get nothing
	 */
	private boolean hasNext;

	public JdbcFetcher(String robotId) {
		super();
		if (robotId == null) {
			throw new IllegalArgumentException("robotId is null");
		}
		this.robotId = robotId;
	}

	@Override
	public void start() {
		hasNext = true;
		jdbcTemplate = SpringBeanGetter.getBean("jdbcTemplate", JdbcTemplate.class);
		sqlRowSet = jdbcTemplate.queryForRowSet(getSql(), new Object[] { robotId });
	}

	@Override
	public List<Knowledge> nextPage() {
		List<Knowledge> resultList = new ArrayList<Knowledge>();
		int rowNum = 0;
		while (rowNum < pageSize && (hasNext = sqlRowSet.next())) {
			try {
				resultList.add(mapRow(sqlRowSet, rowNum));
				rowNum++;
			} catch (Exception e) {
				logger.warn("illgel record", e);
			}
		}
		return resultList;
	}

	private Knowledge mapRow(SqlRowSet rs, int rowNum) {
		Knowledge k = new Knowledge();
		k.setId(rs.getLong(Knowledge.ID));
		k.setGmtCreate(rs.getTimestamp(Knowledge.GMT_CREATE));
		k.setGmtModified(rs.getTimestamp(Knowledge.GMT_MODIFIED));
		k.setIsDeleted(rs.getString(Knowledge.IS_DELETED));
		k.setIndexId(rs.getString(Knowledge.INDEX_ID));
		k.setQuestion(rs.getString(Knowledge.QUESTION));
		k.setAnswer(rs.getString(Knowledge.ANSWER));
		k.setContentType(Knowledge.ContentType.valueOf(rs.getString(Knowledge.CONTENT_TYPE)));
		k.setRobotId(rs.getString(Knowledge.ROBOT_ID));
		return k;
	}

	@Override
	public boolean hasNext() {
		return hasNext;
	}

	private String getSql() {
		return "select * from robot_knowledge where robot_id = ? order by id";
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	@Override
	public void stop() {
		sqlRowSet = null; // help gc, avoid rs is big
	}

}
