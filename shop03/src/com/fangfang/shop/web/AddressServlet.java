package com.fangfang.shop.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fangfang.shop.dao.IAddressDao;
import com.fangfang.shop.dao.IUserDao;
import com.fangfang.shop.model.Address;
import com.fangfang.shop.model.ShopDi;
import com.fangfang.shop.model.User;
import com.fangfang.shop.utils.RequestUtil;

public class AddressServlet extends BaseServlet{
	private static final long serialVersionUID = -1908672572041402665L;
	private IUserDao userDao;
	private IAddressDao addressDao;
	
	@ShopDi
	public void setAddressDao(IAddressDao addressDao) {
		this.addressDao = addressDao;
	}

	@ShopDi
	public void setUserDao(IUserDao userDao) {
		this.userDao = userDao;
	}

	@Author
	public String add(HttpServletRequest request,HttpServletResponse response){
		User u = userDao.load(Integer.parseInt(request.getParameter("userId")));
		Address a = (Address)RequestUtil.setParam(Address.class, request);
		if(!RequestUtil.validate(Address.class, request)) {
			request.setAttribute("user",u);
			return "address/addInput.jsp";
		}
		addressDao.add(a, u.getId());
		return redirPath(request.getContextPath()+"/user.do?method=showSelfInfo&id="+u.getId());
	}
	
	@Author
	public String delete(HttpServletRequest request,HttpServletResponse response){
		int addressId = Integer.parseInt(request.getParameter("id"));
		int userId = Integer.parseInt(request.getParameter("userId"));
		addressDao.delete(addressId);
		return redirPath(request.getContextPath()+"/user.do?method=showSelfInfo&id="+userId);
	}
	

	@Author
	public String addInput(HttpServletRequest request,HttpServletResponse response){
		User user = userDao.load(Integer.parseInt(request.getParameter("userId")));
		request.setAttribute("user", user);
		return "address/addInput.jsp";
	}
	
	@Author
	public String updateInput(HttpServletRequest request,HttpServletResponse response){
		Address address = addressDao.load(Integer.parseInt(request.getParameter("id")));
		request.setAttribute("address",address);
		return "address/updateInput.jsp";
	}
	
	@Author
	public String update(HttpServletRequest request,HttpServletResponse response){
		Address address = addressDao.load(Integer.parseInt(request.getParameter("id")));
		Address taddress = (Address) RequestUtil.setParam(Address.class, request);
		address.setName(taddress.getName());
		address.setPhone(taddress.getPhone());
		address.setPostcode(taddress.getPostcode());
		if (!RequestUtil.validate(Address.class, request)) {
			request.setAttribute("address", address);
			return "address/updateInput.jsp";
		}
		addressDao.update(address);
		return redirPath("user.do?method=showSelfInfo&id="+address.getUser().getId());
	}
}











