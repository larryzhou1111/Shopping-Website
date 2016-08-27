package com.fangfang.shop.test;

import org.junit.Before;
import org.junit.Test;

import com.fangfang.shop.dao.DAOFactory;
import com.fangfang.shop.dao.IUserDao;
import com.fangfang.shop.model.Address;
import com.fangfang.shop.model.Pager;
import com.fangfang.shop.model.SystemContext;
import com.fangfang.shop.model.User;
import com.fangfang.shop.utils.DaoUtil;

public class TestUserDao extends BaseTest{
	private IUserDao userDao;
	
	public IUserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(IUserDao userDao) {
		this.userDao = userDao;
	}

	@Test
	public void testAdd(){
		User user = new User();
		user.setNickname("张伟s1112");
		user.setPassword("123");
		user.setType(1);
		user.setUsername("userw");
		userDao.add(user);
	}
	
	public void testUpdate(){
		User user = userDao.loadByUsername("user1");
		user.setPassword("1");
		userDao.update(user);
	} 
	
	public void testDelete(){
		userDao.delete(2);
	} 
	
	@Test
	public void testFind(){
		SystemContext.setPageOffset(0);
		SystemContext.setPageSize(15);
//		SystemContext.setOrder("desc");
//		SystemContext.setSort("id");
		Pager<User> pager = userDao.find("");
		System.out.println(pager.getTotalRecord());
		for(User user:pager.getDatas() ){
			System.out.println("_____"+user);
		}
	}
	
	@Test
	public void testLogin(){
		User user = userDao.login("user111", "123");
		System.out.println(user.getNickname());
	}
	
	@Test
	public void testLoad(){
		User user = userDao.load(1);
		for(Address address:user.getAddresses()){
			System.out.println(address);
		}
	}
	
	//测试dao是否单例
	@Test
	public void testSingle(){
		IUserDao userDao1 = (IUserDao) DaoUtil.createDAOFactory().getDao("userDao");
		IUserDao userDao11 = (IUserDao) DaoUtil.createDAOFactory().getDao("userDao");
		System.out.println(userDao1==userDao11);
	}
}










