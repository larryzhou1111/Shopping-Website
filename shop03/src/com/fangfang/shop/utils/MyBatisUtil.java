package com.fangfang.shop.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.concurrent.ThreadPoolExecutor;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.sun.corba.se.spi.orbutil.threadpool.ThreadPool;

public class MyBatisUtil {
	private static SqlSessionFactory factory;
	static{
		try {
			InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
			factory = new SqlSessionFactoryBuilder().build(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static SqlSession createSession(){
		return factory.openSession();
	}
	
	public static void closeSession(SqlSession session){
		if (session !=null) {
			session.close();
		}
	}
}

















