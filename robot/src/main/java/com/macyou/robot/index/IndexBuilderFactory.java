package com.macyou.robot.index;

import org.apache.lucene.analysis.Analyzer;

/**
 * @author zili.dengzl
 *
 */
public interface IndexBuilderFactory {

	/**
	 * @param type
	 * @return
	 * @throws Exception
	 */
	IndexBuilder getIndexBuilder(IndexType type) throws Exception;

	/**
	 * @param fetcher
	 */
	void setFetcher(Fetcher fetcher);

	/**
	 * @param analyzer
	 */
	void setAnalyzer(Analyzer analyzer);

	static enum IndexType {
		FULL, INCREMENT;
	};
}
