package Benevole_Manip; 
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import Database_Manip.Database_Control;
import User_Manip.User;
import request.RequestType;
import request.Request;

public class Benevole_Control {

	private Database_Control DB;

 

	public Benevole_Control(Database_Control db){

		this.DB=db;

		DB.createTable("CREATE TABLE IF NOT EXISTS Benevole ("
			+ "id INT AUTO_INCREMENT PRIMARY KEY, "
			+ "nom VARCHAR(50), "
			+ "prenom VARCHAR(50), "
			+ "age INT,"
			+ "UNIQUE (nom,prenom))");
	 }

 

	public void Insertion(Benevole ben) {

		/*String query= "INSERT INTO Benevole (nom, prenom, age) VALUES "+"("+"'"+ben.getNom() +"'" +" , " +"'"+ben.getPrenom()+"'"+" ,"+ String.valueOf(ben.getAge())+")";
		DB.insertData(query);*/
		String insertSQL = "INSERT INTO Benevole (nom, prenom, age) VALUES (?, ?, ?)";
		 try (PreparedStatement stmt = DB.getConnection().prepareStatement(insertSQL)) {
	            stmt.setString(1, ben.getNom());
	            stmt.setString(2, ben.getPrenom());
	            stmt.setInt(3, ben.getAge());	            
	            int id=getId(ben.getNom(),ben.getPrenom());
	            if (id==-1) {
	            	stmt.executeUpdate();
	            	id=getId(ben.getNom(),ben.getPrenom());
	            	System.out.println("A new volunteer! Welcome, you are the "+id +"th volunteer");	     
	            }else {
	            	System.out.println("Welcome Back volunteer n° " + id +" "+ ben.getFullName() );
	            }
	        } catch (SQLException e) {
	            System.out.println("Erreur Benevole insertion: " + e.getMessage());
	        }
	}
		
	public void printBen(Benevole ben) {
	  System.out.println(ben.toString());
	  }
	 
	public int getId(String nom,String prenom) {
		String query = "SELECT id FROM Benevole WHERE nom= ? AND prenom=?";		
		try (PreparedStatement stmt = DB.getConnection().prepareStatement(query)){		
			stmt.setString(1, nom);
			stmt.setString(2, prenom);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) { 
	            return rs.getInt("id");
	        } else {
	        }
		} catch (SQLException e) {
	        System.out.println("Erreur SQL : " + e.getMessage());
	    }
		return -1;
	 }
	public List<Benevole> getAllBenevoles() {
        String querySQL = "SELECT id, nom, prenom, age FROM Benevole";
        List<Benevole> benevoles = new ArrayList<>();
        try (ResultSet resultSet = DB.consulteTable(querySQL)) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nom = resultSet.getString("nom");
                String prenom = resultSet.getString("prenom");
                int age = resultSet.getInt("age");
                benevoles.add(new Benevole(id,nom, prenom, age)); 
            }
        } catch (SQLException e) {
            System.out.println("Pas d'element: " + e.getMessage());
        }
        return benevoles;
    }
	public void printAllBenevoles() {
		  List<Benevole> benevoles=getAllBenevoles();
		  for(Benevole benevole:benevoles) {
			  System.out.println(benevole.toString());
		  }
	  }
	
	public Optional<Benevole> consultUserById(int benevoleId) {
	    String query = "SELECT * FROM Benevole WHERE id = ?";
	    try (PreparedStatement stmt = DB.getConnection().prepareStatement(query)) {
	        stmt.setInt(1, benevoleId);
	        ResultSet rs = stmt.executeQuery();
	        if (rs.next()) {
	            return Optional.of(new Benevole(rs.getInt("id"),rs.getString("nom"), rs.getString("prenom"), rs.getInt("age")));
	        }
	    } catch (SQLException e) {
	        System.out.println("Element non trouve: " + e.getMessage());
	    }
	    return Optional.empty();
	}
	  
	public void updateBenevole(Benevole benevole) {
	        String updateSQL = "UPDATE Benevole SET nom = ?, prenom = ?, age = ? WHERE id = ?";
	        try (PreparedStatement stmt = DB.getConnection().prepareStatement(updateSQL)) {
	            stmt.setString(1, benevole.getNom());
	            stmt.setString(2, benevole.getPrenom());
	            stmt.setInt(3, benevole.getAge());
	            stmt.setInt(4, benevole.getIdentifiant());
	            int rowsAffected = stmt.executeUpdate();
	            if (rowsAffected > 0) {
	                System.out.println("User mtj reussi!");
	            } else {
	                System.out.println("Element non trouve.");
	            }
	        } catch (SQLException e) {
	            System.out.println("Update non reussi: " + e.getMessage());
	        }
	    }
	  
	
	public void consultRequest() {
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
        for(Request request:requests) {
			  System.out.println(request.toString());
		  }
	}
	
	
	public void acceptRequest(int id, String nom_benevole) {
		 String query = "SELECT * FROM Request WHERE id = ?";
		    try (PreparedStatement stmt = DB.getConnection().prepareStatement(query)) {
		        stmt.setInt(1, id);
		        ResultSet rs = stmt.executeQuery();
		        if (rs.next()) {
		            if(!"Not assigned".equals(rs.getString("benevole"))) {
		            	throw new SQLException("This request has been already taken !!");
		            }
		        }
		    } catch (SQLException e) {
		        System.out.println(e.getMessage());
		        return;
		    }
		String updateSQL = "UPDATE Request SET benevole = ? ,etat= ? WHERE id = ?";
		 try (PreparedStatement stmt = DB.getConnection().prepareStatement(updateSQL)) {
	            stmt.setString(1, nom_benevole);
	            stmt.setString(2, RequestType.IN_PROGRESS);
	            stmt.setInt(3, id); 
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
	
	public void finishRequest(int id,String nom_benevole) {
		 String query = "SELECT * FROM Request WHERE id = ?";
		    try (PreparedStatement stmt = DB.getConnection().prepareStatement(query)) {
		        stmt.setInt(1, id);
		        ResultSet rs = stmt.executeQuery();
		        if (rs.next()) {
		            if(!nom_benevole.equals(rs.getString("benevole"))) {
		            	throw new SQLException("You are not the volunteer for this request !!");
		            }
		        }
		    } catch (SQLException e) {
		        System.out.println(e.getMessage());
		        return;
		    } 
		String updateSQL = "UPDATE Request SET etat = ? WHERE id = ?";
		 try (PreparedStatement stmt = DB.getConnection().prepareStatement(updateSQL)) {
	            stmt.setString(1, RequestType.FINISHED);
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