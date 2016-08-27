package com.fangfang.shop.web;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.fangfang.shop.model.User;
import com.fangfang.shop.utils.DaoUtil;
import com.sun.swing.internal.plaf.metal.resources.metal;
import com.sun.xml.internal.ws.handler.HandlerException;

public class BaseServlet extends HttpServlet{
	private static final long serialVersionUID = 3321239601393791088L;
	public static final String redirPath = "redirect";
	private Map<String, String> errors = new HashMap<String, String>();
	
	protected String redirPath(String path){
		return redirPath+path;
	}
	
	protected Map<String, String> getErrors(){
		return errors;
	}
	
	protected boolean hasErrors(){
		if (errors!=null&&errors.size()>0) {
			return true;
		}
		return false;
	}
	
	protected String handleException(Exception e,HttpServletRequest reques){
		reques.setAttribute("errorMsg", e.getMessage());
		return "inc/error.jsp";
	}
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			errors.clear();
			request.setAttribute("errors", errors);
			if (ServletFileUpload.isMultipartContent(request)) {
				request = new MultipartWrapper(request);
			}
			DaoUtil.diDao(this);
			String method = request.getParameter("method");
			Method m = this.getClass().getMethod(method, HttpServletRequest.class,HttpServletResponse.class);
			int flag = checkAuthor(request,m,response);
			if (flag==1) {
				response.sendRedirect("user.do?method=loginInput");
				return;
			}else if(flag==2){
				request.setAttribute("errorMsg", "您没有权限访问该功能");
				request.getRequestDispatcher("WEB-INF/inc/error.jsp").forward(request, response);
				return;
			}
			String path = (String)m.invoke(this, request,response);
			if (path.startsWith(redirPath)) {
				response.sendRedirect(path.substring(redirPath.length()));
			}else {
				request.getRequestDispatcher("/WEB-INF/"+path).forward(request, response);
			}
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	private int checkAuthor(HttpServletRequest request, Method m,HttpServletResponse response) {
		User lu = (User)request.getSession().getAttribute("loginUser");
		if (lu!=null&&lu.getType()==1) {
			return 0;
		}
		if (!m.isAnnotationPresent(Author.class)) {
			if (lu==null) {
				return 1;
			}
			if (lu.getType()!=1) {
				return 2;
			}
		}else {
			Author author = m.getAnnotation(Author.class);
			String v = author.value();
			if (v.equals("any")) {
				return 0;
			}else if (v.equals("user")) {
				if (lu==null) {
					return 1;
				}else {
					return 0; 
				}
			}
		}
		return 0;
	}
}
