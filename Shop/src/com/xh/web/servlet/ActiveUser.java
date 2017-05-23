package com.xh.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xh.domain.User;
import com.xh.exception.MessageException;
import com.xh.factory.BasicFactory;
import com.xh.service.UserServiceDao;

public class ActiveUser extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserServiceDao service = BasicFactory.getBasicFactory().getService(UserServiceDao.class);
		try {
			//1、获取用户激活码
			String activecode = request.getParameter("activecode");
			
			//2、激活用户
			User user;
			user = service.activeUser(activecode);
			
			//3、登录用户
			request.getSession().setAttribute("user", user);

			//4、重定向回到主页
			response.getWriter().write("用户激活成功！3秒后回到主页");
			response.setHeader("refresh", "3;url="+request.getContextPath()+"/index.jsp");
		
		} catch (MessageException e) {
			//激活失败
			response.getWriter().write(e.getMessage()+",3秒后回到主页");
			response.setHeader("refresh", "3;url="+request.getContextPath()+"/index.jsp");
			e.printStackTrace();
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
