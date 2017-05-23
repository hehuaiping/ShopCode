package com.xh.test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.zip.DataFormatException;

import com.xh.dao.OrderDao;
import com.xh.dao.UserDao;
import com.xh.domain.User;
import com.xh.factory.BasicFactory;
import com.xh.service.ProductService;

public class Test {
	public static void main(String[] args) throws ParseException {
		OrderDao dao = BasicFactory.getBasicFactory().getDao(OrderDao.class);
		dao.updateState("a3910cd4-8fc6-4796-b613-6807cd565352",1);
	}
}
