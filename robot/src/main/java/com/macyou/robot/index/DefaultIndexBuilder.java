package com.macyou.robot.index;

import java.io.IOException;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.macyou.robot.common.DocumentHelper;
import com.macyou.robot.common.Knowledge;
import com.macyou.robot.exception.RobotCommonException;

/**
 * @author zili.dengzl
 * 
 */
public class DefaultIndexBuilder implements IndexBuilder {
	private static final Logger logger = LoggerFactory.getLogger(DefaultIndexBuilder.class);

	protected IndexWriter writer;

	protected Fetcher fetcher;

	public DefaultIndexBuilder(IndexWriter writer, Fetcher fetcher) {
		super();
		this.writer = writer;
		this.fetcher = fetcher;
	}

	@Override
	public void fullBuildIndex() {
		try {
			fetcher.start();

			while (fetcher.hasNext()) {
				List<Knowledge> list = fetcher.nextPage();
				for (Knowledge k : list) { // TODO: NULL handling
					addOneDocument(k);
				}
			}

			fetcher.stop();
			// writer.optimize(); // TODO: what should I use
			// writer.close();
			writer.commit();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

	}

	@Override
	public void incrementBuildIndex() {
		try {
			fetcher.start();
			while (fetcher.hasNext()) {
				List<Knowledge> list = fetcher.nextPage();
				for (Knowledge k : list) { // TODO: NULL handling
					updateOneDocument(k);
				}
			}
			fetcher.stop();
			writer.commit();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

	}

	@Override
	public void start() {
		// TODO Auto-generated method stub
	}

	@Override
	public void stop() {
		try {
			writer.close();
		} catch (Exception e) {
			throw new RobotCommonException(e);
		}

	}

	protected void addOneDocument(Knowledge k) {
		try {
			writer.addDocument(DocumentHelper.toDocument(k));
		} catch (Exception e) {
			logger.error(e.getMessage() + ",id=" + k.getId(), e);
		}
	}

	protected void updateOneDocument(Knowledge k) {
		try {

			Term term = new Term(Knowledge.ID, String.valueOf(k.getId()));
			if ("y".equalsIgnoreCase(k.getIsDeleted()))
				writer.deleteDocuments(term);
			else {
				Document doc = DocumentHelper.toDocument(k);
				if (doc != null) {
					writer.updateDocument(term, doc);
				}
			}

		} catch (CorruptIndexException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
