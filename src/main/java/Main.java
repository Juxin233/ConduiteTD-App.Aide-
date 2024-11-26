import java.sql.SQLException;
import java.util.Scanner;

import Benevole_Manip.* ;
import Database_Manip.Database_Control;
import User_Manip.* ; 
import Valideur_Manip.* ; 


public class Main {

	// note : rendre les user en global. 

    public static void main(String[] args) throws SQLException {
    	
    	String type = ""; //stocke le type de compte créé 
    	
    	//Connection BDD 
    	String host="srv-bdens.insa-toulouse.fr";
		int port=3306;
	    String database="projet_gei_024";
	    String username="projet_gei_024";
	    String password="Zai6Xoo7";
	    
	    Database_Control db =new Database_Control(host, port, database, username, password);
		db.connect();
		
		//creation des tables 
		Benevole_Control BEN = new Benevole_Control(db) ;
		User_Control US =new User_Control(db); 
		Valideur_Control VAL = new Valideur_Control(db); 
		
		//initalisation des infos
		String Nom=""; 
		String Prenom=""; 
		int Age = 0 ; 

		
		//Creation des comptes 	
		System.out.println("------------------------------------------------------------------------"); 
		System.out.println("Creation d'un compte"); 
    	
    	Scanner sc = new Scanner(System.in);
    	
    	boolean comptecree = false ; 
    	System.out.println("Entrez UTILISATEUR , BENEVOLE ou VALIDATEUR") ; 
    	while (!comptecree) {
        String str = sc.nextLine(); 
    
    	System.out.println("Vous avez saisi : " + str);

    	if (str.equals( "BENEVOLE")) {
    		
    		System.out.println("Entrez Nom ") ; 
    		Nom = sc.next();
        	System.out.println("Entrez Prénom ") ; 
        	Prenom= sc.next(); 
        	System.out.println("Entrez Age ") ; 
        	Age = sc.nextInt(); 
        	Benevole ben = new Benevole(Nom,Prenom,Age,BEN); 
        	
    		BEN.printBen(ben); 	
    		comptecree = true ; 
    		type ="ben"; 
    	}
    	else if  (str.equals( "UTILISATEUR")) {
    		
    		System.out.println("Entrez Nom ") ; 
        	Nom = sc.next(); 
        	System.out.println("Entrez Prénom ") ; 
        	Prenom = sc.next(); 
        	System.out.println("Entrez Age ") ; 
        	Age = sc.nextInt(); 
    		User u = new User(Nom,Prenom,Age,US); 
   
    		US.printUser(u); 	
    		comptecree = true ; 
    		type="user"; 
    	}
    	
    	else if (str.equals("VALIDATEUR")) {
    		System.out.println("Entrez Nom ") ; 
        	Nom = sc.next(); 
        	System.out.println("Entrez Prénom ") ; 
        	Prenom = sc.next(); 
    		Valideur v = new Valideur(Nom,Prenom,VAL); 
   
    		VAL.printVal(v); 	
    		comptecree = true ; 
    		type = "val"; 
    		
    	
    	}
    	else {
    		System.out.println("Erreur, Re-essayez"); 
    	}
    	}
    	
    	System.out.println("-----------------------------------------------------"); 
		
	
    	//manipulation des requetes 
    	
    	boolean running = true ; 
    	while (running) {
    		String in = sc.nextLine(); 
    	
    		
    		//pour quitter le programme 
    		if (in.equals("exit")){
    			running = false ; 
    		}
    		
    		switch (type) {
	    		case "ben" : 
	    			
	    			System.out.println("Requetes disponibles : "); 
	    			break; 
	    			//choisir une requete 
	    			//finir une requete 
	    			
	    		case "user" : 
	    			System.out.println("Ajoutez votre requête : ");
	    			
	    			//ajout d'une requête 
	    			System.out.println("Choisissez le titre de votre requête: ");
	    			//String titre = sc.next(); 
	    			// Request r = new Request(titre,"WAITING","","",,""); 
	    			break; 
	    			
	    			
	    		case "val" : 
	    			System.out.println("Choisir la requête à valider"); 
	    			break; 
	    			//valider la requete 
	    			
	    		default: 
	    				break; 
    		}
    	}
		
    	
    	
		db.deleteTable("DROP TABLE Benevole");
		db.deleteTable("DROP TABLE User" );
		db.deleteTable("DROP TABLE Valideur");
		db.disconnect();
    	
    
    	

    }
}