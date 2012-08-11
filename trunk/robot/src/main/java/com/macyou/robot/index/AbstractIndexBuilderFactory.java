/**
 * Copyright 2012 zili.dengzl. All Rights Reserved
 */
package com.macyou.robot.index;

import java.io.IOException;

import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.LockObtainFailedException;

/**
 * @author zili.dengzl
 * @time 2012-8-11 下午5:42:31
 * 
 */
public abstract class AbstractIndexBuilderFactory implements IndexBuilderFactory {
	protected String indexDir;

	protected IndexBuilder doGetIndexBuilder(IndexType type, Directory directory, Fetcher fetcher,
			IndexWriterConfig config) throws Exception {
		if (IndexType.FULL.equals(type)) {
			config.setOpenMode(IndexWriterConfig.OpenMode.CREATE);// recreate every time
			IndexWriter writer = new IndexWriter(directory, config);
			return new IndexFullBuilder(writer, fetcher);
		} else {
			config.setOpenMode(IndexWriterConfig.OpenMode.APPEND);
			IndexWriter writer = new IndexWriter(directory, config);
			return new IndexIncrementBuilder(writer, fetcher);
		}
	}
}
