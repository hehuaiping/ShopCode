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
			//1����ȡ�û�������
			String activecode = request.getParameter("activecode");
			
			//2�������û�
			User user;
			user = service.activeUser(activecode);
			
			//3����¼�û�
			request.getSession().setAttribute("user", user);

			//4���ض���ص���ҳ
			response.getWriter().write("�û�����ɹ���3���ص���ҳ");
			response.setHeader("refresh", "3;url="+request.getContextPath()+"/index.jsp");
		
		} catch (MessageException e) {
			//����ʧ��
			response.getWriter().write(e.getMessage()+",3���ص���ҳ");
			response.setHeader("refresh", "3;url="+request.getContextPath()+"/index.jsp");
			e.printStackTrace();
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
