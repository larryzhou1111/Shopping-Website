package com.fangfang.shop.dao;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.fangfang.shop.model.Category;
import com.fangfang.shop.model.Goods;
import com.fangfang.shop.model.Pager;
import com.fangfang.shop.model.ShopDi;
import com.fangfang.shop.model.ShopException;
import com.fangfang.shop.model.SystemContext;

public class GoodsDao extends BaseDao<Goods> implements IGoodsDao {
	private ICategoryDao categoryDao;
	
	public ICategoryDao getCategoryDao() {
		return categoryDao;
	}

	@ShopDi
	public void setCategoryDao(ICategoryDao categoryDao) {
		this.categoryDao = categoryDao;
	}

	@Override
	public void add(int cid, Goods goods) {
		Category category = categoryDao.load(cid);
		if (category==null) {
			throw new ShopException("要添加产品的类别不存在");
		}
		goods.setCategory(category);
		super.add(goods);
	}

	@Override
	public void update(int cid, Goods goods) {
		Category category = categoryDao.load(cid);
		if (category==null) {
			throw new ShopException("要添加产品的类别不存在");
		}
		goods.setCategory(category);
		super.update(goods);
	}

	@Override
	public void delete(int id) {
		// TODO 如果用户购买了该商品就不能删除，该商品存在订单也不能删除，
		//如果可以删除商品的话需要删除商品的图片
		Goods goods = this.load(id);
		String img = goods.getImg();
		super.delete(Goods.class, id);
		//删除图片
		String path = SystemContext.getRealpath()+"/img/";
		File f = new File(path+img);
		f.delete();
	}

	@Override
	public Goods load(int id) {
		return super.load(Goods.class, id);
	}

	@Override
	public Pager<Goods> find(int cid, String name, int status) {
		Map<String, Object> params = new HashMap<String, Object>();
		if (cid>0) {
			params.put("cid", cid);
		}
		if (name!=null&&!"".equals(name.trim())) {
			params.put("name", "%"+name+"%");
		}
		if(status==1||status==-1) {
			params.put("status", status);
		}
		return super.find(Goods.class, params);
	}

	@Override
	public void addStock(int id, int num) {
		Goods goods = this.load(id);
		goods.setStock(goods.getStock()+num);
		this.update(goods);
	}

	@Override
	public void decreaseStock(int id, int num) {
		Goods goods = this.load(id);
		goods.setStock(goods.getStock()-num);
		this.update(goods);
	}

	@Override
	public void changeStatus(int id) {
		Goods goods = this.load(id);
		if(goods.getStatus()==-1) {
			goods.setStatus(1);
		} else {
			goods.setStatus(-1);
		}
		this.update(goods);
	}	

}
