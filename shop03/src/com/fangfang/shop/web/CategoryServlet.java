package com.fangfang.shop.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fangfang.shop.dao.IAddressDao;
import com.fangfang.shop.dao.ICategoryDao;
import com.fangfang.shop.dao.IUserDao;
import com.fangfang.shop.model.Address;
import com.fangfang.shop.model.Category;
import com.fangfang.shop.model.ShopDi;
import com.fangfang.shop.model.ShopException;
import com.fangfang.shop.model.User;
import com.fangfang.shop.utils.RequestUtil;

public class CategoryServlet extends BaseServlet{
	private static final long serialVersionUID = -1908672572041402665L;
	private ICategoryDao categoryDao;
	
	public ICategoryDao getCategoryDao() {
		return categoryDao;
	}

	@ShopDi
	public void setCategoryDao(ICategoryDao categoryDao) {
		this.categoryDao = categoryDao;
	}

	public String list(HttpServletRequest request,HttpServletResponse response){
		String name = request.getParameter("name");
		List<Category> list = categoryDao.list(name);
		request.setAttribute("cs", list);
		return "category/list.jsp";
	}
	
	public String addInput(HttpServletRequest request,HttpServletResponse response){
		return "category/addInput.jsp";
	}
	
	public String add(HttpServletRequest request,HttpServletResponse response){
		Category category = (Category)RequestUtil.setParam(Category.class, request);
		if (!RequestUtil.validate(Category.class, request)) {
			return "category/addInput.jsp";
		}
		categoryDao.add(category);
		return redirPath("category.do?method=list");
	}
	
	public String updateInput(HttpServletRequest req,HttpServletResponse resp) {
		req.setAttribute("category",
				categoryDao.load(Integer.parseInt(req.getParameter("id"))));
		return "category/updateInput.jsp";
	}
	
	public String update(HttpServletRequest req,HttpServletResponse resp) {
		Category category = categoryDao.load(Integer.parseInt(req.getParameter("id")));
		Category tc = (Category)RequestUtil.setParam(Category.class, req);
		category.setName(tc.getName());
		if(!RequestUtil.validate(Category.class, req)) {
			return "category/updateInput.jsp";
		}
		categoryDao.update(category);
		return redirPath("category.do?method=show&id="+category.getId());
	}
	
	public String show(HttpServletRequest req,HttpServletResponse resp) {
		Category category = categoryDao.load(Integer.parseInt(req.getParameter("id")));
		req.setAttribute("category",category);
		return "category/show.jsp";
	}
	
	public String delete(HttpServletRequest req,HttpServletResponse resp) {
		try {
			categoryDao.delete(Integer.parseInt(req.getParameter("id")));
		} catch (ShopException e) {
			req.setAttribute("errorMsg", e.getMessage());
			return "inc/error.jsp";
		}
		return redirPath("category.do?method=list");
	}
	
}











