package com.macyou.robot.index;

import java.io.IOException;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;

import com.macyou.robot.common.DocumentHelper;
import com.macyou.robot.common.FiledName;
import com.macyou.robot.common.Knowledge;

/**
 * @author zili.dengzl
 *
 */
public class IndexIncrementBuilder extends AbstractIndexBuilder {

	private static final String Y = "y";

	public IndexIncrementBuilder(IndexWriter writer, Fetcher fetcher) {
		super();
		this.writer = writer;
		this.fetcher = fetcher;
	}

	@Override
	protected void buildOneDocument(Knowledge knowledge) {
		try {

			Term term = new Term(FiledName.INDEX_ID, knowledge.getIndexId());
			if (Y.equalsIgnoreCase(knowledge.getIsDeleted()))
				writer.deleteDocuments(term);
			else {
				Document doc = DocumentHelper.toDocument(knowledge);
				if (doc != null) {
					writer.updateDocument(term, doc);
				}
			}

		} catch (CorruptIndexException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
