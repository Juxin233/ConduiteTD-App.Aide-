package Valideur_Manip; 
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Database_Manip.Database_Control;
import request.Request;
import request.RequestType; 


public class Valideur_Control {

	 private Database_Control DB;

	 
	
	 public Valideur_Control(Database_Control db){
	
		 this.DB=db;
		
		 DB.createTable("CREATE TABLE Valideur ("
		 + "id INT AUTO_INCREMENT PRIMARY KEY, "
		 + "nom VARCHAR(50) COLLATE utf8_general_ci, "
		 + "prenom VARCHAR(50) COLLATE utf8_general_ci, "
		 + "UNIQUE (nom,prenom)) ");
		
	 }
	
	 //inserer un valideur dans le database
	 public void Insertion(Valideur val) {
		 String insertSQL = "INSERT INTO Valideur (nom, prenom) VALUES (?, ?)";
		 try (PreparedStatement stmt = DB.getConnection().prepareStatement(insertSQL)) {
	            stmt.setString(1, val.getNom());
	            stmt.setString(2, val.getPrenom());
	            int id=getId(val.getNom(),val.getPrenom());
	            if (id==-1) {
	            	stmt.executeUpdate();
	            	id=getId(val.getNom(),val.getPrenom());
	            	System.out.println("A new validator! Welcome, you are the "+id +"th validator");	     
	            }else {
	            	System.out.println("Welcome Back validator n° " + id +" "+ val.getFullName() );
	            }
	        } catch (SQLException e) {
	            System.out.println("Erreur Benevole insertion: " + e.getMessage());
	        }
		
	 }
	 
	 //renvoyer l'idnetifiant d'un valideur
	 public int getId(String nom,String prenom) {
			String query = "SELECT id FROM Valideur WHERE nom= ? AND prenom=?";		
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
	 
	 public void printVal(Valideur val) {
		  System.out.println("Nom: "+ val.getNom() + "  Prenom: " + val.getPrenom());
	}
	
	 //valider une requete par son id
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
	
	//invalider une requete en donnant un motif
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
	
	//voir tous les requete
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
	
	//consulter une seule requete
	public boolean consultById(int id) {
		 String query = "SELECT * FROM Request WHERE ID = ?";
		 boolean isExist= false;
		    try (PreparedStatement stmt = DB.getConnection().prepareStatement(query)) {
		        stmt.setInt(1, id);
		        ResultSet rs = stmt.executeQuery();
		        if (rs.next()) {
		           Request r=(new Request(rs.getInt("id"),rs.getString("titre"), rs.getString("etat"),rs.getString("motif"), rs.getString("feedback"),rs.getString("user"),rs.getString("benevole")));
		           System.out.println(r.toString());
		           isExist= true;
		        }else {
		        	System.out.println("Il n'y a pas de requete correspondante.");
		        }
		    } catch (SQLException e) {
		        System.out.println("Element non trouve: " + e.getMessage());
		    }
		    return isExist;
	}
	
	//send un motif dans une requete en cas de refusion
	 public void Motif(int id, String motif,String nom_user) {
		 String updateSQL = "UPDATE Request SET motif = ? WHERE id = ?";
		 try (PreparedStatement stmt = DB.getConnection().prepareStatement(updateSQL)) {
	          stmt.setString(1, motif);
	          stmt.setInt(2, id);  
	          stmt.executeUpdate();
	      } catch (SQLException e) {
	          System.out.println("Update non reussi: " + e.getMessage());
	      }  
	 }
	 
	 public void deleteValideur(int identifiant) {
			String deleteSQL ="DELETE FROM Valideur WHERE id = ?";
			try (PreparedStatement stmt=DB.getConnection().prepareStatement(deleteSQL)){
				stmt.setInt(1, identifiant);
				int rowsAffected = stmt.executeUpdate(); // Exécute la requête
		        if (rowsAffected > 0) {
		            System.out.println("Valideur avec l'identifiant " + identifiant + " supprimé avec succès.");
		        } else {
		            System.out.println("Aucun valideur trouvé avec l'identifiant " + identifiant + ".");
		        }
			}catch (SQLException e) {
		        System.out.println(e.getMessage());
		        return;
		    } 
		}
}