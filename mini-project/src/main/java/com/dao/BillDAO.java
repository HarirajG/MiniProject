package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONObject;

import com.model.PurchasedProduct;

public class BillDAO {
	Connection con = Database.getConnectionDB();
	
	public JSONObject createBill(int customerID, ArrayList<PurchasedProduct> pp) throws SQLException {

		String query = "INSERT INTO bill(cust_id,bill_amount) values (?,0)";
		
		PreparedStatement ps = con.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
		ps.setInt(1, customerID);
		ps.executeUpdate();
		ResultSet rs = ps.getGeneratedKeys();
		JSONObject json = new JSONObject();
		if(rs.next()) {
			PurchasedProductDAO ppdao = new PurchasedProductDAO();
			int bill_id = rs.getInt(1);
			json = ppdao.insertPurchasedProduct(bill_id,pp);
			json.put("bill_id", bill_id);
		}
		else {
			json.put("code",505 );
			json.put("message","Bill is not created");
		}
		
		return json;
	}
	
	public JSONObject getBill(int bill_id) throws SQLException {
		JSONObject bill = new JSONObject();
		
		String query = "SELECT * FROM bill WHERE bill_id = ?";
		
		PreparedStatement ps = con.prepareStatement(query);
		ps.setInt(1, bill_id);
		
		ResultSet rs = ps.executeQuery();
		
		JSONObject json = new JSONObject();
		if(rs.next()) {
			bill.put("id",bill_id);
			bill.put("item count",rs.getInt("bill_item_count"));
			bill.put("amount",rs.getDouble("bill_amount"));
			bill.put("time", rs.getTimestamp("bill_time"));
			bill.put("discount",rs.getDouble("bill_discount"));		
			bill.put("discounted amount",rs.getDouble("bill_discounted_amount"));	
			CustomerDAO cdao = new CustomerDAO();
			int customerID = rs.getInt("cust_id");
			JSONObject customer = cdao.getCustomer(customerID);
			customer.remove("code");
			bill.put("customer",customer);
			
			PurchasedProductDAO pdao = new PurchasedProductDAO();
			bill.put("purchase",pdao.getPurchasedProducts(bill_id));
			
			
			
			json.put("code", 201);
			json.put("message", "success");
			json.put("bill",bill);
		}
		else {
			json.put("code", 505);
			json.put("message", "data not found");
			
		}
		
		return json;
	}
	
	public JSONObject updateBill(int id,JSONObject b) throws SQLException {
		JSONObject res = new JSONObject();
		PurchasedProductDAO ppdao = new PurchasedProductDAO();
		if(b.has("cust_id") || b.has("bill_time")) {
			Iterator<String> itr1 = b.keys();
			Iterator<String> itr2 = b.keys();
			int len = b.length();		
			
			StringBuilder query = new StringBuilder("UPDATE bill SET ");
			
			for(int i=0;i<len-1;i++) {
				String key = itr1.next();
				if("purchase".compareTo(key) != 0) {
					query.append(key+" = ? ,");
				}	
			}
			query.append(itr1.next()+" = ? ");
			query.append("WHERE bill_id = "+id);
			
			PreparedStatement ps = con.prepareStatement(query.toString());
			
			int i = 1;
			while(itr2.hasNext()) {
				String key = itr2.next();
				if("cust_id".compareTo(key) == 0) {
					ps.setInt(i, b.getInt(key));
				}
				else if("bill_time".compareTo(key) == 0){
					ps.setTime(i, Time.valueOf(b.getString(key)));
				}
			}
			
			int row = ps.executeUpdate();
			
			JSONObject res2 = null;
			if(b.has("purchase")) {
				res2 = ppdao.updatePurchasedProduct(id,b.getJSONArray("purchase"));
				
			}
			if(row == 1 && (res2 == null || res2.getInt("code") == 0)) {
				res.put("code", 0);
				res.put("message", "success");
			}
			else {
				res.put("code", "505");
				if(res2 != null && res2.getInt("code") == 400) {
					res.put("message", "product updation fails");
				}
				else {
					res.put("message", "fails");
				}
			}
			
			return res;
		}
		else {
			return ppdao.updatePurchasedProduct(id,b.getJSONArray("purchase"));
		}
	}
}
