package com.fangfang.shop.model;

import java.util.Date;
import java.util.List;

public class Orders {
	private int id;
	private Date buyDate;
	private Date payDate;
	private Date confirmDate;
	/**
	 * 四种状态:1表示已下订单、2表示已付款、3、表示已发货、4、表示确认收货
	 */
	private int status;
	private User user;
	private Address address;
	private List<CartGoods> goodses;
	/**
	 * 所花费的价格，这个价格是可以修改的
	 */
	private double price;
	
	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	public Date getBuyDate() {
		return buyDate;
	}
	public void setBuyDate(Date buyDate) {
		this.buyDate = buyDate;
	}
	public Date getPayDate() {
		return payDate;
	}
	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}
	public Date getConfirmDate() {
		return confirmDate;
	}
	public void setConfirmDate(Date confirmDate) {
		this.confirmDate = confirmDate;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}

	public List<CartGoods> getGoodses() {
		return goodses;
	}

	public void setGoodses(List<CartGoods> goodses) {
		this.goodses = goodses;
	}
}
