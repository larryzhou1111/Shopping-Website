package com.fangfang.shop.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.fangfang.shop.model.Address;
import com.fangfang.shop.model.User;
import com.fangfang.shop.utils.DBUtil;

public class AddressJDBCDao implements IAddressDao {

	@Override
	public void add(Address address, int userId) {
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = DBUtil.getConn();
			String sql = "insert into t_address(name,phone,postcode,user_id) value (?,?,?,?)";
			ps = con.prepareStatement(sql);
			ps.setString(1, address.getName());
			ps.setString(2, address.getPhone());
			ps.setString(3, address.getPostcode());
			ps.setInt(4, userId);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			DBUtil.close(ps);
			DBUtil.close(con);
		}
	}

	@Override
	public void delete(int id) {
		
	}

	@Override
	public void update(Address address) {
		// TODO Auto-generated method stub

	}

	@Override
	public Address load(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Address> list(int userId) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Address> addresses = new ArrayList<Address>();
		Address address = null;
		User user = null;
		try {
			con = DBUtil.getConn();
			String sql = "select *,t1.id as 'a_id' from t_address t1 left join t_user t2 on(t1.user_id=t2.id ) where user_id=?";
			ps = con.prepareStatement(sql);
			ps.setInt(1, userId);
			rs = ps.executeQuery();
			while (rs.next()) {
				address = new Address();
				address.setId(rs.getInt("a_id"));
				address.setName(rs.getString("name"));
				address.setPhone(rs.getString("phone"));
				address.setPostcode(rs.getString("postcode"));
				if (user == null) {
					user = new User();
					user.setId(rs.getInt("id"));
					user.setNickname(rs.getString("nickname"));
					user.setPassword(rs.getString("password"));
					user.setType(rs.getInt("type"));
					user.setUsername(rs.getString("username"));
					address.setUser(user);
				}
				addresses.add(address);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			DBUtil.close(ps);
			DBUtil.close(con);
			DBUtil.close(rs);
		}
		return addresses;
	}

}
