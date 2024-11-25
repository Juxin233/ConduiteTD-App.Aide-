package Benevole_Manip; 
import java.sql.PreparedStatement;
import java.sql.SQLException;

import Database_Manip.Database_Control;
import request.RequestType;


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
		System.out.println(ben.getNom()+"  "+ ben.getPrenom() + "  "+ String.valueOf(ben.getAge()));
	}
 
	public void acceptRequest(int id, String nom_benevole) {
		String updateSQL = "UPDATE Request SET benevole = ? etat= ? WHERE id = ?";
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
	
	public void finishRequest(int id) {
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