package com.ecommerce2.services;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class OrderService 
{
	static Scanner sc=new Scanner(System.in);
	

	public static void placeOrder(int customerId) throws SQLException 
	{
		String cartSql =
	        "SELECT product_id, quantity FROM cart WHERE customer_id = ?";

	    String priceSql =
	        "SELECT price, stock_quantity FROM products WHERE product_id = ?";

	    String orderSql =
	        "INSERT INTO orders (customer_id, total_amount) VALUES (?, ?)";
	    
	    String itemSql =
	        "INSERT INTO order_items (order_id, product_id, quantity, price) " +
	        "VALUES (?, ?, ?, ?)";

	    String stockSql =
	        "UPDATE products SET stock_quantity = stock_quantity - ? " +
	        "WHERE product_id = ? AND stock_quantity >= ?";

	    String clearCartSql =
	        "DELETE FROM cart WHERE customer_id = ?";

	    try (Connection con = DBConnection.getConnection()) {

	        con.setAutoCommit(false);

	        try {
	            BigDecimal total = BigDecimal.ZERO;

	            java.util.List<Integer> productIds = new java.util.ArrayList<>();
	            java.util.List<Integer> quantities = new java.util.ArrayList<>();
	            java.util.List<BigDecimal> prices = new java.util.ArrayList<>();

	           
	            try (PreparedStatement ps = con.prepareStatement(cartSql)) {
	                ps.setInt(1, customerId);

	                try (ResultSet rs = ps.executeQuery()) {
	                    while (rs.next()) {
	                        int productId = rs.getInt("product_id");
	                        int quantity = rs.getInt("quantity");

	                        try (PreparedStatement p = con.prepareStatement(priceSql)) {
	                            p.setInt(1, productId);

	                            try (ResultSet r = p.executeQuery()) {
	                                if (!r.next() ||
	                                    r.getInt("stock_quantity") < quantity) {
	                                    throw new SQLException("Insufficient stock.");
	                                }

	                                BigDecimal price = r.getBigDecimal("price");

	                                productIds.add(productId);
	                                quantities.add(quantity);
	                                prices.add(price);

	                                total = total.add(
	                                    price.multiply(BigDecimal.valueOf(quantity))
	                                );
	                            }
	                        }
	                    }
	                }
	            }

	            if (productIds.isEmpty()) {
	                throw new SQLException("Cart is empty.");
	            }

	            //  Inserting order
	            int orderId;

	            try (PreparedStatement ps = con.prepareStatement(
	                    orderSql, Statement.RETURN_GENERATED_KEYS)) {

	                ps.setInt(1, customerId);
	                ps.setBigDecimal(2, total);
	                ps.executeUpdate();

	                try (ResultSet rs = ps.getGeneratedKeys()) {
	                    rs.next();
	                    orderId = rs.getInt(1);
	                }
	            }

	           
	            try (PreparedStatement stock = con.prepareStatement(stockSql);
	                 PreparedStatement item = con.prepareStatement(itemSql)) {

	                for (int i = 0; i < productIds.size(); i++) {
	                    int productId = productIds.get(i);
	                    int quantity = quantities.get(i);

	                    stock.setInt(1, quantity);
	                    stock.setInt(2, productId);
	                    stock.setInt(3, quantity);

	                    if (stock.executeUpdate() != 1) {
	                        throw new SQLException("Stock update failed.");
	                    }

	                    item.setInt(1, orderId);
	                   item.setInt(2, productId);
	                    item.setInt(3, quantity);
	                    item.setBigDecimal(4, prices.get(i));
	                    item.addBatch();
	                }

	                item.executeBatch();
            }

	            // Clearing cart
	            try (PreparedStatement ps = con.prepareStatement(clearCartSql)) {
	                ps.setInt(1, customerId);
	                ps.executeUpdate();
	            }

	            
	            con.commit();

	            System.out.println("Order placed successfully!");
	            System.out.println("Order ID: " + orderId);
	            System.out.println("Total amount: " + total);

	        } catch (SQLException | RuntimeException e) {
	            con.rollback();
	            System.out.println("Order failed: " + e.getMessage());
	            
	        }
	    }
	}

}
