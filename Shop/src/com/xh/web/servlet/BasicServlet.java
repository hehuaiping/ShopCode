package com.xh.web.servlet;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BasicServlet extends HttpServlet {
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			//��ȡ���÷���������
			String methodName = req.getParameter("method");
			if(null==methodName) {
				//��request���л�ȡֵ
				methodName = (String) req.getAttribute("method");
			}
			//��ȡ��ǰ�����ʶ�����ֽ���
			Class clazz = this.getClass();
			//��ȡ��ǰ������ָ���ķ���
		 	Method method = clazz.getMethod(methodName, HttpServletRequest.class,HttpServletResponse.class);
			//ִ�з���
			method.invoke(this, req,resp);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
