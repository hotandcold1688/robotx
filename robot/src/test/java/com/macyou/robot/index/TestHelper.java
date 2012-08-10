package com.macyou.robot.index;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class TestHelper {

	public static Document searchFirstDoc(String indexDir, String filed, String text) throws CorruptIndexException,
			IOException {
		Directory directory = FSDirectory.open(new File(indexDir));
		IndexSearcher searcher = new IndexSearcher(IndexReader.open(directory));
		Query query = new TermQuery(new Term(filed, text));
		TopDocs docs = searcher.search(query, 2);
		Document doc = searcher.doc(docs.scoreDocs[0].doc);
		searcher.close();
		return doc;
	}
}
