package User_Manip;
import Database_Manip.*;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import java.util.*;

import org.junit.jupiter.api.Test;

class User_Control_CreationandInsertionTest {

	@Test
	void test() {
		String host="srv-bdens.insa-toulouse.fr";
		int port=3306;
	    String database="projet_gei_024";
	    String username="projet_gei_024";
	    String password="Zai6Xoo7";
	    List<User> result=null;
		try {
		    System.out.println("**Test User Control Creation and Insertion**"); 
			System.out.println("---------Database connexion trial-------");
			Database_Control db =new Database_Control(host, port, database, username, password);
			db.connect();
			System.out.println("---------Database create table trial-------");
			db.deleteTable("DROP TABLE User");
			User_Control UC= new User_Control(db);
			System.out.println("---------Database add trial-------------");
			User u1=new User("AA","aa",52,UC);
			User u11=new User("AA","aa",52,UC);
			System.out.println("u1 id : "+u1.getIdentifiant());
			User u2=new User("BB","bb",58,UC);
			System.out.println("u2 id : "+u2.getIdentifiant());
			User u3=new User("CC","cc",60,UC);
			System.out.println("u3 id : "+u3.getIdentifiant());
			assertTrue(u1.getIdentifiant()==1);
			assertTrue(u11.getIdentifiant()==1);
			assertTrue(u2.getIdentifiant()==2);
			assertTrue(u3.getIdentifiant()==3);
			System.out.println("---------Database consult all the users trial-------");
			result=UC.getAllUsers();
			UC.printAllUsers();
			assertFalse(result==null,"Le resultat ne doit pas etre vide");
			System.out.println("---------Database consult single user trial-------");
			UC.printUser(u1);
			UC.printUser(UC.consultUserById(1).get());
			UC.printUser(u2);
			UC.printUser(UC.consultUserById(2).get());
			UC.printUser(u3);
			UC.printUser(UC.consultUserById(3).get());
			assertTrue(u1.getAge()==UC.consultUserById(1).get().getAge());
			assertTrue(u2.getAge()==UC.consultUserById(2).get().getAge());
			assertTrue(u3.getAge()==UC.consultUserById(3).get().getAge());
			System.out.println("-------------------------------------------------------------");
			UC.printAllUsers();
			System.out.println("---------Database update single user trial-------");
			u1.setAge(53);
			u2.setNom("BBB");
			u3.setPrenom("ccc");
			UC.updateUser(u1);
			UC.updateUser(u2);
			UC.updateUser(u3);
			result=UC.getAllUsers();
			assertFalse(result==null,"Le resultat ne doit pas etre vide");
			UC.printAllUsers();
			assertTrue(53==UC.consultUserById(1).get().getAge(),"u1 Update non reussi");
			assertTrue(UC.consultUserById(2).get().getNom().equals("BBB"),"u2 Update non reussi");
			assertTrue(UC.consultUserById(3).get().getPrenom().equals("ccc"),"u3 Update non reussi");
			System.out.println("---------Database delete table trial-------");
			db.deleteTable("DROP TABLE User");
			db.disconnect();
		    System.out.println(""); 

		}catch(SQLException e) {
			System.out.println(e.getMessage());
			
		}
	}

}
