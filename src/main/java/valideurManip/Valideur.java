package valideurManip ;

public class Valideur {

	private String nom;
	private String prenom; 
	private int identifiant;
	
	
	
	public Valideur(String nom , String prenom,ValideurControl val_control){
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
	
	public void consultRequest(ValideurControl V) {
		V.consultRequest();
	}
	 
	public boolean consultRequestById(int id, ValideurControl V) {
		return V.consultById(id);
	}
	
	public void validRequest(int id, ValideurControl V){
		V.validRequest(id); 
	}
	
	public void refuseRequest(int id,String motif, ValideurControl V){
		V.Motif(id, motif,getPrenom()+getNom());
		V.invalidRequest(id); 
	}
	
}
	



