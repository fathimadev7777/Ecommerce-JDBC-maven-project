package nykaa.ecommerce2;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;

import com.ecommerce2.services.ProductServices;

public class ProductServicesTest 
{
	@Test
	void productServiceTest()
	{
		assertDoesNotThrow(()->ProductServices.viewProducts());
	}

}