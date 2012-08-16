package com.macyou.robot;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.search.IndexSearcher;

/**
 * @author zili.dengzl
 * @time 2012-8-15 下午4:56:41
 * 
 */
public interface Robot {
	/**
	 * TODO:是否需要将answer先抽象威结构化数据返回，比如Class Answer
	 * 
	 * @param question
	 * @param sceneId
	 * @return
	 * @throws Exception
	 */
	public String answer(String question) throws Exception;
	
	
	

	public String getRobotId();

	public String getIndexPath();

	public Analyzer getAnalyzer();

	public IndexSearcher getSearcher();
}
