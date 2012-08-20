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

public class IntimeIndexTest {

	@Test
	public void testSearch() throws Exception {
		String indexDir = "target/lucene/index/IntimeIndexTest/";
		FSDirectory dir = FSDirectory.open(new File(indexDir));
		Analyzer analyzer = new StandardAnalyzer(Constants.LUCENE_VERSION);
		IndexWriterConfig config = new IndexWriterConfig(Constants.LUCENE_VERSION, analyzer);
		config.setOpenMode(IndexWriterConfig.OpenMode.CREATE);// recreate every time
		IndexWriter writer = new IndexWriter(dir, config);

		Document doc = new Document();
		doc.add(new Field("id", "1", Field.Store.YES, Field.Index.NOT_ANALYZED));
		doc.add(new Field("content", "test1", Field.Store.YES, Field.Index.NO));
		writer.addDocument(doc);
		writer.commit();

		IndexReader reader = IndexReader.open(writer, false);
		IndexSearcher searcher = new IndexSearcher(reader);
		Query query = new TermQuery(new Term("id", "1"));
		TopDocs docs = searcher.search(query, 10);
		Document ret = searcher.doc(docs.scoreDocs[0].doc);
		Assert.assertEquals("test1", ret.getFieldable("content").stringValue());

		doc = new Document();
		doc.add(new Field("id", "1", Field.Store.YES, Field.Index.NOT_ANALYZED));
		doc.add(new Field("content", "test2", Field.Store.YES, Field.Index.NO));
		Term term = new Term("id", "1");
		writer.updateDocument(term, doc);
		writer.commit();
		searcher.getIndexReader();
		searcher = new IndexSearcher(reader.openIfChanged(reader));
		// reader = IndexReader.open(writer, false);
		// searcher = new IndexSearcher(reader);
		query = new TermQuery(new Term("id", "1"));
		docs = searcher.search(query, 10);
		ret = searcher.doc(docs.scoreDocs[0].doc);
		Assert.assertEquals("test2", ret.getFieldable("content").stringValue());

	}
}
