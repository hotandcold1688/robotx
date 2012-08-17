package lucene.test;

import java.io.File;

import junit.framework.Assert;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;

import com.macyou.robot.common.Constants;

/**
 * 一些lucene基本用法測試，重复代码未抽象
 * 
 * @author zili.dengzl
 *
 */
public class IndexWriterTest {

	/**
	 * 两次add同一个doc，会有两条记录
	 * 
	 * @throws Exception
	 */
	@Test
	public void testWriter_addTheSameDocAfterCommit() throws Exception {
		String indexDir = "target/lucene/index/IndexWriterTest/";
		FSDirectory dir = FSDirectory.open(new File(indexDir));
		Analyzer analyzer = new StandardAnalyzer(Constants.LUCENE_VERSION);
		IndexWriterConfig config = new IndexWriterConfig(Constants.LUCENE_VERSION, analyzer);
		config.setOpenMode(IndexWriterConfig.OpenMode.CREATE);// recreate every time

		IndexWriter writer = new IndexWriter(dir, config);
		// IndexReader reader = IndexReader.open(writer, false);
		// IndexSearcher searcher = new IndexSearcher(reader);

		Document doc = new Document();
		doc.add(new Field("id", "1", Field.Store.YES, Field.Index.NOT_ANALYZED));
		doc.add(new Field("content", "test1", Field.Store.YES, Field.Index.NO));
		writer.addDocument(doc);
		writer.commit();

		doc = new Document();
		doc.add(new Field("id", "1", Field.Store.YES, Field.Index.NOT_ANALYZED));
		doc.add(new Field("content", "test2", Field.Store.YES, Field.Index.NO));
		writer.addDocument(doc);
		writer.commit();
		writer.close();

		IndexSearcher searcher = new IndexSearcher(IndexReader.open(dir));
		Query query = new TermQuery(new Term("id", "1"));
		TopDocs docs = searcher.search(query, 10);
		Assert.assertEquals(2, docs.totalHits);
		// Document ret = searcher.doc(docs.scoreDocs[1].doc);
		// Assert.assertEquals("test1", ret.getFieldable("content").stringValue());

	}

	@Test
	public void testUpdate() throws Exception {
		String indexDir = "target/lucene/index/IndexWriterTest/testUpdate";
		FSDirectory dir = FSDirectory.open(new File(indexDir));
		Analyzer analyzer = new StandardAnalyzer(Constants.LUCENE_VERSION);
		IndexWriterConfig config = new IndexWriterConfig(Constants.LUCENE_VERSION, analyzer);
		config.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);// recreate every time
		IndexWriter writer = new IndexWriter(dir, config);

		Document doc = new Document();
		doc.add(new Field("id", "1", Field.Store.YES, Field.Index.NOT_ANALYZED));
		doc.add(new Field("content", "test1", Field.Store.YES, Field.Index.NO));
		writer.addDocument(doc);
		writer.commit();

		doc = new Document();
		doc.add(new Field("id", "1", Field.Store.YES, Field.Index.NOT_ANALYZED));
		doc.add(new Field("content", "test2", Field.Store.YES, Field.Index.NO));
		Term term = new Term("id", "2");
		writer.updateDocument(term, doc);
		writer.commit();

		IndexSearcher searcher = new IndexSearcher(IndexReader.open(dir));
		Query query = new TermQuery(new Term("id", "1"));
		TopDocs docs = searcher.search(query, 10);
		Document ret = searcher.doc(docs.scoreDocs[0].doc);
		Assert.assertEquals("test1", ret.getFieldable("content").stringValue());
		searcher.close();

		doc = new Document();
		doc.add(new Field("id", "5", Field.Store.YES, Field.Index.NOT_ANALYZED));
		doc.add(new Field("content", "test5", Field.Store.YES, Field.Index.NO));
		term = new Term("id", "5");
		writer.updateDocument(term, doc);
		writer.commit();

		searcher = new IndexSearcher(IndexReader.open(dir));
		query = new TermQuery(new Term("id", "5"));
		docs = searcher.search(query, 10);
		ret = searcher.doc(docs.scoreDocs[0].doc);
		Assert.assertEquals("test5", ret.getFieldable("content").stringValue());

	}
}
