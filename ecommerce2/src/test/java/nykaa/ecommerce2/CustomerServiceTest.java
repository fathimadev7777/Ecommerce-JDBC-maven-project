package nykaa.ecommerce2;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import org.junit.jupiter.api.Test;
import com.ecommerce2.services.CustomerService;

public class CustomerServiceTest 
{
	@Test
	void addNewCustomer()
	{
		assertDoesNotThrow(()->CustomerService.addNewCustomer("Kalyani", "kalyani@5566"));
	}
	
	@Test
	void emptyCustomerNameTest()
	{
		assertDoesNotThrow(()->CustomerService.addNewCustomer("", "Navya@1122"));
		
	}
	
	@Test
	void emptyCustomerEmailTest()
	{
		assertDoesNotThrow(()->CustomerService.addNewCustomer("Prathan", ""));
	}

}
