package nykaa.ecommerce2;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.Test;
import nykaa.ecommerce2.EcommerceApplication;

public class EcommerceApplicationTest 
{
	 @Test
	    void testInvalidChoiceAndExit() throws Exception {

	        // Provide input: invalid choice, then exit
	        String input = "99\n8\n";

	        System.setIn(
	            new ByteArrayInputStream(input.getBytes())
	        );

	        ByteArrayOutputStream output =
	            new ByteArrayOutputStream();
	        
	        PrintStream originalOut = System.out;
	        
	        try {
	            System.setOut(new PrintStream(output));

	            EcommerceApplication.main(new String[]{});

	        } finally {
	            System.setOut(originalOut);
	        }

	        String result = output.toString();

	        assertTrue(result.contains("Invalid choice."));
	        assertTrue(result.contains("Application closed."));
	    }
	 
	 @Test
	 void testViewProductsAndExit() throws Exception {

	     String input = "2\n8\n";

	     System.setIn(
	         new ByteArrayInputStream(input.getBytes())
	     );

	     ByteArrayOutputStream output =
	         new ByteArrayOutputStream();

	     PrintStream originalOut = System.out;

	     try {
	         System.setOut(new PrintStream(output));

	         EcommerceApplication.main(new String[]{});

	     } finally {
	         System.setOut(originalOut);
	     }

	     String result = output.toString();

	     assertTrue(result.contains("E-COMMERCE SYSTEM"));
	     assertTrue(result.contains("Application closed."));
	 }
	
	 @Test
	 void testAddProductToCartAndExit() throws Exception {

	     // Menu choice 3, customer ID 1,
	     // product name Laptop, quantity 2, then exit
	     String input = "3\n1\nLaptop\n2\n8\n";

	     System.setIn(
	         new ByteArrayInputStream(input.getBytes())
	     );

	     ByteArrayOutputStream output =
	         new ByteArrayOutputStream();

	     PrintStream originalOut = System.out;

	     try {
	         System.setOut(new PrintStream(output));

	         EcommerceApplication.main(new String[]{});

	     } finally {
	         System.setOut(originalOut);
	     }

	     String result = output.toString();

	     assertTrue(result.contains("Enter Customer Id"));
	     assertTrue(result.contains("Enter Product Name"));
	     assertTrue(result.contains("Enter Quantity"));
	     assertTrue(result.contains("Application closed."));
	 }
	 

@Test
void testAddNonExistingProductToCart() throws Exception {

    String input = "3\n1\nUnknownProductXYZ\n2\n8\n";

    System.setIn(new ByteArrayInputStream(input.getBytes()));

    ByteArrayOutputStream output = new ByteArrayOutputStream();
    PrintStream originalOut = System.out;

    try {
        System.setOut(new PrintStream(output));

        EcommerceApplication.main(new String[]{});

    } finally {
        System.setOut(originalOut);
    }

    String result = output.toString();

    assertTrue(result.contains("Enter Product Name"));
    assertTrue(result.contains("Application closed."));
}

	 

	@Test
	void testViewCartAndExit() throws Exception {
	
	    // 4 = View cart, 1 = Customer ID, 8 = Exit
	    String input = "4\n1\n8\n";
	
	    System.setIn(
	        new ByteArrayInputStream(input.getBytes())
	    );
	
	    ByteArrayOutputStream output =
	        new ByteArrayOutputStream();
	
	    PrintStream originalOut = System.out;
	
	    try {
	        System.setOut(new PrintStream(output));
	
	        EcommerceApplication.main(new String[]{});
	
	    } finally {
	        System.setOut(originalOut);
	    }
	
	    String result = output.toString();
	
	    assertTrue(result.contains("Enter customer ID"));
	    assertTrue(result.contains("Application closed."));
	}
	

	@Test
	void testRemoveProductFromCartAndExit() throws Exception {
	
	    // 5 = Remove product, 1 = Customer ID,
	    // Laptop = Product name, 8 = Exit
	    String input = "5\n1\nLaptop\n8\n";
	
	    System.setIn(
	        new ByteArrayInputStream(input.getBytes())
	    );
	
	    ByteArrayOutputStream output =
	        new ByteArrayOutputStream();
	
	    PrintStream originalOut = System.out;
	
	    try {
	        System.setOut(new PrintStream(output));
	
	        EcommerceApplication.main(new String[]{});
	
	    } finally {
	        System.setOut(originalOut);
	    }
	
	    String result = output.toString();
	
	    assertTrue(result.contains("Enter Customer Id"));
	    assertTrue(result.contains("Enter Product Name"));
	    assertTrue(result.contains("Application closed."));
	}
	

	@Test
	void testPlaceOrderAndExit() throws Exception {
	
	    String input = "6\n1\n8\n";
	
	    System.setIn(new ByteArrayInputStream(input.getBytes()));
	
	    ByteArrayOutputStream output = new ByteArrayOutputStream();
	    PrintStream originalOut = System.out;
	
	    try {
	        System.setOut(new PrintStream(output));
	
	        EcommerceApplication.main(new String[]{});
	
	    } finally {
	        System.setOut(originalOut);
	    }
	
	    String result = output.toString();
	
	    assertTrue(result.contains("Enter customer ID"));
	    assertTrue(result.contains("Application closed."));
	}
	

	@Test
	void testViewOrderHistoryAndExit() throws Exception {
	
	    String input = "7\n1\n8\n";
	
	    System.setIn(new ByteArrayInputStream(input.getBytes()));
	
	    ByteArrayOutputStream output = new ByteArrayOutputStream();
	    PrintStream originalOut = System.out;
	
	    try {
	        System.setOut(new PrintStream(output));
	
	        EcommerceApplication.main(new String[]{});
	
	    } finally {
	        System.setOut(originalOut);
	    }
	
	    String result = output.toString();
	
	    assertTrue(result.contains("Enter customer ID"));
	    assertTrue(result.contains("Application closed."));
	}


@Test
void testRegisterCustomerAndExit() throws Exception {

    String input = "1\nTestCustomer\n testcustomer123@example.com \n8\n";

    System.setIn(new ByteArrayInputStream(input.getBytes()));

    ByteArrayOutputStream output = new ByteArrayOutputStream();
    PrintStream originalOut = System.out;

    try {
        System.setOut(new PrintStream(output));

        EcommerceApplication.main(new String[]{});

    } finally {
        System.setOut(originalOut);
    }

    String result = output.toString();

    assertTrue(result.contains("Enter Customer Name"));
    assertTrue(result.contains("Enter Customer Email"));
    assertTrue(result.contains("Application closed."));
}



}
