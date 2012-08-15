package com.macyou.search;


import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TopDocs;

import com.macyou.context.SearchContext;
import com.macyou.exception.RobotCommonException;
import com.macyou.robot.common.Constants;
import com.macyou.robot.common.Knowledge;

public class DefaultAnswerSearcher implements AnswerSearcher{

	public String searchAnswer(SearchContext context) throws Exception {
		validateContext(context);
		String question  = context.getQuestion() ;   
		IndexSearcher searcher = context.getSearcher();
        Analyzer analyzer  = context.getAnalyzer();
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
				|| context.getSearcher()==null) {
			throw new RobotCommonException(
					"queryAnswer error,context validate error:" + context);
		}
	}

}
