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
		//1����ȡ�û�������
		String username = request.getParameter("username");
		String password = request.getParameter("password");
	
		//���������
		password = MD5Utils.md5(password);
		String vfcode = request.getParameter("vfcode");
		if(vfcode.equalsIgnoreCase((String)request.getSession().getAttribute("code"))) {
			//2������Service�в����û��ķ���,�ж��Ƿ����
			User user = service.findUserByUneAndPwd(username,password);
			//����������¼
			if(user!=null) {
				//�鿴�û��Ƿ��Ѽ���
				if(user.getState().equals("0")) {
					try {
						throw new MessageException("���û���δ����,�����������м����û��ٵ�¼...");
					} catch (MessageException e) {
						//��ʾ�û�
						request.setAttribute("msg", e.getMessage());
						response.getWriter().write(e.getMessage());
						
					}
				}else {
					request.getSession().setAttribute("user", user);
					//�ж��û��Ƿ�ѡ���Զ���¼
					if("autologin".equals(request.getParameter("autologin"))) {
						//�û���ѡ���Զ���¼
						Cookie cookie = new Cookie("autologin",user.getUsername()+":"+user.getPassword());
						cookie.setMaxAge(1000*60*24*30);
						cookie.setPath(request.getContextPath());
						//����cookie
						response.addCookie(cookie);
					}
					
					//3����ȡsession��ص���ҳ
					response.sendRedirect(request.getContextPath()+"/index.jsp");
				}
		
			}else {
			//�����������ʾ�û�
				try {
					throw new MessageException("�û������������");
				} catch (MessageException e) {
					//����Ϣ����request��
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
