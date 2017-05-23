package com.xh.web.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;

import com.mchange.v2.codegen.bean.BeangenUtils;
import com.xh.domain.User;
import com.xh.exception.MessageException;
import com.xh.factory.BasicFactory;
import com.xh.service.UserServiceDao;
import com.xh.utils.MD5Utils;

public class RegisServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserServiceDao service = BasicFactory.getBasicFactory().getService(UserServiceDao.class);
		try {
			//1����ȡ�û��ύ������/�������/��װ����
			User user = new User();
			//��ȡ��֤��
			String vfcode = request.getParameter("vfcode");
			//У����֤��
			if(vfcode.equalsIgnoreCase((String)request.getSession().getAttribute("code"))) {
				//���ַ���ת����ʱ�����
				SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
				String bir = request.getParameter("birthday1");
				user.setBirthday(sd.parse(bir));
				user.setState("0");
				BeanUtils.populate(user, request.getParameterMap());
				
				//���û��������
				user.setPassword(MD5Utils.md5(user.getPassword()));
				//�������
				user.checkValue();
				//4������Service�е�ע���û��ķ���
				service.addUser(user);
				//5����ʾ�û���ע������ʾ�û�����
				response.getWriter().write("�û�ע��ɹ����뵽�����м���ʹ��!3���ص���ҳ....");
				response.setHeader("refresh", "3;url="+request.getContextPath()+"/index.jsp");
			}else {
				System.out.println("��֤���쳣");
				throw new MessageException("��֤���������");
			}
			
		} catch (MessageException e) {
			System.out.println("�쳣");
			//��������ݴ����͵�jspҳ��
			request.setAttribute("msg", e.getMessage());
			request.getRequestDispatcher("/index.jsp").forward(request, response);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
