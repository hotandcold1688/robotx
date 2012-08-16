package com.macyou.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.lucene.analysis.Analyzer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.macyou.robot.IndexManager;
import com.macyou.robot.Robot;
import com.macyou.robot.RobotManager;
import com.macyou.robot.index.DefaultIndexBuilderFactory;
import com.macyou.robot.index.IndexBuilder;
import com.macyou.robot.index.IndexBuilderFactory;
import com.macyou.robot.index.JavaFetcher;

public class SimpleServlet extends HttpServlet {
	private static final long serialVersionUID = 3290498972143257177L;
	private RobotManager robotManager;
	private IndexManager indexManager;

	public void init(ServletConfig config) throws ServletException {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("/spring/robot.xml");
		robotManager = (RobotManager) ctx.getBean("robotManager");
		indexManager = (IndexManager) ctx.getBean("indexManager");

		// 初始化index
		indexManager.fullBuildAllRobotIndex();
		// buildIndexInJava();

		// start
		robotManager.start();
	}

	private void buildIndexInJava() {
		Robot robot = robotManager.getRobot("robot1");
		Analyzer analyzer = new IKAnalyzer(true);
		DefaultIndexBuilderFactory factory = new DefaultIndexBuilderFactory(robot.getIndexPath());
		factory.setAnalyzer(analyzer);
		JavaFetcher fetcher = new JavaFetcher();
		fetcher.setSource(SimpleData.knowledges);
		factory.setFetcher(fetcher);
		IndexBuilder builder;
		try {
			builder = factory.getIndexBuilder(IndexBuilderFactory.IndexType.FULL);
			builder.buildIndex();
		} catch (Exception e) {
		}
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.setContentType("text/html; charset=GBK");
		res.setCharacterEncoding("GBK");
		req.setCharacterEncoding("GBK");
		PrintWriter out = res.getWriter();
		StringBuilder sb = new StringBuilder();
		sb.append("<html><head><meta http-equiv=\"content-type\" content=\"text/html; charset=GBK\"><title>chat-debug</title></head><body>");

		String operation = req.getParameter("operation");
		if (operation != null && operation.equals("buildIndex")) {
			String robotId = req.getParameter("robotId");
			Robot robot = robotManager.getRobot(robotId);
			indexManager.fullBuildOneRobotIndex(robot);
			sb.append("rebuild index!");
		}

		sb.append("<form action=/ target=selfframe method=post />");
		sb.append("scenceCode:<input name=robotId type=text maxLength=14 value=robot1 style='width:100px' />");
		sb.append("<br>问题：<input name=question type=text maxLength=25 style='width:450px' />");
		sb.append("<input type=submit value='提交' /></br></form>");
		sb.append("<iframe name='selfframe' src='about:blank' style='border:1px solid #ccc;width:680px;height:500px;overflow-x:break'></iframe>");
		sb.append("<body></html>");
		out.print(sb);
		out.close();
	}

	@SuppressWarnings("finally")
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.setContentType("text/html; charset=GBK");
		res.setCharacterEncoding("GBK");
		req.setCharacterEncoding("GBK");
		String question = req.getParameter("question");
		String robotId = req.getParameter("robotId");
		PrintWriter out = res.getWriter();
		try {
			Robot robot = robotManager.getRobot(robotId);
			if (null == robot) {
				// TODO: DEFALUT ROBOT
			}
			String answer = robot.answer(question);
			out.print(answer);
		} catch (Exception e) {
			out.print("error:");
			out.print("-------------------------------------------------------<br/>");
			out.print(e);
		} finally {
			out.flush();
			out.close();
			return;
		}
	}

	public void setRobotManager(RobotManager robotManager) {
		this.robotManager = robotManager;
	}

}
