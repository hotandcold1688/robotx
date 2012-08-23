package com.macyou.robot;

import java.io.File;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.macyou.robot.common.Constants;
import com.macyou.robot.common.Knowledge;
import com.macyou.robot.common.StringUtils;
import com.macyou.robot.config.RobotConfig;
import com.macyou.robot.context.SearchContext;
import com.macyou.robot.exception.RobotCommonException;
import com.macyou.robot.index.JavaFetcher;

/**
 * @author zili.dengzl
 * @time 2012-8-11 下午9:47:26
 * 
 */
public class SimpleRobotTest {
	static String INDEX_DIR = "target/lucene/index/SimpleRobotTest/";
	static SimpleRobotMock simpleRobot;

	@BeforeClass
	// 有对象初始化前运行一次的方法么(不要静态的)
	public static void setUp() throws Exception {
		RobotConfig config = new RobotConfig();
		config.setIndexPath(INDEX_DIR);
		JavaFetcher fetcher = new JavaFetcher(TestData.knowledges);
		config.setFetcher(fetcher);

		simpleRobot = new SimpleRobotMock(config);
		simpleRobot.start();
	}
	
	

	@Test
	public void testAnswer() throws Exception {
		Assert.assertEquals("你好", simpleRobot.answer("你好你是谁你多少了"));
		Assert.assertEquals("我是机器人小邓", simpleRobot.answer("你是谁"));
		Assert.assertEquals("56个名族", simpleRobot.answer("中国有多少个名族"));

	}

	@Test
	public void testAnswer_samilarity1() throws Exception {
		Assert.assertEquals("你好", simpleRobot.answer("你好吗？"));
	}

	@Test
	public void testAnswer_samilarity2() throws Exception {
		Assert.assertEquals("我是机器人小邓", simpleRobot.answer("你是谁啊？"));
	}

	@Test
	public void testAnswer_samilarity3() throws Exception {
		Assert.assertEquals("56个名族", simpleRobot.answer("中国名族多少个"));
	}

	@Test
	public void testAnswer_samilarity4() throws Exception {
		Assert.assertEquals("凤姐", simpleRobot.answer("宇宙超级无敌美少女是谁"));
	}

	@Test
	public void testAnswer_samilarity5() throws Exception {
		Assert.assertEquals("对不起，没找到答案", simpleRobot.answer("BHB"));
	}

	@Test
	public void testAnswer_samilarity6() throws Exception {
		Assert.assertEquals("很热啊", simpleRobot.answer("天气不错"));
	}
	
	@Test
	public void testAnswer7() throws Exception {
		Assert.assertEquals("恭喜你妹", simpleRobot.answer("恭贺你"));
	}

	static class SearchHelper {

		public static Document searchFirstDoc(String indexDir, String text) throws Exception {
			Directory directory = FSDirectory.open(new File(indexDir));
			IndexSearcher searcher = new IndexSearcher(IndexReader.open(directory));

			// Analyzer analyzer = new IKAnalyzer(true);
			QueryParser parser = new QueryParser(Constants.LUCENE_VERSION, Knowledge.QUESTION,
					simpleRobot.getAnalyzer());
			Query query = parser.parse(text);

			TopDocs docs = searcher.search(query, 1);
			Document doc = searcher.doc(docs.scoreDocs[0].doc);
			searcher.close();
			return doc;
		}

		// public static int getSearchHits(String indexDir, String filed, String text) throws CorruptIndexException,
		// IOException {
		// Directory directory = FSDirectory.open(new File(indexDir));
		// IndexSearcher searcher = new IndexSearcher(IndexReader.open(directory));
		// Query query = new TermQuery(new Term(filed, text));
		// TopDocs docs = searcher.search(query, 2);
		// return docs.totalHits;
		// }
	}
}

class SimpleRobotMock extends SimpleRobot{

	public SimpleRobotMock(RobotConfig config) {
		super(config);
	}
	
	public String answer(String question) throws Exception{
		if (StringUtils.isEmpty(question)) {
			throw new RobotCommonException("queryAnswer error,question is null");
		}
		SearchContext context = new SearchContext();
		context.setQuestion(question);
		return super.answer(context);
	}
	
}
