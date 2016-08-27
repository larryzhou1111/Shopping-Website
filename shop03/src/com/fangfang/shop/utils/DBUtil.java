package com.fangfang.shop.utils;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class DBUtil {
	private static Connection ct = null;
	
	public static Connection getConn(){
		Properties properties = PropertiesUtil.getJdbcProp();
		String drivername = properties.getProperty("drivername");
		String username = properties.getProperty("username");
		String passwd = properties.getProperty("passwd");
		String url = properties.getProperty("url");
		try {
			Class.forName(drivername);
			ct = DriverManager.getConnection(url, username, passwd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ct;
		/*try {
			Context initContext = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			DataSource ds = (DataSource)envContext.lookup("jdbc/msg");
			ct = ds.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ct;*/
	}
	public static void close(Connection con){
		 
			try {
				if (con != null)
					con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	public static void close(PreparedStatement ps){
		 
		try {
			if (ps != null)
				ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void close(ResultSet rs){
		 
		try {
			if (rs != null)
				rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
