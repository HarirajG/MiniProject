package com.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.dao.CustomerDAO;
import com.model.Customer;

import Auth.Authentication;

@WebServlet(urlPatterns = "/customer.do")
public class CustomerServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (!Authentication.isAuthenticated(request)) {
            response.setStatus(400);
            response.getWriter().println("{'code': '1000'}");
        }
		else {
			if(request.getParameter("customer_id") != null) {
				request.setAttribute("customer_id",Integer.parseInt(request.getParameter("customer_id")));
				request.getRequestDispatcher("/getCustomer.do").forward(request, response);
			}
			else {
				request.getRequestDispatcher("/getAllCustomer.do").forward(request, response);
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
			CustomerDAO cdao = new CustomerDAO();
			
			Customer c = new Customer();
			c.setName(request.getParameter("name"));
			c.setMobileNo(Long.parseLong(request.getParameter("mobileNo")));
			c.setStreet(request.getParameter("street"));
			c.setCity(request.getParameter("city"));
			
			JSONObject json = new JSONObject();
			try {
				json = cdao.createCustomer(c);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			response.getWriter().print(json);
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
			CustomerDAO cdao = new CustomerDAO();
			JSONObject res = new JSONObject();
			
			int customerID = Integer.parseInt(request.getParameter("customer_id"));
			JSONObject c = new JSONObject(request.getParameter("data"));
			
			try {
				res = cdao.updateCustomer(customerID,c);
			}
			catch(SQLException e) {
				res.put("code", 505);
				res.put("message", e.getMessage());
			}
			catch(Exception e) {
				res.put("code", 400);
				res.put("meassage", e.getMessage());
			}
			response.getWriter().print(res);
		}
	}
}
