package com.xh.factory;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.SQLException;
import java.util.Properties;

import com.xh.annotation.Tran;
import com.xh.dao.Dao;
import com.xh.service.Service;
import com.xh.utils.TransactionManager;

public class BasicFactory {
	private static Properties prop;
	private static BasicFactory factory = new BasicFactory();
	
	//��ȡ�����ļ�
	static{
		try {
			prop = new Properties();
			prop.load(BasicFactory.class.getClassLoader().getResourceAsStream("config.properties"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//˽�й��췽��
	private BasicFactory(){}
	
	public static BasicFactory getBasicFactory(){
		return factory;
	}
	
	/**
	 * Dao�ӿ�ʵ����Ĵ���
	 * @param clazz dao��ӿ���
	 * @return	����ʵ����
	 */
	public <T extends Dao> T getDao(Class<T> clazz){
		try {
			//�����ȡ��ļ�����
			String daoname = clazz.getSimpleName();
			//���ݽӿ����������ļ��л�ȡʵ����İ�·��
			String implname = prop.getProperty(daoname);
			
			return (T) Class.forName(implname).newInstance();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * service�ӿ�ʵ����Ĵ���
	 * @param clazz service�ӿ���
	 * @return service�ӿ�ʵ����
	 */
	@SuppressWarnings("all")
	public <T extends Service> T getService(Class<T> clazz) {
		try {
			//��ȡ�ӿڵ�����
			String servicename = clazz.getSimpleName();
			//���ݽӿ������������ļ��ж�ȡʵ�����·��
			String implname = prop.getProperty(servicename);
			final T service = (T) Class.forName(implname).newInstance();
			
			//����service����,AOP���,ʵ�ָ���ע���жϵ�ǰservice��ִ��֮ǰ��֮��Ҫ��������:��  �������
			T proxyService = (T) Proxy.newProxyInstance(service.getClass().getClassLoader(), service.getClass().getInterfaces(), new InvocationHandler() {
				//����ע���������
				public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
					//�жϵ�ǰ�������Ƿ���ע��
					if(method.isAnnotationPresent(Tran.class)) {
						try {
							//��Ҫ��������,������������еĿ������񷽷�
							TransactionManager.startTransaction();
							//����service�������ķ���ִ��
							Object obj = method.invoke(service, args);
							//���û�׳��쳣,�ύ����
							TransactionManager.commit();
							//����ִ�н������
							return obj;
						}catch(Exception e) {		
							//�ع�����
							TransactionManager.rollback();
							e.printStackTrace();
						}finally {
							//�ͷ���Դ
							TransactionManager.clear();
						}
					}else {
						//��ǰ����û��@Tranע��,����Ҫ��������,����ԭ����ִ��
						return method.invoke(service, args);
					}
					return null;
					
				}
			});
			//����service��������
			return  proxyService;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
