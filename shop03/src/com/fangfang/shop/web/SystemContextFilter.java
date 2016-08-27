package com.fangfang.shop.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.fangfang.shop.model.SystemContext;
import com.fangfang.shop.utils.RequestUtil;

public class SystemContextFilter implements Filter {

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		try{
			int pageOffset = 0;
			int pageSize = 15;
			String order = request.getParameter("order");
			String sort = request.getParameter("sort");
			try {
				pageOffset = Integer.parseInt(request.getParameter("pager.offset"));
			} catch (NumberFormatException e) {
			}
			SystemContext.setOrder(order);
			SystemContext.setSort(sort);
			SystemContext.setPageOffset(pageOffset);
			SystemContext.setPageSize(pageSize);
			SystemContext.setRealpath(RequestUtil.PATH);
			chain.doFilter(request, response);
		}finally{
			SystemContext.removePageOffset();
			SystemContext.removePageSize();
			SystemContext.removeOrder();
			SystemContext.removeSort();
		}
		

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {

	}

}
