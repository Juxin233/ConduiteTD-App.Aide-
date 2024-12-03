package Valideur_Manip; 
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Database_Manip.Database_Control;
import request.RequestManager ;
import request.RequestType; 


public class Valideur_Control {

 private Database_Control DB;
 private RequestManager RM ; 
 

 public Valideur_Control(Database_Control db){

 this.DB=db;

 DB.createTable("CREATE TABLE Valideur ("

 + "id INT AUTO_INCREMENT PRIMARY KEY, "

 + "nom VARCHAR(50), "

 + "prenom VARCHAR(50) ) ");

 }

 

 public void Insertion(Valideur val) {

 String query= "INSERT INTO Valideur (nom, prenom) VALUES "+"(" + "'" +val.getNom()+ "'"+ " , " +"'"+val.getPrenom()+"'" +" )";
 DB.insertData(query);

 }
 
 public void printVal(Valideur val) {
	  System.out.println("Nom: "+ val.getNom() + "  Prenom: " + val.getPrenom());
}

public void validRequest(int id) {
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
	
}

public void invalidRequest(int id) {
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
	 
 public void Motif(int id, String motif,String nom_user) {
	 String updateSQL = "UPDATE Request SET motif = ? WHERE id = ?";
	 try (PreparedStatement stmt = DB.getConnection().prepareStatement(updateSQL)) {
          stmt.setString(1, motif);
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