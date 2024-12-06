package Benevole_Manip ;
public class Benevole {
	
	private String nom;
	private String prenom;
	private int age;
	private int identifiant;
	
	public Benevole(String nom , String prenom, int age,Benevole_Control ben_control){
		this.nom=nom;
		this.prenom=prenom;
		this.age=age;
		ben_control.Insertion(this);
		this.identifiant=ben_control.getId(nom,prenom);
	}
	
	public Benevole(int id, String nom2, String prenom2, int age2) {
		this.nom=nom2;
		this.prenom=prenom2;		
		this.age=age2;	
		this.identifiant=id;
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
	
	public int getIdentifiant() {
		return identifiant;
	}
	
	public void setAge(int age) {
		this.age = age;
	}
	
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	
	public String toString() {
    	return "ID: " + identifiant + ", nom: " + getNom() +", prenom: "+getPrenom()+ ", Age: " + getAge();
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

}



