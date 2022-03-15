package com.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import com.dao.BillDAO;
import com.model.PurchasedProduct;

import Auth.Authentication;

@WebServlet(urlPatterns="/bill.do")
public class BillServlet extends HttpServlet{
	//@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (!Authentication.isAuthenticated(request)) {
            response.setStatus(400);
            response.getWriter().println("{'code': '1000'}");
        }
		else {
			response.setContentType("application/json");
			
			int billID = Integer.parseInt(request.getParameter("bill_id"));
			
			BillDAO bdao= new BillDAO();
			
			try {
				response.getWriter().print(bdao.getBill(billID));
			} catch (IOException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (!Authentication.isAuthenticated(request)) {
            response.setStatus(400);
            response.getWriter().println("{'code': '1000'}");
        }
		else {
			response.setContentType("application/json");
			
			int customer = Integer.parseInt(request.getParameter("customer_id"));
			JSONObject products = new JSONObject(request.getParameter("line_items"));
			JSONArray lineItems = products.getJSONArray("products");
			
			ArrayList<PurchasedProduct> arr = new ArrayList<PurchasedProduct>();
			
			int len = lineItems.length();
			for(int i =0; i<len;i++) {
				PurchasedProduct p = new PurchasedProduct();
				JSONObject ele = lineItems.getJSONObject(i);
				
				p.setId(ele.getInt("product_id"));
				p.setQuantity(ele.getInt("product_quantity"));
				p.setPrice(ele.getDouble("product_price"));
				p.setTax(ele.getDouble("product_tax"));
				p.setAmount(ele.getDouble("product_amount"));
				arr.add(p);
			}
			
			BillDAO bdao = new BillDAO();
			try {
				JSONObject res = bdao.createBill(customer, arr);
				response.getWriter().print(res);
			} catch (IOException | SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (!Authentication.isAuthenticated(request)) {
            response.setStatus(400);
            response.getWriter().println("{'code': '1000'}");
        }
		else {
			response.setContentType("application/json");
			JSONObject res = new JSONObject();
			BillDAO bdao = new BillDAO();
			
			int billID = Integer.parseInt(request.getParameter("bill_id"));
			JSONObject b = new JSONObject(request.getParameter("data"));
			
			try {
				res = bdao.updateBill(billID,b);
			}
			catch(SQLException e) {
				res.put("code",505);
				res.put("message",e.getMessage());
			}
			catch(Exception e) {
				res.put("code", 400);
				res.put("message",e.getMessage());
			}
			response.getWriter().print(res);
		}
	}
}
