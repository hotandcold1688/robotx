package com.macyou.robot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Filter;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopFieldDocs;

import com.macyou.robot.common.Constants;
import com.macyou.robot.common.DocumentHelper;
import com.macyou.robot.common.Knowledge;
import com.macyou.robot.common.StringUtils;
import com.macyou.robot.config.RobotConfig;
import com.macyou.robot.context.SearchContext;
import com.macyou.robot.exception.RobotCommonException;
import com.macyou.robot.similarity.SimilarityCalculator;

public class SimpleRobot extends AbstractRobot {
	protected SimilarityCalculator similarityCalculator;
	protected QueryParser parser = new QueryParser(Constants.LUCENE_VERSION, Knowledge.QUESTION, analyzer);

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
		Query query = parser.parse(context.getQuestion());
		return query;
	}

	public String getAnswer(Query query, TopFieldDocs docs) throws Exception {
		if (docs.totalHits < 1) {
			return Constants.DEFAULT_ANSWER;
		}

		if (null != similarityCalculator) {
			try {
				Knowledge k = getFirstKnowledge(query, docs);
				return k.getAnswer();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		Document doc = searcher.doc(docs.scoreDocs[0].doc);
		return DocumentHelper.fromDocument(doc).getAnswer();

	}

	/**
	 * 这里只是示例一下，具体实现有待商榷，另外没有单元测试
	 */
	private Knowledge getFirstKnowledge(Query query, TopFieldDocs docs) throws Exception {
		Set<Term> queryTerms = new TreeSet<Term>();
		query.extractTerms(queryTerms); //这里用扩展Analyzer直接获得一个分词后的list是不是更好?
		Term[] queryTermArrsy = queryTerms.toArray(new Term[queryTerms.size()]);
		List<Knowledge> knowledgeList = new ArrayList<Knowledge>();
		for (ScoreDoc sd : docs.scoreDocs) {
			Document doc = searcher.doc(sd.doc);
			Knowledge k = DocumentHelper.fromDocument(doc);

			Query answerQuery = parser.parse(k.getQuestion());
			Set<Term> answerTerms = new TreeSet<Term>();
			answerQuery.extractTerms(answerTerms);
			Term[] answerTermArray = answerTerms.toArray(new Term[answerTerms.size()]);
			float similarity = similarityCalculator.calculateSimilarity(queryTermArrsy, answerTermArray);

			k.setSimilarity(similarity);
			knowledgeList.add(k);
		}

		Collections.sort(knowledgeList);

		return knowledgeList.get(0);
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
