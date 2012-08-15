package com.macyou.context;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.search.IndexSearcher;

public class SearchContext {

	private String robotScene;
	private String question;
	private String indexDir;
	private IndexSearcher searcher;
	private Analyzer analyzer;   
	
	public String getRobotScene() {
		return robotScene;
	}
	public void setRobotScene(String robotScene) {
		this.robotScene = robotScene;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getIndexDir() {
		return indexDir;
	}
	public void setIndexDir(String indexDir) {
		this.indexDir = indexDir;
	}
	public IndexSearcher getSearcher() {
		return searcher;
	}
	public void setSearcher(IndexSearcher searcher) {
		this.searcher = searcher;
	}
	public Analyzer getAnalyzer() {
		return analyzer;
	}
	public void setAnalyzer(Analyzer analyzer) {
		this.analyzer = analyzer;
	}
	
}
