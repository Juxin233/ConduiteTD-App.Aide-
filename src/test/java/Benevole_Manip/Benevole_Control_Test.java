package Benevole_Manip ; 

import Database_Manip.Database_Control ; 

import java.sql.SQLException;
import java.util.*;

import org.junit.jupiter.api.Test;

import Benevole_Manip.Benevole_Control; 

class Benevole_Control_Test {
	
	@Test 
	void test() throws SQLException {
		
		try {
		
		String host="srv-bdens.insa-toulouse.fr";
		int port=3306;
	    String database="projet_gei_024";
	    String username="projet_gei_024";
	    String password="Zai6Xoo7";
	    
	  
	    System.out.println("Test Benevole"); 
	    //connection 
	    Database_Control db =new Database_Control(host, port, database, username, password);
		db.connect();
		db.deleteTable("DROP TABLE Benevole");
		//creation table benevole
		Benevole_Control BEN = new Benevole_Control(db) ;
		//creation d'un benevole 
		Benevole b1 = new Benevole("Benevole1","ben1",20,BEN); 
		
		 BEN.printBen(b1); 	
		
		db.deleteTable("DROP TABLE Benevole");
		db.disconnect();
		}
		
		catch(SQLException e) {
			System.out.println(e.getMessage());
		}
				
		

	
	}

}
