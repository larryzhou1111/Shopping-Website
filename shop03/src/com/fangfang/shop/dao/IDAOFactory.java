package com.fangfang.shop.dao;

public interface IDAOFactory {
	
	public Object getDao(String name);
}
