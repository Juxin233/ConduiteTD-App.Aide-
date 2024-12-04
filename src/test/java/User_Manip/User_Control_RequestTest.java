package User_Manip;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

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
			db.deleteTable("DROP TABLE User");
			db.deleteTable("DROP TABLE Request");
			User_Control UC= new User_Control(db);
			RequestManager RM=new RequestManager(db);
			RM.createRequestsTable();
			System.out.println("---------Database add trial-------------");
			User u1=new User("AA","aa",52,UC);
			User u2=new User("BB","bb",58,UC);
			User u3=new User("CC","cc",60,UC);
			System.out.println("---------Database add request trial-------------");
			u1.sendRequest("Achat", UC);
			u1.sendRequest("Bouger", UC);
			u2.sendRequest("Bouger", UC);
			u3.sendRequest("Accompagner", UC);
			assertTrue(RM.consultRequestById(1).get().getTitre().equals("Achat"),"Insertion not correct");
			assertTrue(RM.consultRequestById(2).get().getTitre().equals("Bouger"),"Insertion not correct");
			//System.out.println(RM.consultRequestById(3).get().getUser());
			assertTrue(RM.consultRequestById(3).get().getUser().equals(u2.getFullName()),"Insertion not correct");
			assertTrue(RM.consultRequestById(4).get().getBenevole().equals("Not assigned"),"Insertion not correct");
			System.out.println("---------Database consult request trial-------------");
			u2.myRequest(UC);
			u1.myRequest(UC);
			u3.myRequest(UC);
			System.out.println("---------Database add feedback trial-------------");
			u1.sendFeedback(1, "BIEN", UC);
			u1.sendFeedback(2, "MOYEN", UC);
			u1.sendFeedback(3, "TRESBIEN", UC);
			u3.sendFeedback(4, "TRESBIEN", UC);
			u1.myRequest(UC);
			u2.myRequest(UC);
			u3.myRequest(UC);
			assertTrue(RM.consultRequestById(1).get().getFeedback().equals("BIEN"),"Insertion not correct");
			assertFalse(RM.consultRequestById(2).get().getFeedback().equals("moyen"),"Insertion not correct");
			System.out.println(RM.consultRequestById(3).get().getFeedback());
			System.out.println(RM.consultRequestById(4).get().getFeedback());
			assertTrue(RM.consultRequestById(4).get().getFeedback().equals("TRESBIEN"),"Insertion not correct");
			System.out.println("---------Database delete table trial-------");
			db.deleteTable("DROP TABLE User");
			db.deleteTable("DROP TABLE Request");
			db.disconnect();
		    System.out.println(""); 
	    }catch(Exception e) {
	    	e.getStackTrace();
	    }
	}

}
