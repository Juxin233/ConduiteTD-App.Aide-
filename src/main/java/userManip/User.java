package userManip;



public class User {
	private String nom;
    private String prenom;
    private int age;
    private int identifiant;

    
    public User(String nom , String prenom, int age,UserControl user_control){
        this.nom=nom;
        this.prenom=prenom;
        this.age=age;
        user_control.Insertion(this);
        this.identifiant=user_control.getId(nom,prenom);
    }

    
    public User(int id,String nom , String prenom, int age){
        this.nom=nom;
        this.prenom=prenom;
        this.age=age;
        this.identifiant=id;
    }
    
    
    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public int getAge() {
        return age;
    }
    
    public String getFullName() {
    	return getNom()+" "+getPrenom();
    }
    public void setAge(int age) {
        this.age = age;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public int getIdentifiant() {
        return identifiant;
    }
    
    public String toString() {
    	return "ID: " + identifiant + ", nom: " + nom +", prenom: "+prenom+ ", Age: " + age;
    }
    
    public void myRequest(UserControl UC) {
    	UC.myRequest(getFullName());
    }
    
    public int sendRequest(String titre,UserControl UC) {
    	return UC.sendRequest(titre, getFullName() );
    }
    
    public void sendFeedback(int requestID,String feedback,UserControl UC) {
    	UC.sendFeedback(requestID, feedback,getFullName());
    }
    
}
