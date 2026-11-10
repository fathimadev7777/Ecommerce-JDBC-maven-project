package nykaa.ecommerce2;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.sql.Connection;

import org.junit.jupiter.api.Test;

import com.ecommerce2.services.DBConnection;

public class DBConnectionTest 
{
	 @Test
	    void testDatabaseConnection() throws Exception {

	        try (Connection con = DBConnection.getConnection()) {

	            assertNotNull(con,
	                    "Connection should not be null");

	            assertFalse(con.isClosed(),
	                    "Connection should be open");
	        }
	    }

}
