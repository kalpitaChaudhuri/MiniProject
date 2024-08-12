package com.miniproj;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/validate")
public class Validate extends HttpServlet {
@Override
protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	resp.setContentType("text/html");
	PrintWriter out=resp.getWriter();
	
	String email=req.getParameter("email");
	String password=req.getParameter("password");
	
	try {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/userdb", "root", "mysql");
		PreparedStatement ps=connection.prepareStatement("select * from user where email=? and password=?");
		ps.setString(1, email);
		ps.setString(2, password);

		ResultSet set=ps.executeQuery();
		if(set.next()) {
			req.getRequestDispatcher("HomePage.html").forward(req, resp);
		}else {
			out.println("<h1>Invalid User</h1>");
			req.getRequestDispatcher("LoginForm.html").include(req, resp);
		}
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
}
