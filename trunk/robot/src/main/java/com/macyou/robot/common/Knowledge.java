package com.macyou.robot.common;

/**
 * base knowledge entity
 * 
 * @author zili.dengzl
 * 
 */
public class Knowledge extends Entity implements Comparable<Knowledge> {
	// /**
	// * index's id
	// */
	// private String indexId;
	// public static final String INDEX_ID = "index_id";
	/**
	 * question
	 */
	private String question;
	public static final String QUESTION = "question";
	/**
	 * answer
	 */
	private String answer;
	public static final String ANSWER = "answer";
	/**
	 * contentType, indicate the answer's contentType, such as text, html, or image
	 */
	private ContentType contentType;
	public static final String CONTENT_TYPE = "content_type";
	/**
	 * robotId
	 */
	private String robotId;
	public static final String ROBOT_ID = "robot_id";

	/**
	 * robotId
	 */
	private float similarity;
	public static final String SIMILARITY = "similarity";

	// public String getIndexId() {
	// return indexId;
	// }
	//
	// public void setIndexId(String indexId) {
	// this.indexId = indexId;
	// }

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

	public ContentType getContentType() {
		return contentType;
	}

	public void setContentType(ContentType contentType) {
		this.contentType = contentType;
	}

	public String getRobotId() {
		return robotId;
	}

	public void setRobotId(String robotId) {
		this.robotId = robotId;
	}

	public float getSimilarity() {
		return similarity;
	}

	public void setSimilarity(float similarity) {
		this.similarity = similarity;
	}

	@Override
	public int compareTo(Knowledge o) {
		// TODO Auto-generated method stub
		return 0;
	}

	public static enum ContentType {
		text, html, jpg
	}

}
