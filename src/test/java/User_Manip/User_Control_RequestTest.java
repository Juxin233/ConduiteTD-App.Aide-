package User_Manip;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import Database_Manip.Database_Control;
import request.*;
class User_Control_RequestTest {

	@Test
	void test() {
		String host="srv-bdens.insa-toulouse.fr";
		int port=3306;
	    String database="projet_gei_024";
	    String username="projet_gei_024";
	    String password="Zai6Xoo7";
	    try {
		    System.out.println("**Test User Control Request**"); 
	    	System.out.println("---------Database connexion trial-------");
			Database_Control db =new Database_Control(host, port, database, username, password);
			db.connect();
			System.out.println("---------Database create table trial-------");
			//db.deleteTable("DROP TABLE User");
			//db.deleteTable("DROP TABLE Request");
			User_Control UC= new User_Control(db);
			RequestManager RM=new RequestManager(db);
			System.out.println("---------Database add trial-------------");
			User u1=new User("AA","aa",52,UC);
			User u2=new User("BB","bb",58,UC);
			User u3=new User("CC","cc",60,UC);
			System.out.println("---------Database add request trial-------------");
			int r1=u1.sendRequest("Achat", UC);
			int r2=u1.sendRequest("Bouger", UC);
			int r3=u2.sendRequest("Bouger", UC);
			int r4=u3.sendRequest("Accompagner", UC);
			assertTrue(RM.consultRequestById(r1).get().getTitre().equals("Achat"),"Insertion not correct");
			assertTrue(RM.consultRequestById(r2).get().getTitre().equals("Bouger"),"Insertion not correct");
			assertTrue(RM.consultRequestById(r3).get().getUser().equals(u2.getFullName()),"Insertion not correct");
			assertTrue(RM.consultRequestById(r4).get().getBenevole().equals("Not assigned"),"Insertion not correct");
			System.out.println("---------Database consult request trial-------------");
			u2.myRequest(UC);
			u1.myRequest(UC);
			u3.myRequest(UC);
			System.out.println("---------Database add feedback trial-------------");
			u1.sendFeedback(r1, "BIEN", UC);
			u1.sendFeedback(r2, "MOYEN", UC);
			u1.sendFeedback(r3, "TRESBIEN", UC);
			u3.sendFeedback(r4, "TRESBIEN", UC);
			u1.myRequest(UC);
			u2.myRequest(UC);
			u3.myRequest(UC);
			assertTrue(RM.consultRequestById(r1).get().getFeedback().equals("BIEN"),"Insertion not correct");
			assertFalse(RM.consultRequestById(r2).get().getFeedback().equals("moyen"),"Insertion not correct");
			assertTrue(RM.consultRequestById(r4).get().getFeedback().equals("TRESBIEN"),"Insertion not correct");
			System.out.println("---------Database delete table trial-------");
			UC.deleteUser(u1.getIdentifiant());
			UC.deleteUser(u2.getIdentifiant());
			UC.deleteUser(u3.getIdentifiant());
			RM.deleteRequest(r1);
			RM.deleteRequest(r2);
			RM.deleteRequest(r3);
			RM.deleteRequest(r4);
			//db.deleteTable("DROP TABLE User");
			//db.deleteTable("DROP TABLE Request");
			db.disconnect();
		    System.out.println(""); 
	    }catch(Exception e) {
	    	e.getStackTrace();
	    }
	}

}
