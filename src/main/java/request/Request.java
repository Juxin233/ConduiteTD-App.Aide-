package request;

public class Request {
	private int id;
	private String titre;
	private String etat;
	private String motif;
	private String feedback;
	private String user;
	private String benevole;
	
	public Request(String titre,String etat,String motif,String feedback,String user,String benevole) {
		this.titre= titre;
		this.etat=etat;
		this.motif=motif;
		this.feedback=feedback;
		this.user=user;
		this.benevole=benevole;
	}
	
	public Request(int id,String titre,String etat,String motif,String feedback,String user,String benevole) {
		this.id=id;
		this.titre= titre;
		this.etat=etat;
		this.motif=motif;
		this.feedback=feedback;
		this.user=user;
		this.benevole=benevole;
	}

	public String toString() {
    	return "ID: "+String.valueOf(id)+", Titre: " + titre + ", etat: " + etat +", motif: "+motif+ ", feedback: " + feedback+ ", User: "+ user + ", benevole: "+benevole;
    }

}
