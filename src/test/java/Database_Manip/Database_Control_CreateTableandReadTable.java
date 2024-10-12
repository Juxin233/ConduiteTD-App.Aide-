package Database_Manip;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.jupiter.api.Test;

class Database_Control_CreateTableandReadTable {

	@Test
	void test() {
		
		String createTableSQL = "CREATE TABLE User ("
                + "id INT AUTO_INCREMENT PRIMARY KEY, "
                + "nom VARCHAR(50), "
                + "prenom VARCHAR(50), "
                + "age INT)";
		String insertSQL1 ="INSERT INTO User (nom, prenom, age) VALUES ('AA', 'aa', 11)";
		String insertSQL2 ="INSERT INTO User (nom, prenom, age) VALUES ('BB', 'bb', 22)";
		String Userquery ="SELECT id, nom, prenom, age FROM User";
		String Userdelete="DROP TABLE User";
		ResultSet result=null;
		try {
			Database_Control db =new Database_Control("srv-bdens.insa-toulouse.fr", 3306, "projet_gei_024", "projet_gei_024", "Zai6Xoo7");
			db.connect();
			db.createTable(createTableSQL);
			db.insertData(insertSQL1);
			db.insertData(insertSQL2);
			result=db.consulteTable(Userquery);
			while (result.next()) {
			        int id = result.getInt("id");
			        String nom = result.getString("nom");
			        String prenom = result.getString("prenom");
			        int age = result.getInt("age");
			       
			        System.out.println("ID: " + id + ", nom: " + nom +", prenom: "+prenom+ ", Age: " + age);
			    }
			db.deleteTable(Userdelete);
			db.disconnect();
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
	}
}


