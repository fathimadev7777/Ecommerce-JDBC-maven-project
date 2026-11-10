package nykaa.ecommerce2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import com.ecommerce2.exceptions.InvalidQuantityException;
import com.ecommerce2.exceptions.ProductNotFoundException;
import com.ecommerce2.services.CartService;
import com.ecommerce2.services.CustomerService;
import com.ecommerce2.services.DBConnection;
import com.ecommerce2.services.OrderHistory;
import com.ecommerce2.services.OrderService;
import com.ecommerce2.services.ProductServices;

public class EcommerceApplication 
{
	DBConnection dbConnection=new DBConnection();
	ProductServices productServices=new ProductServices();
	OrderService orderService=new OrderService();
	OrderHistory orderHistory=new OrderHistory();
	CartService cartService=new CartService();
	CustomerService customerService=new CustomerService();
	
	static Scanner sc;
	
	public static void main(String[] args) throws SQLException 
	{
		sc=new Scanner(System.in);

		while (true)
		{
            System.out.println("\n===== E-COMMERCE SYSTEM =====");
            System.out.println("1.Register New Customer");
            System.out.println("2. View products");
            System.out.println("3. Add product to cart");
            System.out.println("4. View cart");
            System.out.println("5. Remove product from cart");
            System.out.println("6. Place order");
            System.out.println("7. View order history");
            System.out.println("8. Exit");
            System.out.print("Enter choice: ");

            int choice = sc.nextInt();
            
            try {
            switch(choice)
            {
            case 1:
            	sc.nextLine();
            	
            	System.out.println("Enter Customer Name ");
        		String cusName=sc.nextLine().trim();
       		
        		System.out.println("Enter Customer Email");
        		String email=sc.nextLine().trim();
        		
            	CustomerService.addNewCustomer( cusName,email);
            	break;
            case 2:
            	ProductServices.viewProducts();
            	break;
            case 3:
            	System.out.println("Enter Customer Id");
    			int customerId=sc.nextInt();
    			
    			sc.nextLine();
    			
    			System.out.println("Enter Product Name");
    			String productName=sc.nextLine();
    			
    			System.out.println("Enter Quantity");
    			int quantity=sc.nextInt();
            	try {
					CartService.addProduct(customerId,productName,quantity);
				}
            	catch (InvalidQuantityException e) 
            		{
					System.out.println(e.getMessage());
				} 
            	catch (ProductNotFoundException r)
            		{
					System.out.println(r.getMessage());
				}
            	break;
            case 4:
            	System.out.print("Enter customer ID: ");
    	        int cusId = sc.nextInt();
    	        
            	CartService.viewCart(cusId);
            	break;
            case 5:
            	System.out.println("Enter Customer Id");
    			int custmrId=sc.nextInt();
    		
    			sc.nextLine();
    			
    			System.out.println("Enter Product Name");
    			String productNameIs=sc.nextLine();
    			
            	CartService.removingProductFromCart(custmrId,productNameIs);
            	break;
            case 6:
            	 System.out.print("Enter customer ID: ");
         	    int custmrIdIs = sc.nextInt();
         	    
            	OrderService.placeOrder(custmrIdIs);
            	break;
            case 7:
            	System.out.print("Enter customer ID: ");
        	    int customerIdIs = sc.nextInt();
            	OrderHistory.viewOrderHistory(customerIdIs);
            	break;
            case 8:
            	  System.out.println("Application closed.");
                  sc.close();
                  return;
             default:
                  System.out.println("Invalid choice.");
            }
        }
            catch (SQLException e)
            {
            System.out.println("Database error: " + e.getMessage());
            }
		}
	}
}
          
           
	
		
		



