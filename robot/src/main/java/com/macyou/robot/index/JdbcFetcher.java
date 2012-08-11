package com.macyou.robot.index;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.macyou.robot.common.Knowledge;

/**
 * fetch data from MYSQL DB
 * 
 * <b>NOT thread safe!</b>  
 * 
 * @author zili.dengzl
 * 
 */
public class JdbcFetcher implements Fetcher {

	JdbcTemplate jdbcTemplate;

	SqlRowSet sqlRowSet;

	/**
	 * it indicate the current record is valid only, if totalSize can be MOD by pageSize, we will call nextPage one more
	 * time and get nothing
	 */
	private boolean hasNext = true;

	private int pageSize = 20;

	@Override
	public void start() {
		sqlRowSet = jdbcTemplate.queryForRowSet(getSql(), new Object[] {});
	}

	@Override
	public List<Knowledge> nextPage() {
		List<Knowledge> resultList = new ArrayList<Knowledge>();
		int rowNum = 0;
		while ((hasNext = sqlRowSet.next()) && rowNum < pageSize) {
			resultList.add(mapRow(sqlRowSet, rowNum));
			rowNum++;
		}
		return resultList;
	}

	private Knowledge mapRow(SqlRowSet rs, int rowNum) {
		Knowledge k = new Knowledge();
		k.setId(rs.getLong(Knowledge.ID));
		k.setIsDeleted(rs.getString(Knowledge.IS_DELETED));
		k.setIndexId(rs.getString(Knowledge.INDEX_ID));
		k.setQuestion(rs.getString(Knowledge.QUESTION));
		k.setAnswer(rs.getString(Knowledge.ANSWER));
		return k;
	}

	@Override
	public boolean hasNext() {
		return hasNext;
	}

	@Override
	public void end() {
	}

	private String getSql() {
		return "select * from robot_knowledge";
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

}
