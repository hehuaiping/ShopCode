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
	
	//读取配置文件
	static{
		try {
			prop = new Properties();
			prop.load(BasicFactory.class.getClassLoader().getResourceAsStream("config.properties"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//私有构造方法
	private BasicFactory(){}
	
	public static BasicFactory getBasicFactory(){
		return factory;
	}
	
	/**
	 * Dao接口实现类的创建
	 * @param clazz dao层接口类
	 * @return	返回实现类
	 */
	public <T extends Dao> T getDao(Class<T> clazz){
		try {
			//反射获取类的简化名称
			String daoname = clazz.getSimpleName();
			//根据接口名在配置文件中获取实现类的包路径
			String implname = prop.getProperty(daoname);
			
			return (T) Class.forName(implname).newInstance();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * service接口实现类的创建
	 * @param clazz service接口类
	 * @return service接口实现类
	 */
	@SuppressWarnings("all")
	public <T extends Service> T getService(Class<T> clazz) {
		try {
			//获取接口的名称
			String servicename = clazz.getSimpleName();
			//根据接口名称在配置文件中读取实现类包路径
			String implname = prop.getProperty(servicename);
			final T service = (T) Class.forName(implname).newInstance();
			
			//代理service对象,AOP编程,实现根据注解判断当前service在执行之前和之后要做的事情:例  事务控制
			T proxyService = (T) Proxy.newProxyInstance(service.getClass().getClassLoader(), service.getClass().getInterfaces(), new InvocationHandler() {
				//根据注解控制事务
				public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
					//判断当前方法上是否有注解
					if(method.isAnnotationPresent(Tran.class)) {
						try {
							//需要开启事务,调用事务管理中的开启事务方法
							TransactionManager.startTransaction();
							//调用service中真正的方法执行
							Object obj = method.invoke(service, args);
							//如果没抛出异常,提交事务
							TransactionManager.commit();
							//返回执行结果对象
							return obj;
						}catch(Exception e) {		
							//回滚事务
							TransactionManager.rollback();
							e.printStackTrace();
						}finally {
							//释放资源
							TransactionManager.clear();
						}
					}else {
						//当前方法没有@Tran注解,不需要开启事务,调用原方法执行
						return method.invoke(service, args);
					}
					return null;
					
				}
			});
			//返回service代理连接
			return  proxyService;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
