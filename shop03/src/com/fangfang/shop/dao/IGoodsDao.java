package com.fangfang.shop.dao;

import com.fangfang.shop.model.Goods;
import com.fangfang.shop.model.Pager;

public interface IGoodsDao {
	public void add(int cid,Goods goods);
	public void update(int cid,Goods goods);
	public void delete(int id);
	public Goods load(int id);
	/**
	 * 可以通过商品类别和名称进行搜索
	 * 此时可以进行灵活的排序
	 * @param cid
	 * @param name
	 * @return
	 */
	public Pager<Goods> find(int cid,String name,int status);
	/**
	 * 增加库存
	 * @param id
	 * @param num
	 */
	public void addStock(int id,int num);
	/**
	 * 减少库存
	 * @param id
	 * @param num
	 */
	public void decreaseStock(int id,int num);
	/**
	 * 变更状态
	 * @param id
	 */
	public void changeStatus(int id);
}
