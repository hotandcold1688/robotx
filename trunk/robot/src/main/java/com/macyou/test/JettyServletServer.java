package com.macyou.test;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

/**
 * debug use
 * 
 * @author zili.dengzl
 *
 */
public class JettyServletServer {
	public static void main(String[] args) throws Exception {
		Server server = new Server(8080);
		//System.setProperty("org.eclipse.jetty.util.URI.charset", "UTF-8");

		ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
		context.setContextPath("/");
		context.setResourceBase(".");
		server.setHandler(context);


		context.addServlet(new ServletHolder(new SimpleServlet()), "/");
		
		server.start();
		server.join();
	}
}
