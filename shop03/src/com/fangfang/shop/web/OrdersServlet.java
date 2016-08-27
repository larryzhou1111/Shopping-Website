package com.fangfang.shop.web;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;

import com.fangfang.shop.dao.GoodsDao;
import com.fangfang.shop.dao.ICategoryDao;
import com.fangfang.shop.dao.IGoodsDao;
import com.fangfang.shop.dao.IOrdersDao;
import com.fangfang.shop.dao.IUserDao;
import com.fangfang.shop.model.Goods;
import com.fangfang.shop.model.Orders;
import com.fangfang.shop.model.Pager;
import com.fangfang.shop.model.ShopCart;
import com.fangfang.shop.model.ShopDi;
import com.fangfang.shop.model.ShopException;
import com.fangfang.shop.model.SystemContext;
import com.fangfang.shop.model.User;
import com.fangfang.shop.utils.RequestUtil;

public class OrdersServlet extends BaseServlet{
	private IGoodsDao goodsDao;
	private IUserDao userDao;
	private IOrdersDao ordersDao;
	
	public IOrdersDao getOrdersDao() {
		return ordersDao;
	}
	
	@ShopDi
	public void setOrdersDao(IOrdersDao ordersDao) {
		this.ordersDao = ordersDao;
	}

	private static final long serialVersionUID = 1L;
	
	@ShopDi
	public void setUserDao(IUserDao userDao) {
		this.userDao = userDao;
	}
	
	public IGoodsDao getGoodsDao() {
		return goodsDao;
	}
	
	@ShopDi
	public void setGoodsDao(IGoodsDao goodsDao) {
		this.goodsDao = goodsDao;
	}

	@Author
	public String addToCart(HttpServletRequest req,HttpServletResponse resp) {
		try {
			ShopCart shopCart = (ShopCart)req.getSession().getAttribute("shopCart");
			if(shopCart==null) {
				shopCart = new ShopCart();
				req.getSession().setAttribute("shopCart", shopCart);
			} 
			Goods g = goodsDao.load((Integer.parseInt(req.getParameter("id"))));
			shopCart.add(g);
		} catch (ShopException e) {
			return this.handleException(e, req);
		}
		return redirPath("goods.do?method=list");
	}
	
	@Author
	public String showCart(HttpServletRequest req,HttpServletResponse resp) {
		User u = (User)req.getSession().getAttribute("loginUser");
		req.setAttribute("addresses", userDao.load(u.getId()).getAddresses());
		return "orders/showCart.jsp";
	}
	
	@Author
	public String clearGoods(HttpServletRequest req,HttpServletResponse resp) {
		int pid = Integer.parseInt(req.getParameter("pid"));
		ShopCart shopCart = (ShopCart)req.getSession().getAttribute("shopCart");
		if(shopCart!=null) {
			shopCart.clearGoods(pid);
		} 
		return redirPath("orders.do?method=showCart");
	}
	
	@Author
	public String clearShopCart(HttpServletRequest req,HttpServletResponse resp) {

		ShopCart shopCart = (ShopCart)req.getSession().getAttribute("shopCart");
		if(shopCart!=null) {
			shopCart.clearShopCart();
		} 
		return redirPath("orders.do?method=showCart");
	}
	
	@Author
	public String goodsAddNumberInput(HttpServletRequest req,HttpServletResponse resp) {
		req.setAttribute("pid", Integer.parseInt(req.getParameter("pid")));
		return "orders/goodsAddNumberInput.jsp";
	}
	
	@Author
	public String goodsAddNumber(HttpServletRequest req,HttpServletResponse resp) {
		int pid = Integer.parseInt(req.getParameter("pid"));
		try {
			int number = Integer.parseInt(req.getParameter("number"));
			ShopCart shopCart = (ShopCart)req.getSession().getAttribute("shopCart");
			if(shopCart!=null) {
				shopCart.addGoodsNumber(pid,number);
			} 
		} catch (NumberFormatException e) {
			this.getErrors().put("number", "数量必须为整数");
			req.setAttribute("pid", pid);
			return "orders/goodsAddNumberInput.jsp";
		} catch (ShopException e) {
			return this.handleException(e, req);
		}
		return redirPath("orders.do?method=showCart");
	}
	
	@Author
	public String addOrders(HttpServletRequest req,HttpServletResponse resp) {
		int aid = Integer.parseInt(req.getParameter("address"));
		double price = Double.parseDouble(req.getParameter("price"));
		Orders o = new Orders();
		o.setBuyDate(new Date());
		o.setStatus(1);
		o.setPrice(price);
		User u = (User)req.getSession().getAttribute("loginUser");
		ordersDao.add(o,u , aid,
				((ShopCart)req.getSession().getAttribute("shopCart")).getGoodses());
		return redirPath("user.do?method=showSelfInfo&id="+u.getId());
	}
	
	public String list(HttpServletRequest req,HttpServletResponse resp) {
		SystemContext.setOrder("desc");
		SystemContext.setSort("buy_date");
		int status = 0;
		try {
			status = Integer.parseInt(req.getParameter("status"));
		} catch (NumberFormatException e) {}
		req.setAttribute("orders", ordersDao.findByStatus(status));
		return "orders/list.jsp";
	}
	
	@Author
	public String userList(HttpServletRequest req,HttpServletResponse resp) {
		SystemContext.setOrder("desc");
		SystemContext.setSort("buy_date");
		int status = 0;
		int userId = 0;
		try {
			userId = Integer.parseInt(req.getParameter("id"));
			status = Integer.parseInt(req.getParameter("status"));
		} catch (NumberFormatException e) {}
		req.setAttribute("orders", ordersDao.findByUser(userId, status));
		return "orders/userList.jsp";
	}
	
	@Author
	public String delete(HttpServletRequest req,HttpServletResponse resp) {
		int id = Integer.parseInt(req.getParameter("id"));
		ordersDao.delete(id);
		return redirPath("goods.do?method=list");
	}
	
	@Author
	public String show(HttpServletRequest req,HttpServletResponse resp) {
		int id = Integer.parseInt(req.getParameter("id"));
		req.setAttribute("o", ordersDao.load(id));
		return "orders/show.jsp";
	}
	
	@Author
	public String pay(HttpServletRequest req,HttpServletResponse resp) {
		try {
			int id = Integer.parseInt(req.getParameter("id"));
			ordersDao.updatePayStatus(id);
		} catch (ShopException e) {
			return handleException(e, req);
		}
		return redirPath("orders.do?method=userList&id="+((User)req.getSession().getAttribute("loginUser")).getId());
	}
	
	@Author
	public String confirmProduct(HttpServletRequest req,HttpServletResponse resp) {
		try {
			int id = Integer.parseInt(req.getParameter("id"));
			ordersDao.updateConfirmStatus(id);
		} catch (ShopException e) {
			return handleException(e, req);
		}
		return redirPath("orders.do?method=userList&id="+((User)req.getSession().getAttribute("loginUser")).getId());
	}
	
	public String sendProduct(HttpServletRequest req,HttpServletResponse resp) {
		try {
			int id = Integer.parseInt(req.getParameter("id"));
			ordersDao.updateSendStatus(id);
		} catch (ShopException e) {
			return handleException(e, req);
		}
		return redirPath("orders.do?method=list");
	}
	
}










