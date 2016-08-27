package com.fangfang.shop.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.fangfang.shop.model.Pager;
import com.fangfang.shop.model.ShopException;
import com.fangfang.shop.model.User;

public class UserDao extends BaseDao<User> implements IUserDao {

	@Override
	public void add(User user) {
		User tUser = this.loadByUsername(user.getUsername());
		if (tUser!=null) throw new ShopException("要添加的用户已经存在");
		super.add(user);
	}

	@Override
	public void delete(int id) {
		//TODO 需要先删除关联对象
		super.delete(User.class, id);
	}

	@Override
	public User load(int id) {
		return super.load(User.class, id);
	}

	@Override
	public User loadByUsername(String username) {
		return super.loadBySqlId(User.class.getName()+".load_by_username", username);
	}

	@Override
	public void update(User user) {
		super.update(user);
	}

	@Override
	public Pager<User> find(String name) {
		Map<String,Object> params = new HashMap<String,Object>();
		if(name!=null&&!name.equals("")) params.put("name", "%"+name+"%");
		return super.find(User.class, params);
	}

	@Override
	public User login(String username, String password) {
		User user = this.loadByUsername(username);
		if(user==null) throw new ShopException("用户名不存在");
		if(!user.getPassword().equals(password)) throw new ShopException("用户密码不正确");
		return user;
	}
}
