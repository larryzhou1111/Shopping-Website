package com.fangfang.shop.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fangfang.shop.model.Address;
import com.fangfang.shop.model.CartGoods;
import com.fangfang.shop.model.Goods;
import com.fangfang.shop.model.Orders;
import com.fangfang.shop.model.Pager;
import com.fangfang.shop.model.ShopDi;
import com.fangfang.shop.model.ShopException;
import com.fangfang.shop.model.User;

public class OrdersDao extends BaseDao<Orders> implements IOrdersDao {

	private IGoodsDao goodsDao;
	private IAddressDao addressDao;
	
	public IAddressDao getAddressDao() {
		return addressDao;
	}
	@ShopDi
	public void setAddressDao(IAddressDao addressDao) {
		this.addressDao = addressDao;
	}
	public IGoodsDao getGoodsDao() {
		return goodsDao;
	}
	@ShopDi
	public void setGoodsDao(IGoodsDao goodsDao) {
		this.goodsDao = goodsDao;
	}

	@Override
	public void add(Orders orders, User user, int aid, List<CartGoods> cps) {
		Address a = addressDao.load(aid);
		orders.setAddress(a);
		orders.setUser(user);
		super.add(orders);
		//接着添加购物对象
		for(CartGoods cg:cps) {
			this.addCartGoods(cg, orders, cg.getGoods());
		}
	}

	@Override
	public void delete(int id) {
		this.deleteCartGoods(id);
		Orders o = this.load(id);
		if(o.getStatus()!=1) throw new ShopException("只能删除未付款的订单");
		super.delete(Orders.class, id);
	}

	@Override
	public void update(Orders orders) {
		super.update(orders);
	}

	@Override
	public void updatePrice(int id, double price) {
		Orders o = this.load(id);
		o.setPrice(price);
		this.update(o);
	}

	@Override
	public void updatePayStatus(int id) {
		Orders o = this.load(id);
		List<CartGoods> cps = o.getGoodses();
		List<Goods> ps = new ArrayList<Goods>();
		for(CartGoods cp:cps) {
			int num = cp.getNumber();
			Goods p = goodsDao.load(cp.getGoods().getId());
			if(p.getStock()<num) {
				throw new ShopException("要买的"+p.getName()+"库存不足");
			} else {
				p.setStock(p.getStock()-num);
				ps.add(p);
			}
		}
		//更新库存
		for(Goods pp:ps) {
			goodsDao.update(pp.getCategory().getId(), pp);
		}
		o.setStatus(2);
		o.setPayDate(new Date());
		this.update(o);
	}

	@Override
	public void updateSendStatus(int id) {
		Orders o = this.load(id);
		o.setStatus(3);
		this.update(o);
	}

	@Override
	public void updateConfirmStatus(int id) {
		Orders o = this.load(id);
		o.setStatus(4);
		o.setConfirmDate(new Date());
		this.update(o);
	}

	@Override
	public Orders load(int id) {
		return super.load(Orders.class, id);
	}

	@Override
	public Pager<Orders> findByUser(int userId, int status) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("userId", userId);
		if(status>0) {
			params.put("status", status);
		}
		return super.find(Orders.class.getName()+".find_by_user", params);
	}

	@Override
	public Pager<Orders> findByStatus(int status) {
		Map<String,Object> params = new HashMap<String,Object>();
		if(status>0) {
			params.put("status", status);
		}
		return super.find(Orders.class.getName()+".find_by_status", params);
	}

	@Override
	public void addCartGoods(CartGoods cg, Orders o, Goods g) {
		cg.setOrders(o);
		cg.setGoods(g);
		super.add(CartGoods.class.getName()+".add", cg);
	}

	@Override
	public void deleteCartGoods(int oid) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("oid", oid);
		super.delete(CartGoods.class.getName()+".deleteByOrders", params);
	}

}
