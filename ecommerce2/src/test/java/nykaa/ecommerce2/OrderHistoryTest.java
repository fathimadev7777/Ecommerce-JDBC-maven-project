package nykaa.ecommerce2;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.Test;

import com.ecommerce2.services.OrderHistory;

public class OrderHistoryTest 
{
	 @Test
	    void testViewOrderHistorySuccess() throws Exception {

	        ByteArrayOutputStream output = new ByteArrayOutputStream();
	        PrintStream original = System.out;

	        try {
	            System.setOut(new PrintStream(output));

	            OrderHistory.viewOrderHistory(1);

	            String result = output.toString();

	            assertTrue(result.contains("Order ID:"));
	            assertTrue(result.contains("Product:"));

	        } finally {
	            System.setOut(original);
	        }
	 }

	    @Test
	    void testViewOrderHistoryWithNoOrders() throws Exception {

	        ByteArrayOutputStream output = new ByteArrayOutputStream();
	        PrintStream original = System.out;

	        try {
	            System.setOut(new PrintStream(output));

	            OrderHistory.viewOrderHistory(875876);

	            assertTrue(output.toString().contains("No orders found."));

	        } finally {
	            System.setOut(original);
	        }
	    }

}
