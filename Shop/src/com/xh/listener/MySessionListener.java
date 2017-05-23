package com.xh.listener;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.xh.domain.Cart;


public class MySessionListener implements HttpSessionListener {

	//当用户访问商城时
	public void sessionCreated(HttpSessionEvent event) {
//		//创建购物车集合
		Cart cart = new Cart();
//		//获取session对象,将购物车存入session域中
		event.getSession().setAttribute("cart", cart);
	}

	//用户离开是商城
	public void sessionDestroyed(HttpSessionEvent event) {
		// TODO Auto-generated method stub

	}

}
