package com.macyou.robot.index;

import java.util.ArrayList;
import java.util.List;

import mockit.Mock;
import mockit.MockUp;

import org.apache.lucene.document.Document;
import org.junit.Assert;
import org.junit.Test;

import com.macyou.robot.common.FiledName;
import com.macyou.robot.common.Knowledge;

/**
 * @author zili.dengzl
 * 
 */
public class SimpleIndexBuilderTest {

	String INDEX_DIR = "target/lucene/index/SimpleIndexBuilderTest/";
	IndexBuilderFactory factory = new MockIndexBuilderFactory(INDEX_DIR);

	@Test
	public void testFullBuild() throws Exception {
		IndexBuilder builder = factory.getIndexBuilder(IndexType.FULL);
		builder.buildIndex();
		Document doc = SearchHelper.searchFirstDoc(INDEX_DIR, FiledName.INDEX_ID, "1");
		Assert.assertEquals("a serach tool", doc.getFieldable(FiledName.ANSWER).stringValue());
	}

	@Test
	@SuppressWarnings("unused")
	public void testIncrementBuild() throws Exception {
		IndexBuilder builder = factory.getIndexBuilder(IndexType.FULL);
		builder.buildIndex();

		new MockUp<MockFetcher>() {
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

		IndexBuilder increamentBuilder = factory.getIndexBuilder(IndexType.INCREMENT);
		increamentBuilder.buildIndex();

		Document doc = SearchHelper.searchFirstDoc(INDEX_DIR, FiledName.INDEX_ID, "1");
		Assert.assertEquals("increment build", doc.getFieldable(FiledName.ANSWER).stringValue());

	}

	@Test
	@SuppressWarnings("unused")
	public void testIncrementBuild_delete() throws Exception {
		IndexBuilder builder = factory.getIndexBuilder(IndexType.FULL);
		builder.buildIndex();

		new MockUp<MockFetcher>() {
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

		IndexBuilder increamentBuilder = factory.getIndexBuilder(IndexType.INCREMENT);
		increamentBuilder.buildIndex();

		int hits = SearchHelper.getSearchHits(INDEX_DIR, FiledName.INDEX_ID, "1");
		Assert.assertEquals(0, hits);

	}
}
