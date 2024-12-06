package request;
import Database_Manip.*;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;

import org.junit.jupiter.api.Test;

class RequestManagerTest {
	
	@Test
	void test() {
		String host="srv-bdens.insa-toulouse.fr";
		int port=3306;
	    String database="projet_gei_024";
	    String username="projet_gei_024";
	    String password="Zai6Xoo7";
	    try {
		    System.out.println("**Test Request Control**"); 

	    	System.out.println("---------Database connexion trial-------");
	    	Database_Control db =new Database_Control(host, port, database, username, password);
	    	db.connect();   	
	    	System.out.println("---------Database create table trial-------");
	    	RequestManager RM=new RequestManager(db);
	    	System.out.println("---------Request insertions trial-------");
	    	int r1=RM.Insertion("ACHAT", "titi");
	    	int r2=RM.Insertion("ACCOMPAGNEMENT", "toto");
	    	assertFalse(r1==-1);
	    	assertFalse(r2==-1);
	    	System.out.println("---------Database consult all the requests trial-------");
	    	RM.printAllRequests();
	    	System.out.println("---------Database consult single user trial-------");
	    	RM.printRequest(RM.consultRequestById(r1).get());
	    	RM.printRequest(RM.consultRequestById(r2).get());
	    	System.out.println("---------Database update single user trial-------");
	    	RM.updateRequest(r1, 1);
	    	RM.updateRequest(r2, 0);
	    	RM.printRequest(RM.consultRequestById(r1).get());
	    	RM.printRequest(RM.consultRequestById(r2).get());
	    	assertTrue(RM.consultRequestById(r1).get().getEtat().equals(RequestType.VALID));
	    	assertTrue(RM.consultRequestById(r2).get().getEtat().equals(RequestType.INVALID));
	    	System.out.println("---------Database consult all the requests trial-------");
	    	System.out.println("---------Database delete table trial-------");
			RM.deleteRequest(r1);
			RM.deleteRequest(r2);
	    	//db.deleteTable("DROP TABLE Request");
			db.disconnect();
		    System.out.println(""); 

			
	    }catch(SQLException e) {
	    	
	    }
	    
	    
	}

}
