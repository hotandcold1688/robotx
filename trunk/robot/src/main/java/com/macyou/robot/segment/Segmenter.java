package com.macyou.robot.segment;

import java.util.List;

public interface Segmenter {

	/**
	 * 调用默认分词方法
	 * @param question  isOrigan 是否是原始问题
	 * @return
	 * @throws 
	 */
	public List<String> segment(String question,boolean isOrigan);
}
