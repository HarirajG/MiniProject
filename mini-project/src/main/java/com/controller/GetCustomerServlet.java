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

import Auth.Authentication;

@WebServlet(urlPatterns = "/getCustomer.do")
public class GetCustomerServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (!Authentication.isAuthenticated(request)) {
            response.setStatus(400);
            response.getWriter().println("{'code': '1000'}");
        }
		else {
			int customerID = (Integer)request.getAttribute("customer_id");
			response.setContentType("application/json");
			CustomerDAO cdao = new CustomerDAO();
			try {
				JSONObject json = cdao.getCustomer(customerID);
				response.getWriter().print(json);
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
