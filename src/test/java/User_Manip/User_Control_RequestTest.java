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
			System.out.println("---------Database consult request trial-------------");
			u1.myRequest(UC);
			u2.myRequest(UC);
			u3.myRequest(UC);
			System.out.println("---------Database add motivation trial-------------");
			u1.sendMotif(1, "ALED1", UC);
			u1.sendMotif(2, "ALED2", UC);
			u1.sendMotif(3, "ALED3", UC);
			u3.sendMotif(4, "ALED4", UC);
			u1.myRequest(UC);
			u2.myRequest(UC);
			u3.myRequest(UC);
			System.out.println("---------Database add feedback trial-------------");
			u1.sendFeedback(1, "BIEN", UC);
			u1.sendFeedback(2, "MOYEN", UC);
			u1.sendFeedback(3, "TRESBIEN", UC);
			u3.sendFeedback(4, "TRESBIEN", UC);
			u1.myRequest(UC);
			u2.myRequest(UC);
			u3.myRequest(UC);
			System.out.println("---------Database delete table trial-------");
			db.deleteTable("DROP TABLE User");
			db.disconnect();
	    }catch(Exception e) {
	    	e.getStackTrace();
	    }
	}

}
