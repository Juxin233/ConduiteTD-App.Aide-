package Valideur_Manip ; 
import Database_Manip.Database_Control ; 

import java.sql.SQLException;
import java.util.*;

import org.junit.jupiter.api.Test;

import Valideur_Manip.Valideur_Control; 

class Valideur_Control_Test {
	
	@Test 
	void test() throws SQLException {
		
		try {
		
		String host="srv-bdens.insa-toulouse.fr";
		int port=3306;
	    String database="projet_gei_024";
	    String username="projet_gei_024";
	    String password="Zai6Xoo7";
	    
	  
	    System.out.println("Test Valideur"); 
	    //connection 
	    Database_Control db =new Database_Control(host, port, database, username, password);
		db.connect();
		//creation table Valideur
		Valideur_Control VAL = new Valideur_Control(db) ;
		//creation d'un benevole 
		Valideur b1 = new Valideur("VAL1","val1",VAL); 
		
		 VAL.printVal(b1); 	
		
		db.deleteTable("DROP TABLE Valideur");
		db.disconnect();
		}
		
		catch(SQLException e) {
			System.out.println(e.getMessage());
		}
				
		

	
	}

}
