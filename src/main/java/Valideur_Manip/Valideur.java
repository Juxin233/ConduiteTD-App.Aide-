package Valideur_Manip ;

import User_Manip.User_Control;
import request.Request;

public class Valideur {

	private String nom;
	private String prenom;
	//pas besoin de l'age pour le valideur 
	private int identifiant;
	
	
	
	public Valideur(String nom , String prenom,Valideur_Control val_control){
		this.nom=nom;
		this.prenom=prenom;
		val_control.Insertion(this);
		identifiant= val_control.getId(nom, prenom);
	
	}
	
	 
	
	public String getNom() {
		return nom;
	}
	
	 
	
	public String getPrenom() {
		return prenom;
	}
	public String getFullName() {
		return nom+" "+prenom;
	}
	
	public int getIdentifiant() {
	
		return identifiant;
	
	}
	
	
	
	
	public void setIdentifiant(int identifiant) {
	
		this.identifiant = identifiant;
	
	}
	
	
	public void setPrenom(String prenom) {
	
		this.prenom = prenom;
	
	}
	
	 
	 
	
	public void Valid_Request(int id, Valideur_Control V){
	 //change the state of the request from pending to validated  
		V.validRequest(id); 
	}
	
	public void Refuse_Request(int id,String motif, Valideur_Control V){
		 //change the state of the request from pending to Invalid
		V.Motif(id, motif,getPrenom()+getNom());
		V.invalidRequest(id); 
	}
	
	
	
}
	



