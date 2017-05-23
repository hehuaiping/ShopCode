package com.xh.listener;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.xh.domain.Cart;


public class MySessionListener implements HttpSessionListener {

	//���û������̳�ʱ
	public void sessionCreated(HttpSessionEvent event) {
//		//�������ﳵ����
		Cart cart = new Cart();
//		//��ȡsession����,�����ﳵ����session����
		event.getSession().setAttribute("cart", cart);
	}

	//�û��뿪���̳�
	public void sessionDestroyed(HttpSessionEvent event) {
		// TODO Auto-generated method stub

	}

}
