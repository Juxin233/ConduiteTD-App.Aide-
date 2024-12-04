package request;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import Database_Manip.*;
import User_Manip.*;

public class RequestManager {
	private Database_Control DB;
	
	
	public RequestManager(Database_Control DB) {
		this.DB=DB;
	}
	
	public void createRequestsTable() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS Request ("
                + "id INT AUTO_INCREMENT PRIMARY KEY, "
                + "titre VARCHAR(50), "
                + "etat VARCHAR(50), "
                + "motif VARCHAR(50), "
                + "user VARCHAR(50), "
                + "benevole VARCHAR(50) DEFAULT 'Not assigned', "
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
	            System.out.println("Request insertion reussi!");
	        } catch (SQLException e) {
	            System.out.println("Erreur Request insertion: " + e.getMessage());
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
                requests.add(new Request(id,titre,etat, motif, feedback, user,benevole)); 
            }
        } catch (SQLException e) {
            System.out.println("Pas d'element: " + e.getMessage());
        }
        return requests;
    }
	
	 public void printAllRequests() {
		  List<Request> requests=getAllRequests();
		  for(Request request:requests) {
			  System.out.println(request.toString());
		  }
	  }
	 
	 public void printRequest(Request request) {
		  System.out.println(request.toString());
	  }
	 
	 public Optional<Request> consultRequestById(int requestId) {
		    String query = "SELECT * FROM Request WHERE ID = ?";
		    try (PreparedStatement stmt = DB.getConnection().prepareStatement(query)) {
		        stmt.setInt(1, requestId);
		        ResultSet rs = stmt.executeQuery();
		        if (rs.next()) {
		            return Optional.of(new Request(rs.getInt("id"),rs.getString("titre"), rs.getString("etat"),rs.getString("motif"), rs.getString("feedback"),rs.getString("user"),rs.getString("benevole")));
		        }
		    } catch (SQLException e) {
		        System.out.println("Element non trouve: " + e.getMessage());
		    }
		    return Optional.empty();
		}
	 
	public void updateRequest(int id,int valid) {
		if (valid==1) {
			   String updateSQL = "UPDATE Request SET etat = ? WHERE id = ?";
		        try (PreparedStatement stmt = DB.getConnection().prepareStatement(updateSQL)) {
		            stmt.setString(1, RequestType.VALID);
		            stmt.setInt(2, id);
		            int rowsAffected = stmt.executeUpdate();
		            if (rowsAffected > 0) {
		                System.out.println("Request mtj reussi!");
		            } else {
		                System.out.println("Element non trouve.");
		            }
		        } catch (SQLException e) {
		            System.out.println("Update non reussi: " + e.getMessage());
		        }
		}else {
			 String updateSQL = "UPDATE Request SET etat = ? WHERE id = ?";
		        try (PreparedStatement stmt = DB.getConnection().prepareStatement(updateSQL)) {
		            stmt.setString(1, RequestType.INVALID);
		            stmt.setInt(2, id);
		            int rowsAffected = stmt.executeUpdate();
		            if (rowsAffected > 0) {
		                System.out.println("Request mtj reussi!");
		            } else {
		                System.out.println("Element non trouve.");
		            }
		        } catch (SQLException e) {
		            System.out.println("Update non reussi: " + e.getMessage());
		        }
		}
	}
	
	public void sendFeedback(int id, String feedback) {
		String updateSQL = "UPDATE Request SET feedback = ? WHERE id = ?";
		 try (PreparedStatement stmt = DB.getConnection().prepareStatement(updateSQL)) {
	            stmt.setString(1, feedback);
	            stmt.setInt(2, id);
	            int rowsAffected = stmt.executeUpdate();
	            if (rowsAffected > 0) {
	                System.out.println("Request mtj reussi!");
	            } else {
	                System.out.println("Element non trouve.");
	            }
	        } catch (SQLException e) {
	            System.out.println("Update non reussi: " + e.getMessage());
	        }
	}
	
	
}
