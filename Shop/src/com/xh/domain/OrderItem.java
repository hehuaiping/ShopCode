package com.xh.domain;

import java.io.Serializable;
/**
 * 订单项实体
 * @author Administrator
 *
 */
public class OrderItem implements Serializable {

	private String itemid; //订单项ID
	private int count; //该订单中商品购买的数量
	private double subtotal;//订单项金额总计
	
	private Product product;//订单项中的商品
	
	private Orders orders; //该订单项属于那个订单
	/**
	 * @return the itemid
	 */
	public String getItemid() {
		return itemid;
	}
	/**
	 * @param itemid the itemid to set
	 */
	public void setItemid(String itemid) {
		this.itemid = itemid;
	}
	/**
	 * @return the count
	 */
	public int getCount() {
		return count;
	}
	/**
	 * @param count the count to set
	 */
	public void setCount(int count) {
		this.count = count;
	}
	/**
	 * @return the subtotal
	 */
	public double getSubtotal() {
		return subtotal;
	}
	/**
	 * @param subtotal the subtotal to set
	 */
	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
	}
	/**
	 * @return the product
	 */
	public Product getProduct() {
		return product;
	}
	/**
	 * @param product the product to set
	 */
	public void setProduct(Product product) {
		this.product = product;
	}
	/**
	 * @return the orders
	 */
	public Orders getOrders() {
		return orders;
	}
	/**
	 * @param orders the orders to set
	 */
	public void setOrders(Orders orders) {
		this.orders = orders;
	}
	
	
}
