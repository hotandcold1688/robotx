/**
 * Copyright 2012 zili.dengzl. All Rights Reserved
 */
package com.macyou.robot.common;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

/**
 * @author zili.dengzl
 * @time 2012-8-16 下午2:02:21
 * 
 */
public class SpringBeanGetter implements BeanFactoryAware {
	private static BeanFactory beanFactory;

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;
	}

	public static Object getBean(String name) {
		return beanFactory.getBean(name);
	}

	public static <T> T getBean(String name, Class<T> clazz) {
		return beanFactory.getBean(name, clazz);
	}
}
