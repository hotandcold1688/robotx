package com.macyou.robot.index;

import org.apache.lucene.document.Document;
import org.junit.Assert;
import org.junit.Test;

import com.macyou.robot.common.FiledName;

/**
 * @author zili.dengzl
 *
 */
public class SimpleIndexBuilderTest {

	String INDEX_DIR = "target/lucene/index/SimpleIndexBuilderTest/";;

	@Test
	public void testFullBuild() throws Exception {
		IndexBuilderFactory factory = new MockIndexBuilderFactory(INDEX_DIR);
		IndexBuilder builder = factory.getIndexBuilder(IndexType.FULL);
		builder.buildIndex();
		Document doc = TestHelper.searchFirstDoc(INDEX_DIR, FiledName.INDEX_ID, "1");
		Assert.assertEquals("a serach tool", doc.getFieldable(FiledName.ANSWER).stringValue());
	}

//	@Test
//	public void testIncrementBuild() throws Exception {
//		IndexBuilderFactory factory = new MockIndexBuilderFactory(INDEX_DIR);
//		IndexBuilder builder = factory.getIndexBuilder(IndexType.FULL);
//		builder.buildIndex();
//
//		IndexBuilder increamentBuilder = factory.getIndexBuilder(IndexType.INCREMENT);
//		increamentBuilder.buildIndex();
//	}

}
