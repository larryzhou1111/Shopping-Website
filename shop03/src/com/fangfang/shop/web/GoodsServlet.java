package com.fangfang.shop.web;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;

import com.fangfang.shop.dao.GoodsDao;
import com.fangfang.shop.dao.ICategoryDao;
import com.fangfang.shop.dao.IGoodsDao;
import com.fangfang.shop.model.Goods;
import com.fangfang.shop.model.Pager;
import com.fangfang.shop.model.ShopDi;
import com.fangfang.shop.model.ShopException;
import com.fangfang.shop.model.SystemContext;
import com.fangfang.shop.model.User;
import com.fangfang.shop.utils.RequestUtil;

public class GoodsServlet extends BaseServlet{

	private static final long serialVersionUID = -6052588806321778205L;
	private ICategoryDao categoryDao;
	private IGoodsDao goodsDao;
	
	public ICategoryDao getCategoryDao() {
		return categoryDao;
	}

	@ShopDi
	public void setCategoryDao(ICategoryDao categoryDao) {
		this.categoryDao = categoryDao;
	}


	public IGoodsDao getGoodsDao() {
		return goodsDao;
	}

	@ShopDi
	public void setGoodsDao(IGoodsDao goodsDao) {
		this.goodsDao = goodsDao;
	}

	public String changeStatus(HttpServletRequest req,HttpServletResponse resp) {
		int id = Integer.parseInt(req.getParameter("id"));
		goodsDao.changeStatus(id);
		return redirPath("goods.do?method=list");
	}
	
	@Author("any")
	public String list(HttpServletRequest request,HttpServletResponse response){
		request.setAttribute("goods", goodsDao.find(0, null, 0));
		return "goods/list.jsp";
	}
	
	public String addStockInput(HttpServletRequest req,HttpServletResponse resp) {
		req.setAttribute("c",goodsDao.load(Integer.parseInt(req.getParameter("id"))));
		return "goods/addStockInput.jsp";
	}
	
	public String addStock(HttpServletRequest req,HttpServletResponse resp) {
		try {
			int number = Integer.parseInt(req.getParameter("number"));
			int id = Integer.parseInt(req.getParameter("id"));
			goodsDao.addStock(id, number);
		} catch (NumberFormatException e) {
			this.getErrors().put("number", "库存的类型必须为整数");
			req.setAttribute("c",goodsDao.load(Integer.parseInt(req.getParameter("id"))));
			return "goods/addStockInput.jsp";
		}
		return redirPath("goods.do?method=list");
	}
	

	public String delete(HttpServletRequest request,HttpServletResponse response){
		try {
			goodsDao.delete(Integer.parseInt(request.getParameter("id")));
		} catch (ShopException e) {
			return this.handleException(e, request);
		}
		return redirPath("goods.do?method=list");
	}
	
	@Author("any")
	public String show(HttpServletRequest req,HttpServletResponse resp) {
		req.setAttribute("goods", goodsDao.load(Integer.parseInt(req.getParameter("id"))));
		return "goods/show.jsp";
	}

	public String addInput(HttpServletRequest request,HttpServletResponse response){
		request.setAttribute("cs", categoryDao.list());
		return "goods/addInput.jsp";
	}

	public String add(HttpServletRequest request,HttpServletResponse response) throws FileNotFoundException, IOException{
		Goods goods = (Goods)RequestUtil.setParam(Goods.class, request);
		System.out.println(goods.getImg());
		goods.setStatus(1);
		RequestUtil.validate(Goods.class, request); 
		int cid = 0;
		try {
			cid = Integer.parseInt(request.getParameter("cid"));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		if (cid==0) {
			((Map<String, String>)request.getAttribute("errors")).put("cid", "商品类别必须选择");
		}
		if(!this.hasErrors()) {
			//文件上传
			byte[] fs = (byte[])request.getAttribute("fs");
			String fname = request.getParameter("img");
			fname = FilenameUtils.getName(fname);
			RequestUtil.uploadFile(fname, "img", fs, request);

		}
		if(this.hasErrors()) {
			addInput(request,response);
			return "goods/addInput.jsp";
		}
		goodsDao.add(cid, goods);
		return redirPath("goods.do?method=list");
	}

	public String updateInput(HttpServletRequest req,HttpServletResponse resp) {
		req.setAttribute("cs",categoryDao.list());
		req.setAttribute("p", goodsDao.load(Integer.parseInt(req.getParameter("id"))));
		return "goods/updateInput.jsp";
	}
	
	public String update(HttpServletRequest req,HttpServletResponse resp) throws FileNotFoundException, IOException {
		Goods p = (Goods)RequestUtil.setParam(Goods.class, req);
		RequestUtil.validate(Goods.class, req);
		String img = req.getParameter("img");
		int cid = 0;
		try {
			cid = Integer.parseInt(req.getParameter("cid"));
		} catch (NumberFormatException e) {}
		if(cid==0) {
			this.getErrors().put("cid", "商品类别必须选择");
		}
		Goods tp = goodsDao.load(Integer.parseInt(req.getParameter("id")));
		tp.setIntro(p.getIntro());
		tp.setName(p.getName());
		tp.setPrice(p.getPrice());
		tp.setStock(p.getStock());
		boolean updateImg = false;
		if(img==null||img.trim().equals("")) {
			//此时说明不修改图片
		} else {
			//此时说明需要修改图片
			if(!this.hasErrors()) {
				//是否要修改文件
				byte[] fs = (byte[])req.getAttribute("fs");
				String fname = req.getParameter("img");
				fname = FilenameUtils.getName(fname);
				RequestUtil.uploadFile(fname, "img", fs, req);
			}
			updateImg = true;
		}
		if(this.hasErrors()) {
			req.setAttribute("p", tp);
			req.setAttribute("cs",categoryDao.list());
			return "product/updateInput.jsp";
		}
		if(updateImg) {
			//先删除原有的图片
			String oimg = tp.getImg();
			File f = new File(SystemContext.getRealpath()+"/img/"+oimg);
			f.delete();
			tp.setImg(p.getImg());
		}
		goodsDao.update(cid, tp);
		return redirPath("goods.do?method=list");
	}
}










