package com.xh.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginOutServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//判断用户是否有自动登录cookie
		Cookie[] cookies =  request.getCookies();
		for(Cookie cookie : cookies) {
			if("autologin".equals(cookie.getName())) {
				//清除cookie信息
				Cookie cook = new Cookie("autologin","");
				cook.setMaxAge(0);
				cook.setPath(request.getContextPath());
				response.addCookie(cook);
			}
		}
		
		//1、获取session,销毁session
		request.getSession().invalidate();
		//2、重定向回到主页
		response.sendRedirect(request.getContextPath()+"/index.jsp");
		return;
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
