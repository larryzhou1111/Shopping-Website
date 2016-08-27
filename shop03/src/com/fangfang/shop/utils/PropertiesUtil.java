package com.fangfang.shop.utils;

import java.util.Properties;

public class PropertiesUtil {
	private static Properties jdbcProp;
	private static Properties daoProp;
	
	public static Properties getJdbcProp(){
		try {
			if(jdbcProp == null){
				jdbcProp = new Properties();
				jdbcProp.load(PropertiesUtil.class.getClassLoader().getResourceAsStream("jdbc.properties"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jdbcProp;
	}
	
	public static Properties getdaoProp(){
		try {
			if(daoProp == null){
				daoProp = new Properties();
				daoProp.load(PropertiesUtil.class.getClassLoader().getResourceAsStream("dao.properties"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return daoProp;
	}
}
