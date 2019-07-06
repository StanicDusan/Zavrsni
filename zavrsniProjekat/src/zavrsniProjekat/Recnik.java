package zavrsniProjekat;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class Recnik {
	private HashMap<String,String> mapa;
	private Connection konekcija;
	
	public Recnik() {
		this.mapa=new HashMap<>();
		try {
			konekcija=DriverManager.getConnection("jdbc:sqlite:Dictionary.db");
			upisUMapu(this.mapa);
		} catch (SQLException e) {
		
			e.printStackTrace();
		}
	}
	
	private void upisUMapu(HashMap<String,String> mapa) throws SQLException {
		
		java.sql.Statement statement = this.konekcija.createStatement();
		statement.execute("select word,definition from entries");
		ResultSet results =statement.getResultSet();
		while(results.next()) {
			mapa.put(results.getString("word").toLowerCase(), results.getString("definition"));
		}
		results.close();
	}
	
	public HashMap<String,String> getHashMap(){
		return this.mapa;
	}

}
