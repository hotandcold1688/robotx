/**
 * Copyright 2012 zili.dengzl. All Rights Reserved
 */
package com.macyou.robot;

import org.apache.lucene.search.Filter;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TopFieldDocs;

import com.macyou.context.SearchContext;
import com.macyou.robot.config.RobotConfig;

/**
 * @author zili.dengzl
 * @time 2012-8-15 下午4:41:55
 * 
 */
public abstract class AbstractRobot implements Robot {

	RobotConfig config;
	
//	SessionManager sessionManager;

	@Override
	public String answer(String question, String sceneId) throws Exception {
        //准备查询上下文，包括session的处理
		SearchContext context = prepareContext(question, sceneId);

		//尝试从cache中获取结果
		String answer = getAnswerFromCache(context);
		if (answer != null) {
			return answer;
		}
		//尝试通过indexId直接获取结果
		answer = getAnswerDirectlyIfPossible(context);
		if (answer != null) {
			return answer;
		}
		// 分词,获取查询项
		Query query = getQuery(context);
		// 获取filter
		Filter filter = getFilter(context);
		// 通过lucene搜索
		TopFieldDocs docs = context.getSearcher().search(query, filter, 0, null);
		// 计算相似度,拼装结果
		answer = getAnswer(docs);

		return answer;
	}

	/**
	 * @param context
	 * @return
	 */
	protected abstract String getAnswerFromCache(SearchContext context);

	/**
	 * 利用indexId这样的参数直接查询结果
	 * 
	 * @param context
	 * @return
	 */
	protected abstract String getAnswerDirectlyIfPossible(SearchContext context);

	/**
	 * 1.计算相似度 2.选择最佳答案,拼装返回结果
	 * 
	 * @param docs
	 * @return
	 */
	protected abstract String getAnswer(TopFieldDocs docs);

	/**
	 * @param context
	 * @return
	 */
	protected abstract Filter getFilter(SearchContext context);

	/**
	 * @param context
	 * @return
	 */
	protected abstract Query getQuery(SearchContext context);

	/**
	 * 准备查询上下文，包括session的处理
	 * 
	 * @param question
	 * @param sceneId
	 * @return
	 */
	protected abstract SearchContext prepareContext(String question, String sceneId);

}
