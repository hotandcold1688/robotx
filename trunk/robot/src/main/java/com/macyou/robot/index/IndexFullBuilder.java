package com.macyou.robot.index;

import org.apache.lucene.index.IndexWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.macyou.robot.common.DocumentHelper;
import com.macyou.robot.common.Knowledge;

/**
 * @author zili.dengzl
 * 
 */
public class IndexFullBuilder extends AbstractIndexBuilder {
	private static final Logger logger = LoggerFactory.getLogger(IndexFullBuilder.class);

	public IndexFullBuilder(IndexWriter writer, Fetcher fetcher) {
		super();
		this.writer = writer;
		this.fetcher = fetcher;
	}

	@Override
	protected void buildOneDocument(Knowledge knowledge) {
		try {
			writer.addDocument(DocumentHelper.toDocument(knowledge));
		} catch (Exception e) {
			logger.error(e.getMessage()+",indexId="+knowledge.getIndexId(), e);
		}

	}
}
