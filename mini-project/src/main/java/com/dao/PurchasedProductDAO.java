package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.model.PurchasedProduct;

public class PurchasedProductDAO {
	Connection con = Database.getConnectionDB();
	
	public JSONObject insertPurchasedProduct(int billId, ArrayList<PurchasedProduct> pp) throws SQLException {
		
		StringBuffer query = new StringBuffer("INSERT INTO purchase(bill_id,prod_id,pur_qua,pur_price,pur_tax,pur_amount) values (?,?,?,?,?,?)");
		int len = pp.size();
		
		for(int i = 0; i<len-1; i++) {
			query.append(", (?,?,?,?,?,?)");
		}
		
		
		PreparedStatement ps = con.prepareStatement(query.toString());
		System.out.println(billId);
		int i = 0,row = 6;
		for(PurchasedProduct p:pp) {
			ps.setInt(row*i+1, billId);
			ps.setInt(row*i+2, p.getId());
			ps.setInt(row*i+3, p.getQuantity());
			ps.setDouble(row*i+4, p.getPrice());
			ps.setDouble(row*i+5, p.getTax());
			ps.setDouble(row*i+6, p.getAmount());
			i++;
		}
		row = ps.executeUpdate();
		
		JSONObject json = new JSONObject();
		if(row>0) {
			json.put("code", 201);
			json.put("message", "Bill is Created");
		}
		else {
			json.put("code", 505);
			json.put("message", "Bill is not Created");
		}
		
		return json;
		
	}
	
	public JSONArray getPurchasedProducts(int billId) throws SQLException{
		JSONArray arr = new  JSONArray();
		
		String query = "SELECT * FROM billed_product_details WHERE bill_id = ?";
		PreparedStatement ps = con.prepareStatement(query);
		ps.setInt(1, billId);
		
		ResultSet rs = ps.executeQuery();
		
		while(rs.next()) {
			JSONObject p = new JSONObject();
			p.put("amount",rs.getDouble("pur_amount"));
			p.put("id",rs.getInt("prod_id"));
			p.put("name",rs.getString("prod_name"));
			p.put("price",rs.getDouble("pur_price"));
			p.put("no of units",rs.getInt("pur_qua"));
			p.put("tax percent",rs.getDouble("pur_tax"));
			p.put("total amount",rs.getDouble("pur_total_amount"));
			p.put("discounted amount",rs.getDouble("pur_discount_amount"));
			arr.put(p);
		}
		
		return arr;
	}
	
	public JSONObject updatePurchasedProduct(int id,JSONArray arr) throws SQLException {
		JSONObject res = new JSONObject();
		int arr_len = arr.length();
		int k = 0;
		int row = 0;
		while(k<arr_len) {
			JSONObject pp = arr.getJSONObject(k);
			 String query = "UPDATE purchase SET pur_qua = ? WHERE bill_id  = ? AND prod_id = ?";
			 
			 PreparedStatement ps = con.prepareStatement(query);
			 ps.setInt(1,pp.getInt("pur_qua"));
			 ps.setInt(2, id);
			 ps.setInt(3, pp.getInt("prod_id"));
			 int x = ps.executeUpdate();
			 if( x == 1) {
				 row++;
			 }
			k++; 
		}
		if(row == arr_len) {
			res.put("code",0);
			res.put("message", "success");
		}
		else {
			res.put("code",400);
			res.put("message", "fails");
		}
		return res;
	}
}
