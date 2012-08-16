/**
 * Copyright 2012 zili.dengzl. All Rights Reserved
 */
package com.macyou.robot.common;

import junit.framework.Assert;

import org.junit.Test;

/**
 * @author zili.dengzl	
 * @time 2012-8-16 上午10:25:36
 *
 */
public class KnowledgeTest {
	@Test
	public void testContentType(){
		Assert.assertTrue(Knowledge.ContentType.text.equals(Knowledge.ContentType.valueOf("text")));
		Assert.assertEquals(Knowledge.ContentType.html, Knowledge.ContentType.valueOf("html"));
		Assert.assertFalse(Knowledge.ContentType.text.equals(Knowledge.ContentType.valueOf("html")));
	}
}
