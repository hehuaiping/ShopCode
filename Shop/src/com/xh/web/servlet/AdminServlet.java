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
	// 处理Ajax请求商品分类列表数据
	public void findAllCategory(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		AdminService service = BasicFactory.getBasicFactory().getService(AdminService.class);

		// 1、调用Service中查找商品分类列表的方法
		List<Category> categoryList = service.findAllCategory();
		// 2、将数据转换为json格式数据
		String json = new Gson().toJson(categoryList);
		// 3、将数据返回
		response.getWriter().write(json);
	}

	// 查找所有订单
	public void findAllOrders(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		AdminService service = BasicFactory.getBasicFactory().getService(AdminService.class);

		// 1、调用service查找所有订单
		List<Orders> ordersList = service.findAllOrders();
		// 2、将数据存入session域,重定向到展示订单页面
		request.getSession().setAttribute("ordersList", ordersList);
		response.sendRedirect(request.getContextPath() + "/admin/order/list.jsp");
	}

	// 根据订单ID查找订单项
	public void findOrderItemByOid(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		AdminService service = BasicFactory.getBasicFactory().getService(AdminService.class);

		// 1、获取订单ID
		String oid = request.getParameter("oid");
		// 2、调用service查询该订单下的所有订单项
		List<Map<String, Object>> listMap = service.findOrderItemByOid(oid);
		// 3、将数据封装成json数据返回
		String json = new Gson().toJson(listMap);
		// System.out.println(json);
		// [{"pimage":"products/1/c_0001.jpg","shop_price":1299.0,"pname":"小米 4c
		// 标准版","subtotal":1299.0,"count":1},{"pimage":"products/1/c_0013.jpg","shop_price":1799.0,"pname":"努比亚（nubia）My
		// 布拉格","subtotal":1799.0,"count":1}]
		response.getWriter().write(json);

	}

	// 登录用户
	public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		AdminService service = BasicFactory.getBasicFactory().getService(AdminService.class);
		// 1、获取用户名密码
		String username = request.getParameter("username");
		String password = MD5Utils.md5(request.getParameter("password"));
		// 2、调用service中根据用户名密码查找用户的方法
		User user = service.findUserByNaAndPwd(username, password);
		if (user != null) {
			// 登录成功
			request.getSession().setAttribute("user", user);
			request.getRequestDispatcher("/admin/home.jsp").forward(request, response);
			return;
		}
		// 登录不成功
		response.getWriter().write("用户名或密码错误");

	}

	// 查询所有商品
	public void findAllProduct(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		AdminService service = BasicFactory.getBasicFactory().getService(AdminService.class);

		// 1、调用service中查询全部订单的方法
		List<Product> prodList = service.findAllProduct();
		// 2、将数据存入request域返回
		request.setAttribute("prodList", prodList);
		request.getRequestDispatcher("/admin/product/list.jsp").forward(request, response);
	}

	// 根据商品ID查询商品
	public void findProductByPid(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			AdminService service = BasicFactory.getBasicFactory().getService(AdminService.class);
			Product product = new Product();

			// 1、获取商品ID
			String pid = request.getParameter("pid");
			if (null == pid) {
				// servlet转发过来,获取request域中pid
				pid = (String) request.getAttribute("pid");
			}
			// 2、调用service中根据商品ID查询商品的方法
			Map<String, Object> prodMap = service.findProductByPid(pid);
			// --封装数据
			BeanUtils.copyProperties(product, prodMap);
			// --封装category数据
			Category category = new Category();
			category.setCid((String) prodMap.get("cid"));
			// --将category封装到product
			product.setCategory(category);
			// 3、将返回商品数据存入request域中,带回页面展示
			request.setAttribute("prod", product);
			request.getRequestDispatcher("/admin/product/edit.jsp").forward(request, response);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 查询所有商品种类
	public void findAllCategoryJsp(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		AdminService service = BasicFactory.getBasicFactory().getService(AdminService.class);

		// 1、调用Service中查找商品分类列表的方法
		List<Category> categoryList = service.findAllCategory();
		// 2、将数据存入request域中,转发到展示页面
		request.setAttribute("categoryList", categoryList);
		request.getRequestDispatcher("/admin/category/list.jsp").forward(request, response);
	}

	// 按照商品种类ID查询商品
	public void findCategoryByCid(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		AdminService service = BasicFactory.getBasicFactory().getService(AdminService.class);

		// 1、获取商品ID
		String cid = request.getParameter("cid");
		// 2、调用service查找查找商品种类数据
		Category category = service.findCategoryByCid(cid);
		// 3、将商品种类数据存入request,转发到页面展示
		request.setAttribute("category", category);
		request.getRequestDispatcher("/admin/category/edit.jsp").forward(request, response);
	}

	// 根据商品种类ID删除商品种类
	public void deleteCategoryByCid(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		AdminService service = BasicFactory.getBasicFactory().getService(AdminService.class);

		// 1、获取商品ID
		String cid = request.getParameter("cid");
		// 2、调用service根据商品种类ID删除商品种类的方法
		service.deleteCategoryByCid(cid);
		// 3、转发到商品种类管理页面,展示最新的数据
		this.findAllCategoryJsp(request, response);
	}
	
	//添加商品种类
	public void addCategory(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			AdminService service = BasicFactory.getBasicFactory().getService(AdminService.class);
			
			//1、获取商品种类ID,商品种类名称封装到bean中
			Category category = new Category();
			BeanUtils.populate(category, request.getParameterMap());
			//2、调用service中的添加方法
			service.addCategory(category);
			//3、转发商品种类管理页面
			this.findAllCategoryJsp(request, response);
		}catch(Exception e) {
			e.printStackTrace();
		}
	
	}
	
	// 根据商品种类ID更新商品种类信息
	public void updateCategoryByCid(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			AdminService service = BasicFactory.getBasicFactory().getService(AdminService.class);
			
			//1、获取商品种类数据,将数据封装到bean中
			Category category = new Category();
			BeanUtils.populate(category, request.getParameterMap());
			//2、调用service中根据商品种类Id更新商品种类的方法
			service.updateCategoryByCid(category);
			//3、转发到商品种类管理页面
			this.findAllCategoryJsp(request, response);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//根据商品ID删除商品
	public void deleteProductByPid(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		AdminService service = BasicFactory.getBasicFactory().getService(AdminService.class);
		
		//1、获取商品ID
		String pid = request.getParameter("pid");
		//2、调用service执行删除操作
		service.deleteProducyByPid(pid);
		//3、转发到商品列表
		this.findAllProduct(request, response);
	}
}
