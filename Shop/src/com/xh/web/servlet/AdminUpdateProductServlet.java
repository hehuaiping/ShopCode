package com.xh.web.servlet;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.xh.domain.Category;
import com.xh.domain.Product;
import com.xh.factory.BasicFactory;
import com.xh.service.AdminService;
import com.xh.utils.IOUtil;

public class AdminUpdateProductServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//更新商品信息
			AdminService service  = BasicFactory.getBasicFactory().getService(AdminService.class);
			Product product = new Product();
			String encode = this.getServletContext().getInitParameter("encode");
			
			//封装普通字段项数据的map
			Map<String,Object> paramMap = new HashMap<String, Object>();
		
			//1、创建文件上传工厂
			DiskFileItemFactory factory = new DiskFileItemFactory();
			//利用工厂创建文件上传核心类
			ServletFileUpload upload = new ServletFileUpload(factory);
			//解析request
			List<FileItem> fileItemList = upload.parseRequest(request);
			//遍历文件传项集合
			for(FileItem item : fileItemList) {
				//判断当前项是否是普通字段项
				if(item.isFormField()) {
					//将普通字段项封装到参数集合中
					paramMap.put(item.getFieldName(), item.getString(encode));
				}else {
					//判断用户否选择了图片
					if(item.getSize()!=0) {
						//文件上传项
						//获取存放上传文件的文件夹路径
						String path = this.getServletContext().getRealPath("upload");
						//获取上传文件名
						String filename = item.getName();
						//获取上传输入流
						InputStream in = item.getInputStream();
						//获取本地输出流,并创建文件
						FileOutputStream out = new FileOutputStream(path+"/"+filename);
						//对接流
						IOUtil.IoToOut(in, out);
						//关闭流
						IOUtil.close(in, out);
						
						//将文件存放的相对路径放封装到参数集合中
						paramMap.put("pimage", "upload/"+filename);
					}else {
						//没有更改图片,获取原来的图片路径
						paramMap.put("pimage", service.findProductByPid((String)paramMap.get("pid")).get("pimage"));
					}
				}
			}
			
			//将数据封装到bean中
			BeanUtils.copyProperties(product, paramMap);
			//封装product中的category数据
			Category category = new Category();
			category.setCid((String)paramMap.get("cid"));
			//将category放入集合
			product.setCategory(category);
			
			//2、调用service执行更新商品方法
			service.updateProduct(product);
			
			//3、转发到查询商品的servetl获取该商品的最新的信息,并提示用户修改成功
			request.setAttribute("msg", "修改商品成功");
			request.setAttribute("method", "findProductByPid");
			request.setAttribute("pid", product.getPid());
			request.getRequestDispatcher("/AdminServlet").forward(request, response);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
