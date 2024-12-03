package Benevole_Manip ;
public class Benevole {

private String nom;
private String prenom;
private int age;
private int identifiant;
public static int Nb_User=0;



public Benevole(String nom , String prenom, int age,Benevole_Control ben_control){

this.nom=nom;

this.prenom=prenom;

this.age=age;

this.identifiant=Nb_User+1;

Nb_User ++;

ben_control.Insertion(this);

}

 

public String getNom() {
return nom;
}

 

public String getPrenom() {
return prenom;
}

public String getFullName() {
	return getNom() + " "+ getPrenom();
}

public int getAge() {
return age;
}


public static int getNb_User() {

return Nb_User;

}



public int getIdentifiant() {

return identifiant;

}


 

public void setAge(int age) {

this.age = age;

}

 

public void setIdentifiant(int identifiant) {

this.identifiant = identifiant;

}


public void setPrenom(String prenom) {

this.prenom = prenom;

}


 
public void consultAllRequest(Benevole_Control BC) {
	//les bénévoles peuvent conculter toutes les demandes
	BC.consultRequest();
}

public void setNom(String nom) {

this.nom = nom;

}
 

public void Accept_Request(int requestID,Benevole_Control BC){

 //accept a request 
	BC.acceptRequest(requestID, getFullName());
}

public void finishRequest(int id,Benevole_Control BC) {
	BC.finishRequest(id,getFullName());
}

public void AddSpontaneousRequest () {
	// les bénévoles peuvent proposer des aides spontanées (sujet) 
}

}
 
//besoin de feedback ?? 



