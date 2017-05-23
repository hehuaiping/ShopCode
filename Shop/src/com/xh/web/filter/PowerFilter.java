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
		
		
		//1���жϵ�ǰ�Ƿ��½
		User user =  (User) req.getSession().getAttribute("user");
		if(user == null) {
			//�û�û�е�¼,��ʾ�û���¼
			resp.getWriter().write("���¼��,�ٷ��ʸ���Դ,3���ص���¼....");
			resp.setHeader("refresh", "3;url="+req.getContextPath()+"/login.jsp");
			chain.doFilter(request, response);
			return;
		}
		//2���жϵ�ǰ�û��Ƿ��ǹ���Ա�û�
		String role = service.findUserRoleByUid(user.getUid());
		if(role.equals("admin")) {
			//����
			chain.doFilter(request, response);
			return;
		}else {
			//�û���¼��,�����ǹ���Ա
			resp.getWriter().write("����Դ��Ҫ����Ա���ܷ���,��û���㹻��Ȩ��,3������ҳ....");
			resp.setHeader("refresh", "3;url="+req.getContextPath()+"/index");
			chain.doFilter(request, response);
		}
		
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub

	}

}
