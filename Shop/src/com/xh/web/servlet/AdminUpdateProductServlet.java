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
			//������Ʒ��Ϣ
			AdminService service  = BasicFactory.getBasicFactory().getService(AdminService.class);
			Product product = new Product();
			String encode = this.getServletContext().getInitParameter("encode");
			
			//��װ��ͨ�ֶ������ݵ�map
			Map<String,Object> paramMap = new HashMap<String, Object>();
		
			//1�������ļ��ϴ�����
			DiskFileItemFactory factory = new DiskFileItemFactory();
			//���ù��������ļ��ϴ�������
			ServletFileUpload upload = new ServletFileUpload(factory);
			//����request
			List<FileItem> fileItemList = upload.parseRequest(request);
			//�����ļ������
			for(FileItem item : fileItemList) {
				//�жϵ�ǰ���Ƿ�����ͨ�ֶ���
				if(item.isFormField()) {
					//����ͨ�ֶ����װ������������
					paramMap.put(item.getFieldName(), item.getString(encode));
				}else {
					//�ж��û���ѡ����ͼƬ
					if(item.getSize()!=0) {
						//�ļ��ϴ���
						//��ȡ����ϴ��ļ����ļ���·��
						String path = this.getServletContext().getRealPath("upload");
						//��ȡ�ϴ��ļ���
						String filename = item.getName();
						//��ȡ�ϴ�������
						InputStream in = item.getInputStream();
						//��ȡ���������,�������ļ�
						FileOutputStream out = new FileOutputStream(path+"/"+filename);
						//�Խ���
						IOUtil.IoToOut(in, out);
						//�ر���
						IOUtil.close(in, out);
						
						//���ļ���ŵ����·���ŷ�װ������������
						paramMap.put("pimage", "upload/"+filename);
					}else {
						//û�и���ͼƬ,��ȡԭ����ͼƬ·��
						paramMap.put("pimage", service.findProductByPid((String)paramMap.get("pid")).get("pimage"));
					}
				}
			}
			
			//�����ݷ�װ��bean��
			BeanUtils.copyProperties(product, paramMap);
			//��װproduct�е�category����
			Category category = new Category();
			category.setCid((String)paramMap.get("cid"));
			//��category���뼯��
			product.setCategory(category);
			
			//2������serviceִ�и�����Ʒ����
			service.updateProduct(product);
			
			//3��ת������ѯ��Ʒ��servetl��ȡ����Ʒ�����µ���Ϣ,����ʾ�û��޸ĳɹ�
			request.setAttribute("msg", "�޸���Ʒ�ɹ�");
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
