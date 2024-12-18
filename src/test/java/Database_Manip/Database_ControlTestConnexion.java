package Database_Manip;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;

import org.junit.jupiter.api.Test;

import databaseManip.DatabaseControl;

class Database_ControlTestConnexion {

	@Test
	void test() {
		DatabaseControl db =new DatabaseControl("srv-bdens.insa-toulouse.fr", 3306, "projet_gei_024", "projet_gei_024", "Zai6Xoo7");
		try {
		    System.out.println("**Test Database Control Connexion**"); 

			db.connect();
			assertTrue(db.is_Connected(),"La connexion doit etre etablie."); 
			db.disconnect();
			assertFalse(db.is_Connected(),"La connexion doit etre fermee.");
		    System.out.println(""); 
		}catch (SQLException e) {
				System.out.println(e.getMessage());
		}
	}

}
