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

public class IndexWriterTest {

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
		// writer.close();

		doc = new Document();
		doc.add(new Field("id", "1", Field.Store.YES, Field.Index.NOT_ANALYZED));
		doc.add(new Field("content", "test2", Field.Store.YES, Field.Index.NO));
		writer.addDocument(doc);
		writer.commit();
		writer.close();
		
		IndexSearcher searcher = new IndexSearcher(IndexReader.open(dir));
		Query query = new TermQuery(new Term("id", "1"));
		TopDocs docs = searcher.search(query, 10);
		Document ret = searcher.doc(docs.scoreDocs[0].doc);
		Assert.assertEquals("test1", ret.getFieldable("content").stringValue());

	}
}
