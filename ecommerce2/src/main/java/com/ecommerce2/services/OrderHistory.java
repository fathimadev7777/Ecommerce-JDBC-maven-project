package com.ecommerce2.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class OrderHistory 
{
	static  Scanner sc=new Scanner(System.in);
	

	public static void viewOrderHistory(int customerId) throws SQLException
	{
		String sql = "SELECT o.order_id, o.order_date, o.total_amount, " +
	                 "p.product_name, oi.quantity, oi.price " +
	                 "FROM orders o " +
	                 "JOIN order_items oi ON o.order_id = oi.order_id " +
                 "JOIN products p ON oi.product_id = p.product_id " +
	                 "WHERE o.customer_id = ?";

	    try (Connection con = DBConnection.getConnection();
	         PreparedStatement ps = con.prepareStatement(sql)) {

	        ps.setInt(1, customerId);

	        try (ResultSet rs = ps.executeQuery()) {

	            boolean found = false;
	            
	            while (rs.next()) {
	                found = true;

	                System.out.println("Order ID: " +
	                    rs.getInt("order_id"));

	                System.out.println("Date: " +
	                    rs.getTimestamp("order_date"));

	                System.out.println("Total Amount: " +
	                    rs.getBigDecimal("total_amount"));

	                System.out.println("Product: " +
	                    rs.getString("product_name"));

	                System.out.println("Quantity: " +
	                    rs.getInt("quantity"));

	                System.out.println("Price: " +
	                    rs.getBigDecimal("price"));

	                System.out.println("----------------------");
	            }

	            if (!found) {
	                System.out.println("No orders found.");
	            }
	        }
	    }
	}

}
