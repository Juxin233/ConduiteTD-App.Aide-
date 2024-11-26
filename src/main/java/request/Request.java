package request;

public class Request {
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
	
	
}
