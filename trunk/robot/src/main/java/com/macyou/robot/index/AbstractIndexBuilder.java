package com.macyou.robot.index;

import java.util.List;

import org.apache.lucene.index.IndexWriter;

import com.macyou.robot.common.Knowledge;

/**
 * @author zili.dengzl
 * 
 */
public abstract class AbstractIndexBuilder implements IndexBuilder {

	IndexWriter writer;

	Fetcher fetcher;

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
			e.printStackTrace(); //TODO: 
		}
	}

	private void buildOnePage(List<Knowledge> list) {
		for (Knowledge k : list) {
			buildOneDocument(k);
		}
	}

	protected abstract void buildOneDocument(Knowledge k);
}
