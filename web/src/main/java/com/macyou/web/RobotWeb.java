package com.macyou.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.macyou.robot.Robot;
import com.macyou.robot.RobotManager;
import com.macyou.robot.common.StringUtils;
import com.macyou.robot.context.SearchContext;
import com.macyou.robot.exception.RobotCommonException;
import com.macyou.robot.session.SessionManager;
import com.macyou.test.CookieUtil;

@Controller
@RequestMapping("/robot.htm")
public class RobotWeb {
	private static final Logger logger = LoggerFactory.getLogger(RobotWeb.class);
	
	@Autowired
	private RobotManager robotManager;
	
	private CookieUtil cookieUtil = new CookieUtil();

	@RequestMapping(method = RequestMethod.GET)
	public String getForm(ModelMap model) {
		return "robot";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String postForm(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		logger.info("postForm is called");
		String answer = search(request, response);
		model.put("answer", answer);
		return "robot";
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
}
