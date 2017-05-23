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
			// Ŀ��:��װproduct����,���ͻ���ͼƬ���浽���ش���
			AdminService service = BasicFactory.getBasicFactory().getService(AdminService.class);
			Product product = new Product();
			String encode = this.getServletContext().getInitParameter("encode");
			//��װ��ͨ�ֶεļ���
			Map<String,Object> paramMap = new HashMap<String, Object>(); 
			
			// �����ļ��ϴ�����
			DiskFileItemFactory factory = new DiskFileItemFactory();
			// ���ù��������ļ��ϴ�������
			ServletFileUpload upload = new ServletFileUpload(factory);
			// ����request
			List<FileItem> parseRequest = upload.parseRequest(request);
			//ѭ���ļ��ϴ���
			for(FileItem item : parseRequest ) {
				//�����ǰ����ͨ�ֶ���
				if(item.isFormField()) {
					paramMap.put(item.getFieldName(), item.getString(encode));
				}else {
					//��ǰ���ļ��ϴ���
					//��ȡ�ϴ��ļ��е�Ӳ�̵�·��
					String path = this.getServletContext().getRealPath("upload");
					//��ȡ�ϴ��ļ���
					String filename = item.getName();
					//��ȡ������
				 	InputStream in = item.getInputStream();
				 	//��ȡ���������
				 	FileOutputStream out = new FileOutputStream(path+"/"+filename);
				 	//�Խ���
				 	IOUtils.copy(in, out);
				 	//�ر��� 
				 	IOUtil.close(in, out);
				 	
				 	//���ļ���ŵ����·�������װ���ݵļ���
				 	paramMap.put("pimage", "upload/"+filename);
				}
			}
			
			//��װ����
			BeanUtils.copyProperties(product, paramMap);
			
			//��װ�������� private String pid;private int is_hot;private int pflag;private Category category;
			product.setPid(UUID.randomUUID().toString());
			product.setIs_hot(1);
			product.setPflag(0);
			Category category = new Category();
			category.setCid(paramMap.get("cid").toString());
			product.setCategory(category);
			
			//����service����prodcut
			service.addProduct(product);
			
		} catch (SQLException e) {
			//���ʧ��
			response.getWriter().write("�ǳ���Ǹ���ʧ��");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
