package com.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.dao.ProductDAO;
import com.model.Product;

import Auth.Authentication;

@WebServlet(urlPatterns="/product.do")
public class ProductServlet extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!Authentication.isAuthenticated(request)) {
            response.setStatus(400);
            response.getWriter().println("{'code': '1000'}");
        }
        else {
			String parameter = request.getParameter("product_id");
			response.setContentType("application/json");
			
			JSONObject res = new JSONObject();
			
			if(parameter == null) {
				try {
					ProductDAO pdao = new ProductDAO();
					response.getWriter().print(pdao.getProduct());
					
				}
				catch(Exception e) {
					res.put("code", 400);
					res.put("meassage", e.getMessage());
					response.getWriter().print(res);
				}
				
			}
			else {
				try {
					int productID = Integer.parseInt(request.getParameter("product_id"));
					ProductDAO pdao = new ProductDAO();
					response.getWriter().print(pdao.getProduct(productID));
					
				}
				catch(Exception e) {
					res.put("code", 400);
					res.put("meassage", e.getMessage());
					response.getWriter().print(res);
				}
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
			
			Product p = new Product();
			ProductDAO pdao = new ProductDAO();
			
			p.setName(request.getParameter("name"));
			p.setPrice(Double.parseDouble(request.getParameter("price")));
			p.setQuantity(Integer.parseInt(request.getParameter("quantity")));
			p.setTax(Double.parseDouble(request.getParameter("tax")));
			JSONObject json = new JSONObject();
			try {
				json = pdao.createProduct(p);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			response.getWriter().print(json.toString());
        }
	}
	
	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
		if (!Authentication.isAuthenticated(request)) {
            response.setStatus(400);
            response.getWriter().println("{'code': '1000'}");
        }
		else {
			String parameter = request.getParameter("product_id");
			response.setContentType("application/json");
			
			JSONObject res = new JSONObject();
			
			if(parameter == null) {
				try {
					ProductDAO pdao = new ProductDAO();
					response.getWriter().print(pdao.deleteProduct());
					
				}
				catch(SQLException e){
					res.put("code",505);
					res.put("message","internal server error");
				}
				catch(Exception e) {
					res.put("code", 400);
					res.put("meassage", e.getMessage());
					response.getWriter().print(res);
				}
				
			}
			else {
				try {
					int productID = Integer.parseInt(parameter);
					ProductDAO pdao = new ProductDAO();
					response.getWriter().print(pdao.deleteProduct(productID));
					
				}
				catch(Exception e) {
					res.put("code", 400);
					res.put("meassage", e.getMessage());
					response.getWriter().print(res);
				}
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
			int id = Integer.parseInt(request.getParameter("product_id"));
			JSONObject p = new JSONObject(request.getParameter("data"));
			
			try {
				ProductDAO pdao = new ProductDAO();
				if(!p.has("prod_add_qua")) {
					response.getWriter().print(pdao.updateProduct(id, p));
				}
				else {
					response.getWriter().print(pdao.addProductQuantity(id, p.getInt("prod_add_qua")));
				}
			}
			catch(SQLException e){
				res.put("code",505);
				res.put("message",e.getMessage());
				response.getWriter().print(res);
			}
			catch(Exception e) {
				res.put("code", 400);
				res.put("message", e.getMessage());
				response.getWriter().print(res);
			}
		}
	}
}