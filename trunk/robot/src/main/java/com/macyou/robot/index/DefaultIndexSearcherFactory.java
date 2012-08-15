package com.macyou.robot.index;

import java.io.File;

import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.FSDirectory;

public class DefaultIndexSearcherFactory implements IndexSearcherFactory{
	
	private IndexSearcher searcher;
	
	public void init() throws Exception{
		searcher= new IndexSearcher(IndexReader.open(FSDirectory.open(new File("target/lucene/index/SimpleRobotTest/"))));

	}
	
	
	
	public IndexSearcher getIndexSearcher(String sceneId) {
		return searcher;
	}
	
	
	
	
	public void setSearcher(IndexSearcher searcher) {
		this.searcher = searcher;
	}
	
	

}
