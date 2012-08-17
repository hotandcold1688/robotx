package com.macyou.robot.index;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import mockit.Mock;
import mockit.MockUp;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.FSDirectory;
import org.junit.Assert;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.macyou.robot.common.Constants;
import com.macyou.robot.common.Knowledge;

/**
 * @author zili.dengzl
 * 
 */
public class IndexBuilderTest {

	static String INDEX_DIR = "target/lucene/index/SimpleIndexBuilderTest/";
	static IndexBuilder indexBuilder;
	static {
		try {
			Analyzer analyzer = new IKAnalyzer();
			IndexWriterConfig indexConfig = new IndexWriterConfig(Constants.LUCENE_VERSION, analyzer);
			indexConfig.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);// recreate every time;
			File indexDir = new File(INDEX_DIR);
			Fetcher fetcher = new JavaFetcher(null);
			IndexWriter writer = new IndexWriter(FSDirectory.open(indexDir), indexConfig);
			indexBuilder = new DefaultIndexBuilder(writer, fetcher);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test
	public void testFullBuild() throws Exception {
		indexBuilder.fullBuildIndex();
		Document doc = SearchHelper.searchFirstDoc(INDEX_DIR, Knowledge.INDEX_ID, "1");
		Assert.assertEquals("a serach tool", doc.getFieldable(Knowledge.ANSWER).stringValue());
		// indexBuilder.stop();
	}

	@Test
	@SuppressWarnings("unused")
	public void testIncrementBuild() throws Exception {
		indexBuilder.fullBuildIndex();

		new MockUp<JavaFetcher>() {
			int count;

			@Mock
			public List<Knowledge> nextPage() {
				List<Knowledge> list = new ArrayList<Knowledge>();
				Knowledge k = new Knowledge();
				k.setIndexId("1");
				k.setQuestion("what's this");
				k.setAnswer("increment build");
				list.add(k);
				count++;
				return list;
			}

			@Mock
			public boolean hasNext() {
				return count == 0;
			}
		};

		indexBuilder.incrementBuildIndex();
		Document doc = SearchHelper.searchFirstDoc(INDEX_DIR, Knowledge.INDEX_ID, "1");
		Assert.assertEquals("increment build", doc.getFieldable(Knowledge.ANSWER).stringValue());
		// indexBuilder.stop();
	}

	@Test
	@SuppressWarnings("unused")
	public void testIncrementBuild_delete() throws Exception {
		indexBuilder.fullBuildIndex();
		new MockUp<JavaFetcher>() {
			int count;

			@Mock
			public List<Knowledge> nextPage() {
				List<Knowledge> list = new ArrayList<Knowledge>();
				Knowledge k = new Knowledge();
				k.setIndexId("1");
				k.setIsDeleted("y");
				list.add(k);
				count++;
				return list;
			}

			@Mock
			public boolean hasNext() {
				return count == 0;
			}
		};

		indexBuilder.incrementBuildIndex();
		int hits = SearchHelper.getSearchHits(INDEX_DIR, Knowledge.INDEX_ID, "1");
		Assert.assertEquals(0, hits);

	}
}
