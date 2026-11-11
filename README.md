E-Commerce Application | Java + JDBC + MySQL

Over past few days i am revising core java concepts and,this is my console-based E-Commerce Application developed using Java, JDBC, Maven, and MySQL.
The application allows customers to register, view available products, add products to their shopping cart, remove products, place orders, and view their order history.
The main objective of this project is to understand how Java applications interact with relational databases and how real-world e-commerce operations can be implemented using JDBC.

Technologies Used
Java – Application development
JDBC – Database connectivity
MySQL – Data storage and management
Maven – Project and dependency management
JUnit 5 – Unit testing

Features
Customer Registration: Register new customers with their name and email.
View Products: Display available products from the database.
Add to Cart: Add products to the shopping cart with the required quantity.
View Cart: Display products added to the cart.
Remove from Cart: Remove products from the shopping cart.
Place Order: Place orders using the products in the shopping cart.
Order History: View previous orders for a customer.

Exception Handling: Handle invalid quantities and products that are not available.

Database Tables

The application uses MySQL with the following tables:
customers
products
cart
orders
order_items 

From this Project I have revised my concepts :
1. Java and JDBC database connectivity.
2. Writing SQL queries using PreparedStatement.
3. Performing database operations using JDBC.
4. Implementing business logic using service classes.
5. Handling custom exceptions.
6. Managing dependencies using Maven.
7. Writing unit tests using JUnit 5.

 Future Enhancements
1. Integrate Log4j2 for application logging.
2. Implement connection pooling.
3. Develop REST APIs using Spring Boot.
4. Add customer authentication.
