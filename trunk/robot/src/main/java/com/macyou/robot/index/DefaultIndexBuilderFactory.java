/**
 * Copyright 2012 zili.dengzl. All Rights Reserved
 */
package com.macyou.robot.index;

import java.io.File;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.macyou.robot.common.Constants;

/**
 * @author zili.dengzl
 * @time 2012-8-11 下午5:41:14
 * 
 */
public class DefaultIndexBuilderFactory extends AbstractIndexBuilderFactory {
	private static final Logger logger = LoggerFactory.getLogger(DefaultIndexBuilderFactory.class);

	Fetcher fetcher = new JavaFetcher();

	Analyzer analyzer = new StandardAnalyzer(Constants.LUCENE_VERSION);

	public DefaultIndexBuilderFactory(String indexDir) {
		super();
		this.indexDir = indexDir;
	}

	@Override
	public IndexBuilder getIndexBuilder(IndexType type) throws Exception {
		logger.debug("start build one indexBuilder");

		Directory directory = FSDirectory.open(new File(indexDir));

		IndexWriterConfig config = new IndexWriterConfig(Constants.LUCENE_VERSION, analyzer);

		return doGetIndexBuilder(type, directory, fetcher, config);
	}

	public void setFetcher(Fetcher fetcher) {
		this.fetcher = fetcher;
	}

	public void setAnalyzer(Analyzer analyzer) {
		this.analyzer = analyzer;
	}
}
