package com.macyou.test;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.macyou.robot.Robot;
import com.macyou.robot.RobotManager;
import com.macyou.robot.common.StringUtils;
import com.macyou.robot.context.SearchContext;
import com.macyou.robot.exception.RobotCommonException;
import com.macyou.robot.session.SessionManager;
/**
 * 
 * debug use
 *
 */
public class SimpleServlet extends HttpServlet {
	private static final long serialVersionUID = 3290498972143257177L;
	@Autowired
	private RobotManager robotManager;
	CookieUtil cookieUtil = new CookieUtil();

	public void init(ServletConfig config) throws ServletException {
		initWhileNoSpringContext();
	}

	private void initWhileNoSpringContext() {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("spring/robot.xml");

		robotManager = new RobotManager();
		// start
		robotManager.createRobot("robot1");
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.setContentType("text/html; charset=GBK");
		res.setCharacterEncoding("GBK");
		req.setCharacterEncoding("GBK");
		PrintWriter out = res.getWriter();
		StringBuilder sb = new StringBuilder();
		sb.append("<html><head><meta http-equiv=\"content-type\" content=\"text/html; charset=GBK\"><title>chat-debug</title></head><body>");
		sb.append("<form action=/robot.do target=selfframe method=post />");
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

		PrintWriter out = res.getWriter();
		try {
			System.out.println(req.getServerName());
			String answer = search(req, res);
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

	private String search(HttpServletRequest req, HttpServletResponse res) throws Exception {
		String robotId = req.getParameter("robotId");
		Robot robot = robotManager.getRobot(robotId);
		if (null == robot) {
			// TODO: DEFALUT ROBOT
		}
		SearchContext context = prepareContext(req);
		String answer = robot.answer(context);
		if (null != context.getSessionId()) {
			cookieUtil.setToCookie(res, SessionManager.SESSION_COOKIE_NAME, context.getSessionId());
		}
		return answer;
	}

	public SearchContext prepareContext(HttpServletRequest req) throws Exception {
		String question = req.getParameter("question");
		if (StringUtils.isEmpty(question)) {
			throw new RobotCommonException("queryAnswer error,question is null");
		}
		SearchContext context = new SearchContext();
		context.setQuestion(question);
		String sessionId = cookieUtil.getFromCookie(req, SessionManager.SESSION_COOKIE_NAME);
		context.setSessionId(sessionId);
		return context;
	}

	public void setRobotManager(RobotManager robotManager) {
		this.robotManager = robotManager;
	}

}
