package com.fangfang.shop.dao;

import com.fangfang.shop.model.CartGoods;

public interface ICartGoodsDao {
	public void add(CartGoods cp,int oid);
	public void delete(int oid);
}
