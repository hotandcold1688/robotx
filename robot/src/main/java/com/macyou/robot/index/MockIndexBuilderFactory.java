package com.macyou.robot.index;

import java.io.File;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.macyou.robot.common.Constants;

@Deprecated
public class MockIndexBuilderFactory extends AbstractIndexBuilderFactory {
	private static final Logger logger = LoggerFactory.getLogger(IndexFullBuilder.class);

	public MockIndexBuilderFactory(String indexDir) {
		super();
		this.indexDir = indexDir;
	}

	public IndexBuilder getIndexBuilder(IndexType type) throws Exception {
		logger.debug("start build one indexBuilder");

		Directory directory = FSDirectory.open(new File(indexDir));
		Fetcher fetcher = new JavaFetcher();
		Analyzer analyzer = new StandardAnalyzer(Constants.LUCENE_VERSION);
		IndexWriterConfig config = new IndexWriterConfig(Constants.LUCENE_VERSION, analyzer);

		return doGetIndexBuilder(type, directory, fetcher, config);

	}
}
