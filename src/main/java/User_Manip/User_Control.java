package User_Manip;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import request.Request;
import request.RequestType;

import Database_Manip.*;

public class User_Control {
	private Database_Control DB;
	
	public User_Control(Database_Control db){
		this.DB=db;
		createUsersTable();
		
	}

	private void createUsersTable() {
	        String createTableSQL = "CREATE TABLE IF NOT EXISTS User ("
	                + "id INT AUTO_INCREMENT PRIMARY KEY, "
	                + "nom VARCHAR(50), "
	                + "prenom VARCHAR(50), "
	                + "age INT,"
	                + "UNIQUE (nom,prenom))";
	        DB.createTable(createTableSQL);
	}
	
	
	public void Insertion(User user) {
		//"INSERT INTO User (nom, prenom, age) VALUES ('AA', 'aa', 11)"
		//String query= "INSERT INTO User (nom, prenom, age) VALUES "+" ("+"'"+user.getNom()+"'"+" , "+"'"+user.getPrenom()+"'"+" , "+String.valueOf(user.getAge())+")";
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
	
	
	/*old version
	 * 
	 * public ResultSet CousultAll() {
		ResultSet result=null;
		try{
			result=DB.consulteTable("SELECT id, nom, prenom, age FROM User");
			while (result.next()) {
		        int id = result.getInt("id");
		        String nom = result.getString("nom");
		        String prenom = result.getString("prenom");
		        int age = result.getInt("age");
		        System.out.println("ID: " + id + ", nom: " + nom +", prenom: "+prenom+ ", Age: " + age);
		    }
		}catch(SQLException e) {
			System.out.println("Consultation non reussie: " + e.getMessage());
		}
		return result;
	}*/
	
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
	
	
	/*old version
	 * 
	 * 
	 * public ResultSet Consult_User_oneline(User user) throws SQLException{
		String query="SELECT * FROM User WHERE ID="+ user.getIdentifiant();
		ResultSet result =DB.consulteTable(query);;
		if(!result.next()) throw new SQLException("Element pas trouve");
		return result;
	}*/

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
	  
	  public void sendRequest(String titre,String nom_user) {
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
	  
	  
	  public void sendFeedback(int id, String feedback,String nom_user) {
		  String query = "SELECT * FROM Request WHERE id = ?";
		    try (PreparedStatement stmt = DB.getConnection().prepareStatement(query)) {
		        stmt.setInt(1, id);
		        ResultSet rs = stmt.executeQuery();
		        if (rs.next()) {
		            if(!nom_user.equals(rs.getString("user"))) {
		            	throw new SQLException("You are not the sender of this request !!");
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
	  
	  
	  /*public void sendMotif(int id, String motif,String nom_user) {
		  //Verify if user is the sender of the request
		  String query = "SELECT * FROM Request WHERE id = ?";
		    try (PreparedStatement stmt = DB.getConnection().prepareStatement(query)) {
		        stmt.setInt(1, id);
		        ResultSet rs = stmt.executeQuery();
		        if (rs.next()) {
		            if(!nom_user.equals(rs.getString("user"))) {
		            	throw new SQLException("You are not the sender of this request !!");
		            };
		        }
		    } catch (SQLException e) {
		        System.out.println(e.getMessage());
		        return;
		    }
		  //Update the request
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
	  }*/
	  
	  public void myRequest(String nom_user) {
		  String querySQL = "SELECT * FROM Request WHERE user = ? ";
	        List<Request> requests = new ArrayList<>();
	        try (PreparedStatement stmt = DB.getConnection().prepareStatement(querySQL)) {
	            stmt.setString(1, nom_user);
	            ResultSet resultSet = stmt.executeQuery();
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
	            System.out.println("Not Request: " + e.getMessage());
	        }
	        for(Request request:requests) {
				  System.out.println(request.toString());
			}
	  }
}


