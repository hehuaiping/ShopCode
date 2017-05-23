package com.xh.web.servlet;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

import com.xh.domain.Category;
import com.xh.domain.Product;
import com.xh.factory.BasicFactory;
import com.xh.service.AdminService;
import com.xh.utils.IOUtil;

public class AdminProductServlet extends HttpServlet {
	@SuppressWarnings("all")
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			// 目的:封装product数据,将客户端图片保存到本地磁盘
			AdminService service = BasicFactory.getBasicFactory().getService(AdminService.class);
			Product product = new Product();
			String encode = this.getServletContext().getInitParameter("encode");
			//封装普通字段的集合
			Map<String,Object> paramMap = new HashMap<String, Object>(); 
			
			// 创建文件上传工厂
			DiskFileItemFactory factory = new DiskFileItemFactory();
			// 利用工厂创建文件上传核心类
			ServletFileUpload upload = new ServletFileUpload(factory);
			// 解析request
			List<FileItem> parseRequest = upload.parseRequest(request);
			//循环文件上传项
			for(FileItem item : parseRequest ) {
				//如果当前是普通字段项
				if(item.isFormField()) {
					paramMap.put(item.getFieldName(), item.getString(encode));
				}else {
					//当前是文件上传项
					//获取上传文件夹的硬盘的路径
					String path = this.getServletContext().getRealPath("upload");
					//获取上传文件名
					String filename = item.getName();
					//获取输入流
				 	InputStream in = item.getInputStream();
				 	//获取本地输出流
				 	FileOutputStream out = new FileOutputStream(path+"/"+filename);
				 	//对接流
				 	IOUtils.copy(in, out);
				 	//关闭流 
				 	IOUtil.close(in, out);
				 	
				 	//将文件存放的相对路径存入封装数据的集合
				 	paramMap.put("pimage", "upload/"+filename);
				}
			}
			
			//封装数据
			BeanUtils.copyProperties(product, paramMap);
			
			//封装其他数据 private String pid;private int is_hot;private int pflag;private Category category;
			product.setPid(UUID.randomUUID().toString());
			product.setIs_hot(1);
			product.setPflag(0);
			Category category = new Category();
			category.setCid(paramMap.get("cid").toString());
			product.setCategory(category);
			
			//调用service处理prodcut
			service.addProduct(product);
			
		} catch (SQLException e) {
			//添加失败
			response.getWriter().write("非常抱歉添加失败");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
