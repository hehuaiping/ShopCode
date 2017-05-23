package com.xh.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.xh.domain.Category;
import com.xh.factory.BasicFactory;
import com.xh.service.CategoryService;
import com.xh.utils.JedisPoolUtils;

import redis.clients.jedis.Jedis;

public class CategoryServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		CategoryService service = BasicFactory.getBasicFactory().getService(CategoryService.class);
		Jedis jedis = JedisPoolUtils.getJedis();
		
		//1���ӻ�����ȡ������,���������û������,������ݿ��л�ȡ���ݴ��뻺��
		String categoryListJedis = jedis.get("categoryJedis");
		if(categoryListJedis==null) {
			System.out.println("redis����û������...");
			//2������Service�л�ȡcategory������
			List<Category> categoryList = service.findCategory();
			//3�������ݴ����json��ʽ����
			Gson gson = new Gson();
			categoryListJedis = gson.toJson(categoryList);
			jedis.set("categoryJedis", categoryListJedis);
		}
		
		//3����json���ݷ���
		response.getWriter().write(categoryListJedis);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
