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
		//��װpage����
		Page<Product> page = new Page<Product>();
		//��ǰҳ
		page.setThisPage(thisPage);
		//ÿҳ��ʾ������
		page.setRowPage(rowPage);
		//�ܹ�����������
	 	int totalNum = dao.findProductCountByCid(cid);
	 	page.setTotalNum(totalNum);
	 	//�ܹ�����ҳ����
	 	page.setCountPage(totalNum/rowPage+(totalNum%rowPage==0 ? 0 : 1));
	 	//��ҳ
	 	page.setFirstPage(0);	
	 	//βҳ
	 	page.setLastPage(page.getCountPage());
	 	//��һҳ
	 	page.setNextPage(thisPage+1<=page.getCountPage()? thisPage+1 : thisPage);
	 	//��һҳ
	 	page.setUpPage(thisPage-1>=page.getFirstPage()? thisPage+1 : thisPage);
	 	//����PruductDao�еķ�ҳ��ѯ����
	  	List<Product> list = dao.findProductListByCid(cid, (thisPage-1)*rowPage, rowPage);
	  	page.setList(list);
		return page;
	}

	public Product findProductById(String pid) {
		return dao.findProductById(pid);
	}

}
