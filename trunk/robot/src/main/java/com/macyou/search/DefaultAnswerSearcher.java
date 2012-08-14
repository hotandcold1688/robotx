package com.macyou.search;


import java.io.File;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.macyou.context.SearchContext;
import com.macyou.exception.RobotCommonException;
import com.macyou.robot.common.Constants;
import com.macyou.robot.common.Knowledge;
import com.macyou.robot.common.StringUtils;

public class DefaultAnswerSearcher implements AnswerSearcher{

	public String searchAnswer(SearchContext context) throws Exception {
		validateContext(context);
		String question  = context.getQuestion() ;   
		String indexDir=context.getIndexDir();
        Directory directory = FSDirectory.open(new File(indexDir));
		IndexSearcher searcher = new IndexSearcher(IndexReader.open(directory));
        Analyzer analyzer  =   new  IKAnalyzer();   
        QueryParser parser = new QueryParser(Constants.LUCENE_VERSION, Knowledge.QUESTION, analyzer);
		Query query = parser.parse(question);
        TopDocs docs = searcher.search(query, 1);
        if(docs.scoreDocs.length<1){
        	return Constants.DEFAULT_ANSWER;
        }
		Document doc = searcher.doc(docs.scoreDocs[0].doc);
		searcher.close();
		
		return  doc.getFieldable(Knowledge.ANSWER).stringValue();
	}
	
	private void validateContext(SearchContext context) {
		if (context == null || context.getQuestion() == null
				|| StringUtils.isEmpty(context.getIndexDir())) {
			throw new RobotCommonException(
					"queryAnswer error,context validate error:" + context);
		}
	}

}
