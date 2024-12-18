package Database_Manip;


import org.junit.jupiter.api.Test;

import databaseManip.DatabaseControl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


class Database_Control_CreateTableandReadTable {

	@Test
	void test() {
		
		String createTableSQL = "CREATE TABLE User ("
                + "id INT AUTO_INCREMENT PRIMARY KEY, "
                + "nom VARCHAR(50), "
                + "prenom VARCHAR(50), "
                + "age INT)";
		//String insertSQL1 ="INSERT INTO User (nom, prenom, age) VALUES ('AA', 'aa', 11)";
		//String insertSQL2 ="INSERT INTO User (nom, prenom, age) VALUES ('BB', 'bb', 22)";
		//String Userquery ="SELECT id, nom, prenom, age FROM User";
		//String Userdelete="DROP TABLE User";
		try {
		    System.out.println("**Test Database Control Create Table and Read Table Test**"); 

			DatabaseControl db =new DatabaseControl("srv-bdens.insa-toulouse.fr", 3306, "projet_gei_024", "projet_gei_024", "Zai6Xoo7");
			db.connect();
			
			db.createTable(createTableSQL);
			String insertSQL = "INSERT INTO User (nom, prenom, age) VALUES (?, ?, ?)";
			String deleteSQL = "DELETE FROM User WHERE id = ?";
			int generatedId=-1; 
			try (PreparedStatement stmt = db.getConnection().prepareStatement(insertSQL,Statement.RETURN_GENERATED_KEYS)) {
		            stmt.setString(1, "Titi");
		            stmt.setString(2, "Toto");
		            stmt.setInt(3, 18);
		            stmt.executeUpdate();
		            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
			            if (generatedKeys.next()) {
			                generatedId = generatedKeys.getInt(1);
			                System.out.println("User insertion réussi avec ID : " + generatedId);
			            } else {
			                System.out.println("Aucun ID généré !");
			            }   
			        }catch(SQLException e) {
						System.out.println(e.getMessage());
			        }
		            try(PreparedStatement stmt1= db.getConnection().prepareStatement(deleteSQL)){
		            	stmt1.setInt(1, generatedId);
		            	int rowsAffected = stmt1.executeUpdate(); // Exécute la requête
				        if (rowsAffected > 0) {
				            System.out.println("User avec l'identifiant " + generatedId + " supprimé avec succès.");
				        } else {
				            System.out.println("Aucun user trouvé avec l'identifiant " + generatedId + ".");
				        }
		            }catch(SQLException e) {
						System.out.println(e.getMessage());
		            }
			}
			//db.deleteTable(Userdelete);
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
	}
}


