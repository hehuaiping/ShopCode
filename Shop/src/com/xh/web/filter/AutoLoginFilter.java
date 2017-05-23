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
		
		//1���ж��û��Ƿ��¼
		if(req.getSession().getAttribute("user")==null) {
			//2���ж��û��Ƿ����Զ���¼��cookie
			Cookie[] cookies = req.getCookies();
			if(null!=cookies) {
				//����cookie
				for(Cookie cookie : cookies) {
					if("autologin".equals(cookie.getName())) {
						//�û����Զ���¼cookie,��ȡcookie�е��û���,����
						String username = cookie.getValue().split(":")[0];
						String password = cookie.getValue().split(":")[1];
						//�����ݿ��в�ѯ�û��������Ƿ�ƥ��
						User user = service.findUserByUneAndPwd(username, password);
						//����û���Ϊ��,�����û���������ȷ,��¼�û�
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
