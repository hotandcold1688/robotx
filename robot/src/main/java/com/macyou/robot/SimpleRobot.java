package com.macyou.robot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang.StringUtils;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Filter;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopFieldDocs;
import org.apache.lucene.search.BooleanClause.Occur;

import com.macyou.robot.common.Constants;
import com.macyou.robot.common.DocumentHelper;
import com.macyou.robot.common.Knowledge;
import com.macyou.robot.config.RobotConfig;
import com.macyou.robot.context.SearchContext;
import com.macyou.robot.segment.HITSynonymGroup;
import com.macyou.robot.segment.HITSynonymLexicon;
import com.macyou.robot.segment.SynonymLexicon;
import com.macyou.robot.similarity.SimilarityCalculator;
import com.macyou.robot.util.WordsSimpleConverter;

/**
 * @author BHB
 */
public class SimpleRobot extends AbstractRobot {
	protected SimilarityCalculator similarityCalculator;
	protected QueryParser parser;
    private SynonymLexicon synonym =new HITSynonymLexicon();
    private WordsSimpleConverter convertUtil;
    
	public SimpleRobot(RobotConfig config) {
		super(config);
	}


	public Query getQuery(SearchContext context) throws Exception {
		String question=context.getQuestion();
		//全角转半角,繁体转简体
		 question = convertUtil.convert(question);
		 //先分一下词
		 Query tempQuery=parser.parse(question);
		 String[]  words=tempQuery.toString().split("question:");
		 List<String> searchWords=new ArrayList<String>();
        for (String queryWord : words) {
            if (StringUtils.isEmpty(queryWord)) {
                continue;
            }
            searchWords.add(queryWord.trim());
        }
		 BooleanQuery query = new BooleanQuery();
		 Set<String> addedTerms = new HashSet<String>();
		  for (String word : searchWords) {
	            if (StringUtils.isEmpty(word))
	                continue;
	            //处理同义词
	            handleSynonymLexicon(synonym, query,word,addedTerms);
	        }
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
		query.extractTerms(queryTerms); // 这里用扩展Analyzer直接获得一个分词后的list是不是更好?
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
	
	private static void handleSynonymLexicon(SynonymLexicon synonymLexicon,BooleanQuery query, String word,Set<String> addedTerms) {
		String field=Knowledge.QUESTION;
        //TODO 这里的逻辑有点问题else之后synonymLexicon一样可能为null
		if (synonymLexicon == null && !addedTerms.contains(word)) {// 1.1词直接加入lucene的查询
			TermQuery tq = new TermQuery(new Term(field, word));
			query.add(tq, Occur.SHOULD);
			addedTerms.add(word);
		} else {
			List<String> groups = synonymLexicon.getSynonymGroupsIdByWord(word);
			if (groups == null || groups.isEmpty()) {
				if (!addedTerms.contains(word)) {
					TermQuery tq = new TermQuery(new Term(field, word));
					query.add(tq, Occur.SHOULD);
					addedTerms.add(word);
				}
			} else {
				BooleanQuery posQuery = new BooleanQuery();
                for (String groupId : groups) {
                	HITSynonymGroup group = synonymLexicon.getSynonymGroupById(groupId);
                    String[] words = group.getWords();
                    for (String synonymWord : words) {
                        TermQuery tq = new TermQuery(new Term(field, synonymWord));
                        posQuery.add(tq, Occur.SHOULD);
                    }
                }
				query.add(posQuery, Occur.SHOULD);
			}
		}
	}
	

	protected void doStart() {
		parser = new QueryParser(Constants.LUCENE_VERSION, Knowledge.QUESTION, analyzer);
		synonym.init();
		convertUtil=new WordsSimpleConverter("simple_conplex.txt");
		// similarityCalculator = null;
	}

	
	public static void main(String[] args) throws Exception{
	String question="恭喜你是中国的相关牵连";
	SimpleRobot robot=new SimpleRobot(new RobotConfig());
	robot.start();
	SearchContext context=new SearchContext();
	context.setQuestion(question);
	robot.getQuery(context);
	}
}
