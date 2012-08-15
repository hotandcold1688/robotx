package com.macyou.robot.index;

import org.apache.lucene.search.IndexSearcher;

public interface IndexSearcherFactory {

	public IndexSearcher getIndexSearcher(String sceneId);
}
