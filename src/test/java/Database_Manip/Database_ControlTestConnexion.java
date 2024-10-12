package Database_Manip;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;

import org.junit.jupiter.api.Test;

class Database_ControlTestConnexion {

	@Test
	void test() {
		Database_Control db =new Database_Control("srv-bdens.insa-toulouse.fr", 3306, "projet_gei_024", "projet_gei_024", "Zai6Xoo7");
		try {
			db.connect();
			assertTrue(db.is_Connected(),"La connexion doit etre etablie."); 
			db.disconnect();
			assertFalse(db.is_Connected(),"La connexion doit etre fermee.");
		}catch (SQLException e) {
				System.out.println(e.getMessage());
		}
	}

}
