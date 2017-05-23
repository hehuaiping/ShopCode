package com.xh.web.servlet;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xh.domain.Cart;
import com.xh.domain.CartItem;
import com.xh.domain.Product;
import com.xh.factory.BasicFactory;
import com.xh.service.ProductService;

public class CartServlet extends BasicServlet {

	// 添加购物车
	public void addCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ProductService service = BasicFactory.getBasicFactory().getService(ProductService.class);
		// 获取购物车
		Cart cart = (Cart) request.getSession().getAttribute("cart");

		if (cart != null) {
			// 获取当前商品ID
			String pid = request.getParameter("pid");
			// 获取当前商品购买数量
			String buynum = request.getParameter("buynum");

			// 封装数据
			// 调用service根据当前商品Id查询商品
			Product prod = service.findProductById(pid);
			// 创建购物车项
			CartItem cartItem = new CartItem();
			//购买数量
			cartItem.setBuynum(Integer.parseInt(buynum));
			//商品ID
			cartItem.setProduct(prod);
			//订单金额
			cartItem.setTotlesum(cartItem.getBuynum() * prod.getShop_price());

			

			Map<String, CartItem> cartMap = cart.getCartMap();
			// 判断购物车中是否已经存在了该是商品 如果已经存在修改购买数量
			if (cartMap.containsKey(pid)) {
				CartItem cim = cartMap.get(pid);
				
				// 已经存在该商品,取出原来的购买数量
				int num = cim.getBuynum();
				// 重新设置值 原来购买的数量+现在购买的数量
				cim.setBuynum(num + Integer.parseInt(buynum));	
				// 计算现在购买的订单金额
				cim.setTotlesum(cim.getBuynum()*prod.getShop_price());
			} else {
				// 购物车中没有该商品
				cartMap.put(prod.getPid(), cartItem);
			}
			
			// 商品总计
			cart.setTotleMoney(cart.getTotleMoney() + cartItem.getTotlesum());
			
			// 将数据放到购物车中
			cart.setCartMap(cartMap);

			// 将处理好的数据放回session中
			request.getSession().setAttribute("cart", cart);

			// 4、重定向到购物车展示
			response.sendRedirect(request.getContextPath() + "/cart.jsp");
		}

	}

	// 删除单个商品
	public void deleteProductById(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ProductService service = BasicFactory.getBasicFactory().getService(ProductService.class);
		// 获取到当前商品的ID
		String pid = request.getParameter("pid");
		// 获取购物车
		Cart cart = (Cart) request.getSession().getAttribute("cart");
		// 减去该商品在总金额的金额
		double totlesum = cart.getCartMap().get(pid).getTotlesum();
		cart.setTotleMoney(cart.getTotleMoney()-totlesum);
		// 根据商品ID删除该购物车项
		cart.getCartMap().remove(pid);
		
		// 转发到购物车页面
		request.getRequestDispatcher("/cart.jsp").forward(request, response);
	}

	// 清空购物车
	public void clearCart(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ProductService service = BasicFactory.getBasicFactory().getService(ProductService.class);
		// 获取购物车
		Cart cart = (Cart) request.getSession().getAttribute("cart");
		// 清空购物车
		cart.getCartMap().clear();
		cart.setTotleMoney(0);
		// 转发到购物车页面
		request.getRequestDispatcher("/cart.jsp").forward(request, response);
	}

}
