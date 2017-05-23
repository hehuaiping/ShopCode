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
		//��Ӧ����
		request.setCharacterEncoding(encode);
		HttpServletResponse res = (HttpServletResponse) response;
		res.setCharacterEncoding(encode);
		res.setContentType("text/html;charset=utf-8");
		
		//װ�����ģʽ����request�л�ȡ�����ķ���\����
		chain.doFilter(new MyRequest((HttpServletRequest) request), response);
		
	}

	public void init(FilterConfig config) throws ServletException {
		//��ȡServletContext
		servletContext = config.getServletContext();
		//��ȡ�����ʽ
		encode = servletContext.getInitParameter("encode");
		//��ȡ�����ʽ
		decode = servletContext.getInitParameter("decode");
	}
	
	//�ڲ���ʵ��װ�����ģʽ
	private class MyRequest extends HttpServletRequestWrapper {
		HttpServletRequest request = null;
		//ֻ����һ������
		boolean flag = true;
		public MyRequest(HttpServletRequest request) {
			super(request);
			this.request = request;
		}
		
		@Override
		public Map getParameterMap() {
			try {
			//�����get��ʽ�ύ������
			if(request.getMethod().equalsIgnoreCase("GET")) {
				return super.getParameterMap();
			}else if(request.getMethod().equalsIgnoreCase("POST")) {
				//������ǵ�һ�ν�������
				if(flag) {					
					//�Ȼ�ȡ����
					Map<String,String[]> map = super.getParameterMap();
					//����map����
					for(Entry<String, String[]> entry : map.entrySet()) {
						String[] values = entry.getValue();
						//����Ͳ����õ�����,����������ʱ�����޸�ֵ
						for(int i = 0 ; i < values.length ; i++ ) {
							//����������Ԫ�ذ��ձ������
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
