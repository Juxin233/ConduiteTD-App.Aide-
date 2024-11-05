package Benevole_Manip; 
import Database_Manip.Database_Control;


public class Benevole_Control {

 private Database_Control DB;

 

 public Benevole_Control(Database_Control db){

 this.DB=db;

 DB.createTable("CREATE TABLE Benevole ("

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
 
 

 

}