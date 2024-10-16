package User_Manip;

public class User {
	private String nom;
    private String prenom;
    private int age;
    private int identifiant;
    public static int Nb_User=0;

    public User(String nom , String prenom, int age,User_Control user_control){
        this.nom=nom;
        this.prenom=prenom;
        this.age=age;
        this.identifiant=Nb_User+1;
        Nb_User ++;
        user_control.Insertion(this);
    }

    
    public User(int id,String nom , String prenom, int age,User_Control user_control){
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

    public void setAge(int age) {
        this.age = age;
    }

    public void setIdentifiant(int identifiant) {
        this.identifiant = identifiant;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public static int getNb_User() {
        return Nb_User;
    }

    public int getIdentifiant() {
        return identifiant;
    }
    
    public void Send_Request(){
    	
    }

    public void Send_Feedback(){
    	
    }
    
    public String toString() {
    	return "ID: " + identifiant + ", nom: " + nom +", prenom: "+prenom+ ", Age: " + age;
    }
    
    
}
