package com.cszjo.com.http.context;

import java.util.Map;
import java.util.Set;

/**  
 * @Title:  Request.java   
 * @Description: 接口设计:Request接口
 * @author: Ye   
 */  
public interface Request {
	
	public static final String POST = "POST";
	
	public static final String GET = "GET";
	/**
	 * 得到参数
	 * @param:  @return  
	 * @return: Map<String,Object>
	 * @Autor: Ye
	 */
	public Map<String, Object> getAttribute();
	
	/**
	 * 得到请求方式
	 * @param:  @return  
	 * @return: String
	 * @Autor: Ye
	 */
	public String getMethod();
	
	/**
	 * 得到URI
	 * @param:  @return  
	 * @return: String
	 * @Autor: Ye
	 */
	public String getUri();

	/**
	 * 版本协议
	 * @param:  @return  
	 * @return: String
	 * @Autor: Ye
	 */
	public String getProtocol();

	/**
	 * 得到请求头Map
	 * @param:  @return  
	 * @return: String
	 * @Autor: Ye
	 */
	public Map<String, Object> getHeaders();

	/**
	 * 得到请求头参数集合
	 * @param:  @return  
	 * @return: String
	 * @Autor: Ye
	 */
	public Set<String> getHeaderNames();

	/**
	 * 根据请求头名得到对应的请求头
	 * @param:  @return  
	 * @return: String
	 * @Autor: Ye
	 */
	public Object getHeader(String key);
}
