/**
 * Copyright 2012 zili.dengzl. All Rights Reserved
 */
package com.macyou.robot.index;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.macyou.robot.common.Constants;

/**
 * @author zili.dengzl
 * @time 2012-8-11 下午5:42:31
 * 
 */
public class DefaultIndexBuilderFactory implements IndexBuilderFactory {
	
	private static final Logger logger = LoggerFactory.getLogger(DefaultIndexBuilderFactory.class);

	protected String indexDir;

	protected Fetcher fetcher = new JavaFetcher();

	protected Analyzer analyzer = new StandardAnalyzer(Constants.LUCENE_VERSION);

	public DefaultIndexBuilderFactory(String indexDir) {
		this.indexDir = indexDir;
	}

	@Override
	public IndexBuilder getIndexBuilder(IndexType type) throws Exception {
		logger.debug("start build one indexBuilder");

		IndexWriterConfig config = new IndexWriterConfig(Constants.LUCENE_VERSION, analyzer);

		return doGetIndexBuilder(type, fetcher, config);
	}

	protected IndexBuilder doGetIndexBuilder(IndexType type, Fetcher fetcher, IndexWriterConfig config)
			throws Exception {
		if (IndexType.FULL.equals(type)) {
			config.setOpenMode(IndexWriterConfig.OpenMode.CREATE);// recreate every time
			IndexWriter writer = new IndexWriter(getIndexDirectory(), config);
			return new IndexFullBuilder(writer, fetcher);
		} else {
			config.setOpenMode(IndexWriterConfig.OpenMode.APPEND);
			IndexWriter writer = new IndexWriter(getIndexDirectory(), config);
			return new IndexIncrementBuilder(writer, fetcher);
		}
	}

	private Directory getIndexDirectory() throws IOException {
		return FSDirectory.open(new File(indexDir));
	}

	public void setFetcher(Fetcher fetcher) {
		this.fetcher = fetcher;
	}

	public void setAnalyzer(Analyzer analyzer) {
		this.analyzer = analyzer;
	}
}
