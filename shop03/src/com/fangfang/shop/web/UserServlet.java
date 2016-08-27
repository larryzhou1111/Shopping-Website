package com.fangfang.shop.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fangfang.shop.dao.IUserDao;
import com.fangfang.shop.model.Pager;
import com.fangfang.shop.model.ShopDi;
import com.fangfang.shop.model.ShopException;
import com.fangfang.shop.model.User;
import com.fangfang.shop.utils.RequestUtil;

public class UserServlet extends BaseServlet{
	private static final long serialVersionUID = -1908672572041402665L;
	private IUserDao userDao;
	
	@ShopDi
	public void setUserDao(IUserDao userDao) {
		this.userDao = userDao;
	}


	public String list(HttpServletRequest request,HttpServletResponse response){
		Pager<User> users = userDao.find("");
		request.setAttribute("users", users);
		return "user/list.jsp";
	}
	
	@Author("any")
	public String addInput(HttpServletRequest request,HttpServletResponse response){
		return "user/addInput.jsp";
	}
	
	public String delete(HttpServletRequest request,HttpServletResponse response){
		int id = Integer.parseInt(request.getParameter("id"));
		userDao.delete(id);
		return redirPath+"user.do?method=list";
	}
	
	public String updateInput(HttpServletRequest request,HttpServletResponse response){
		int id = Integer.parseInt(request.getParameter("id"));
		User u = userDao.load(id);
		request.setAttribute("user", u);
		return "user/updateInput.jsp";
	}
	
	public String changeType(HttpServletRequest request,HttpServletResponse response){
		int id = Integer.parseInt(request.getParameter("id"));
		User u = userDao.load(id);
		if (u.getType()==0) {
			u.setType(1);
		}else {
			u.setType(0);
		}
		userDao.update(u);
		return redirPath+"user.do?method=list";
	}
	
	public String update(HttpServletRequest request,HttpServletResponse response){
		User tu = (User)RequestUtil.setParam(User.class, request);
		boolean isValidate = RequestUtil.validate(User.class, request);
		int id = Integer.parseInt(request.getParameter("id"));
		User user = userDao.load(id);
		if(!isValidate) {
			request.setAttribute("user", user);
			return "user/updateInput.jsp";
		}
		user.setPassword(tu.getPassword());
		user.setNickname(tu.getNickname());
		userDao.update(user);
		return redirPath+"user.do?method=list";
	}
	
	@Author("any")
	public String add(HttpServletRequest request,HttpServletResponse response){
		User u = (User) RequestUtil.setParam(User.class, request);
		boolean isValidate = RequestUtil.validate(User.class, request);
		if (!isValidate) {
			return "user/addInput.jsp";
		}
		try {
			userDao.add(u);
		} catch (ShopException e) {
			request.setAttribute("errorMsg",e.getMessage());
			return "inc/error.jsp";
		}
		
		/*String username = request.getParameter("username");
		String password = request.getParameter("password");
		String nickname = request.getParameter("nickname");
		User u = new User();
		u.setUsername(username);
		u.setPassword(password);
		u.setNickname(nickname);
		userDao.add(u);*/
		return redirPath+"user.do?method=list";
	}
	
	@Author("any")
	public String loginInput(HttpServletRequest request,HttpServletResponse response){
		return "user/loginInput.jsp";
	}
	
	@Author("any")
	public String login(HttpServletRequest request,HttpServletResponse response){
		try {
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			User user = userDao.login(username, password);
			request.getSession().setAttribute("loginUser", user);
		} catch (ShopException e) {
			request.setAttribute("errorMsg", e.getMessage());
			return "inc/error.jsp";
		}
		return redirPath("goods.do?method=list");
	}
	
	@Author("any")
	public String loginOut(HttpServletRequest request,HttpServletResponse response){
		request.getSession().invalidate();
		return redirPath("goods.do?method=list");
	}
	
	@Author
	public String updateSelfInput(HttpServletRequest request,HttpServletResponse response){
		request.setAttribute("user", (User)request.getSession().getAttribute("loginUser"));
		return "user/updateSelfInput.jsp";
	}
	
	@Author
	public String updateSelf(HttpServletRequest request,HttpServletResponse response){
		User tu = (User)RequestUtil.setParam(User.class, request);
		boolean isValidate = RequestUtil.validate(User.class, request);
		User user = (User)request.getSession().getAttribute("loginUser");
		user.setPassword(tu.getPassword());
		user.setNickname(tu.getNickname());
		if(!isValidate) {
			request.setAttribute("user", user);
			return "user/updateSelfInput.jsp";
		}
		userDao.update(user);
		return redirPath("goods.do?method=list");
	}
	
	@Author
	public String showSelfInfo(HttpServletRequest request,HttpServletResponse response){
		User user = userDao.load(Integer.parseInt(request.getParameter("id")));
		request.setAttribute("user",user);
		return "user/showSelfInfo.jsp";
	}
}











