package com.ecommerce2.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductServices
{
	
	//Displaying all the products
		public static void  viewProducts() throws SQLException
		{
			String query="select * from products";
			
			try(Connection con=DBConnection.getConnection();
				PreparedStatement ps=con.prepareStatement(query);
					ResultSet rs=ps.executeQuery())
		{
				while(rs.next())
				{
					 System.out.println(rs.getInt("product_id") + "  " +
			                    rs.getString("product_name") + "  " +
			                    rs.getBigDecimal("price") + "  " +
			                    rs.getInt("stock_quantity"));
				}
			}
		}

}



