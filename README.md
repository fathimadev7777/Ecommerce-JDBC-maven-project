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

customers | CREATE TABLE `customers` (
  `customer_id` int NOT NULL AUTO_INCREMENT,
  `customer_name` varchar(100) NOT NULL,
  `email` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`customer_id`),
  UNIQUE KEY `email` (`email`));

  products | CREATE TABLE `products` (
  `product_id` int NOT NULL AUTO_INCREMENT,
  `product_name` varchar(100) NOT NULL,
  `price` decimal(10,2) NOT NULL,
  `stock_quantity` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`product_id`),
  UNIQUE KEY `product_name` (`product_name`));

   cart  | CREATE TABLE `cart` (
  `customer_id` int NOT NULL,
  `product_id` int NOT NULL,
  `quantity` int NOT NULL,
  PRIMARY KEY (`customer_id`,`product_id`),
  KEY `product_id` (`product_id`),
  CONSTRAINT `cart_ibfk_1` FOREIGN KEY (`customer_id`) REFERENCES `customers` (`customer_id`),
  CONSTRAINT `cart_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`));

   order_items | CREATE TABLE `order_items` (
  `order_item_id` int NOT NULL AUTO_INCREMENT,
  `order_id` int NOT NULL,
  `product_id` int NOT NULL,
  `quantity` int NOT NULL,
  `price` decimal(10,2) NOT NULL,
  PRIMARY KEY (`order_item_id`),
  KEY `order_id` (`order_id`),
  KEY `product_id` (`product_id`),
  CONSTRAINT `order_items_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `orders` (`order_id`),
  CONSTRAINT `order_items_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`));

   orders | CREATE TABLE `orders` (
  `order_id` int NOT NULL AUTO_INCREMENT,
  `customer_id` int NOT NULL,
  `order_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `total_amount` decimal(10,2) NOT NULL,
  PRIMARY KEY (`order_id`),
  KEY `customer_id` (`customer_id`),
  CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`customer_id`) REFERENCES `customers` (`customer_id`));

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
