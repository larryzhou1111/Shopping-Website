package com.fangfang.shop.model;

public class SystemContext {
	public static ThreadLocal<Integer> pageSize = new ThreadLocal<Integer>();
	private static ThreadLocal<Integer> pageNow = new ThreadLocal<Integer>();
	private static ThreadLocal<Integer> pageOffset = new ThreadLocal<Integer>();
	private static ThreadLocal<String> realpath = new ThreadLocal<String>(); 
	//升序还是降序
	private static ThreadLocal<String> order = new ThreadLocal<String>();
	//根据哪个字段排序
	private static ThreadLocal<String> sort = new ThreadLocal<String>();
	
	public static String getRealpath() {
		return realpath.get();
	}

	public static void setRealpath(String _realpath) {
		realpath.set(_realpath);
	}

	public static void removeRealPath(){
		realpath.remove();
	}
	
	public static String getOrder() {
		return order.get();
	}

	public static void setOrder(String _order) {
		order.set(_order);
	}
	
	public static void removeOrder(){
		order.remove();
	}

	public static String getSort() {
		return sort.get();
	}

	public static void setSort(String _sort) {
		sort.set(_sort);
	}

	public static void removeSort(){
		sort.remove();
	}
	
	public static int getPageOffset() {
		return pageOffset.get();
	}

	public static void setPageOffset(int  _pageOffset) {
		pageOffset.set(_pageOffset);
	}

	public static void removePageOffset(){
		pageOffset.remove();
	}
	
	public static void setPageSize(int _pageSize){
		pageSize.set(_pageSize);
	}
	
	public static int getPageSize(){
		return pageSize.get();
	}	

	public static void removePageSize(){
		pageSize.remove();
	}
	
	public static void setPageNow(int _pageNow){
		pageNow.set(_pageNow);
	}
	
	public static int getPageNow(){
		return pageNow.get();
	}
	
	public static void removePageNow(){
		pageNow.remove();
	}
}

