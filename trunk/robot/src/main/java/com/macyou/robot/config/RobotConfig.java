package com.macyou.robot.config;

import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;

import com.macyou.robot.index.Fetcher;

/**
 * 负责机器人配置的读取和维护，每个机器人一份
 * 
 * @author zili.dengzl
 * 
 */
public class RobotConfig {
	/**
	 * 
	 */
	private String robotId;

	/**
	 * 索引文件位置
	 */
	private String indexPath;

	/**
	 * 
	 */
	private Fetcher fetcher;

	/**
	 * lucene一次查询返回top多少个文档，默认100
	 */
	private int topHitsNum = 100;

	/**
	 * lucene的search接口的排序规则，默认按评分排序
	 */
	private Sort sort = new Sort(new SortField[] { SortField.FIELD_SCORE });

	public int getTopHitsNum() {
		return topHitsNum;
	}

	public void setTopHitsNum(int topHitsNum) {
		this.topHitsNum = topHitsNum;
	}

	public Sort getSort() {
		return sort;
	}

	public void setSort(Sort sort) {
		this.sort = sort;
	}

	public String getRobotId() {
		return robotId;
	}

	public void setRobotId(String robotId) {
		this.robotId = robotId;
	}

	public String getIndexPath() {
		return indexPath;
	}

	public void setIndexPath(String indexPath) {
		this.indexPath = indexPath;
	}

	public Fetcher getFetcher() {
		return fetcher;
	}

	public void setFetcher(Fetcher fetcher) {
		this.fetcher = fetcher;
	}


}
