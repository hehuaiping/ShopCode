package com.xh.web.servlet;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.google.gson.Gson;
import com.xh.domain.Category;
import com.xh.domain.Orders;
import com.xh.domain.Product;
import com.xh.domain.User;
import com.xh.factory.BasicFactory;
import com.xh.service.AdminService;
import com.xh.utils.MD5Utils;

public class AdminServlet extends BasicServlet {
	// ����Ajax������Ʒ�����б�����
	public void findAllCategory(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		AdminService service = BasicFactory.getBasicFactory().getService(AdminService.class);

		// 1������Service�в�����Ʒ�����б�ķ���
		List<Category> categoryList = service.findAllCategory();
		// 2��������ת��Ϊjson��ʽ����
		String json = new Gson().toJson(categoryList);
		// 3�������ݷ���
		response.getWriter().write(json);
	}

	// �������ж���
	public void findAllOrders(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		AdminService service = BasicFactory.getBasicFactory().getService(AdminService.class);

		// 1������service�������ж���
		List<Orders> ordersList = service.findAllOrders();
		// 2�������ݴ���session��,�ض���չʾ����ҳ��
		request.getSession().setAttribute("ordersList", ordersList);
		response.sendRedirect(request.getContextPath() + "/admin/order/list.jsp");
	}

	// ���ݶ���ID���Ҷ�����
	public void findOrderItemByOid(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		AdminService service = BasicFactory.getBasicFactory().getService(AdminService.class);

		// 1����ȡ����ID
		String oid = request.getParameter("oid");
		// 2������service��ѯ�ö����µ����ж�����
		List<Map<String, Object>> listMap = service.findOrderItemByOid(oid);
		// 3�������ݷ�װ��json���ݷ���
		String json = new Gson().toJson(listMap);
		// System.out.println(json);
		// [{"pimage":"products/1/c_0001.jpg","shop_price":1299.0,"pname":"С�� 4c
		// ��׼��","subtotal":1299.0,"count":1},{"pimage":"products/1/c_0013.jpg","shop_price":1799.0,"pname":"Ŭ���ǣ�nubia��My
		// ������","subtotal":1799.0,"count":1}]
		response.getWriter().write(json);

	}

	// ��¼�û�
	public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		AdminService service = BasicFactory.getBasicFactory().getService(AdminService.class);
		// 1����ȡ�û�������
		String username = request.getParameter("username");
		String password = MD5Utils.md5(request.getParameter("password"));
		// 2������service�и����û�����������û��ķ���
		User user = service.findUserByNaAndPwd(username, password);
		if (user != null) {
			// ��¼�ɹ�
			request.getSession().setAttribute("user", user);
			request.getRequestDispatcher("/admin/home.jsp").forward(request, response);
			return;
		}
		// ��¼���ɹ�
		response.getWriter().write("�û������������");

	}

	// ��ѯ������Ʒ
	public void findAllProduct(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		AdminService service = BasicFactory.getBasicFactory().getService(AdminService.class);

		// 1������service�в�ѯȫ�������ķ���
		List<Product> prodList = service.findAllProduct();
		// 2�������ݴ���request�򷵻�
		request.setAttribute("prodList", prodList);
		request.getRequestDispatcher("/admin/product/list.jsp").forward(request, response);
	}

	// ������ƷID��ѯ��Ʒ
	public void findProductByPid(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			AdminService service = BasicFactory.getBasicFactory().getService(AdminService.class);
			Product product = new Product();

			// 1����ȡ��ƷID
			String pid = request.getParameter("pid");
			if (null == pid) {
				// servletת������,��ȡrequest����pid
				pid = (String) request.getAttribute("pid");
			}
			// 2������service�и�����ƷID��ѯ��Ʒ�ķ���
			Map<String, Object> prodMap = service.findProductByPid(pid);
			// --��װ����
			BeanUtils.copyProperties(product, prodMap);
			// --��װcategory����
			Category category = new Category();
			category.setCid((String) prodMap.get("cid"));
			// --��category��װ��product
			product.setCategory(category);
			// 3����������Ʒ���ݴ���request����,����ҳ��չʾ
			request.setAttribute("prod", product);
			request.getRequestDispatcher("/admin/product/edit.jsp").forward(request, response);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// ��ѯ������Ʒ����
	public void findAllCategoryJsp(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		AdminService service = BasicFactory.getBasicFactory().getService(AdminService.class);

		// 1������Service�в�����Ʒ�����б�ķ���
		List<Category> categoryList = service.findAllCategory();
		// 2�������ݴ���request����,ת����չʾҳ��
		request.setAttribute("categoryList", categoryList);
		request.getRequestDispatcher("/admin/category/list.jsp").forward(request, response);
	}

	// ������Ʒ����ID��ѯ��Ʒ
	public void findCategoryByCid(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		AdminService service = BasicFactory.getBasicFactory().getService(AdminService.class);

		// 1����ȡ��ƷID
		String cid = request.getParameter("cid");
		// 2������service���Ҳ�����Ʒ��������
		Category category = service.findCategoryByCid(cid);
		// 3������Ʒ�������ݴ���request,ת����ҳ��չʾ
		request.setAttribute("category", category);
		request.getRequestDispatcher("/admin/category/edit.jsp").forward(request, response);
	}

	// ������Ʒ����IDɾ����Ʒ����
	public void deleteCategoryByCid(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		AdminService service = BasicFactory.getBasicFactory().getService(AdminService.class);

		// 1����ȡ��ƷID
		String cid = request.getParameter("cid");
		// 2������service������Ʒ����IDɾ����Ʒ����ķ���
		service.deleteCategoryByCid(cid);
		// 3��ת������Ʒ�������ҳ��,չʾ���µ�����
		this.findAllCategoryJsp(request, response);
	}
	
	//�����Ʒ����
	public void addCategory(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			AdminService service = BasicFactory.getBasicFactory().getService(AdminService.class);
			
			//1����ȡ��Ʒ����ID,��Ʒ�������Ʒ�װ��bean��
			Category category = new Category();
			BeanUtils.populate(category, request.getParameterMap());
			//2������service�е���ӷ���
			service.addCategory(category);
			//3��ת����Ʒ�������ҳ��
			this.findAllCategoryJsp(request, response);
		}catch(Exception e) {
			e.printStackTrace();
		}
	
	}
	
	// ������Ʒ����ID������Ʒ������Ϣ
	public void updateCategoryByCid(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			AdminService service = BasicFactory.getBasicFactory().getService(AdminService.class);
			
			//1����ȡ��Ʒ��������,�����ݷ�װ��bean��
			Category category = new Category();
			BeanUtils.populate(category, request.getParameterMap());
			//2������service�и�����Ʒ����Id������Ʒ����ķ���
			service.updateCategoryByCid(category);
			//3��ת������Ʒ�������ҳ��
			this.findAllCategoryJsp(request, response);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//������ƷIDɾ����Ʒ
	public void deleteProductByPid(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		AdminService service = BasicFactory.getBasicFactory().getService(AdminService.class);
		
		//1����ȡ��ƷID
		String pid = request.getParameter("pid");
		//2������serviceִ��ɾ������
		service.deleteProducyByPid(pid);
		//3��ת������Ʒ�б�
		this.findAllProduct(request, response);
	}
}
