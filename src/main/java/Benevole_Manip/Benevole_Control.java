package Benevole_Manip; 
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Database_Manip.Database_Control;
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
			+ "age INT)");
	 }

 

	public void Insertion(Benevole ben) {

		String query= "INSERT INTO Benevole (nom, prenom, age) VALUES "+"("+"'"+ben.getNom() +"'" +" , " +"'"+ben.getPrenom()+"'"+" ,"+ String.valueOf(ben.getAge())+")";
		DB.insertData(query);
	}
		
 public void printBen(Benevole ben) {
	  System.out.println("Nom: " +ben.getNom()+"  Prenom: "+ ben.getPrenom() + "  Age: "+ String.valueOf(ben.getAge()));
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