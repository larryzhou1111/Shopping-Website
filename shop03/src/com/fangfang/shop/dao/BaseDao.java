package com.fangfang.shop.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.fangfang.shop.model.Pager;
import com.fangfang.shop.model.SystemContext;
import com.fangfang.shop.utils.DaoUtil;
import com.fangfang.shop.utils.MyBatisUtil;

public class BaseDao<T> {
	
	public BaseDao(){
		DaoUtil.diDao(this);
	}
	
	public void add(T obj) {
		SqlSession session = null;
		try {
			session = MyBatisUtil.createSession();
			session.insert(obj.getClass().getName()+".add", obj);
			session.commit();
		} catch (Exception e) {
			e.printStackTrace();
			session.rollback();
		}finally {
			MyBatisUtil.closeSession(session);
		}
	}
	
	public void add(String sqlId,Object obj) {
		SqlSession session = null;
		try {
			session = MyBatisUtil.createSession();
			session.insert(sqlId,obj);
			session.commit();
		} catch (Exception e) {
			e.printStackTrace();
			session.rollback();
		} finally {
			MyBatisUtil.closeSession(session);
		}
	}
	
	public void update(T obj) {
		SqlSession session = null;
		try {
			session = MyBatisUtil.createSession();
			session.update(obj.getClass().getName()+".update", obj);
			session.commit();
		}catch(Exception exception){
			exception.printStackTrace();
			session.rollback();
		}
		finally{
			MyBatisUtil.closeSession(session);
		}
	}
	
	public void delete(String sqlId,Map<String,Object> params) {
		SqlSession session = null;
		try {
			session = MyBatisUtil.createSession();
			session.delete(sqlId,params);
			session.commit();
		} catch (Exception e) {
			e.printStackTrace();
			session.rollback();
		} finally {
			MyBatisUtil.closeSession(session);
		}
	}
	
	public void delete(Class<T> clz,int id) {
		SqlSession session = null;
		try {
			session = MyBatisUtil.createSession();
			session.delete(clz.getName()+".delete", id);
			System.out.println("ahhahaha");
			session.commit();
		}catch(Exception exception){
			exception.printStackTrace();
			session.rollback();
		}
		finally{
			MyBatisUtil.closeSession(session);
		}
	}
	
	@SuppressWarnings("unchecked")
	public T load(Class<T> clz,int id) {
		SqlSession session = null;
		T t = null;
		try {
			session = MyBatisUtil.createSession();
			t = (T)session.selectOne(clz.getName()+".load", id);
		} finally{
			MyBatisUtil.closeSession(session);
		}
		return t;
	}
	
	public T loadBySqlId(String sqlId,Object obj) {
		SqlSession session = null;
		T t = null;
		try {
			session = MyBatisUtil.createSession();
			t = (T)session.selectOne(sqlId, obj);
		} finally{
			MyBatisUtil.closeSession(session);
		}
		return t;
	}
	
	public T loadBySqlId(String sqlId,Map<String, Object> params) {
		SqlSession session = null;
		T t = null;
		try {
			session = MyBatisUtil.createSession();
			t = (T)session.selectOne(sqlId, params);
		} finally{
			MyBatisUtil.closeSession(session);
		}
		return t;
	}
	
	public Pager<T> find(Class<T> clz,Map<String, Object> params) {
		return this.find(clz.getName()+".find", params);
	}
	
	public Pager<T> find(String sqlId,Map<String, Object> params) {
		int pageSize = SystemContext.getPageSize();
		int pageOffset = SystemContext.getPageOffset();
		String order = SystemContext.getOrder();
		String sort = SystemContext.getSort();
		Pager<T> pages = new Pager<T>();
		SqlSession session = null;
		try{
			session = MyBatisUtil.createSession();
			if(params==null) new HashMap<String,Object>();
			params.put("pageSize", pageSize);
			params.put("pageOffset", pageOffset);
			params.put("order", order);
			params.put("sort", sort);
			List<T> datas = session.selectList(sqlId, params);
			pages.setDatas(datas);
			pages.setPageOffset(pageOffset);
			pages.setPageSize(pageSize);
			int totalRecord = session.selectOne(sqlId+"_count", params);
			pages.setTotalRecord(totalRecord);
		}finally{
			MyBatisUtil.closeSession(session);
		}
		return pages;
	}
	
	public List<T> list(Class<T> clz,Map<String,Object> params) {
		return this.list(clz.getName()+".list", params);
	}
	
	public List<T> list(String sqlId,Map<String, Object> params){
		List<T> list =  null;
		SqlSession session = null;
		try{
			session = MyBatisUtil.createSession();
			list = session.selectList(sqlId,params);
		}finally{
			MyBatisUtil.closeSession(session);
		}
		return list;
	}
}


















