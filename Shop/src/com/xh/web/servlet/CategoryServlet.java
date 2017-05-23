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
		
		//1、从缓存中取出数据,如果缓存中没有数据,则从数据库中获取数据存入缓存
		String categoryListJedis = jedis.get("categoryJedis");
		if(categoryListJedis==null) {
			System.out.println("redis缓存没有数据...");
			//2、调用Service中获取category的数据
			List<Category> categoryList = service.findCategory();
			//3、将数据打包成json格式数据
			Gson gson = new Gson();
			categoryListJedis = gson.toJson(categoryList);
			jedis.set("categoryJedis", categoryListJedis);
		}
		
		//3、将json数据返回
		response.getWriter().write(categoryListJedis);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
