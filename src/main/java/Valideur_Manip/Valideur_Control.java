package Valideur_Manip; 
import Database_Manip.Database_Control;


public class Valideur_Control {

 private Database_Control DB;

 

 public Valideur_Control(Database_Control db){

 this.DB=db;

 DB.createTable("CREATE TABLE Valideur ("

 + "id INT AUTO_INCREMENT PRIMARY KEY, "

 + "nom VARCHAR(50), "

 + "prenom VARCHAR(50) ) ");

 }

 

 public void Insertion(Valideur val) {

 String query= "INSERT INTO Valideur (nom, prenom) VALUES "+"(" + "'" +val.getNom()+ "'"+ " , " +"'"+val.getPrenom()+"'" +" )";
 System.out.println(query);
 DB.insertData(query);

 }
 
 public void printVal(Valideur val) {
	  System.out.println(val.getNom() + " " + val.getPrenom());
}

 
 

 

}