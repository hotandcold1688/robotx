package com.macyou.web;

import javax.servlet.Servlet;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author zili.dengzl
 *
 */
public class JettyServer {
	public static void main(String[] args) throws Exception {
		Server server = new Server(8080);
		//System.setProperty("org.eclipse.jetty.util.URI.charset", "UTF-8");

		ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
		context.setContextPath("/");
		context.setResourceBase(".");
		server.setHandler(context);

		ApplicationContext ctx = new ClassPathXmlApplicationContext("/spring/index.xml");
		context.addServlet(new ServletHolder((Servlet) ctx.getBean("simpleServlet")), "/");
		
		server.start();
		server.join();
	}
}
