package com.cszjo.com.http.server;

/**  
 * @Title:  Solution.java   
 * @Description: 启动Web服务器入口
 * @author: Ye   
 */  
public class Solution {
	
	//启动方法
	public static void main(String[] args) {
		new Thread(new Server(false)).start();
	}
}
