package com.macyou.robot;

import org.apache.lucene.document.Document;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Filter;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TopFieldDocs;

import com.macyou.robot.common.Constants;
import com.macyou.robot.common.Knowledge;
import com.macyou.robot.common.StringUtils;
import com.macyou.robot.config.RobotConfig;
import com.macyou.robot.context.SearchContext;
import com.macyou.robot.exception.RobotCommonException;

public class SimpleRobot extends AbstractRobot {

	public SimpleRobot(RobotConfig config) {
		super(config);
	}

	public SearchContext prepareContext(String question) throws Exception {
		if (StringUtils.isEmpty(question)) {
			throw new RobotCommonException("queryAnswer error,question is null");
		}
		SearchContext context = new SearchContext();
		context.setQuestion(question);
		return context;
	}

	public Query getQuery(SearchContext context) throws Exception {
		QueryParser parser = new QueryParser(Constants.LUCENE_VERSION, Knowledge.QUESTION, analyzer);
		Query query = parser.parse(context.getQuestion());
		return query;
	}

	public String getAnswer(TopFieldDocs docs) throws Exception {
		if (docs.scoreDocs.length < 1) {
			return Constants.DEFAULT_ANSWER;
		}
		Document doc = searcher.doc(docs.scoreDocs[0].doc);
		return doc.getFieldable(Knowledge.ANSWER).stringValue();
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
