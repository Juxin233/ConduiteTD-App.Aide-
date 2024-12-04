package User_Manip;



public class User {
	private String nom;
    private String prenom;
    private int age;
    private int identifiant;

    
    public User(String nom , String prenom, int age,User_Control user_control){
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
    
    public void myRequest(User_Control UC) {
    	UC.myRequest(getFullName());
    }
    public void sendRequest(String titre,User_Control UC) {
    	UC.sendRequest(titre, getFullName() );
    }
    
    public void sendFeedback(int requestID,String feedback,User_Control UC) {
    	UC.sendFeedback(requestID, feedback,getFullName());
    }
    
    //public void sendMotif(int requestID,String motif,User_Control UC) {
    //	UC.sendMotif(requestID, motif,getFullName());
    //}
}
