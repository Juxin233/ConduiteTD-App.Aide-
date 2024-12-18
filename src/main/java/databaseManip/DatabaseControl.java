package databaseManip;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class DatabaseControl {
	private String host;
    private int port;
    private String database;
    private String username;
    private String password;
    private Connection connection;

    
    public DatabaseControl(String host, int port, String database, String username, String password) {
        this.host = host;
        this.port = port;
        this.database = database;
        this.username = username;
        this.password = password;
        this.connection = null; 
    }
    
    //connecter au database 
    public void connect() throws SQLException{
    	String jdbcUrl = "jdbc:mysql://" + host + ":" + port + "/" + database;
        try {
            connection = DriverManager.getConnection(jdbcUrl, username, password);
            System.out.println("Connexion_reussi!");
        } catch (SQLException e) {
            System.out.println("Connexion_non_reussi: " + e.getMessage());
            throw e;
        }
    }
    //deconnecter au database
    public void disconnect() {
    	if (this.connection != null) {
            try {
                connection.close();
                System.out.println("Database fermee!");
                connection=null;
            } catch (SQLException e) {
                System.out.println("Database non fermee: " + e.getMessage());
            }
        }
    }
    
    
    public boolean is_Connected() {
    	if (this.connection!=null) {
    		return true;
    	}else return false;
    		
    }
    
    //creer des tables 
    public void createTable(String createTableSQL) {
        try {
            if(connection != null) {
                Statement stmt = connection.createStatement();
                stmt.execute(createTableSQL);
                System.out.println("Table cree!");
                stmt.close();
            }
        }catch (SQLException e) {
            System.out.println("Table non cree " + e.getMessage());
        }
    }
    
    public void deleteTable(String deleteTableSQL) {
    	try {
    		Statement stmt = connection.createStatement();
	        stmt.execute(deleteTableSQL);
	        System.out.println("Table supprimee!");
	        stmt.close();
    	}catch(SQLException e) {
    		System.out.println(e.getMessage());
    	}
    }
    
    public ResultSet consulteTable(String consulteSQL) {
    	Statement stmt=null;
    	ResultSet resultSet=null;
		try {
			stmt = connection.createStatement();
			resultSet = stmt.executeQuery(consulteSQL);
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	return resultSet;
    }
    public void insertData(String query) {
        try {
            if (is_Connected()) {
                Statement stmt = connection.createStatement();
                int rowsAffected = stmt.executeUpdate(query);
                System.out.println("Insertion reussie，ligne affectee: " + rowsAffected);
                stmt.close();
            } else {
                System.out.println("Table pas trouvee!");
            }
        } catch (SQLException e) {
            System.out.println("Insertion non reussie: " + e.getMessage());
        }
    }

	public Connection getConnection() {
		return connection;
	}
}
