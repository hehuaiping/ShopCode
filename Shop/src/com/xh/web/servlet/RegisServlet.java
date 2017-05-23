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
			//1、获取用户提交的数据/检查数据/封装数据
			User user = new User();
			//获取验证码
			String vfcode = request.getParameter("vfcode");
			//校验验证码
			if(vfcode.equalsIgnoreCase((String)request.getSession().getAttribute("code"))) {
				//将字符串转换成时间对象
				SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
				String bir = request.getParameter("birthday1");
				user.setBirthday(sd.parse(bir));
				user.setState("0");
				BeanUtils.populate(user, request.getParameterMap());
				
				//将用户密码加密
				user.setPassword(MD5Utils.md5(user.getPassword()));
				//检查数据
				user.checkValue();
				//4、调用Service中的注册用户的方法
				service.addUser(user);
				//5、提示用户以注册让提示用户激活
				response.getWriter().write("用户注册成功，请到邮箱中激活使用!3秒后回到主页....");
				response.setHeader("refresh", "3;url="+request.getContextPath()+"/index.jsp");
			}else {
				System.out.println("验证码异常");
				throw new MessageException("验证码输入错误");
			}
			
		} catch (MessageException e) {
			System.out.println("异常");
			//将检查数据错误发送到jsp页面
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
