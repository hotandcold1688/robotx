package com.macyou.robot.common;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;

/**
 * 
 * 
 * @author zili.dengzl
 * 
 */
public class DocumentHelper {

	public static Document toDocument(Knowledge knowledge) {
		Document doc = new Document();
		doc.add(new Field(Knowledge.ID, String.valueOf(knowledge.getId()), Field.Store.YES, Field.Index.NOT_ANALYZED));
		doc.add(new Field(Knowledge.QUESTION, knowledge.getQuestion(), Field.Store.YES, Field.Index.ANALYZED));
		doc.add(new Field(Knowledge.ANSWER, knowledge.getAnswer(), Field.Store.YES, Field.Index.NOT_ANALYZED));
		return doc;
	}

	public static Knowledge fromDocument(Document document) {
		Knowledge k = new Knowledge();
		k.setQuestion(document.getFieldable(Knowledge.QUESTION).stringValue());
		k.setAnswer(document.getFieldable(Knowledge.ANSWER).stringValue());
		return k;
	}

}
