package com.macyou.robot.common;

/**
 * base knowledge entity
 * 
 * @author zili.dengzl
 * 
 */
public class Knowledge extends Entity {
	/**
	 * index's id
	 */
	private String indexId;
	public static final String INDEX_ID = "INDEX_ID";
	/**
	 * question
	 */
	private String question;
	public static final String QUESTION = "QUESTION";
	/**
	 * answer
	 */
	private String answer;
	public static final String ANSWER = "ANSWER";

	public String getIndexId() {
		return indexId;
	}

	public void setIndexId(String indexId) {
		this.indexId = indexId;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

}
