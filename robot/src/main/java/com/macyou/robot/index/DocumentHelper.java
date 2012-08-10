package com.macyou.robot.index;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;

import com.macyou.robot.common.FiledName;
import com.macyou.robot.common.Knowledge;

/**
 * 
 * 
 * @author zili.dengzl
 * 
 */
public class DocumentHelper {

	public static Document toDocument(Knowledge knowledge) {
		Document doc = new Document();
		doc.add(new Field(FiledName.INDEX_ID, String.valueOf(knowledge.getIndexId()), Field.Store.YES,
				Field.Index.NOT_ANALYZED));
		doc.add(new Field(FiledName.QUESTION, knowledge.getQuestion(), Field.Store.YES, Field.Index.ANALYZED));
		doc.add(new Field(FiledName.ANSWER, knowledge.getAnswer(), Field.Store.YES, Field.Index.ANALYZED));
		return doc;
	}

	public static Knowledge fromDocument(Document document) {
		return null;
	}
}
