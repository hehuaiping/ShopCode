package com.xh.domain;

import java.io.Serializable;

public class CartItem implements Serializable {
	//商品
	private Product product;
	//购买数量
	private int buynum;
	//该项订单的金额
	private double totlesum;
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
	 * @return the buynum
	 */
	public int getBuynum() {
		return buynum;
	}
	/**
	 * @param buynum the buynum to set
	 */
	public void setBuynum(int buynum) {
		this.buynum = buynum;
	}
	/**
	 * @return the totlesum
	 */
	public double getTotlesum() {
		return totlesum;
	}
	/**
	 * @param totlesum the totlesum to set
	 */
	public void setTotlesum(double totlesum) {
		this.totlesum = totlesum;
	}
	
	
}
