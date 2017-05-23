package com.xh.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xh.domain.Product;
import com.xh.factory.BasicFactory;
import com.xh.service.ProductService;

public class IndexServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ProductService service  = BasicFactory.getBasicFactory().getService(ProductService.class);
		
		//��ȡ������Ʒ---List<Product>
		List<Product> hotProductList = service.findHotProductList();
		//��ȡ������Ʒ---List<Product>
		List<Product> newProductList = service.findNewProductList();
		
		//�����ݴ���request����
		request.setAttribute("hotProductList", hotProductList);
		request.setAttribute("newProductList", newProductList);
		
		//ת����jspҳ��
		request.getRequestDispatcher("/index.jsp").forward(request, response);
	}
	

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
