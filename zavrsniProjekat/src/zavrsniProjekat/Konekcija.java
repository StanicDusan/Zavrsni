package zavrsniProjekat;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Konekcija {
	
	private String baza="Dictionary.db";
	public Connection con;
	public Konekcija() throws SQLException {
		this.con=DriverManager.getConnection("jdbc:sqlite:" + baza);
	}
	

}
