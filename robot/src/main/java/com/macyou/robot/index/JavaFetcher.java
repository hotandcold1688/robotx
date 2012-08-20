/**
 * Copyright 2012 zili.dengzl. All Rights Reserved
 */
package com.macyou.robot.index;

import java.util.ArrayList;
import java.util.List;

import com.macyou.robot.common.Knowledge;

/**
 * @author zili.dengzl
 * @time 2012-8-11 下午5:39:12
 * 
 */
public class JavaFetcher implements Fetcher {

	/**
	 * format: [indexId,question,answer]
	 */
	private String[] source = { "1|what is lucene|a serach tool", "2|who is yikela|a rich man named shaoyi",
			"3|how r u|I'm fine, thank you" };

	private int page;

	private int pageSize = 2;

	public JavaFetcher(String[] source) {
		super();
		if (null != source) {
			this.source = source;
		}
	}

	@Override
	public List<Knowledge> nextPage() {
		List<Knowledge> list = new ArrayList<Knowledge>();
		for (int i = page * pageSize; i < Math.min((page + 1) * pageSize, source.length); i++) {
			list.add(getKnowledge(source[i]));
		}
		page++;
		return list;
	}

	private Knowledge getKnowledge(String s) {
		Knowledge knowledge = new Knowledge();
		String[] ss = s.split("\\|");
		knowledge.setId(Long.valueOf(ss[0]));
		knowledge.setQuestion(ss[1]);
		knowledge.setAnswer(ss[2]);
		return knowledge;
	}

	@Override
	public boolean hasNext() {
		return page * pageSize < source.length;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	@Override
	public void start() {

	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub

	}

}
