package com.fangfang.shop.model;

import java.util.ArrayList;
import java.util.List;

public class ShopCart {
	public ShopCart() {
		isEmpty = true;
		goodses = new ArrayList<CartGoods>();
	}
	List<CartGoods> goodses;
	private boolean isEmpty;
	
	public boolean getIsEmpty() {
		return isEmpty;
	}

	public void setIsEmpty(boolean isEmpty) {
		this.isEmpty = isEmpty;
	}
	
	public List<CartGoods> getGoodses() {
		return goodses;
	}

	public void setGoodses(List<CartGoods> goodses) {
		this.goodses = goodses;
	}

	public void add(Goods goods) {
		CartGoods cp = findCartProduct(goods.getId());
		if(cp==null) {
			if(goods.getStock()<1) {
				throw new ShopException("该商品库存不够");
			}
			cp = new CartGoods();
			cp.setPid(goods.getId());
			cp.setGoods(goods);
			cp.setNumber(1);
			cp.setPrice(goods.getPrice());
			goodses.add(cp);
		} else {
			if(goods.getStock()<cp.getNumber()+1) {
				System.out.println("find exception");
				throw new ShopException("该商品库存不够");
			}
			cp.setNumber(cp.getNumber()+1);
			cp.setPrice(cp.getPrice()+goods.getPrice());
		}
		isEmpty = false;
	}
	
	private CartGoods findCartProduct(int pid) {
		for(CartGoods cp:goodses) {
			if(cp.getPid()==pid) return cp;
		}
		return null;
	}

	public void addGoodsNumber(int pid, int number) {
		for(CartGoods cp:goodses) {
			if(cp.getPid()==pid) {
				if(cp.getGoods().getStock()<cp.getNumber()+number) {
					//库存不够
					throw new ShopException("商品库存不够");
				}
				cp.setNumber(cp.getNumber()+number);
				cp.setPrice(cp.getPrice()+(number*cp.getGoods().getPrice()));
			}
		}
	}
	
	public void clearGoods(int pid) {
		for(int i=0;i<goodses.size();i++) {
			CartGoods cp = goodses.get(i);
			if(cp.getPid()==pid) {
				goodses.remove(cp);
			}
		}
		if(goodses.size()<=0) isEmpty = true;
	}
	
	public void clearShopCart() {
		goodses.clear();
		isEmpty = true;
	}
	
}
