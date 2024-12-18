package Valideur_Manip ; 
import java.sql.SQLException;

import org.junit.jupiter.api.Test;

import databaseManip.DatabaseControl;
import valideurManip.Valideur;
import valideurManip.ValideurControl;

class Valideur_Control_Test {
	
	@Test 
	void test() throws SQLException {
		
		try {
		
		String host="srv-bdens.insa-toulouse.fr";
		int port=3306;
	    String database="projet_gei_024";
	    String username="projet_gei_024";
	    String password="Zai6Xoo7";
	    
	    System.out.println("**Test Valideur Control**"); 
	    //connection 
	    DatabaseControl db =new DatabaseControl(host, port, database, username, password);
		db.connect();
		//creation table Valideur
		ValideurControl VAL = new ValideurControl(db) ;
		//creation d'un benevole 
		Valideur b1 = new Valideur("VAL1","val1",VAL); 
		
		VAL.printVal(b1); 	
		VAL.deleteValideur(b1.getIdentifiant());
		//db.deleteTable("DROP TABLE Valideur");
		db.disconnect();
	    System.out.println(""); 
		}
		
		catch(SQLException e) {
			System.out.println(e.getMessage());
		}
				
		

	
	}

}
