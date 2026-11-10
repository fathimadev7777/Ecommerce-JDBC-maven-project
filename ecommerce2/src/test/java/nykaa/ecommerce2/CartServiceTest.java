package nykaa.ecommerce2;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.sql.SQLException;

import org.junit.jupiter.api.Test;

import com.ecommerce2.exceptions.InvalidQuantityException;
import com.ecommerce2.exceptions.ProductNotFoundException;
import com.ecommerce2.services.CartService;

public class CartServiceTest 
{
	@Test
	void productNotFoundTest()
	{
		assertThrows(ProductNotFoundException.class, ()->CartService.addProduct(90, "iphone76", 1));
	}
	
	@Test
	 void addProductTest() throws Exception 
	{
     assertDoesNotThrow(
        () -> CartService.addProduct(1, "speaker", 1)
    		 );
	  }
	 
	@Test
	void invalidQuantityExceptionTest()
	{
		assertThrows(InvalidQuantityException.class, ()->CartService.addProduct(1, "speaker", 0));
	}
	
	@Test
	void invalidQuantityExceptionNegetiveTest()
	{
		assertThrows(InvalidQuantityException.class, ()->CartService.addProduct(1, "speaker", -2));
	}
	

	@Test
	void insufficientStockTest() {
	    assertThrows(
	        InvalidQuantityException.class,
	        () -> CartService.addProduct(1, "speaker", 758758)
	    );
	}

	@Test
	void customerNotFoundTest() {
	    assertDoesNotThrow(
	        () -> CartService.addProduct(99999, "speaker", 1)
	    );
	}
	

	@Test
	void addSameProductAgainTest() {
	    assertDoesNotThrow(
	        () -> CartService.addProduct(1, "speaker", 1)
	    );
	
	    assertDoesNotThrow(
	        () -> CartService.addProduct(1, "speaker", 1)
	    );
	}

	

	@Test
	void productAddedSucessfully()
	{
		assertDoesNotThrow(()->CartService.addProduct(1, "Speaker", 2));
	}


	@Test
	void viewCartWithProductsTest() throws Exception {
	    CartService.addProduct(1, "speaker", 1);
	
	    assertDoesNotThrow(
	        () -> CartService.viewCart(1)
	    );
	}
	

	@Test
	void viewEmptyCartTest() throws Exception {
	    assertDoesNotThrow(
	        () -> CartService.viewCart(7647)
	    );
	}

	@Test
	void removingProductTest() throws SQLException, InvalidQuantityException, ProductNotFoundException
	{
		CartService.addProduct(1, "Speaker", 1);
		
		assertDoesNotThrow(()->CartService.removingProductFromCart(1, "Speaker"));
	}

	@Test
	void removingProductNotFoundTest()
	{
		assertDoesNotThrow(()->CartService.removingProductFromCart(1, "iphone99"));
	}
	
	@Test
	void cartItemNotFoundTest() throws Exception {
	    CartService.removingProductFromCart(1, "speaker");

	    assertDoesNotThrow(
	        () -> CartService.removingProductFromCart(1, "speaker")
	    );
	}

}
