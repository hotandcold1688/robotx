package com.macyou.robot.similarity;

import org.apache.lucene.index.Term;

/**
 * @author zili.dengzl
 * 
 */
public interface SimilarityCalculator {
	/**
	 * @param termsLeft
	 * @param termsRight
	 * @return
	 */
	float calculateSimilarity(Term[] termsLeft, Term[] termsRight);

}
