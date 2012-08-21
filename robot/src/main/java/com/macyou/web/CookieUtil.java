package com.macyou.web;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zili.dengzl
 *
 */
public class CookieUtil {
	static String domain = " "; // 能否重request或者response中取得

	public String getFromCookie(HttpServletRequest request, String name) {
		Cookie[] cookies = request.getCookies();
		if (cookies == null) {
			return null;
		}

		String value = null;
		for (Cookie cookie : cookies) {
			if (name.equals(cookie.getName())) {
				value = cookie.getValue();
				break;
			}
		}

		if (value != null) {
			// 解密value?
		}
		return value;
	}

	public void setToCookie(HttpServletResponse response, String name, String value) {
		// 加密value？
		Cookie cookie = new Cookie(name, value);
		//cookie.setDomain(domain);
		cookie.setPath("/");
		cookie.setMaxAge(-1);

		response.addCookie(cookie);
	}
}
