package com.fangfang.shop.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.fangfang.shop.model.Address;
import com.fangfang.shop.model.Pager;
import com.fangfang.shop.model.User;
import com.fangfang.shop.utils.DBUtil;

public class UserJDBCDao implements IUserDao {

	private User loadOnceUser(int id){
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Address> addresses = new ArrayList<Address>();
		Address address = null;
		User user = null;
		try {
			con = DBUtil.getConn();
			String sql = "select * ,t2.id as 'a_id' from t_user t1 left join "
					+ "t_address t2 on(t1.id=t2.user_id) where t1.id=?";
			ps = con.prepareStatement(sql);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			while (rs.next()) {
				if (user==null) {
					user = new User();
					user.setId(rs.getInt("id"));
					user.setNickname(rs.getString("nickname"));
					user.setPassword(rs.getString("password"));
					user.setType(rs.getInt("type"));
					user.setUsername(rs.getString("username"));
				}
				address = new Address();
				address.setId(rs.getInt("a_id"));
				address.setName(rs.getString("name"));
				address.setPhone(rs.getString("phone"));
				address.setPostcode(rs.getString("postcode"));
				addresses.add(address);
			}
			user.setAddresses(addresses);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			DBUtil.close(rs);
			DBUtil.close(ps);
			DBUtil.close(con);
		}
		return user;
	}
	
	private User loadSecondUser(int id){
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Address> addresses = new ArrayList<Address>();
		Address address = null;
		User user = null;
		try {
			con = DBUtil.getConn();
			String sql = "select * from t_user where id=?";
			ps = con.prepareStatement(sql);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			while (rs.next()) {
				user = new User();
				user.setId(rs.getInt("id"));
				user.setNickname(rs.getString("nickname"));
				user.setPassword(rs.getString("password"));
				user.setType(rs.getInt("type"));
				user.setUsername(rs.getString("username"));
			}
			sql = "select * from t_address where user_id=?";
			ps = con.prepareStatement(sql);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			while (rs.next()) {
				address = new Address();
				address.setId(rs.getInt("id"));
				address.setName(rs.getString("name"));
				address.setPhone(rs.getString("phone"));
				address.setPostcode(rs.getString("postcode"));
				addresses.add(address);
			}
			user.setAddresses(addresses);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			DBUtil.close(rs);
			DBUtil.close(ps);
			DBUtil.close(con);
		}
		return user;
	}
	
	@Override
	public User load(int id) {
//		return loadSecondUser(id);
		return loadOnceUser(id);
	}

	@Override
	public void add(User user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public User loadByUsername(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(User user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Pager<User> find(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User login(String username, String password) {
		// TODO Auto-generated method stub
		return null;
	}

}
