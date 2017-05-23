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
	//�ύ����
	public void submitOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		OrderService service = BasicFactory.getBasicFactory().getService(OrderService.class);
		User user = (User) request.getSession().getAttribute("user");
		Cart cart = (Cart) request.getSession().getAttribute("cart");
		Orders orders = new Orders();
		
		//�ж��û��Ƿ��¼
		if(user== null) {
			//��ʾ�û���¼
			response.getWriter().write("������¼�Ժ��ٲ����ù���ģ��....,3���ص���¼ҳ��");
			response.setHeader("refresh", "3;url="+request.getContextPath()+"/login.jsp");
			return;
		}
		if(cart!=null) {
			//��װorders����
			orders.setOid(UUID.randomUUID().toString()); //����Id
			orders.setTotal(cart.getTotleMoney());//�����ܽ��
			orders.setState(0);//����״̬
			
			orders.setAddress("");//�ջ���ַ
			orders.setName("");//�ջ�������
			orders.setTelephone("");//�ջ�����ϵ��ʽ
			
			orders.setUser(user);//�ö��������Ǹ��û�
			
			//��װ����������
			//��ȡ���ﳵ�еĹ�����,������������ת����OrderItem����
			Map<String,CartItem> cartMap = cart.getCartMap();
			//�涩����ļ���
			List<OrderItem> orderList = new ArrayList<OrderItem>();
			
			//�������ﳵ,��ȡ������
			for(Entry<String,CartItem> entry : cartMap.entrySet()) {
				//����һ���µĶ�����
				OrderItem orderItem = new OrderItem();
				//��װ����������
				orderItem.setItemid(UUID.randomUUID().toString());
				orderItem.setCount(entry.getValue().getBuynum());	//�����������
				orderItem.setSubtotal(entry.getValue().getTotlesum());	//��������
				orderItem.setProduct(entry.getValue().getProduct()); //�������е���Ʒ
				orderItem.setOrders(orders);	//�ö����������Ǹ�����
				
				//��ÿһ����������뼯����
				orderList.add(orderItem);
			}			
			//��������Ϸ���orders��
			orders.setOrderItem(orderList);
			
			//2������service�����ɶ����ķ���
			service.submitOrder(orders);
			
			//3����չ��ﳵ
			cartMap.clear();
			
			//4������װ�õ����ݴ���session��
			request.getSession().setAttribute("orders", orders);
			
			//5���ض��򵽶�����Ϣҳ��
			response.sendRedirect(request.getContextPath()+"/order_info.jsp");
		}
	}
	
	//ȷ�϶���
	public void confirmOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		OrderService service = BasicFactory.getBasicFactory().getService(OrderService.class);
		try {
			//1�������ջ�����Ϣ
			Orders orders = new Orders();
			BeanUtils.populate(orders, request.getParameterMap());
			service.updateOrdersAddress(orders);
			
			//2����������֧������,֧���ɹ�,�ص��������޸Ķ����� ״̬
			// ��� ֧�������������
			String order_id = request.getParameter("oid");
			//�������Ӻ�̨��ȡ,��Ҫ����ǰ̨�ύ������,����OrderServoce�еĸ��ݶ���ID��ѯ�������ķ���
			//Orders order = service.findOrdersByOid(oid); //��ȡ��Ʒ
			//String money = order.getTotal();  //��ȡ�������
			String money = "0.01";
			// ����
			String pd_FrpId = request.getParameter("pd_FrpId");

			// ����֧����˾��Ҫ��Щ����
			String p0_Cmd = "Buy";
			String p1_MerId = ResourceBundle.getBundle("merchantInfo").getString("p1_MerId");
			String p2_Order = order_id;
			String p3_Amt = money;
			String p4_Cur = "CNY";
			String p5_Pid = "";
			String p6_Pcat = "";
			String p7_Pdesc = "";
			// ֧���ɹ��ص���ַ ---- ������֧����˾����ʡ��û�����
			// ������֧�����Է�����ַ  CallbackServletд֧���ɹ��Ĵ����߼�
			String p8_Url = "http://localhost/Shop/CallbackServlet"; //�����Ϸ��ʲ��������ַ,������DNS��������û��
			String p9_SAF = "";
			String pa_MP = "";
			String pr_NeedResponse = "1";
			// ����hmac ��Ҫ��Կ
			
			String keyValue = ResourceBundle.getBundle("merchantInfo").getString("keyValue");
			String hmac = PaymentUtil.buildHmac(p0_Cmd, p1_MerId, p2_Order, p3_Amt, p4_Cur, p5_Pid, p6_Pcat, p7_Pdesc,
					p8_Url, p9_SAF, pa_MP, pd_FrpId, pr_NeedResponse, keyValue);
			// ����url --- �ض���֧��ҳ��
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
			
			//3����ʾ�û�֧���ɹ�
		}catch(Exception e) {
			e.printStackTrace();
		}
	
	}
	
	//��ѯ����
	public void allOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		OrderService service = BasicFactory.getBasicFactory().getService(OrderService.class);
		User user = (User) request.getSession().getAttribute("user");
		//1���ж��û��Ƿ��¼
		if(user== null) {
			//��ʾ�û���¼
			response.getWriter().write("������¼�Ժ��ٲ����ù���ģ��....,3���ص���¼ҳ��");
			response.setHeader("refresh", "3;url="+request.getContextPath()+"/login.jsp");
			return;
		}
		
		//2����ѯ��ǰ�û��µ����ж���
		List<Orders> ordersList = service.findOrdersByUid(user.getUid());
		
		//3������װ�ŵĶ������ϴ���request����
		request.setAttribute("ordersList", ordersList);
		
		//4��ת����jspҳ��չʾ����
		request.getRequestDispatcher("/order_list.jsp").forward(request, response);
	}
	
	public void deleteOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		OrderService service = BasicFactory.getBasicFactory().getService(OrderService.class);
		
		//1����ȡ������ID
		String oid = request.getParameter("oid");
		//2������Service�и��ݶ�����IDɾ��������
		service.deleteOrderByOid(oid);
		//3������allOrder��ȡ���µĶ�����Ϣ
		this.allOrder(request, response);
	}
}
