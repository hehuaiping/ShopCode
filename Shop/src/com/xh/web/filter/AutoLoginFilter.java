package com.xh.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xh.domain.User;
import com.xh.factory.BasicFactory;
import com.xh.service.UserServiceDao;

public class AutoLoginFilter implements Filter {

	public void destroy() {
		// TODO Auto-generated method stub

	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		UserServiceDao service = BasicFactory.getBasicFactory().getService(UserServiceDao.class);
		
		//1、判断用户是否登录
		if(req.getSession().getAttribute("user")==null) {
			//2、判断用户是否有自动登录的cookie
			Cookie[] cookies = req.getCookies();
			if(null!=cookies) {
				//遍历cookie
				for(Cookie cookie : cookies) {
					if("autologin".equals(cookie.getName())) {
						//用户有自动登录cookie,获取cookie中的用户名,密码
						String username = cookie.getValue().split(":")[0];
						String password = cookie.getValue().split(":")[1];
						//从数据库中查询用户名密码是否匹配
						User user = service.findUserByUneAndPwd(username, password);
						//如果用户不为空,代表用户名密码正确,登录用户
						if(user!=null) {
							req.getSession().setAttribute("user", user);
						}
					}
				}
			}
		}
		
		chain.doFilter(request, response);

	}

	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
