package Benevole_Manip;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import benevoleManip.Benevole;
import benevoleManip.BenevoleControl;
import databaseManip.DatabaseControl;
import request.RequestManager;
import userManip.User;
import userManip.UserControl;

class Benevole_Control_RequestTest {

	@Test
	void test() {
		String host="srv-bdens.insa-toulouse.fr";
		int port=3306;
	    String database="projet_gei_024";
	    String username="projet_gei_024";
	    String password="Zai6Xoo7";
	    System.out.println("**Test Benevole Control Request**"); 
	    try {
	    	System.out.println("---------Database connexion trial-------");
			DatabaseControl db =new DatabaseControl(host, port, database, username, password);
			db.connect();
			System.out.println("---------Database create table trial-------");
			UserControl UC= new UserControl(db);
			RequestManager RM=new RequestManager(db);
			BenevoleControl BC=new BenevoleControl(db);
			System.out.println("---------Database add trial-------------");
			User u1=new User("AA","aa",52,UC);
			User u2=new User("BB","bb",58,UC);
			User u3=new User("CC","cc",60,UC);
			Benevole b1=new Benevole("Titi","toto",25,BC);
			Benevole b2=new Benevole("Didi","dodo",21,BC);
			assertTrue(u1.getIdentifiant()!=-1);
			System.out.println("---------Database add request trial-------------");
			int r1=u1.sendRequest("Achat", UC);
			int r2=u1.sendRequest("Bouger", UC);
			int r3=u2.sendRequest("Bouger", UC);
			int r4=u3.sendRequest("Accompagner", UC);
			System.out.println("---------Database volunteer consult request trial-------------");
			b1.consultAllRequest(BC);
			System.out.println("---------Database volunteer accept request trial-------------");
			//RM.updateRequest(r1, 1);
			RM.updateRequest(r2, 1);
			RM.updateRequest(r3, 1);
			RM.updateRequest(r4, 1);
			b1.acceptRequest(r1, BC);
			b2.acceptRequest(r2, BC);
			b1.acceptRequest(r2, BC);
			b1.consultAllRequest(BC);
			System.out.println("---------Database volunteer finish trial-------------");
			b1.finishRequest(r1, BC);
			b1.finishRequest(r2, BC);
			b2.finishRequest(r2, BC);
			b2.consultAllRequest(BC);
			System.out.println("---------Database delete trial-------");
			BC.deleteBenevole(b1.getIdentifiant());
			BC.deleteBenevole(b2.getIdentifiant());
			UC.deleteUser(u1.getIdentifiant());
			UC.deleteUser(u2.getIdentifiant());
			UC.deleteUser(u3.getIdentifiant());
			RM.deleteRequest(r1);
			RM.deleteRequest(r2);
			RM.deleteRequest(r3);
			RM.deleteRequest(r4);
			//db.deleteTable("DROP TABLE User");
			//db.deleteTable("DROP TABLE Benevole");
			//db.deleteTable("DROP TABLE Request");
			db.disconnect();
		    System.out.println(""); 
	    }catch(Exception e) {
	    	e.getStackTrace();
	    }
	}

}


