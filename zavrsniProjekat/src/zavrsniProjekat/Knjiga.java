package zavrsniProjekat;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;

public class Knjiga {
	private String nazivKnjige;
	//// public umesto getera
	public HashMap<String, Integer> reci;

	public Knjiga(String fajl) {
		this.reci = new HashMap<>();
		upisUhashMapu(fajl);

	}

	private void upisUhashMapu(String fajl) {
		BufferedReader r;
		try {
			r = new BufferedReader(new FileReader(fajl));

			String line = r.readLine();
			while (line != null) {
				String[] niz = line.split("\\P{Alpha}+");

				for (int i = 0; i < niz.length; i++) {

					Integer br = reci.get(niz[i].toLowerCase());

					if (br != null)
						reci.put(niz[i].toLowerCase(), br + 1);
					else
						reci.put(niz[i].toLowerCase(), 1);

				}

				line = r.readLine();

			}

		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

// kreiranje nove tabele u bazi i insertovanje reci kojih nema u recniku
	public void upisNovihReci(LinkedList<String> reci, Konekcija con) throws SQLException {
		Statement statement = con.con.createStatement();
		statement.execute("CREATE TABLE IF NOT EXISTS NoveReci (word TEXT)");
		String trenutna = "";
		int indeks = 0;
		for (int i = 0; i < reci.size(); i++) {
			if (indeks == 49) {
				statement.execute("INSERT INTO NoveReci(word) values " + trenutna);
				indeks = 0;
				trenutna = "";

			}
			if (indeks == 48 || i == reci.size() - 1) {
				trenutna += "('" + reci.get(i) + "')";
				indeks++;
			} else {
				trenutna += "('" + reci.get(i) + "'),\n";
				indeks++;
			}

		}

		statement.execute("INSERT INTO NoveReci(word) VALUES " + trenutna);

	}

//20 najcescih reci u knjizi
	public void najcesceReci(HashMap<String, Integer> map) {
		int[] value = new int[map.size()];
		String[] words = new String[map.size()];
		int i = 0;
		for (String a : map.keySet()) {
			value[i] = map.get(a);
			words[i] = a;
			i++;

		}

		for (int j = 0; j < 20; j++) {
			int max = value[j];
			int pozicija = j;
			String rec = words[j];
			for (int k = j + 1; k < value.length - 1; k++) {
				if (value[k] > max) {
					max = value[k];
					pozicija = k;
					rec = words[k];
				}
			}
			value[pozicija] = value[j];
			words[pozicija] = words[j];
			value[j] = max;
			words[j] = rec;

		}
		for (int j = 0; j < 20; j++) {
			System.out.println("\"" + words[j] + "\"" + " se pojavljuje: " + value[j]);
		}

	}

}
