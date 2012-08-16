package com.macyou.robot;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.search.IndexSearcher;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.macyou.context.SearchContext;
import com.macyou.exception.RobotCommonException;
import com.macyou.robot.common.StringUtils;
import com.macyou.robot.index.IndexSearcherFactory;
import com.macyou.search.AnswerSearcher;

public class SimpleRobot implements Robot {

	private IndexSearcherFactory indexSearcherFactory;
	private AnswerSearcher defaultAnswerSearcher;
	private IndexSearcher searcher;
	Analyzer analyzer = new IKAnalyzer();

	public String answer(String question, String sceneId) throws Exception {
		SearchContext context = prepareContext(question, sceneId);
		return defaultAnswerSearcher.searchAnswer(context);
	}

	private SearchContext prepareContext(String question, String sceneId) {
		if (StringUtils.isEmpty(question)) {
			throw new RobotCommonException("queryAnswer error,question is null");
		}
		if (StringUtils.isEmpty(sceneId)) {
			throw new RobotCommonException("queryAnswer error,sceneId is null");
		}
		//获取索引地址
		searcher = indexSearcherFactory.getIndexSearcher(sceneId);
		SearchContext context = new SearchContext();
		context.setQuestion(question);
		context.setRobotScene(sceneId);
		context.setSearcher(searcher);
		context.setAnalyzer(analyzer);
		return context;
	}

	public void setIndexSearcherFactory(IndexSearcherFactory indexSearcherFactory) {
		this.indexSearcherFactory = indexSearcherFactory;
	}

	public void setDefaultAnswerSearcher(AnswerSearcher defaultAnswerSearcher) {
		this.defaultAnswerSearcher = defaultAnswerSearcher;
	}

	@Override
	public String getRobotId() {
		// TODO Auto-generated method stub
		return "robot1";
	}

	@Override
	public String getIndexPath() {
		// TODO Auto-generated method stub
		return "target/lucene/index/robot1/";
	}

}
