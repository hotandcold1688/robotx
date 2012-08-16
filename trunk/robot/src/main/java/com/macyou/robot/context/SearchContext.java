package com.macyou.robot.context;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.search.IndexSearcher;

public class SearchContext {

	private String robotScene;
	private String question;
	private String indexDir;  
	
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

	
}
