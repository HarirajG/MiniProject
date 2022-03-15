package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONObject;

import com.model.Product;
import com.mysql.cj.jdbc.CallableStatement;

public class ProductDAO {
	Connection con = Database.getConnectionDB();
	
	public JSONObject createProduct(Product p) throws SQLException {
		JSONObject json = new JSONObject();
		
		String query = "INSERT INTO product(prod_name,prod_price,prod_qua,prod_tax) values(?,?,?,?)";
		PreparedStatement ps = con.prepareStatement(query); 
		
		ps.setString(1,p.getName());
		ps.setDouble(2,p.getPrice());
		ps.setInt(3, p.getQuantity());
		ps.setDouble(4, p.getTax());
		
		int row = ps.executeUpdate();
		if(row>0) {
			json.put("code", 201);
			json.put("message", "Product Added");
		}
		else {
			json.put("code", 505);
			json.put("message", "Product not Added");
		}
		
		return json;
		
	}
	
	public JSONObject getProductPrice(int id) throws SQLException {
		JSONObject json = new JSONObject();
		
		String query = "SELECT prod_price FROM product WHERE prod_id = ?";
		PreparedStatement ps = con.prepareStatement(query);
		ps.setInt(1, id);
		ResultSet rs = ps.executeQuery();
		
		if(rs.next()) {
			json.put("code", 201);
			json.put("message", "success");
			json.put("product_price", rs.getDouble(1));
		}
		else {
			json.put("code", 404);
			json.put("message","Data not Found");
		}
		
		return json;
	}
	
	public JSONObject getProduct(int id)throws SQLException{
		JSONObject json = new JSONObject();
		
		String query = "SELECT * FROM product WHERE prod_id = ?";
		PreparedStatement ps = con.prepareStatement(query);
		ps.setInt(1,id);
		ResultSet rs = ps.executeQuery();
		
		if(rs.next()) {
			json.put("code", 201);
			json.put("message", "success");
			JSONObject product = new JSONObject();
			product.put("id", id);
			product.put("name",rs.getString("prod_name"));
			product.put("price", rs.getDouble("prod_price"));
			product.put("tax",rs.getDouble("prod_tax"));
			product.put("quantity", rs.getDouble("prod_qua"));
			product.put("amount", rs.getDouble("prod_amount"));
			json.put("product", product);
		}
		else {
			json.put("code", 404);
			json.put("message","Data not Found");
		}
		
		return json;
	}

	public JSONObject getProduct()throws SQLException{
		JSONObject json = new JSONObject();
		
		String query = "SELECT * FROM product";
		Statement ps = con.createStatement();
		ResultSet rs = ps.executeQuery(query);
		
		if(rs.next()) {
			json.put("code", 201);
			json.put("message", "success");
			
			JSONArray arr = new JSONArray();
			do {
				JSONObject product = new JSONObject();
				product.put("id",rs.getInt("prod_id"));
				product.put("price", rs.getDouble("prod_price"));
				product.put("tax",rs.getDouble("prod_tax"));
				product.put("quantity", rs.getDouble("prod_qua"));
				product.put("amount", rs.getDouble("prod_amount"));
				product.put("name",rs.getString("prod_name"));
				arr.put(product);
			}while(rs.next());
			json.put("products", arr);
		}
		else {
			json.put("code", 404);
			json.put("message","no products available");
		}
		
		return json;
	}
	
	public JSONObject deleteProduct(int id) throws SQLException {
		JSONObject res = new JSONObject();
		
		String query = "DELETE FROM product WHERE prod_id = ?";
		PreparedStatement ps = con.prepareStatement(query);
		ps.setInt(1, id);
		int row = ps.executeUpdate();
		if(row > 0) {
			res.put("code", 0);
			res.put("message", "success");
		}
		else {
			res.put("code", 404);
			res.put("message", "data not found");
			
		}
		return res;
		
	}
	
	public JSONObject deleteProduct() throws SQLException {
		JSONObject res = new JSONObject();
		
		String query = "DELETE FROM product";
		Statement ps = con.createStatement();
		int row = ps.executeUpdate(query);
		if(row > 0) {
			res.put("code", 0);
			res.put("message", "success");
		}
		else {
			res.put("code", 404);
			res.put("message", "data not found");
			
		}
		return res;
	}
	
	public JSONObject updateProduct(int id,JSONObject p)throws SQLException{
		JSONObject res = new JSONObject();
		
		StringBuilder query = new StringBuilder("UPDATE product SET ");
		int len = p.length();
		
		Iterator<String> iter1 = p.keys();
		for(int i=0;i<len-1;i++) {
			query.append(iter1.next() + "  = ? ,");
		}
		query.append(iter1.next() + "  = ? ");
		query.append("WHERE prod_id ="+id);
		PreparedStatement ps = con.prepareStatement(query.toString());
		
		Iterator<String> iter = p.keys();
		
		int i =1;
		while(iter.hasNext()) {
			
			String key = iter.next();
			if(key.compareTo("prod_name") == 0) {
				ps.setString(i,p.getString(key));
			}
			else if(key.compareTo("prod_price") == 0) {
				ps.setDouble(i,p.getDouble(key));
			}
			else if(key.compareTo("prod_qua") == 0) {
				ps.setInt(i, p.getInt(key));
			}
			else if(key.compareTo("prod_tax") == 0) {
				ps.setDouble(i, p.getDouble(key));
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
	
	public JSONObject addProductQuantity(int id, int qua) throws SQLException{
		String query = "{CALL addProduct(?,?,?)}";
		JSONObject res = new JSONObject();
		
		CallableStatement ps = (CallableStatement) con.prepareCall(query);
		ps.setInt(1, id);
		ps.setInt(2, qua);
		ps.registerOutParameter(3,Types.INTEGER);
		boolean rs = ps.execute();
		
		if(!rs) {
			res.put("code",0);
			res.put("message","success");
			res.put("updated quantity", ps.getInt(3));
		}
		else {
			res.put("code",505);
			res.put("message","fails");
		}
		
		return res;
	}
}
