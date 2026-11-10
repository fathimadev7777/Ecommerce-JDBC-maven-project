package com.ecommerce2.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class CustomerService 
{
	static Scanner sc=new Scanner(System.in);
	
	//registering the new customer
	public static void addNewCustomer(String cusName,String email) throws SQLException
	{
		if (cusName.isEmpty()) {
            System.out.println("Customer name cannot be empty.");
            return;
       }
		
		String query="insert into customers (customer_name, email) values (?,?)";
		
		try(Connection con=DBConnection.getConnection();
				PreparedStatement ps=con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS))
		{
			ps.setString(1, cusName);
			
			if(email.isEmpty())
			{
				ps.setNull(2, java.sql.Types.VARCHAR);
			}
			else
			{
				ps.setString(2, email);
			}
		
			int rows=ps.executeUpdate();
			
			if(rows>0)
			{
				try(ResultSet rs=ps.getGeneratedKeys())
				{
					if(rs.next())
					{
						int customerId=rs.getInt(1);
						
						System.out.println(
	                            "Customer registered successfully!");
	                            
	                    System.out.println("Customer Id : "+customerId);
	                    
	                    System.out.println("Please use this ID while Shopping");
					}
				}
			}
		}
	}

}
