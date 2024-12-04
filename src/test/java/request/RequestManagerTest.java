package request;
import Database_Manip.*;
import User_Manip.User;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import java.util.List;

import org.junit.jupiter.api.Test;

class RequestManagerTest {
	
	@Test
	void test() {
		String host="srv-bdens.insa-toulouse.fr";
		int port=3306;
	    String database="projet_gei_024";
	    String username="projet_gei_024";
	    String password="Zai6Xoo7";
	    List<Request> result = null;
	    try {
		    System.out.println("**Test Request Control**"); 

	    	System.out.println("---------Database connexion trial-------");
	    	Database_Control db =new Database_Control(host, port, database, username, password);
	    	db.connect();
	    	RequestManager RM=new RequestManager(db);
	    	System.out.println("---------Database create table trial-------");
	    	RM.createRequestsTable();
	    	System.out.println("---------Request insertions trial-------");
	    	RM.Insertion("ACHAT", "titi");
	    	RM.Insertion("ACCOMPAGNEMENT", "toto");
	    	System.out.println("---------Database consult all the requests trial-------");
	    	RM.printAllRequests();
	    	System.out.println("---------Database consult single user trial-------");
	    	RM.printRequest(RM.consultRequestById(1).get());
	    	RM.printRequest(RM.consultRequestById(2).get());
	    	System.out.println("---------Database update single user trial-------");
	    	RM.updateRequest(1, 1);
	    	RM.updateRequest(2, 0);
	    	RM.printRequest(RM.consultRequestById(1).get());
	    	RM.printRequest(RM.consultRequestById(2).get());
	    	System.out.println("---------Database consult all the requests trial-------");
	    	System.out.println("---------Database delete table trial-------");
			db.deleteTable("DROP TABLE Request");
			db.disconnect();
		    System.out.println(""); 

			
	    }catch(SQLException e) {
	    	
	    }
	    
	    
	}

}
