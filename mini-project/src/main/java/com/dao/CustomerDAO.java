package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONObject;

import com.model.Customer;

public class CustomerDAO {
	Connection con = Database.getConnectionDB();
	
	public JSONObject createCustomer(Customer c) throws SQLException {
		JSONObject json = new JSONObject();
		
		String query = "INSERT INTO customer(cust_name,cust_phno,cust_add_st,cust_add_city) values (?,?,?,?)";
		PreparedStatement ps = con.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
		
		ps.setString(1,c.getName());
		ps.setLong(2, c.getMobileNo());
		ps.setString(3,c.getStreet());
		ps.setString(4,c.getCity());
		
		int row = ps.executeUpdate();
		ResultSet rs = ps.getGeneratedKeys();
		if(row > 0 && rs.next()) {
			json.put("code",201);
			json.put("message", "Customer Created");
			json.put("cust_id", rs.getInt(1));
			
		}
		else {
			json.put("code",505);
			json.put("message", "Customer Not Created");
		}
		return json;
	}
	
	public JSONObject getCustomerName(int id) throws SQLException {
		JSONObject json = new JSONObject();
		
		String query = "SELECT cust_name FROM customer WHERE cust_id = ?";
		PreparedStatement ps = con.prepareStatement(query);
		ps.setInt(1,id);
		ResultSet rs = ps.executeQuery();
		
		if(rs.next()) {
			json.put("code", 200);
			json.put("message", "success");
			json.put("customer_name",rs.getString(1));
		}
		else {
			json.put("code", 404);
			json.put("message", "resource not found");
			
		}
		return json;
	}
	
	public JSONObject getCustomer(int id) throws SQLException{
		
		String query = "SELECT * FROM customer WHERE cust_id = ?";
		PreparedStatement ps = con.prepareStatement(query);
		ps.setInt(1,id);
		ResultSet rs = ps.executeQuery();
		
		JSONObject json = new JSONObject();
		
		if(rs.next()) {
			JSONObject address = new JSONObject();
			json.put("code", 200);
			json.put("id", id);
			json.put("name", rs.getString("cust_name"));
			json.put("phone number", rs.getLong("cust_phno"));
			address.put("street", rs.getString("cust_add_st"));
			address.put("city", rs.getString("cust_add_city"));
			address.put("country", rs.getString("cust_add_country"));
			json.put("address", address);
		}
		else {
			json.put("code",505);
			json.put("message","data not found");
		}
		
		return json;
	}
	
	
	public JSONObject getCustomer() throws SQLException{
		
		String query = "SELECT * FROM customer";
		Statement ps = con.createStatement();
		ResultSet rs = ps.executeQuery(query);
		
		JSONObject json = new JSONObject();
		
		if(rs.next()) {
			json.put("code",200);
			JSONArray arr = new JSONArray();
			do {
				JSONObject customer = new JSONObject();
				JSONObject address = new JSONObject();
				customer.put("id", rs.getInt("cust_id"));
				customer.put("name", rs.getString("cust_name"));
				customer.put("phone number", rs.getLong("cust_phno"));
				address.put("street", rs.getString("cust_add_st"));
				address.put("city", rs.getString("cust_add_city"));
				address.put("country", rs.getString("cust_add_country"));
				customer.put("address", address);
				arr.put(customer);
			}while(rs.next());
			json.put("customer", arr);
		}
		else {
			json.put("code",505);
			json.put("message","data not found");
		}
		
		return json;
	}
	
	public JSONObject updateCustomer(int id,JSONObject c)throws SQLException{
		JSONObject res = new JSONObject();
		
		StringBuilder query = new StringBuilder("UPDATE customer SET ");
		int len = c.length();
		
		Iterator<String> iter1 = c.keys();
		for(int i=0;i<len-1;i++) {
			query.append(iter1.next() + "  = ? ,");
		}
		query.append(iter1.next() + "  = ? ");
		query.append("WHERE cust_id ="+id);
		PreparedStatement ps = con.prepareStatement(query.toString());
		
		Iterator<String> iter = c.keys();
		
		int i =1;
		while(iter.hasNext()) {
			
			String key = iter.next();
			if(key.compareTo("cust_phno") == 0) {
				ps.setLong(i, c.getLong(key));
			}
			else{
				ps.setString(i, c.getString(key));
			}
			i++;
		}
		int row = ps.executeUpdate();

		if(row  == 1) {
			res.put("code", 0);
			res.put("message", "success");
		}
		else {
			res.put("code", 400);
			res.put("message", "fail");
		}
		return res;
	}
}
