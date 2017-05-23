package com.xh.service;

import java.sql.Timestamp;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.xh.dao.UserDao;
import com.xh.domain.User;
import com.xh.exception.MessageException;
import com.xh.factory.BasicFactory;

public class UserServiceDaoImpl implements UserServiceDao {
	UserDao userdao = BasicFactory.getBasicFactory().getDao(UserDao.class);
	
	public void addUser(User user) throws MessageException {
		//1、检查用户名 是否存在
		if(userdao.findUserByUserName(user.getUsername())!=null) {
			//用户已存在
			throw new MessageException("用户名已存在...");
		}
		//2、添加用户
		userdao.addUser(user);
		
		//3、发送激活邮件
		Properties props = new Properties();
		props.setProperty("mail.transport.protocol", "smtp");//使用smtp协议
		props.setProperty("mail.smtp.host", "localhost");//服务器主机
		props.setProperty("mail.smtp.auth", "ture");//开启用户权限
		props.setProperty("mail.debug", "ture");//打印发送信息
		
		//创建程序到邮件服务器之间的一次会话
		Session session = Session.getInstance(props);
		try{
			//获取邮件对象
			Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress("aa@luckily.com"));//发件人
			//收件人数组
			msg.setRecipients(RecipientType.TO, new InternetAddress[]{new InternetAddress(user.getEmail())});
			//邮件标题
			msg.setSubject("尊敬的:"+user.getName()+"这是来自estore的激活邮件");
			//邮件正文内容
			msg.setText("恭喜您注册Shop成功！！！请您点击这个链接激活您的用户,<a href='http://localhost/Shop/servlet/ActiveUser?activecode="+user.getCode()+"'>点击激活</a>");
			
			//获取传输对象
			Transport trans = session.getTransport();
			//用户名密码
			trans.connect("aa", "123");
			//发送
			trans.sendMessage(msg, msg.getAllRecipients());
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public User findUserByUneAndPwd(String username, String password) {
		return userdao.findUserByUneAndPwd(username,password);
	}

	public User activeUser(String activecode) throws MessageException {
		//1、到数据库中根据激活码查找用户
		User user = userdao.findUserByActiveCode(activecode);		
		if(user==null) {
			//如果查找到用户不存在
			throw new MessageException("该用户不存在,请检查激活码是否正确");
		}
		//2、查看用户是否已激活
		if(user.getState().equals("1")) {
			//已激活的用户不在激活,提示用户已激活
			throw new MessageException("该用户已经激活,请不要重复激活！！！");
				
		}else {
			//如没有激活检查用户的激活码是否已过期
		 	long nowtime = System.currentTimeMillis();
		 	Timestamp oldtime = user.getUpdatetime();
		 	if(oldtime.getTime()-nowtime>1000*3600*24) {
		 		//已过期,删除用户记录,提示用户重新注册
		 		userdao.deleteUser(user);
		 		throw new MessageException("该激活码已经过期,请您重新注册....");
		 	}
			//如果没有过期,则激活用户
		 	userdao.activeUser(user.getCode());
		}
						
		return user;
	}

	public String findUserRoleByUid(String uid) {
		return userdao.findUserRoleByUid(uid);
	}

}
