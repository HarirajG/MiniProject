package com.viewContoller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Auth.Authentication;

@WebServlet("/billGeneration.do")
public class BillGenerationServlet extends HttpServlet {	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (!Authentication.isAuthenticated(request)) {
            response.setStatus(400);
            response.getWriter().println("<h1>Kindly log in!!!<h1>");
        }
		else {
		request.getRequestDispatcher("WEB-INF/view/billGeneration.jsp").forward(request, response);
		}
	}
}