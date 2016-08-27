package com.fangfang.shop.dao;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.fangfang.shop.utils.PropertiesUtil;
import com.sun.corba.se.spi.ior.ObjectKey;

import jdk.nashorn.internal.runtime.regexp.JoniRegExp.Factory;

public class PropertiesFactory implements IDAOFactory {
	private static PropertiesFactory f = new PropertiesFactory();
	private static Map<String, Object> daos = new HashMap<String, Object>();
	private PropertiesFactory(){}
 	
	public static IDAOFactory getInstance(){
		return f;
	}
	
	@Override
	public Object getDao(String name) {
		try {
			if (daos.containsKey(name)) {
				return daos.get(name);
			}
			Properties properties = PropertiesUtil.getdaoProp();
			String str = properties.getProperty(name);
			Object obj = Class.forName(str).newInstance();
			daos.put(name, obj);
			return obj;
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

}
