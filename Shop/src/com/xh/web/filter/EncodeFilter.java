package com.xh.web.filter;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

public class EncodeFilter implements Filter {
	private  ServletContext servletContext;
	private String encode;
	private String decode;

	public void destroy() {
		// TODO Auto-generated method stub

	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		//响应乱码
		request.setCharacterEncoding(encode);
		HttpServletResponse res = (HttpServletResponse) response;
		res.setCharacterEncoding(encode);
		res.setContentType("text/html;charset=utf-8");
		
		//装饰设计模式覆盖request中获取参数的方法\放行
		chain.doFilter(new MyRequest((HttpServletRequest) request), response);
		
	}

	public void init(FilterConfig config) throws ServletException {
		//获取ServletContext
		servletContext = config.getServletContext();
		//获取编码格式
		encode = servletContext.getInitParameter("encode");
		//获取解码格式
		decode = servletContext.getInitParameter("decode");
	}
	
	//内部内实现装饰设计模式
	private class MyRequest extends HttpServletRequestWrapper {
		HttpServletRequest request = null;
		//只解析一次乱码
		boolean flag = true;
		public MyRequest(HttpServletRequest request) {
			super(request);
			this.request = request;
		}
		
		@Override
		public Map getParameterMap() {
			try {
			//如果是get方式提交的数据
			if(request.getMethod().equalsIgnoreCase("GET")) {
				return super.getParameterMap();
			}else if(request.getMethod().equalsIgnoreCase("POST")) {
				//如果这是第一次解析乱码
				if(flag) {					
					//先获取乱码
					Map<String,String[]> map = super.getParameterMap();
					//遍历map集合
					for(Entry<String, String[]> entry : map.entrySet()) {
						String[] values = entry.getValue();
						//这里就不能用迭代器,迭代器遍历时不能修改值
						for(int i = 0 ; i < values.length ; i++ ) {
							//将遍历到的元素按照编码解析
							values[i] = new String(values[i].getBytes(decode),encode);
						}
					}
					flag = false;
					return map;
				}
			}else {
				return super.getParameterMap();
			}
			}catch(Exception e) {
				e.printStackTrace();
			}
			return null;
		}
		
		@Override
		public String[] getParameterValues(String name) {
			return (String[]) request.getParameterMap().get(name);
		}
		
		@Override
		public String getParameter(String name) {
			return request.getParameterMap().get(name) == null? null : request.getParameterValues(name)[0];
		}
		
		
	}  
}
