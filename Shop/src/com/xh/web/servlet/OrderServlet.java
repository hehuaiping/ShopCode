package com.xh.web.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.xh.domain.Cart;
import com.xh.domain.CartItem;
import com.xh.domain.OrderItem;
import com.xh.domain.Orders;
import com.xh.domain.User;
import com.xh.factory.BasicFactory;
import com.xh.service.OrderService;
import com.xh.utils.PaymentUtil;

public class OrderServlet extends BasicServlet {
	//提交订单
	public void submitOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		OrderService service = BasicFactory.getBasicFactory().getService(OrderService.class);
		User user = (User) request.getSession().getAttribute("user");
		Cart cart = (Cart) request.getSession().getAttribute("cart");
		Orders orders = new Orders();
		
		//判断用户是否登录
		if(user== null) {
			//提示用户登录
			response.getWriter().write("请您登录以后再操作该功能模块....,3秒后回到登录页面");
			response.setHeader("refresh", "3;url="+request.getContextPath()+"/login.jsp");
			return;
		}
		if(cart!=null) {
			//封装orders数据
			orders.setOid(UUID.randomUUID().toString()); //订单Id
			orders.setTotal(cart.getTotleMoney());//订单总金额
			orders.setState(0);//订单状态
			
			orders.setAddress("");//收货地址
			orders.setName("");//收货人姓名
			orders.setTelephone("");//收货人联系方式
			
			orders.setUser(user);//该订单属于那个用户
			
			//封装订单项数据
			//获取购物车中的购物项,将购物项数据转换成OrderItem数据
			Map<String,CartItem> cartMap = cart.getCartMap();
			//存订单项的集合
			List<OrderItem> orderList = new ArrayList<OrderItem>();
			
			//遍历购物车,获取购物项
			for(Entry<String,CartItem> entry : cartMap.entrySet()) {
				//创建一个新的订单项
				OrderItem orderItem = new OrderItem();
				//封装订单项数据
				orderItem.setItemid(UUID.randomUUID().toString());
				orderItem.setCount(entry.getValue().getBuynum());	//订单项购买数量
				orderItem.setSubtotal(entry.getValue().getTotlesum());	//订单项金额
				orderItem.setProduct(entry.getValue().getProduct()); //订单项中的商品
				orderItem.setOrders(orders);	//该订单项属于那个订单
				
				//将每一个订单项存入集合中
				orderList.add(orderItem);
			}			
			//将订单项集合放入orders中
			orders.setOrderItem(orderList);
			
			//2、调用service中生成订单的方法
			service.submitOrder(orders);
			
			//3、清空购物车
			cartMap.clear();
			
			//4、将封装好的数据存入session域
			request.getSession().setAttribute("orders", orders);
			
			//5、重定向到订单信息页面
			response.sendRedirect(request.getContextPath()+"/order_info.jsp");
		}
	}
	
	//确认订单
	public void confirmOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		OrderService service = BasicFactory.getBasicFactory().getService(OrderService.class);
		try {
			//1、更新收货人信息
			Orders orders = new Orders();
			BeanUtils.populate(orders, request.getParameterMap());
			service.updateOrdersAddress(orders);
			
			//2、调用在线支付功能,支付成功,回调方法中修改订单的 状态
			// 获得 支付必须基本数据
			String order_id = request.getParameter("oid");
			//订单金额从后台获取,不要相信前台提交的数据,调用OrderServoce中的根据订单ID查询订单金额的方法
			//Orders order = service.findOrdersByOid(oid); //获取商品
			//String money = order.getTotal();  //获取订单金额
			String money = "0.01";
			// 银行
			String pd_FrpId = request.getParameter("pd_FrpId");

			// 发给支付公司需要哪些数据
			String p0_Cmd = "Buy";
			String p1_MerId = ResourceBundle.getBundle("merchantInfo").getString("p1_MerId");
			String p2_Order = order_id;
			String p3_Amt = money;
			String p4_Cur = "CNY";
			String p5_Pid = "";
			String p6_Pcat = "";
			String p7_Pdesc = "";
			// 支付成功回调地址 ---- 第三方支付公司会访问、用户访问
			// 第三方支付可以访问网址  CallbackServlet写支付成功的处理逻辑
			String p8_Url = "http://localhost/Shop/CallbackServlet"; //公网上访问不到这个网址,域名在DNS服务器上没有
			String p9_SAF = "";
			String pa_MP = "";
			String pr_NeedResponse = "1";
			// 加密hmac 需要密钥
			
			String keyValue = ResourceBundle.getBundle("merchantInfo").getString("keyValue");
			String hmac = PaymentUtil.buildHmac(p0_Cmd, p1_MerId, p2_Order, p3_Amt, p4_Cur, p5_Pid, p6_Pcat, p7_Pdesc,
					p8_Url, p9_SAF, pa_MP, pd_FrpId, pr_NeedResponse, keyValue);
			// 生成url --- 重定向到支付页面
			String url = "https://www.yeepay.com/app-merchant-proxy/node?pd_FrpId="+pd_FrpId+
					"&p0_Cmd="+p0_Cmd+
					"&p1_MerId="+p1_MerId+
					"&p2_Order="+p2_Order+
					"&p3_Amt="+p3_Amt+
					"&p4_Cur="+p4_Cur+
					"&p5_Pid="+p5_Pid+
					"&p6_Pcat="+p6_Pcat+
					"&p7_Pdesc="+p7_Pdesc+
					"&p8_Url="+p8_Url+
					"&p9_SAF="+p9_SAF+
					"&pa_MP="+pa_MP+
					"&pr_NeedResponse="+pr_NeedResponse+
					"&hmac="+hmac;
			
			
			response.sendRedirect(url);
			
			//3、提示用户支付成功
		}catch(Exception e) {
			e.printStackTrace();
		}
	
	}
	
	//查询订单
	public void allOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		OrderService service = BasicFactory.getBasicFactory().getService(OrderService.class);
		User user = (User) request.getSession().getAttribute("user");
		//1、判断用户是否登录
		if(user== null) {
			//提示用户登录
			response.getWriter().write("请您登录以后再操作该功能模块....,3秒后回到登录页面");
			response.setHeader("refresh", "3;url="+request.getContextPath()+"/login.jsp");
			return;
		}
		
		//2、查询当前用户下的所有订单
		List<Orders> ordersList = service.findOrdersByUid(user.getUid());
		
		//3、将封装号的订单集合存入request域中
		request.setAttribute("ordersList", ordersList);
		
		//4、转发到jsp页面展示数据
		request.getRequestDispatcher("/order_list.jsp").forward(request, response);
	}
	
	public void deleteOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		OrderService service = BasicFactory.getBasicFactory().getService(OrderService.class);
		
		//1、获取订单项ID
		String oid = request.getParameter("oid");
		//2、调用Service中根据订单项ID删除订单项
		service.deleteOrderByOid(oid);
		//3、调用allOrder获取最新的订单信息
		this.allOrder(request, response);
	}
}
