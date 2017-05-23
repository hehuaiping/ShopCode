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

	// ��Ʒ��ϸ��Ϣ
	public void productInfo(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ProductService service = BasicFactory.getBasicFactory().getService(ProductService.class);

		// 1����ȡ��ƷID
		String pid = request.getParameter("pid");
		String thisPage = request.getParameter("thisPage");
		String cid = request.getParameter("cid");
		// 2������Servic�и�����ƷID��ѯ��Ʒ�ķ���
		Product prod = service.findProductById(pid);
		// 3�������ص���Ʒ��Ϣ����request��ת����jspҳ��
		request.setAttribute("prod", prod);
		request.setAttribute("thisPage", thisPage);
		request.setAttribute("cid", cid);

		// ��ȡ�ͻ��˵�cookie
		String pids = pid;
		Cookie[] cookies = request.getCookies();
		// �ж�cookie�Ƿ�Ϊ��
		if (cookies != null) {
			// �ж�ָ����cookie�Ƿ����
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("pids")) {
					// ��ȡcookie��ֵ
					pids = cookie.getValue(); // "1-2-3"

					// ��cookie��ֵ�ָ������
					String[] split = pids.split("-"); // {1,2,3}
					// ������ת�ɼ��϶���
					List<String> aslist = Arrays.asList(split); // [1,2,3]
					// ת������
					LinkedList<String> list = new LinkedList<String>(aslist);

					// �жϼ����Ƿ������ǰPID
					if (list.contains(pid)) {
						// ����
						list.remove(pid);
						list.addFirst(pid);
					} else {
						// ������
						list.addFirst(pid);
					}

					// ��[1,2,3]ת��"1-2-3"
					StringBuffer sb = new StringBuffer();
					for (int i = 0; i < list.size(); i++) {
						sb.append(list.get(i));
						sb.append("-");
					}

					// ��ȡ����� -
					pids = sb.substring(0, sb.length() - 1);

				}
			}
		}

		// ����cookie
		Cookie cookie = new Cookie("pids", pids);
		cookie.setMaxAge(100 * 3600);
		cookie.setPath(request.getContextPath());
		response.addCookie(cookie);

		request.getRequestDispatcher("/product_info.jsp").forward(request, response);
	}

	// ������ƷID��ѯ��Ʒ�б�
	public void productListByCid(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ProductService service = BasicFactory.getBasicFactory().getService(ProductService.class);

		// 1����ȡ��Ʒ���
		String cid = request.getParameter("cid");
		// ��ȡ��ǰҪ��ʾ��ҳ��
		String p = request.getParameter("thisPage");
		if (p == null) {
			p = "1";
		}
		// ��ǰҳ
		int thisPage = Integer.parseInt(p);
		// ÿҳ��ʾ������
		int rowPage = 12;
		// 2������Service�а���Ʒ��������Ʒ�ķ���
		Page<Product> page = service.findProductListByCid(cid, thisPage, rowPage);

		// ������Ʒ��ʷ��¼
		List<Product> historyList = new ArrayList<Product>();
		// ��ȡ�����¼cookie��Ϣ
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			// cookie����
			for (Cookie cookie : cookies) {
				// �ж�cookie�Ƿ���pids
				if (cookie.getName().equals("pids")) {
					// ȡֵ
					String pids = cookie.getValue();
					String[] split = pids.split("-");
					for (int i = 0; i < split.length; i++) {
						// ����Service�и�����ƷID������Ʒ�ķ���
						Product prod = service.findProductById(split[i]);
						historyList.add(prod);
					}
				}
			}
		}

		// 3������Ʒ��Ϣ����request����,����jspҳ�����չʾ
		request.setAttribute("page", page);
		request.setAttribute("cid", cid);
		request.setAttribute("historyList", historyList);

		request.getRequestDispatcher("/product_list.jsp").forward(request, response);
	}
	
	//������ƷIDɾ����Ʒ
	public void deleteProductById(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ProductService service = BasicFactory.getBasicFactory().getService(ProductService.class);
		//��ȡ���ﳵ����
		Map<Product,Integer> cartMap =  (Map<Product, Integer>) request.getSession().getAttribute("cartMap");
		
		//1����ȡ��ǰ��ƷID
		String pid = request.getParameter("pid");
		//2������service��ѯ����Ʒ
		Product product = service.findProductById(pid);
		//3���ӹ��ﳵ��ɾ������Ʒ
		if(cartMap!=null) {
			cartMap.remove(product);
		}
		//4��ת�������ﳵ
		request.getRequestDispatcher("/cart.jsp").forward(request, response);
	}
}
