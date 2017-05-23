package com.xh.domain;

import java.io.Serializable;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
/**
 * ����ʵ��
 * @author Administrator
 *
 */
public class Orders implements Serializable {

	private String oid;//����Id
	private Timestamp ordertime;//��������ʱ��
	private double total;//�����ܽ��
	private int state;//����״̬
	
	private String address;//�ջ���ַ
	private String name;//�ջ�������
	private String telephone;//�ջ�����ϵ��ʽ
	
	private User user;//�ö��������Ǹ��û�
	
	//�ö����еĶ�����
	private List<OrderItem> orderItem = new ArrayList<OrderItem>();

	/**
	 * @return the oid
	 */
	public String getOid() {
		return oid;
	}

	/**
	 * @param oid the oid to set
	 */
	public void setOid(String oid) {
		this.oid = oid;
	}

	/**
	 * @return the ordertime
	 */
	public Timestamp getOrdertime() {
		return ordertime;
	}

	/**
	 * @param ordertime the ordertime to set
	 */
	public void setOrdertime(Timestamp ordertime) {
		this.ordertime = ordertime;
	}

	/**
	 * @return the total
	 */
	public double getTotal() {
		return total;
	}

	/**
	 * @param total the total to set
	 */
	public void setTotal(double total) {
		this.total = total;
	}

	/**
	 * @return the state
	 */
	public int getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(int state) {
		this.state = state;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the telephone
	 */
	public String getTelephone() {
		return telephone;
	}

	/**
	 * @param telephone the telephone to set
	 */
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * @return the orderItem
	 */
	public List<OrderItem> getOrderItem() {
		return orderItem;
	}

	/**
	 * @param orderItem the orderItem to set
	 */
	public void setOrderItem(List<OrderItem> orderItem) {
		this.orderItem = orderItem;
	}
	
	
}
