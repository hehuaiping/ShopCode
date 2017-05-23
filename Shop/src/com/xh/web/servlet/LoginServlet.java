package com.xh.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xh.domain.User;
import com.xh.exception.MessageException;
import com.xh.factory.BasicFactory;
import com.xh.service.UserServiceDao;
import com.xh.utils.MD5Utils;

public class LoginServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserServiceDao service = BasicFactory.getBasicFactory().getService(UserServiceDao.class);
		//1、获取用户名密码
		String username = request.getParameter("username");
		String password = request.getParameter("password");
	
		//将密码加密
		password = MD5Utils.md5(password);
		String vfcode = request.getParameter("vfcode");
		if(vfcode.equalsIgnoreCase((String)request.getSession().getAttribute("code"))) {
			//2、调用Service中查找用户的方法,判断是否存在
			User user = service.findUserByUneAndPwd(username,password);
			//如果存在则登录
			if(user!=null) {
				//查看用户是否已激活
				if(user.getState().equals("0")) {
					try {
						throw new MessageException("该用户暂未激活,请您到邮箱中激活用户再登录...");
					} catch (MessageException e) {
						//提示用户
						request.setAttribute("msg", e.getMessage());
						response.getWriter().write(e.getMessage());
						
					}
				}else {
					request.getSession().setAttribute("user", user);
					//判断用户是否勾选了自动登录
					if("autologin".equals(request.getParameter("autologin"))) {
						//用户勾选了自动登录
						Cookie cookie = new Cookie("autologin",user.getUsername()+":"+user.getPassword());
						cookie.setMaxAge(1000*60*24*30);
						cookie.setPath(request.getContextPath());
						//发送cookie
						response.addCookie(cookie);
					}
					
					//3、存取session域回到主页
					response.sendRedirect(request.getContextPath()+"/index.jsp");
				}
		
			}else {
			//如果不存在提示用户
				try {
					throw new MessageException("用户名或密码错误");
				} catch (MessageException e) {
					//将信息存入request域
					request.setAttribute("msg", e.getMessage());
					request.getRequestDispatcher("/login.jsp").forward(request, response);
					return;
				}
			}

		}
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
