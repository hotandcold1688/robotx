package com.macyou.test;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

/**
 * @author zili.dengzl
 *
 */
@Deprecated
public class JettyWebServer {
	public static void main(String[] args) throws Exception {
		//System.setProperty("org.eclipse.jetty.util.URI.charset", "GBK");
		String baseDir = System.getProperty("user.dir") + "/target/classes/webroot";
		System.out.println("baseDir=" + baseDir);
		Server server = new Server(8080);

		WebAppContext webapp = new WebAppContext();
		webapp.setDescriptor(baseDir + "/WEB-INF/web.xml");
		webapp.setResourceBase(baseDir );
		// webapp.setDefaultsDescriptor(baseDir+"WEB-INF/webdefault.xml");
		webapp.setContextPath("/");

		server.setHandler(webapp);

		server.start();
		server.join();

	}
}
