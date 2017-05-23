package com.xh.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xh.domain.User;
import com.xh.factory.BasicFactory;
import com.xh.service.UserServiceDao;

public class PowerFilter implements Filter {

	public void destroy() {
		// TODO Auto-generated method stub

	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		UserServiceDao service = BasicFactory.getBasicFactory().getService(UserServiceDao.class);
		
		
		//1、判断当前是否登陆
		User user =  (User) req.getSession().getAttribute("user");
		if(user == null) {
			//用户没有登录,提示用户登录
			resp.getWriter().write("请登录后,再访问该资源,3秒后回到登录....");
			resp.setHeader("refresh", "3;url="+req.getContextPath()+"/login.jsp");
			chain.doFilter(request, response);
			return;
		}
		//2、判断当前用户是否是管理员用户
		String role = service.findUserRoleByUid(user.getUid());
		if(role.equals("admin")) {
			//放行
			chain.doFilter(request, response);
			return;
		}else {
			//用户登录了,但不是管理员
			resp.getWriter().write("该资源需要管理员才能访问,您没有足够的权利,3秒后回主页....");
			resp.setHeader("refresh", "3;url="+req.getContextPath()+"/index");
			chain.doFilter(request, response);
		}
		
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub

	}

}
