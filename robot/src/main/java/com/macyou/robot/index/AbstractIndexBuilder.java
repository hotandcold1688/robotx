package com.macyou.robot.index;

import java.util.List;

import org.apache.lucene.index.IndexWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.macyou.robot.common.Knowledge;

/**
 * @author zili.dengzl
 * 
 */
public abstract class AbstractIndexBuilder implements IndexBuilder {
	private static final Logger logger = LoggerFactory.getLogger(AbstractIndexBuilder.class);

	protected IndexWriter writer;

	protected Fetcher fetcher;

	@Override
	public void buildIndex() {
		try {
			fetcher.start();

			while (fetcher.hasNext()) {
				List<Knowledge> list = fetcher.nextPage();
				buildOnePage(list);
			}

			fetcher.end();
			writer.optimize(); // TODO: what should I use
			writer.close();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	private void buildOnePage(List<Knowledge> list) {
		for (Knowledge k : list) { //TODO: NULL handling
			buildOneDocument(k);
		}
	}

	protected abstract void buildOneDocument(Knowledge k);
}
