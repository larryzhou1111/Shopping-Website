package com.fangfang.shop.dao;

import java.util.List;

import com.fangfang.shop.model.CartGoods;
import com.fangfang.shop.model.Goods;
import com.fangfang.shop.model.Orders;
import com.fangfang.shop.model.Pager;
import com.fangfang.shop.model.User;

public interface IOrdersDao {
	public void add(Orders orders,User user,int aid,List<CartGoods> cps);
	public void delete(int id);
	public void update(Orders orders);
	public void updatePrice(int id,double price);
	public void updatePayStatus(int id);
	public void updateSendStatus(int id);
	public void updateConfirmStatus(int id);
	public Orders load(int id);
	public Pager<Orders> findByUser(int userId,int status);
	public Pager<Orders> findByStatus(int status);
	
	public void addCartGoods(CartGoods cg, Orders o,Goods g);
	public void deleteCartGoods(int oid);
}
