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
			Benevole_Control BEN=null;
			User_Control US=null;
			Valideur_Control VAL=null; 
			RequestManager RM=new RequestManager(db);
			//initialisation des utilisateurs 
			Benevole ben=null;
			User u=null;
			Valideur v=null;
	
			//initalisation des infos
			String Nom=""; 
			String Prenom=""; 
			int Age = 0 ; 
	
			
			//Creation des comptes 	
			System.out.println("------------------------------------------------------------------------"); 
			System.out.println("Creation d'un compte"); 
	    	
	    	try (Scanner sc = new Scanner(System.in)) {
				boolean comptecree = false ; 
				System.out.println("Entrez UTILISATEUR , BENEVOLE ou VALIDATEUR. EXIT pour terminer") ; 
				while (!comptecree) {
					String str = sc.nextLine(); 
	   
					System.out.println("Vous avez saisi : " + str);
	
					if (str.equalsIgnoreCase( "BENEVOLE")) {
						BEN = new Benevole_Control(db) ;
						System.out.println("Entrez Nom ") ; 
						Nom = sc.next();
						System.out.println("Entrez Prénom ") ; 
						Prenom= sc.next(); 
						System.out.println("Entrez Age ") ; 
						boolean isValid = false;
					    while (!isValid) {
					        if (sc.hasNextInt()) { 
					            Age = sc.nextInt();
					            isValid = true;
					        } else {
					            System.out.println("Veuillez entrer un nombre entier : ");
					            sc.next();
					        }
					    }
						ben = new Benevole(Nom,Prenom,Age,BEN); 
						BEN.printBen(ben); 	
						comptecree = true ; 
						type ="ben"; 
					}
					
					else if  (str.equalsIgnoreCase( "UTILISATEUR")) {
						US =new User_Control(db); 
						System.out.println("Entrez Nom ") ; 
						Nom = sc.next(); 
						System.out.println("Entrez Prénom ") ; 
						Prenom = sc.next(); 
						System.out.println("Entrez Age ") ; 
						boolean isValid = false;
					    while (!isValid) {
					        if (sc.hasNextInt()) { 
					            Age = sc.nextInt();
					            isValid = true;
					        } else {
					            System.out.println("Veuillez entrer un nombre entier : ");
					            sc.next();
					        }
					    } 
						u = new User(Nom,Prenom,Age,US); 
						US.printUser(u); 	
						comptecree = true ; 
						type="user"; 
					}
					
					else if (str.equalsIgnoreCase("VALIDATEUR")) {
						VAL = new Valideur_Control(db); 
						System.out.println("Entrez Nom ") ; 
						Nom = sc.next(); 
						System.out.println("Entrez Prénom ") ; 
						Prenom = sc.next(); 
						v = new Valideur(Nom,Prenom,VAL); 
						VAL.printVal(v); 	
						comptecree = true ; 
						type = "val"; 
					}
					else if(str.equalsIgnoreCase("EXIT")) {
						return;}
					else {
						System.out.println("Erreur, Re-essayez"); 
					}
				}
  	
				System.out.println("-----------------------------------------------------"); 

		  	boolean running = true ; 
		  	while (running) {
						//pour quitter le programme taper exit 
						switch (type) {
							case "ben" : //Actions bénévole
								System.out.println("Taper CONSULTER|ACCEPTER|TERMINER|EXIT");
								String choix1 = sc.next(); 
								int id1=0;
								if (choix1.equalsIgnoreCase("ACCEPTER")) {
									System.out.println("Choisissez la requete que vous souhaitez effectuer (ID) ");
									boolean isValid = false;
							        while (!isValid) {
							            if (sc.hasNextInt()) { 
							                id1 = sc.nextInt();
							                isValid = true;
							            } else {
							                System.out.println("Veuillez entrer un nombre entier : ");
							                sc.next();
							            }
							        }  
									ben.Accept_Request(id1, BEN);
								}else if(choix1.equalsIgnoreCase("CONSULTER")) {
									ben.consultAllRequest(BEN);
								}
								else if (choix1.equalsIgnoreCase("TERMINER")) {
									System.out.println("Choisissez la requete que vous souhaitez terminer (ID) ");
									boolean isValid = false;
							        while (!isValid) {
							            if (sc.hasNextInt()) { 
							                id1 = sc.nextInt();
							                isValid = true;
							            } else {
							                System.out.println("Veuillez entrer un nombre entier : ");
							                sc.next();
							            }
							        }  
									ben.finishRequest(id1, BEN);
								}
								else if (choix1.equalsIgnoreCase("EXIT")) {
									 running=false;    				
								}
								else {
									System.out.println("Veuillez taper CONSULTER|ACCEPTER|TERMINER|EXIT"); 
								}
								break; 
								
							case "user" : //Actions Utilisateur
								System.out.println("DEMANDER|MAREQUETE|FEEDBACK|EXIT");
								String choix = sc.next(); 
								int id2 =0;
								if (choix.equalsIgnoreCase("DEMANDER")) {
									System.out.println("Choisissez le titre de votre requête: ");
									String titre = sc.next(); 
									u.sendRequest(titre, US);
								}
								else if(choix.equalsIgnoreCase("MAREQUETE")){
									u.myRequest(US);
								}
								else if (choix.equalsIgnoreCase("FEEDBACK")) {
									System.out.println("Choisissez la requête que vous voulez évaluez (ID) ");
									boolean isValid = false;
							        while (!isValid) {
							            if (sc.hasNextInt()) { 
							                id2 = sc.nextInt();
							                isValid = true;
							            } else {
							                System.out.println("Veuillez entrer un nombre entier : ");
							                sc.next();
							            }
							        }   
									System.out.println("Entrez votre feedback: ");
									String fb = sc.next(); 
									u.sendFeedback(id2, fb, US);
								}
								else if (choix.equalsIgnoreCase("EXIT")) {
									 running=false;    				
								}
								else {
									System.out.println("Veuillez taper DEMANDER|MAREQUETE|FEEDBACK|EXIT"); 
								}
								break; 
							case "val" : //Actions Validateur 
								System.out.println("CONSULTER|EXAMINER|EXIT"); 
								String choix2 = sc.next();
								int id3=0;
								if (choix2.equalsIgnoreCase("EXIT")) { //quitter le programme 
									 running=false;    				
								}
								else if(choix2.equalsIgnoreCase("CONSULTER")) {
									v.consultRequest(VAL);
								}
								else if(choix2.equalsIgnoreCase("EXAMINER")){
									System.out.println("Voulez vous (in)valider quelle requête [id]?"); 
									boolean isValid = false;
							        while (!isValid) {
							            if (sc.hasNextInt()) {
							                id3 = sc.nextInt();
							                isValid = true; 
							            } else {
							                System.out.println("Voulez vous (in)valider quelle requête [id]?");
							                sc.next();
							            }
							        }
									if(v.consultRequestById(id3, VAL)) {
								        System.out.println("Voulez vous valider cette requête [oui|non]?"); 
										String choix3 = sc.next(); 
										
										while( !choix3.equalsIgnoreCase("oui") && !choix3.equalsIgnoreCase("non")) {
											System.out.println("Voulez vous valider cette requête [oui|non]?"); 
											choix3 = sc.next(); 
										}
										if (choix3.equalsIgnoreCase("oui")) {
							    			v.Valid_Request(id3, VAL); 
										}
										else if (choix3.equalsIgnoreCase("non")) {
											sc.nextLine(); 
											System.out.println("Entrer Motif ");  
							    			String m = sc.nextLine(); 
											v.Refuse_Request(id3,m, VAL); 
										}
									}
								}else {
									System.out.println("Veuillez taper CONSULTER|EXAMINER|EXIT"); 
								}
								break; 	
							default: 
									break; 
						}
		  		}
		  	
		  	sc.close();
		}
	    	//db.deleteTable("DROP TABLE Benevole");
	    	//db.deleteTable("DROP TABLE Valideur");
	    	//db.deleteTable("DROP TABLE Request");
	    	db.disconnect();
    }
}