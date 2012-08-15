package com.macyou.robot.config;

import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;

/**
 * 负责机器人配置的读取和维护，每个机器人一份
 * 
 * @author zili.dengzl
 *
 */
public class RobotConfig {
	/**
	 * lucene一次查询返回top多少个文档，默认100
	 */
	private int topHitsNum = 100;

	/**
	 * lucene的search接口的排序规则，默认按评分排序
	 */
	private Sort sort = new Sort(new SortField[] { SortField.FIELD_SCORE });;

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

}
