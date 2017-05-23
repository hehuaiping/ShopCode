package com.xh.web.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xh.domain.Page;
import com.xh.domain.Product;
import com.xh.factory.BasicFactory;
import com.xh.service.ProductService;

public class ProductServlet extends BasicServlet {

	// 商品详细信息
	public void productInfo(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ProductService service = BasicFactory.getBasicFactory().getService(ProductService.class);

		// 1、获取商品ID
		String pid = request.getParameter("pid");
		String thisPage = request.getParameter("thisPage");
		String cid = request.getParameter("cid");
		// 2、调用Servic中根据商品ID查询商品的方法
		Product prod = service.findProductById(pid);
		// 3、将返回的商品信息存入request域，转发到jsp页面
		request.setAttribute("prod", prod);
		request.setAttribute("thisPage", thisPage);
		request.setAttribute("cid", cid);

		// 获取客户端的cookie
		String pids = pid;
		Cookie[] cookies = request.getCookies();
		// 判断cookie是否为空
		if (cookies != null) {
			// 判断指定的cookie是否存在
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("pids")) {
					// 获取cookie的值
					pids = cookie.getValue(); // "1-2-3"

					// 将cookie的值分割成数组
					String[] split = pids.split("-"); // {1,2,3}
					// 将数组转成集合对象
					List<String> aslist = Arrays.asList(split); // [1,2,3]
					// 转换集合
					LinkedList<String> list = new LinkedList<String>(aslist);

					// 判断集合是否包含当前PID
					if (list.contains(pid)) {
						// 包含
						list.remove(pid);
						list.addFirst(pid);
					} else {
						// 不包含
						list.addFirst(pid);
					}

					// 将[1,2,3]转成"1-2-3"
					StringBuffer sb = new StringBuffer();
					for (int i = 0; i < list.size(); i++) {
						sb.append(list.get(i));
						sb.append("-");
					}

					// 截取多余的 -
					pids = sb.substring(0, sb.length() - 1);

				}
			}
		}

		// 发送cookie
		Cookie cookie = new Cookie("pids", pids);
		cookie.setMaxAge(100 * 3600);
		cookie.setPath(request.getContextPath());
		response.addCookie(cookie);

		request.getRequestDispatcher("/product_info.jsp").forward(request, response);
	}

	// 根据商品ID查询商品列表
	public void productListByCid(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ProductService service = BasicFactory.getBasicFactory().getService(ProductService.class);

		// 1、获取商品类别
		String cid = request.getParameter("cid");
		// 获取当前要显示的页码
		String p = request.getParameter("thisPage");
		if (p == null) {
			p = "1";
		}
		// 当前页
		int thisPage = Integer.parseInt(p);
		// 每页显示多少条
		int rowPage = 12;
		// 2、调用Service中按商品类别查找商品的方法
		Page<Product> page = service.findProductListByCid(cid, thisPage, rowPage);

		// 保存商品历史记录
		List<Product> historyList = new ArrayList<Product>();
		// 获取浏览记录cookie信息
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			// cookie存在
			for (Cookie cookie : cookies) {
				// 判断cookie是否是pids
				if (cookie.getName().equals("pids")) {
					// 取值
					String pids = cookie.getValue();
					String[] split = pids.split("-");
					for (int i = 0; i < split.length; i++) {
						// 调用Service中根据商品ID查找商品的方法
						Product prod = service.findProductById(split[i]);
						historyList.add(prod);
					}
				}
			}
		}

		// 3、将商品信息存入request域中,带到jsp页面进行展示
		request.setAttribute("page", page);
		request.setAttribute("cid", cid);
		request.setAttribute("historyList", historyList);

		request.getRequestDispatcher("/product_list.jsp").forward(request, response);
	}
	
	//根据商品ID删除商品
	public void deleteProductById(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ProductService service = BasicFactory.getBasicFactory().getService(ProductService.class);
		//获取购物车集合
		Map<Product,Integer> cartMap =  (Map<Product, Integer>) request.getSession().getAttribute("cartMap");
		
		//1、获取当前商品ID
		String pid = request.getParameter("pid");
		//2、调用service查询到商品
		Product product = service.findProductById(pid);
		//3、从购物车中删除该商品
		if(cartMap!=null) {
			cartMap.remove(product);
		}
		//4、转发到购物车
		request.getRequestDispatcher("/cart.jsp").forward(request, response);
	}
}
