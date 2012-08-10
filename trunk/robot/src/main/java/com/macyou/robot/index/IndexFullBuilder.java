package com.macyou.robot.index;

import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;

import com.macyou.robot.common.Knowledge;

/**
 * @author zili.dengzl
 *
 */
public class IndexFullBuilder extends AbstractIndexBuilder {

	public IndexFullBuilder(IndexWriter writer, Fetcher fetcher) {
		super();
		this.writer = writer;
		this.fetcher = fetcher;
	}

	@Override
	protected void buildOneDocument(Knowledge knowledge) {
		try {
			writer.addDocument(DocumentHelper.toDocument(knowledge));
		} catch (CorruptIndexException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
