package com.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
	private static Connection con;
	private Database() {
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} 
		catch(ClassNotFoundException e) {
			System.out.println("Class is not found!!!"+"\n"+e.getMessage());
		}
		
		String url ="jdbc:mysql://localhost:3306/mini_project_final";
		String name = "root";
		String pswd = "harirajg";
		
	
		try {
			con = DriverManager.getConnection(url,name,pswd);
		} 
		catch (SQLException e) {
			System.out.println("Failure in Connection!!! "+"\n"+e.getMessage());
		}
	}
	
	public static Connection getConnectionDB() {
		if(con == null) {
			new Database();
		}
		return con;
	}
}
