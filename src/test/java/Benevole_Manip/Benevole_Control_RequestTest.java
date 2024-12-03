package Benevole_Manip;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import Database_Manip.Database_Control;
import User_Manip.User;
import User_Manip.User_Control;
import request.RequestManager;

class Benevole_Control_RequestTest {

	@Test
	void test() {
		String host="srv-bdens.insa-toulouse.fr";
		int port=3306;
	    String database="projet_gei_024";
	    String username="projet_gei_024";
	    String password="Zai6Xoo7";
	    try {
	    	System.out.println("---------Database connexion trial-------");
			Database_Control db =new Database_Control(host, port, database, username, password);
			db.connect();
			System.out.println("---------Database create table trial-------");
			User_Control UC= new User_Control(db);
			RequestManager RM=new RequestManager(db);
			Benevole_Control BC=new Benevole_Control(db);
			RM.createRequestsTable();
			System.out.println("---------Database add trial-------------");
			User u1=new User("AA","aa",52,UC);
			User u2=new User("BB","bb",58,UC);
			User u3=new User("CC","cc",60,UC);
			Benevole b1=new Benevole("Titi","toto",25,BC);
			Benevole b2=new Benevole("Didi","dodo",21,BC);
			System.out.println("---------Database add request trial-------------");
			u1.sendRequest("Achat", UC);
			u1.sendRequest("Bouger", UC);
			u2.sendRequest("Bouger", UC);
			u3.sendRequest("Accompagner", UC);
			System.out.println("---------Database volunteer consult request trial-------------");
			b1.consultAllRequest(BC);
			System.out.println("---------Database volunteer accept request trial-------------");
			b1.Accept_Request(1, BC);
			b2.Accept_Request(2, BC);
			b1.Accept_Request(2, BC);
			b1.consultAllRequest(BC);
			System.out.println("---------Database volunteer finish trial-------------");
			b1.finishRequest(1, BC);
			b1.finishRequest(2, BC);
			b2.finishRequest(2, BC);
			b2.consultAllRequest(BC);
			System.out.println("---------Database delete table trial-------");
			db.deleteTable("DROP TABLE User");
			db.deleteTable("DROP TABLE Benevole");
			db.deleteTable("DROP TABLE Request");
			db.disconnect();
	    }catch(Exception e) {
	    	e.getStackTrace();
	    }
	}

}


