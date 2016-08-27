package com.fangfang.shop.test;

import java.util.List;

import org.junit.Test;

import com.fangfang.shop.dao.AddressDao;
import com.fangfang.shop.dao.DAOFactory;
import com.fangfang.shop.dao.IAddressDao;
import com.fangfang.shop.dao.IUserDao;
import com.fangfang.shop.model.Address;
import com.fangfang.shop.utils.DaoUtil;

public class TestAddressDao extends BaseTest{
	private IAddressDao addressDao ;
	private IUserDao userDao;
	
	public IAddressDao getAddressDao() {
		return addressDao;
	}

	public void setAddressDao(IAddressDao addressDao) {
		this.addressDao = addressDao;
	}

	public IUserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(IUserDao userDao) {
		this.userDao = userDao;
	}
	
	@Test
	public void testAddressDao(){
		new AddressDao();
	} 

	@Test
	public void testAdd(){
		Address address = new Address();
		address.setName("四川省成都市电子科大张皇收1");
		address.setPhone("11");
		address.setPostcode("666333");
		addressDao.add(address, 1);
	}
	
	@Test
	public void testList(){
		List<Address> list = addressDao.list(1);
		for (Address address : list) {
			System.out.println(address.getId()+"|||"+address.getName()+"||||"+address.getPhone());
		}
	}
}

