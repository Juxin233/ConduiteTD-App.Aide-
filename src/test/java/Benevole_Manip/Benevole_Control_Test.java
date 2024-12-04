package Benevole_Manip ; 

import Database_Manip.Database_Control ;
import User_Manip.User;
import User_Manip.User_Control;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.SQLException;
import java.util.*;

import org.junit.jupiter.api.Test;

import Benevole_Manip.Benevole_Control; 

class Benevole_Control_Test {
	
	@Test 
	void test() throws SQLException {
		
		String host="srv-bdens.insa-toulouse.fr";
		int port=3306;
	    String database="projet_gei_024";
	    String username="projet_gei_024";
	    String password="Zai6Xoo7";
	    List<Benevole> result=null;
		try {
		    System.out.println("**Test Benevole Control Creation and Insertion**"); 
			System.out.println("---------Database connexion trial-------");
			Database_Control db =new Database_Control(host, port, database, username, password);
			db.connect();
			db.deleteTable("DROP TABLE Benevole");
			System.out.println("---------Database create table trial-------");
			Benevole_Control BC= new Benevole_Control(db);
			System.out.println("---------Database add trial-------------");
			Benevole b1=new Benevole("AA","aa",52,BC);
			Benevole b11=new Benevole("AA","aa",52,BC);
			System.out.println("b1 id : "+b1.getIdentifiant());
			Benevole b2=new Benevole("BB","bb",58,BC);
			System.out.println("b2 id : "+b2.getIdentifiant());
			Benevole b3=new Benevole("CC","cc",60,BC);
			System.out.println("b3 id : "+b3.getIdentifiant());
			assertTrue(b1.getIdentifiant()==1);
			assertTrue(b11.getIdentifiant()==1);
			assertTrue(b2.getIdentifiant()==2);
			assertTrue(b3.getIdentifiant()==3);
			System.out.println("---------Database consult all the benevoles trial-------");
			result=BC.getAllBenevoles();
			BC.printAllBenevoles();
			assertFalse(result==null,"Le resultat ne doit pas etre vide");
			System.out.println("---------Database consult single benevole trial-------");
			BC.printBen(b1);
			BC.printBen(BC.consultUserById(1).get());
			BC.printBen(b2);
			BC.printBen(BC.consultUserById(2).get());
			BC.printBen(b3);
			BC.printBen(BC.consultUserById(3).get());
			assertTrue(b1.getAge()==BC.consultUserById(1).get().getAge());
			assertTrue(b2.getAge()==BC.consultUserById(2).get().getAge());
			assertTrue(b2.getAge()==BC.consultUserById(2).get().getAge());
			System.out.println("-------------------------------------------------------------");
			BC.printAllBenevoles();
			System.out.println("---------Database update single benevole trial-------");
			b1.setAge(53);
			b2.setNom("BBB");
			b3.setPrenom("ccc");
			BC.updateBenevole(b1);
			BC.updateBenevole(b2);
			BC.updateBenevole(b3);
			result=BC.getAllBenevoles();
			assertFalse(result==null,"Le resultat ne doit pas etre vide");
			BC.printAllBenevoles();
			assertTrue(53==BC.consultUserById(1).get().getAge(),"u1 Update non reussi");
			assertTrue(BC.consultUserById(2).get().getNom().equals("BBB"),"u2 Update non reussi");
			assertTrue(BC.consultUserById(3).get().getPrenom().equals("ccc"),"u3 Update non reussi");
			System.out.println("---------Database delete table trial-------");
			db.deleteTable("DROP TABLE Benevole");
			db.disconnect();
		    System.out.println(""); 

		}catch(SQLException e) {
			System.out.println(e.getMessage());
			
		}

	
	}

}
