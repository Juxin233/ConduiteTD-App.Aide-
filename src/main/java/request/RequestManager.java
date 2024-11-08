package request;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Database_Manip.*;
import User_Manip.*;

public class RequestManager {
	private Database_Control DB;
	
	
	
	private void createUsersTable() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS Request ("
                + "id INT AUTO_INCREMENT PRIMARY KEY, "
                + "titre VARCHAR(50), "
                + "etat VARCHAR(50), "
                + "motif VARCHAR(50), "
                + "user VARCHAR(50), "
                + "benevole VARCHAR(50), "
                + "feedback VARCHAR(50))";
        		DB.createTable(createTableSQL);
	}
	
	public void Insertion(String titre,String nom_user/*,String motif*/) {
		 String insertSQL = "INSERT INTO Request (titre, user,etat) VALUES (?,?,?)";
		 try (PreparedStatement stmt = DB.getConnection().prepareStatement(insertSQL)) {
	            stmt.setString(1, titre);
	            stmt.setString(2, nom_user);
	            stmt.setString(3, RequestType.WAITING);   
	            stmt.executeUpdate();
	            System.out.println("User insertion reussi!");
	        } catch (SQLException e) {
	            System.out.println("Erreur User insertion: " + e.getMessage());
	        }
	}
	
	public List<Request> getAllRequests() {
        String querySQL = "SELECT id,titre,etat,motif,feedback,user,benevole FROM Request";
        List<Request> requests = new ArrayList<>();
        try (ResultSet resultSet = DB.consulteTable(querySQL)) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String titre = resultSet.getString("titre");
                String etat = resultSet.getString("etat");
                String motif = resultSet.getString("motif");
                String feedback = resultSet.getString("feedback");
                String user = resultSet.getString("user");
                String benevole = resultSet.getString("benevole");
                requests.add(new Request(titre,etat, motif, feedback, user,benevole)); 
            }
        } catch (SQLException e) {
            System.out.println("Pas d'element: " + e.getMessage());
        }
        return requests;
    }
	
	
}
