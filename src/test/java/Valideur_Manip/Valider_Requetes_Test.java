package Valideur_Manip ; 
import Database_Manip.Database_Control ; 

import java.sql.SQLException;
import java.util.*;

import org.junit.jupiter.api.Test;

import Valideur_Manip.Valideur_Control; 
import request.*; 

class Valider_Requetes_Test {
	
	@Test 
	void test() throws SQLException {
		
		try {
		
		String host="srv-bdens.insa-toulouse.fr";
		int port=3306;
	    String database="projet_gei_024";
	    String username="projet_gei_024";
	    String password="Zai6Xoo7";
	     
	    //connection 
	    Database_Control db =new Database_Control(host, port, database, username, password);
		db.connect();
		//creation table Valideur
		Valideur_Control VAL = new Valideur_Control(db) ;
		//creation d'un benevole 
		Valideur b1 = new Valideur("VAL1","val1",VAL); 
		
		//Creation d'une table de requetes
		RequestManager R = new RequestManager (db); 
    	R.createRequestsTable();
    	//Insertion de 2 requêtes initiales 
    	R.Insertion("Récuperer colis", "Alice");
    	R.Insertion("Faire des courses","Bob"); 
		
    	b1.Valid_Request(1, VAL); //Valider requete 1
    	b1.Refuse_Request(2,"non valide",VAL); //Refuser requete 2
    	
    	R.printAllRequests();
    	
		db.deleteTable("DROP TABLE Valideur");
		db.deleteTable("DROP TABLE Request");
		db.disconnect();
		}
		
		catch(SQLException e) {
			System.out.println(e.getMessage());
		}
				
		

	
	}

}
