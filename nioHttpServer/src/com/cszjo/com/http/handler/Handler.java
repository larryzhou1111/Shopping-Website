package com.cszjo.com.http.handler;

import com.cszjo.com.http.context.Context;

/**  
 * @Title:  Handler.java   
 * @Description: 接口设计：处理器Handler接口
 * @author: Ye   
 */  
public interface Handler {
	
	/**
	 * 初始化handler
	 * @param:  @param context  
	 * @return: void
	 * @Autor: Ye
	 */
	public void init(Context context);
	
	/**
	 * handler service(service应该不是这样做的... - -!)
	 * @param:  @param context  
	 * @return: void
	 * @Autor: Ye
	 */
	public void service(Context context);
	
	/**
	 * Get形式执行该方法
	 * @param:  @param context  
	 * @return: void
	 * @Autor: Ye
	 */
	public void doGet(Context context);
	
	/**
	 * POST形式执行该方法
	 * @param:  @param context  
	 * @return: void
	 * @Autor: Ye
	 */
	public void doPost(Context context);
	
	/**
	 * 销毁Handler(并没有销毁... - -!)
	 * @param:  @param context  
	 * @return: void
	 * @Autor: Ye
	 */
	public void destory(Context context);
}
