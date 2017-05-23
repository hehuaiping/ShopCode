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

	// ��ӹ��ﳵ
	public void addCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ProductService service = BasicFactory.getBasicFactory().getService(ProductService.class);
		// ��ȡ���ﳵ
		Cart cart = (Cart) request.getSession().getAttribute("cart");

		if (cart != null) {
			// ��ȡ��ǰ��ƷID
			String pid = request.getParameter("pid");
			// ��ȡ��ǰ��Ʒ��������
			String buynum = request.getParameter("buynum");

			// ��װ����
			// ����service���ݵ�ǰ��ƷId��ѯ��Ʒ
			Product prod = service.findProductById(pid);
			// �������ﳵ��
			CartItem cartItem = new CartItem();
			//��������
			cartItem.setBuynum(Integer.parseInt(buynum));
			//��ƷID
			cartItem.setProduct(prod);
			//�������
			cartItem.setTotlesum(cartItem.getBuynum() * prod.getShop_price());

			

			Map<String, CartItem> cartMap = cart.getCartMap();
			// �жϹ��ﳵ���Ƿ��Ѿ������˸�����Ʒ ����Ѿ������޸Ĺ�������
			if (cartMap.containsKey(pid)) {
				CartItem cim = cartMap.get(pid);
				
				// �Ѿ����ڸ���Ʒ,ȡ��ԭ���Ĺ�������
				int num = cim.getBuynum();
				// ��������ֵ ԭ�����������+���ڹ��������
				cim.setBuynum(num + Integer.parseInt(buynum));	
				// �������ڹ���Ķ������
				cim.setTotlesum(cim.getBuynum()*prod.getShop_price());
			} else {
				// ���ﳵ��û�и���Ʒ
				cartMap.put(prod.getPid(), cartItem);
			}
			
			// ��Ʒ�ܼ�
			cart.setTotleMoney(cart.getTotleMoney() + cartItem.getTotlesum());
			
			// �����ݷŵ����ﳵ��
			cart.setCartMap(cartMap);

			// ������õ����ݷŻ�session��
			request.getSession().setAttribute("cart", cart);

			// 4���ض��򵽹��ﳵչʾ
			response.sendRedirect(request.getContextPath() + "/cart.jsp");
		}

	}

	// ɾ��������Ʒ
	public void deleteProductById(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ProductService service = BasicFactory.getBasicFactory().getService(ProductService.class);
		// ��ȡ����ǰ��Ʒ��ID
		String pid = request.getParameter("pid");
		// ��ȡ���ﳵ
		Cart cart = (Cart) request.getSession().getAttribute("cart");
		// ��ȥ����Ʒ���ܽ��Ľ��
		double totlesum = cart.getCartMap().get(pid).getTotlesum();
		cart.setTotleMoney(cart.getTotleMoney()-totlesum);
		// ������ƷIDɾ���ù��ﳵ��
		cart.getCartMap().remove(pid);
		
		// ת�������ﳵҳ��
		request.getRequestDispatcher("/cart.jsp").forward(request, response);
	}

	// ��չ��ﳵ
	public void clearCart(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ProductService service = BasicFactory.getBasicFactory().getService(ProductService.class);
		// ��ȡ���ﳵ
		Cart cart = (Cart) request.getSession().getAttribute("cart");
		// ��չ��ﳵ
		cart.getCartMap().clear();
		cart.setTotleMoney(0);
		// ת�������ﳵҳ��
		request.getRequestDispatcher("/cart.jsp").forward(request, response);
	}

}
