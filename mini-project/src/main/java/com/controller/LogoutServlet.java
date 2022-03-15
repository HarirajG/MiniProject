package com.controller;
import org.json.JSONObject;

import Auth.Authentication;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/logout.do")
public class LogoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        JSONObject resultJSON = new JSONObject();
        int logoutStatus = Authentication.logout(request);
        if (logoutStatus == 1) {
            resultJSON.put("code", 0);
            resultJSON.put("message", "You've been logged out");
            response.sendRedirect("/login.do");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}