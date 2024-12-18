package userManip;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import databaseManip.*;
import request.Request;
import request.RequestType;

public class UserControl {
	private DatabaseControl DB;
	
	public UserControl(DatabaseControl db){
		this.DB=db;
		createUsersTable();
		
	}

	private void createUsersTable() {
	        String createTableSQL = "CREATE TABLE IF NOT EXISTS User ("
	                + "id INT AUTO_INCREMENT PRIMARY KEY, "
	                + "nom VARCHAR(50) COLLATE utf8_general_ci, "
	                + "prenom VARCHAR(50) COLLATE utf8_general_ci, "
	                + "age INT,"
	                + "UNIQUE (nom,prenom))";
	        DB.createTable(createTableSQL);
	}
	
	//inserer un user dans le database
	public void Insertion(User user) {
		 String insertSQL = "INSERT INTO User (nom, prenom, age) VALUES (?, ?, ?)";
		 try (PreparedStatement stmt = DB.getConnection().prepareStatement(insertSQL)) {
	            stmt.setString(1, user.getNom());
	            stmt.setString(2, user.getPrenom());
	            stmt.setInt(3, user.getAge());
	            int id=getId(user.getNom(),user.getPrenom());
	            if (id==-1) {
	            	stmt.executeUpdate();
	            	id=getId(user.getNom(),user.getPrenom());
	            	System.out.println("A new user! Welcome, you are the "+ id +"th user");	     
	            }else {
	            	System.out.println("Welcome Back user n° " + id +" "+ user.getFullName() );
	            }
	        } catch (SQLException e) {
	            System.out.println("Erreur User insertion: " + e.getMessage());
	        }
	}
	
	//trouver un identifiant par son nom et prenom
	public int getId(String nom,String prenom) {
		String query = "SELECT id FROM User WHERE nom= ? AND prenom=?";
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
	
	//renvoyer tous les users
	public List<User> getAllUsers() {
        String querySQL = "SELECT id, nom, prenom, age FROM User";
        List<User> users = new ArrayList<>();
        try (ResultSet resultSet = DB.consulteTable(querySQL)) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nom = resultSet.getString("nom");
                String prenom = resultSet.getString("prenom");
                int age = resultSet.getInt("age");
                users.add(new User(id,nom, prenom, age)); 
            }
        } catch (SQLException e) {
            System.out.println("Pas d'element: " + e.getMessage());
        }
        return users;
    }
	
	//renvoyer un seul user par son id
	public Optional<User> consultUserById(int userId) {
	    String query = "SELECT * FROM User WHERE id = ?";
	    try (PreparedStatement stmt = DB.getConnection().prepareStatement(query)) {
	        stmt.setInt(1, userId);
	        ResultSet rs = stmt.executeQuery();
	        if (rs.next()) {
	            return Optional.of(new User(rs.getInt("id"),rs.getString("nom"), rs.getString("prenom"), rs.getInt("age")));
	        }
	    } catch (SQLException e) {
	        System.out.println("Element non trouve: " + e.getMessage());
	    }
	    return Optional.empty();
	}
	  
	public void updateUser(User user) {
	        String updateSQL = "UPDATE User SET nom = ?, prenom = ?, age = ? WHERE id = ?";
	        try (PreparedStatement stmt = DB.getConnection().prepareStatement(updateSQL)) {
	            stmt.setString(1, user.getNom());
	            stmt.setString(2, user.getPrenom());
	            stmt.setInt(3, user.getAge());
	            stmt.setInt(4, user.getIdentifiant());
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
	  
	  public void printAllUsers() {
		  List<User> users=getAllUsers();
		  for(User user:users) {
			  System.out.println(user.toString());
		  }
	  }
	  
	  public void printUser(User user) {
		  System.out.println(user.toString());
	  }
	  
	  // un user peut demander en donnant une titre
	  public int sendRequest(String titre,String nom_user) {
		  String insertSQL = "INSERT INTO Request (titre, user, etat) VALUES (?, ?, ?)";
		    int generatedId = -1; // Pour stocker l'ID généré
		    try (PreparedStatement stmt = DB.getConnection().prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS)) {
		        stmt.setString(1, titre);
		        stmt.setString(2, nom_user);
		        stmt.setString(3, RequestType.WAITING);   
		        stmt.executeUpdate();

		        // Récupérer l'ID généré
		        try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
		            if (generatedKeys.next()) {
		                generatedId = generatedKeys.getInt(1);
		                System.out.println("Request insertion réussi avec ID : " + generatedId);
		            } else {
		                System.out.println("Aucun ID généré !");
		            }
		        }
		    } catch (SQLException e) {
		        System.out.println("Erreur Request insertion : " + e.getMessage());
		    }
		    return generatedId; // Retourner l'ID généré
	  }
	  
	  //un user peut envoyer un feedback apres que la requete est finie
	  public void sendFeedback(int id, String feedback,String nom_user) {
		  String query = "SELECT * FROM Request WHERE id = ?";
		    try (PreparedStatement stmt = DB.getConnection().prepareStatement(query)) {
		        stmt.setInt(1, id);
		        ResultSet rs = stmt.executeQuery();
		        if (rs.next()) {
		            if(!nom_user.equals(rs.getString("user"))) {
		            	System.out.println("You are not the sender of this request !!");
		            	return;
		            }else if(!(RequestType.FINISHED).equals(rs.getString("etat"))) {
		            	System.out.println("Ce requete n'est pas encore finie!");
		            	return;
		            };
		        }
		    } catch (SQLException e) {
		        System.out.println(e.getMessage());
		        return;
		    }
		    
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
	  
	  //chaque user peut voir ses propre demandes
	  public void myRequest(String nom_user) {
		  String querySQL = "SELECT * FROM Request WHERE user = ? ";
	        List<Request> requests = new ArrayList<>();
	        try (PreparedStatement stmt = DB.getConnection().prepareStatement(querySQL)) {
	            stmt.setString(1, nom_user);
	            ResultSet resultSet = stmt.executeQuery();
	            boolean hasResults = false;
	            while (resultSet.next()) {
	                hasResults= true;
	            	int id = resultSet.getInt("id");
	                String titre = resultSet.getString("titre");
	                String etat = resultSet.getString("etat");
	                String motif = resultSet.getString("motif");
	                String feedback = resultSet.getString("feedback");
	                String user = resultSet.getString("user");
	                String benevole = resultSet.getString("benevole");
	                requests.add(new Request(id,titre,etat, motif, feedback, user,benevole)); 
            	}
	            if (!hasResults) {
	            	System.out.println("Vous n'avez demandé aucune demande");
	            }
	        } catch (SQLException e) {
	            System.out.println("Not Request: " + e.getMessage());
	        }
	        for(Request request:requests) {
				  System.out.println(request.toString());
			}
	  }
	  
	  //le controleur peut jeter un user
	  public void deleteUser(int identifiant) {
			String deleteSQL ="DELETE FROM User WHERE id = ?";
			try (PreparedStatement stmt=DB.getConnection().prepareStatement(deleteSQL)){
				stmt.setInt(1, identifiant);
				int rowsAffected = stmt.executeUpdate(); // Exécute la requête
		        if (rowsAffected > 0) {
		            System.out.println("User avec l'identifiant " + identifiant + " supprimé avec succès.");
		        } else {
		            System.out.println("Aucun user trouvé avec l'identifiant " + identifiant + ".");
		        }
			}catch (SQLException e) {
		        System.out.println(e.getMessage());
		        return;
		    } 
		}
}


