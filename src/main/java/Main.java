import java.sql.SQLException;
import java.util.Scanner;
import Benevole_Manip.* ;
import Database_Manip.Database_Control;
import User_Manip.* ; 
import Valideur_Manip.* ; 
import request.*; 


public class Main {

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
		RequestManager Requests = new RequestManager(db); 
		
		//initialisation des utilisateurs 
		Benevole ben = null; 
		User u=null; 
		Valideur v=null; 
		
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
        	ben = new Benevole(Nom,Prenom,Age,BEN); 
        	
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
        	u = new User(Nom,Prenom,Age,US); 
   
    		US.printUser(u); 	
    		comptecree = true ; 
    		type="user"; 
    	}
    	
    	else if (str.equals("VALIDATEUR")) {
    		System.out.println("Entrez Nom ") ; 
        	Nom = sc.next(); 
        	System.out.println("Entrez Prénom ") ; 
        	Prenom = sc.next(); 
        	v = new Valideur(Nom,Prenom,VAL); 

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
    	
    	//Création de la table des requetes 
    	Requests.createRequestsTable();
    	//Insertion de 2 requêtes initiales pour démontrer le fonctionnement de l'appli 
    	Requests.Insertion("Récuperer colis", "Alice");
    	Requests.Insertion("Faire des courses","Bob");
    	Requests.printAllRequests();//TODO : afficher seulement les requêtes dont les utilisateurs ont besoin ? 
    	
 
    	boolean running = true ; 
    	while (running) {
    		//pour quitter le programme taper exit 
    		switch (type) {
	    		case "ben" : //Actions bénévole
	    			System.out.println("Choisissez CHOISIR ou TERMINER ");
	    			String choix1 = sc.next(); 
	    			if (choix1.equals("CHOISIR")) {
		    			System.out.println("Choisissez la requete que vous souhaitez effectuer (ID) ");
		    			int id = sc.nextInt(); 
		    			ben.Accept_Request(id, BEN);
	    			}
	    			else if (choix1.equals("TERMINER")) {
	    				System.out.println("Choisissez la requete que vous souhaitez terminer (ID) ");
		    			int id = sc.nextInt(); 
		    			ben.finishRequest(id, BEN);
	    			}
					else if (choix1.equals("exit")) {
						 running=false;    				
						    			}
	    			else {
	    				System.out.println("Erreur. Recommencez"); 
	    			}
	    			break; 
	    			
	    		case "user" : //Actions Utilisateur
	    			System.out.println("Choisissez REQUETE ou FEEDBACK ");
	    			String choix = sc.next(); 
	    			if (choix.equals("REQUETE")) {
		    			System.out.println("Choisissez le titre de votre requête: ");
		    			String titre = sc.next(); 
		    			u.sendRequest(titre, US);
	    			}
	    			else if (choix.equals("FEEDBACK")) {
	    				System.out.println("Choisissez la requête que vous voulez évaluez (ID) ");
		    			int id = sc.nextInt(); 
		    			System.out.println("Entrez votre feedback: ");
		    			String fb = sc.next(); 
		    			u.sendFeedback(id, fb, US);
	    			}
					else if (choix.equals("exit")) {
						 running=false;    				
						    			}
	    			else {
	    				System.out.println("Erreur. Recommencez"); 
	    			}
	    			break; 
	    			
	    			
	    		case "val" : //Actions Validateur 
	    			System.out.println("Choisir la requête à valider"); 
	    			String id = sc.next();
	    			
	    			if (id.equals("exit")) { //quitter le programme 
						 running=false;    				
	    			}
	    			
	    			else  {
	    				System.out.println("Voulez vous valider la requête ? (oui/non)"); 
		    			String choix3 = sc.next(); 
		    			if (choix3.equals("oui")) {
			    			v.Valid_Request(Integer.parseInt(id), VAL); 
		    			}
		    			else if (choix3.equals("non")) {
		    				sc.nextLine(); 
		    				System.out.println("Entrer Motif ");  
			    			String m = sc.nextLine(); 
		    				v.Refuse_Request(Integer.parseInt(id),m, VAL); 
		    			}
		    			else {
		    				System.out.println("Erreur. Recommencez"); 
		    			}
	    			}
	    			break; 
	    			
	    		default: 
	    				break; 
    		}
    	}
		
    	Requests.printAllRequests();
    	
		db.deleteTable("DROP TABLE Benevole");
		db.deleteTable("DROP TABLE User" );
		db.deleteTable("DROP TABLE Valideur");
		db.deleteTable("DROP TABLE Request"); 
		db.disconnect();
    	
    
    	

    }
}