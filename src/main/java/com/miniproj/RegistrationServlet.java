package com.miniproj;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/register")
public class RegistrationServlet extends HttpServlet {
@Override
protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	resp.setContentType("text/html");
	PrintWriter out=resp.getWriter();
	
	String name=req.getParameter("name");
	String email=req.getParameter("email");
	String password=req.getParameter("password");
	int age=Integer.parseInt(req.getParameter("age"));
	String location=req.getParameter("location");
	long mobile=Long.parseLong(req.getParameter("mobile"));
	
	try {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/userdb", "root", "mysql");
		PreparedStatement ps=connection.prepareStatement("Insert into user values (?,?,?,?,?,?)");
		ps.setString(1, name);
		ps.setString(2, email);
		ps.setString(3, password);
		ps.setInt(4, age);
		ps.setString(5, location);
		ps.setLong(6, mobile);
		int i=ps.executeUpdate();
		if(i>0) {
			out.println("<h3>Registration Success</h3>");
			RequestDispatcher dispatcher=req.getRequestDispatcher("LoginForm.html");
			dispatcher.forward(req, resp);
		}
	} catch (ClassNotFoundException | SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
}
