package com.macyou.robot;

import java.io.File;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Filter;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TopFieldDocs;
import org.apache.lucene.store.FSDirectory;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.macyou.robot.common.Constants;
import com.macyou.robot.common.Knowledge;
import com.macyou.robot.common.StringUtils;
import com.macyou.robot.context.SearchContext;
import com.macyou.robot.exception.RobotCommonException;

public class SimpleRobot extends AbstractRobot{
	
	private IndexSearcher searcher;
	Analyzer analyzer  =   new  IKAnalyzer();   
	
    
    public SearchContext prepareContext(String question,String sceneId) throws Exception{
    	if(StringUtils.isEmpty(question)){
    		throw new RobotCommonException("queryAnswer error,question is null");
    	}
    	if(StringUtils.isEmpty(sceneId)){
    		throw new RobotCommonException("queryAnswer error,sceneId is null");
    	}
    	searcher=getSearcher();
    	SearchContext context=new SearchContext();
    	context.setQuestion(question);
    	context.setRobotScene(sceneId);
    	context.setSearcher(searcher);
    	context.setAnalyzer(analyzer);
    	return context;	
    }
    
	public Query getQuery(SearchContext context) throws Exception {
		Analyzer analyzer = context.getAnalyzer();
		QueryParser parser = new QueryParser(Constants.LUCENE_VERSION,Knowledge.QUESTION, analyzer);
		Query query = parser.parse(context.getQuestion());
		return query;
	}
    
    public String getAnswer(TopFieldDocs docs,IndexSearcher searcher) throws Exception {
    	 if(docs.scoreDocs.length<1){
         	return Constants.DEFAULT_ANSWER;
         }
 		Document doc = searcher.doc(docs.scoreDocs[0].doc);
 		return  doc.getFieldable(Knowledge.ANSWER).stringValue();
	}
    
    public IndexSearcher getSearcher() throws Exception{
		return getOrCreateSearch();
	}
    
	private synchronized IndexSearcher getOrCreateSearch() throws Exception {
		if (searcher == null) {
			searcher = new IndexSearcher(IndexReader.open(FSDirectory.open(new File(getIndexPath()))));
		}
		return searcher;
	}

	public String getRobotId() {
		return "robot1";
	}

	public String getIndexPath() {
		return "target/lucene/index/SimpleRobotTest/";
	}

	public String getAnswerDirectlyIfPossible(SearchContext context) {
		return null;
	}

	public String getAnswerFromCache(SearchContext context) {
		return null;
	}

	public Filter getFilter(SearchContext context) {
		return null;
	}
}

