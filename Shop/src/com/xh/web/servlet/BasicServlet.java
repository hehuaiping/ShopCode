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
			//获取调用方法的名字
			String methodName = req.getParameter("method");
			if(null==methodName) {
				//从request域中获取值
				methodName = (String) req.getAttribute("method");
			}
			//获取当前被访问对象的字节码
			Class clazz = this.getClass();
			//获取当前对象中指定的方法
		 	Method method = clazz.getMethod(methodName, HttpServletRequest.class,HttpServletResponse.class);
			//执行方法
			method.invoke(this, req,resp);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
