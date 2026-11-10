package com.ecommerce2.services;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import com.ecommerce2.exceptions.InvalidQuantityException;
import com.ecommerce2.exceptions.ProductNotFoundException;

public class CartService
{
	static Scanner sc=new Scanner(System.in);
	
	//We are Adding Product to Cart
		public static void addProduct(int customerId,String productName,int quantity) throws SQLException, InvalidQuantityException, ProductNotFoundException
		{
			
			
			String query0="select stock_quantity from products where product_name=?";
			
			int availibleStock=0;
			try(Connection con=DBConnection.getConnection();
				PreparedStatement ps=con.prepareStatement(query0))
			{
				ps.setString(1, productName);
				
				
				try(ResultSet rs=ps.executeQuery())
				{
					if(!rs.next())
					{
						 throw new ProductNotFoundException("Product Not Found");
					}
					availibleStock=rs.getInt("stock_quantity");
				}
			}
			if(quantity<=0 )
			{
				throw new InvalidQuantityException("Quantity Must be greater than 0");
			}
			if(quantity>availibleStock)
			{
				throw new InvalidQuantityException("Stock is limited : "+availibleStock);
			}
			
			try(Connection con=DBConnection.getConnection())
			{
				String query1="select customer_id from customers where customer_id=?";
				
				try(PreparedStatement ps1 =con.prepareStatement(query1))
				{
					ps1.setInt(1, customerId);
					try(ResultSet rs1=ps1.executeQuery())
					{
						if(!rs1.next())
						{
							System.out.println("Customer Not Found");
							return;
						}
					}
				}
				String query2="select product_id from products where product_name=?";
				
				int prodId=0;
				try(PreparedStatement ps2=con.prepareStatement(query2))
				{
					ps2.setString(1, productName);
					
					
					try(ResultSet rs2=ps2.executeQuery())
					{
						if(!rs2.next())
						{
							throw new ProductNotFoundException("Product Not Found");
						
						}
						
						prodId=rs2.getInt("product_id");
					}
				}
				//Increasing the product Quantity
				String query3 =
		                "INSERT INTO cart (customer_id, product_id, quantity) " +
		                "VALUES (?, ?, ?) " +
		                "ON DUPLICATE KEY UPDATE quantity = quantity + ?";
				
				try(PreparedStatement ps3=con.prepareStatement(query3))
				{
					
				ps3.setInt(1, customerId);
				ps3.setInt(2, prodId);
				ps3.setInt(3, quantity);
				ps3.setInt(4, quantity);
				
				ps3.executeUpdate();
				
				System.out.println("Product Added Successfully");
				
				}
			}
			
		}
			
		//Displaying Cart item
		public static void viewCart(int customerId) throws SQLException
		{
			String query4 =
	            "SELECT p.product_name, p.price, c.quantity, " +
	            "(p.price * c.quantity) AS subtotal " +
	            "FROM cart c JOIN products p " +
	            "ON c.product_id = p.product_id " +
	            "WHERE c.customer_id = ?";
	        
	        try(Connection con=DBConnection.getConnection();
	        		PreparedStatement ps3=con.prepareStatement(query4))
	        {
	        	ps3.setInt(1, customerId);
	        	
	        	try(ResultSet rs=ps3.executeQuery())
	        	{
	        		BigDecimal total=BigDecimal.ZERO;
	        		boolean found = false;

	        		while(rs.next())
	        		{
	        			found=true;
	        			 System.out.println(
	                             rs.getString("product_name") +
	                             " | Price: " + rs.getBigDecimal("price") +
	                             " | Quantity: " + rs.getInt("quantity") +
	                             " | Subtotal: " + rs.getBigDecimal("subtotal")
	                         );
	        			 total=total.add(rs.getBigDecimal("subtotal"));
	        		}
	        		
	        		 if (!found) {
	                     System.out.println("Cart is empty.");
	                 } else {
	                     System.out.println("Cart total: " + total);
	                 }
	        	}
	        }
		}
		
		//Removing the product from the cart...
		public static void removingProductFromCart(int customerId,String productName) throws SQLException
		{
			String query5="select product_id from products where product_name=?";
			
			try(Connection con=DBConnection.getConnection();
					PreparedStatement ps=con.prepareStatement(query5))
			{
			ps.setString(1, productName);
			
			int id=0;
			try(ResultSet rs=ps.executeQuery())
			{
				if (!rs.next()) {
	                System.out.println("Product Not Found");
	                return;
	            }

	            id = rs.getInt("product_id");
			}
			
			String query6="DELETE FROM cart WHERE customer_id = ? AND product_id = ?";
			
			try(PreparedStatement ps2=con.prepareStatement(query6))
			{
				ps2.setInt(1, customerId);
				ps2.setInt(2, id);
				
				int rows=ps2.executeUpdate();
				
				 if (rows > 0) 
				 {
		            System.out.println("Product removed from cart.");
		         }
				 else
				 {
		            System.out.println("Cart item not found.");
		         }
			 }
			
			}
		}
	}


		
				
			
			


