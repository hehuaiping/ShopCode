package com.xh.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xh.utils.CreateImageCode;

public class VerifiServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// ������Ӧ�����͸�ʽΪͼƬ��ʽ
		response.setContentType("image/jpeg");
		// ��ֹͼ�񻺴档
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);

		CreateImageCode vCode = new CreateImageCode(100, 30, 5, 10);
		
		//request.getSession().removeAttribute("code");
		request.getSession().setAttribute("code", "");
		request.getSession().setAttribute("code", vCode.getCode());
		System.out.println(request.getSession().getAttribute("code"));
		vCode.write(response.getOutputStream());
		
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
