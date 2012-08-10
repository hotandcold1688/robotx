package com.macyou.robot.index;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import com.macyou.robot.common.Knowledge;

/**
 * fetch data from MYSQL DB
 * 
 * <b>NOT thread safe!</b>
 * 
 * consider to modify to thread safe by new ResultSetExtractor every ,and change hasMore to threadLocal?
 * 
 * @author zili.dengzl
 * 
 */
public class JdbcFetcher implements Fetcher {

	JdbcTemplate jdbcTemplate;

	ResultSetExtractor<List<Knowledge>> rsExtractor;

	/**
	 * it indicate the current record is valid only, if totalSize can be MOD by pageSize, we will call nextPage one more
	 * time and get nothing
	 */
	private boolean hasNext = true;

	private int pageSize;

	@Override
	public void start() {
		rsExtractor = new PagingResultSetExtractor<Knowledge>(new KnowledgeRowMapper());
		jdbcTemplate.setFetchSize(10);
	}

	@Override
	public List<Knowledge> nextPage() {
		List<Knowledge> result = jdbcTemplate.query(getSql(), new Object[0], rsExtractor);
		// TODO:LOG
		return result;
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

	class PagingResultSetExtractor<T> implements ResultSetExtractor<List<T>> {
		private final RowMapper<T> rowMapper;

		public PagingResultSetExtractor(RowMapper<T> rowMapper) {
			super();
			this.rowMapper = rowMapper;
		}

		@Override
		public List<T> extractData(ResultSet rs) throws SQLException, DataAccessException {
			List<T> resultList = new ArrayList<T>();
			int rowNum = 0;
			while ((hasNext = rs.next()) && rowNum < pageSize) {
				resultList.add(rowMapper.mapRow(rs, rowNum));
				rowNum++;
			}
			return resultList;
		}

	}
}

class KnowledgeRowMapper implements RowMapper<Knowledge> {
	@Override
	public Knowledge mapRow(ResultSet rs, int rowNum) throws SQLException {
		Knowledge k = new Knowledge();
		try {
			k.setId(rs.getLong(Knowledge.ID));
			k.setIsDeleted(rs.getString(Knowledge.IS_DELETED));
			k.setIndexId(rs.getString(Knowledge.INDEX_ID));
			k.setQuestion(rs.getString(Knowledge.QUESTION));
			k.setAnswer(rs.getString(Knowledge.ANSWER));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return k;
	}
}
