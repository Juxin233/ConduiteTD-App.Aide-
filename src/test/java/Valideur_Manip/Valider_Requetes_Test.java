package Valideur_Manip ; 
import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;

import org.junit.jupiter.api.Test;

import databaseManip.DatabaseControl;
import request.*;
import valideurManip.Valideur;
import valideurManip.ValideurControl; 

class Valider_Requetes_Test {
	
	@Test 
	void test() throws SQLException {
		
		try {
		
			String host="srv-bdens.insa-toulouse.fr";
			int port=3306;
		    String database="projet_gei_024";
		    String username="projet_gei_024";
		    String password="Zai6Xoo7";
		    System.out.println("**Test Valideur Control Requestes**"); 
	
		    //connection 
		    DatabaseControl db =new DatabaseControl(host, port, database, username, password);
			db.connect();
			//creation table Valideur
			ValideurControl VAL = new ValideurControl(db) ;
			//creation d'un benevole 
			Valideur b1 = new Valideur("VAL1","val1",VAL); 
			Valideur b11 = new Valideur("VAL1","val1",VAL); 
			
			//Creation d'une table de requetes
			RequestManager R = new RequestManager (db); 
	    	//Insertion de 2 requêtes initiales 
	    	int r1=R.Insertion("Récuperer colis", "Alice");
	    	int r2=R.Insertion("Faire des courses","Bob"); 
			assertTrue(R.consultRequestById(r1).get().getUser().equals("Alice"));
			assertTrue(R.consultRequestById(r2).get().getTitre().equals("Faire des courses"));
	    	b1.validRequest(r1, VAL); //Valider requete 1
	    	b1.refuseRequest(r2,"non valide",VAL); //Refuser requete 2
	    	assertTrue(R.consultRequestById(r1).get().getEtat().equals(RequestType.VALID));
			assertTrue(R.consultRequestById(r2).get().getEtat().equals(RequestType.INVALID));
	    	R.printAllRequests();
	    	VAL.deleteValideur(b1.getIdentifiant());
	    	//VAL.deleteValideur(b11.getIdentifiant());
	    	R.deleteRequest(r1);
	    	R.deleteRequest(r2);
			//db.deleteTable("DROP TABLE Valideur");
			//db.deleteTable("DROP TABLE Request");
			db.disconnect();
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
				
		

	
	}

}
