package com.ecommerce2.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection
{
	public static Connection getConnection() throws SQLException
	{
	return DriverManager.getConnection("jdbc:mysql://localhost:3306/ecommerce_db", "root", "@Fathima9008");
	
	}
}