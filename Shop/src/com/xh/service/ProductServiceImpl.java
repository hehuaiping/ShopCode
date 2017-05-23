package com.xh.service;

import java.util.List;

import com.xh.dao.ProductDao;
import com.xh.domain.Page;
import com.xh.domain.Product;
import com.xh.factory.BasicFactory;

public class ProductServiceImpl implements ProductService {
	ProductDao dao = BasicFactory.getBasicFactory().getDao(ProductDao.class);
	
	public List<Product> findHotProductList() {
		return dao.finfHotProductList();
	}

	public List<Product> findNewProductList() {
		return dao.finfNewProductList();
	}

	public Page<Product> findProductListByCid(String cid, int thisPage, int rowPage) {
		//封装page数据
		Page<Product> page = new Page<Product>();
		//当前页
		page.setThisPage(thisPage);
		//每页显示多少条
		page.setRowPage(rowPage);
		//总共多少条数据
	 	int totalNum = dao.findProductCountByCid(cid);
	 	page.setTotalNum(totalNum);
	 	//总共多少页数据
	 	page.setCountPage(totalNum/rowPage+(totalNum%rowPage==0 ? 0 : 1));
	 	//首页
	 	page.setFirstPage(0);	
	 	//尾页
	 	page.setLastPage(page.getCountPage());
	 	//下一页
	 	page.setNextPage(thisPage+1<=page.getCountPage()? thisPage+1 : thisPage);
	 	//上一页
	 	page.setUpPage(thisPage-1>=page.getFirstPage()? thisPage+1 : thisPage);
	 	//调用PruductDao中的分页查询方法
	  	List<Product> list = dao.findProductListByCid(cid, (thisPage-1)*rowPage, rowPage);
	  	page.setList(list);
		return page;
	}

	public Product findProductById(String pid) {
		return dao.findProductById(pid);
	}

}
