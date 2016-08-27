package com.fangfang.shop.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Properties;

import com.fangfang.shop.dao.IDAOFactory;
import com.fangfang.shop.model.ShopDi;
import com.sun.swing.internal.plaf.metal.resources.metal;

public class DaoUtil {
	
	public static void diDao(Object obj){
		try {
			Method[] methods = obj.getClass().getDeclaredMethods();
			for (Method method : methods) {
				if (method.isAnnotationPresent(ShopDi.class)) {
					ShopDi sd = method.getAnnotation(ShopDi.class);
					String val = sd.value();  
					if (val==null||"".equals(val.trim())) {
						val = method.getName().substring(3);
						val = val.substring(0, 1).toLowerCase()+val.substring(1);
					}
					Object o = DaoUtil.createDAOFactory().getDao(val);
					method.invoke(obj, o);
				}
			}
		} catch (SecurityException e) {
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
	/*public static void diDao(Object obj){
		try {
			Method[] methods = obj.getClass().getDeclaredMethods();
			for (Method method : methods) {
				String mm = method.getName();
				if(mm.startsWith("set")){
					mm = mm.substring(3);
					mm = mm.substring(0,1).toLowerCase()+mm.substring(1);
					Object o = DaoUtil.createDAOFactory().getDao(mm);
					method.invoke(obj, o);
				}
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}*/
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static IDAOFactory createDAOFactory(){
		IDAOFactory factory = null;
		try {
			Properties prop = PropertiesUtil.getdaoProp();
			String fs = prop.getProperty("factory");
			Class clz = Class.forName(fs);
			String mm = "getInstance";
			Method m = clz.getMethod(mm);
			factory = (IDAOFactory)m.invoke(clz);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return factory;
	}
}
