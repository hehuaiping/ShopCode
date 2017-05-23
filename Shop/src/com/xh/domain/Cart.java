package com.xh.domain;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;
//购物车
public class Cart implements Serializable {
	//订单项
	private Map<String,CartItem> cartMap = new LinkedHashMap<String, CartItem>();
	//订单的总金额
	private double totleMoney;
	/**
	 * @return the cartMap
	 */
	public Map<String, CartItem> getCartMap() {
		return cartMap;
	}
	/**
	 * @param cartMap the cartMap to set
	 */
	public void setCartMap(Map<String, CartItem> cartMap) {
		this.cartMap = cartMap;
	}
	/**
	 * @return the totleMoney
	 */
	public double getTotleMoney() {
		return totleMoney;
	}
	/**
	 * @param totleMoney the totleMoney to set
	 */
	public void setTotleMoney(double totleMoney) {
		this.totleMoney = totleMoney;
	}
	
	
}
