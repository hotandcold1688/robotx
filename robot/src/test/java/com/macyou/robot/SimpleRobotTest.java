package com.macyou.robot;

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
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.macyou.robot.common.Constants;
import com.macyou.robot.common.Knowledge;
import com.macyou.robot.index.DefaultIndexBuilderFactory;
import com.macyou.robot.index.IndexBuilder;
import com.macyou.robot.index.IndexBuilderFactory;
import com.macyou.robot.index.JavaFetcher;

/**
 * @author zili.dengzl
 * @time 2012-8-11 下午9:47:26
 * 
 */
public class SimpleRobotTest {

	static String INDEX_DIR = "target/lucene/index/SimpleRobotTest/";
	static DefaultIndexBuilderFactory factory = new DefaultIndexBuilderFactory(INDEX_DIR);
	static Analyzer analyzer;
	static Robot simpleRobot;
	//	static Analyzer analyzer = new IKAnalyzer();

	@BeforeClass
	//有对象初始化前运行一次的方法么(不要静态的)
	public static void setUp() throws Exception {
		analyzer = new IKAnalyzer(true);
		// analyzer = new IKAnalyzer();
		// analyzer = new StandardAnalyzer(Constants.LUCENE_VERSION, new
		// FileReader("target/classes/stopword.dic"));

		factory.setAnalyzer(analyzer);
		JavaFetcher fetcher = new JavaFetcher();
		fetcher.setSource(TestData.knowledges);
		factory.setFetcher(fetcher);

		IndexBuilder builder = factory
				.getIndexBuilder(IndexBuilderFactory.IndexType.FULL);
		builder.buildIndex();
		ApplicationContext ctx = new ClassPathXmlApplicationContext("/com/macyou/robot/index/spring/index.xml");
		simpleRobot = (Robot) ctx.getBean("simpleRobot");
	}
	
	@Test
	public void testAnswer() throws Exception {
		Assert.assertEquals("你好", simpleRobot.answer("你好", "test"));
		Assert.assertEquals("我是机器人小邓", simpleRobot.answer("你是谁", "test"));
		Assert.assertEquals("56个名族", simpleRobot.answer("中国有多少个名族", "test"));

	}


	@Test
	public void testAnswer_samilarity1() throws Exception {
		Assert.assertEquals("你好", simpleRobot.answer("你好吗？", "test"));
	}
	
	@Test
	public void testAnswer_samilarity2() throws Exception {
		Assert.assertEquals("我是机器人小邓", simpleRobot.answer("你是谁啊？", "test"));
	}

	@Test
	public void testAnswer_samilarity3() throws Exception {
		Assert.assertEquals("56个名族", simpleRobot.answer("中国名族多少个", "test"));
	}
	
	@Test
	public void testAnswer_samilarity4() throws Exception {
		Assert.assertEquals("凤姐", simpleRobot.answer("宇宙超级无敌美少女是谁", "test"));
	}
	
	@Test
	public void testAnswer_samilarity5() throws Exception {
		Assert.assertEquals("对不起，没找到答案", simpleRobot.answer("BHB", "test"));
	}

	static class SearchHelper {

		public static Document searchFirstDoc(String indexDir, String text) throws Exception {
			Directory directory = FSDirectory.open(new File(indexDir));
			IndexSearcher searcher = new IndexSearcher(IndexReader.open(directory));

			//Analyzer analyzer = new IKAnalyzer(true);
			QueryParser parser = new QueryParser(Constants.LUCENE_VERSION, Knowledge.QUESTION, analyzer);
			Query query = parser.parse(text);

 			TopDocs docs = searcher.search(query, 1);
			Document doc = searcher.doc(docs.scoreDocs[0].doc);
			searcher.close();
			return doc;
		}

		//	public static int getSearchHits(String indexDir, String filed, String text) throws CorruptIndexException,
		//			IOException {
		//		Directory directory = FSDirectory.open(new File(indexDir));
		//		IndexSearcher searcher = new IndexSearcher(IndexReader.open(directory));
		//		Query query = new TermQuery(new Term(filed, text));
		//		TopDocs docs = searcher.search(query, 2);
		//		return docs.totalHits;
		//	}
	}
}
