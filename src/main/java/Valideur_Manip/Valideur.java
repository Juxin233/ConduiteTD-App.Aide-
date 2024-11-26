package Valideur_Manip ;

import request.Request;

public class Valideur {

	private String nom;
	private String prenom;
	//pas besoin de l'age pour le valideur 
	private int identifiant;
	public static int Nb_Val=0;
	
	
	
	public Valideur(String nom , String prenom,Valideur_Control val_control){
	
		this.nom=nom;
	
		this.prenom=prenom;
	
		this.identifiant=Nb_Val+1;
	
		Nb_Val ++;
	
		val_control.Insertion(this);
	
	}
	
	 
	
	public String getNom() {
		return nom;
	}
	
	 
	
	public String getPrenom() {
		return prenom;
	}
	
	
	
	
	public static int getNb_Val() {
	
		return Nb_Val;
	
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
	
	 
	 
	
	public void Valid_Request(){
	
	 //change the state of the request from pending to validated  
	
	}
	
}
	



