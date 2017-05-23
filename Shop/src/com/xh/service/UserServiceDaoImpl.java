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
		//1������û��� �Ƿ����
		if(userdao.findUserByUserName(user.getUsername())!=null) {
			//�û��Ѵ���
			throw new MessageException("�û����Ѵ���...");
		}
		//2������û�
		userdao.addUser(user);
		
		//3�����ͼ����ʼ�
		Properties props = new Properties();
		props.setProperty("mail.transport.protocol", "smtp");//ʹ��smtpЭ��
		props.setProperty("mail.smtp.host", "localhost");//����������
		props.setProperty("mail.smtp.auth", "ture");//�����û�Ȩ��
		props.setProperty("mail.debug", "ture");//��ӡ������Ϣ
		
		//���������ʼ�������֮���һ�λỰ
		Session session = Session.getInstance(props);
		try{
			//��ȡ�ʼ�����
			Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress("aa@luckily.com"));//������
			//�ռ�������
			msg.setRecipients(RecipientType.TO, new InternetAddress[]{new InternetAddress(user.getEmail())});
			//�ʼ�����
			msg.setSubject("�𾴵�:"+user.getName()+"��������estore�ļ����ʼ�");
			//�ʼ���������
			msg.setText("��ϲ��ע��Shop�ɹ��������������������Ӽ��������û�,<a href='http://localhost/Shop/servlet/ActiveUser?activecode="+user.getCode()+"'>�������</a>");
			
			//��ȡ�������
			Transport trans = session.getTransport();
			//�û�������
			trans.connect("aa", "123");
			//����
			trans.sendMessage(msg, msg.getAllRecipients());
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public User findUserByUneAndPwd(String username, String password) {
		return userdao.findUserByUneAndPwd(username,password);
	}

	public User activeUser(String activecode) throws MessageException {
		//1�������ݿ��и��ݼ���������û�
		User user = userdao.findUserByActiveCode(activecode);		
		if(user==null) {
			//������ҵ��û�������
			throw new MessageException("���û�������,���鼤�����Ƿ���ȷ");
		}
		//2���鿴�û��Ƿ��Ѽ���
		if(user.getState().equals("1")) {
			//�Ѽ�����û����ڼ���,��ʾ�û��Ѽ���
			throw new MessageException("���û��Ѿ�����,�벻Ҫ�ظ��������");
				
		}else {
			//��û�м������û��ļ������Ƿ��ѹ���
		 	long nowtime = System.currentTimeMillis();
		 	Timestamp oldtime = user.getUpdatetime();
		 	if(oldtime.getTime()-nowtime>1000*3600*24) {
		 		//�ѹ���,ɾ���û���¼,��ʾ�û�����ע��
		 		userdao.deleteUser(user);
		 		throw new MessageException("�ü������Ѿ�����,��������ע��....");
		 	}
			//���û�й���,�򼤻��û�
		 	userdao.activeUser(user.getCode());
		}
						
		return user;
	}

	public String findUserRoleByUid(String uid) {
		return userdao.findUserRoleByUid(uid);
	}

}
