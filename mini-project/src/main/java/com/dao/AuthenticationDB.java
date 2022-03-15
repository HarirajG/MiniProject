package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONObject;

public class AuthenticationDB {
	Connection con = Database.getConnectionDB();
	
	public JSONObject checkUser(String email,String pass) throws SQLException{
		JSONObject res = new JSONObject();
		String query = "SELECT * FROM user WHERE user_email = ? AND user_pass = ?";
		
		PreparedStatement ps = con.prepareStatement(query);
		ps.setString(1,email);
		ps.setString(2, pass);
		
		ResultSet rs = ps.executeQuery();
		
		int len  = 0;
		while(rs.next()) {
			len++;
			res.put("name",rs.getString("user_name"));
		}
		
		if(len == 1) {
			res.put("code",0);
		}
		else {
			res.put("code",1000);
		}
		return res;
	}

}
