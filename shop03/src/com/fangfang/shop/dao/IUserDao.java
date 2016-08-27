package com.fangfang.shop.dao;

import org.apache.ibatis.annotations.Update;

import com.fangfang.shop.model.Pager;
import com.fangfang.shop.model.User;

public interface IUserDao {
	public void add(User user);
	public void delete(int id);
	public User load(int id);
	public User loadByUsername(String username);
	public void update(User user);
	public Pager<User> find(String name);
	public User login(String username,String password);
}
