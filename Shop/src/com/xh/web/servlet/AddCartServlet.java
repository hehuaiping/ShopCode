package com.xh.web.servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xh.domain.Product;
import com.xh.factory.BasicFactory;
import com.xh.service.ProductService;

public class AddCartServlet extends HttpServlet {
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ProductService service = BasicFactory.getBasicFactory().getService(ProductService.class);
		//��ȡ���ﳵ����
		Map<Product,Integer> cartMap = (Map<Product, Integer>) request.getSession().getAttribute("cartMap");
		
		//1����ȡ��ƷID,��Ʒ�Ĺ�������
		String pid = request.getParameter("pid");
		String buynum = request.getParameter("buynum");
		System.out.println(buynum);
		//2������Service�и�����ƷID������Ʒ�ķ���
		Product product = service.findProductById(pid);
		//3�������ҵ�����Ʒ����cart������
		cartMap.put(product, cartMap.containsKey(product)?cartMap.get(product)+Integer.parseInt(buynum) : Integer.parseInt(buynum));
		//4��ת�������ﳵ
		request.getRequestDispatcher("/cart.jsp").forward(request, response);
	}
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req,resp);
	}
}
