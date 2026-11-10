package nykaa.ecommerce2;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

import com.ecommerce2.services.DBConnection;
import com.ecommerce2.services.OrderService;

public class OrderServiceTest 
{
	 private int customerId;
	    private int productId;

	    @BeforeEach
	    void setUp() throws Exception {

	        String unique = UUID.randomUUID().toString();

	        try (Connection con = DBConnection.getConnection()) {

	            // Create test customer
	            String customerSql =
	                "INSERT INTO customers (customer_name, email) VALUES (?, ?)";

	            try (PreparedStatement ps =
	                     con.prepareStatement(
	                         customerSql, Statement.RETURN_GENERATED_KEYS)) {

	                ps.setString(1, "Test Customer");
	                ps.setString(2, unique + "@test.com");
	                ps.executeUpdate();

	                try (ResultSet rs = ps.getGeneratedKeys()) {
	                    assertTrue(rs.next());
	                    customerId = rs.getInt(1);
	                }
	            }

	            // Create test product
	            String productSql =
	                "INSERT INTO products " +
	                "(product_name, price, stock_quantity) VALUES (?, ?, ?)";

	            try (PreparedStatement ps =
	                     con.prepareStatement(
	                         productSql, Statement.RETURN_GENERATED_KEYS)) {

	                ps.setString(1, "Test Product " + unique);
	                ps.setBigDecimal(2, new BigDecimal("10.50"));
	                ps.setInt(3, 5);
	                ps.executeUpdate();

	                try (ResultSet rs = ps.getGeneratedKeys()) {
	                    assertTrue(rs.next());
	                    productId = rs.getInt(1);
	                }
	            }
	        }
	    }

	    @AfterEach
	    void tearDown() throws Exception {

	        try (Connection con = DBConnection.getConnection()) {

	            // Delete order items first because they reference orders
	            String deleteItems =
	                "DELETE FROM order_items WHERE order_id IN " +
	                "(SELECT order_id FROM orders WHERE customer_id = ?)";

	            try (PreparedStatement ps = con.prepareStatement(deleteItems)) {
	                ps.setInt(1, customerId);
	                ps.executeUpdate();
	            }

	            // Delete orders
	            try (PreparedStatement ps = con.prepareStatement(
	                    "DELETE FROM orders WHERE customer_id = ?")) {
	                ps.setInt(1, customerId);
	                ps.executeUpdate();
	            }

	            // Delete cart entries
	            try (PreparedStatement ps = con.prepareStatement(
	                    "DELETE FROM cart WHERE customer_id = ?")) {
	                ps.setInt(1, customerId);
	                ps.executeUpdate();
	            }

	            // Delete test product
	            try (PreparedStatement ps = con.prepareStatement(
	                    "DELETE FROM products WHERE product_id = ?")) {
	                ps.setInt(1, productId);
	                ps.executeUpdate();
	            }

	            // Delete test customer
	            try (PreparedStatement ps = con.prepareStatement(
	                    "DELETE FROM customers WHERE customer_id = ?")) {
	                ps.setInt(1, customerId);
	                ps.executeUpdate();
	            }
	        }
	    }

	    @Test
	    void testPlaceOrderSuccess() throws Exception {

	        // Add two units of the test product to the cart
	        addToCart(2);

	        OrderService.placeOrder(customerId);

	        // Verify order total: 10.50 * 2 = 21.00
	        assertEquals(
	            new BigDecimal("21.00"),
	            getOrderTotal()
	        );

	        // Verify stock: 5 - 2 = 3
	        assertEquals(3, getStockQuantity());

	        // Verify cart is empty
	        assertEquals(0, getCartCount());

	        // Verify one order item was created
	        assertEquals(1, getOrderItemCount());
	    }

	    @Test
	    void testPlaceOrderWithEmptyCart() throws Exception {

	        // Do not add any cart items

	        OrderService.placeOrder(customerId);

	        // No order should be created
	        assertEquals(0, getOrderCount());

	        // Product stock should remain unchanged
	        assertEquals(5, getStockQuantity());
	    }

	    @Test
	    void testPlaceOrderWithInsufficientStock() throws Exception {

	        // Stock is 5, but request 6 units
	        addToCart(6);

	        OrderService.placeOrder(customerId);

	        // Order should not be created
	        assertEquals(0, getOrderCount());

	        // Stock should remain unchanged
	        assertEquals(5, getStockQuantity());
	        
	        // Cart should remain unchanged after rollback
	        assertEquals(1, getCartCount());
	    }

	    private void addToCart(int quantity) throws Exception {

	        try (Connection con = DBConnection.getConnection();
	             PreparedStatement ps = con.prepareStatement(
	                 "INSERT INTO cart (customer_id, product_id, quantity) " +
	                 "VALUES (?, ?, ?)")) {

	            ps.setInt(1, customerId);
	            ps.setInt(2, productId);
	            ps.setInt(3, quantity);
	            ps.executeUpdate();
	        }
	    }

	    private int getOrderCount() throws Exception {

	        return getIntValue(
	            "SELECT COUNT(*) FROM orders WHERE customer_id = ?",
	            customerId
	        );
	    }

	    private int getOrderItemCount() throws Exception {

	        String sql =
	            "SELECT COUNT(*) FROM order_items oi " +
	            "JOIN orders o ON oi.order_id = o.order_id " +
	            "WHERE o.customer_id = ?";

	        return getIntValue(sql, customerId);
	    }

	    private int getCartCount() throws Exception {

	        return getIntValue(
	            "SELECT COUNT(*) FROM cart WHERE customer_id = ?",
	            customerId
	        );
	    }

	    private int getStockQuantity() throws Exception {

	        try (Connection con = DBConnection.getConnection();
	             PreparedStatement ps = con.prepareStatement(
	                 "SELECT stock_quantity FROM products WHERE product_id = ?")) {

	            ps.setInt(1, productId);

	            try (ResultSet rs = ps.executeQuery()) {
	                assertTrue(rs.next());
	                return rs.getInt(1);
	            }
	        }
	    }

	    private BigDecimal getOrderTotal() throws Exception {

	        try (Connection con = DBConnection.getConnection();
	             PreparedStatement ps = con.prepareStatement(
	                 "SELECT total_amount FROM orders WHERE customer_id = ?")) {

	            ps.setInt(1, customerId);

	            try (ResultSet rs = ps.executeQuery()) {
	                assertTrue(rs.next());
	                BigDecimal total = rs.getBigDecimal(1);
	                assertFalse(rs.next(), "Expected exactly one order");
	                return total;
	            }
	        }
	    }

	    private int getIntValue(String sql, int id) throws Exception {

	        try (Connection con = DBConnection.getConnection();
	             PreparedStatement ps = con.prepareStatement(sql)) {

	            ps.setInt(1, id);

	            try (ResultSet rs = ps.executeQuery()) {
	                assertTrue(rs.next());
	                return rs.getInt(1);
	            }
	        }
	    }
}
