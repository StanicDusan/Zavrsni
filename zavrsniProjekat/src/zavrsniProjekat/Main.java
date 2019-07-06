package zavrsniProjekat;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.text.Collator;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;

public class Main {

	public static void main(String[] args) {
		long timestamp = System.currentTimeMillis();
		try {
			Konekcija kon = new Konekcija();

			Knjiga k1 = new Knjiga("knjiga");
			k1.reci.remove("");

			Recnik r = new Recnik();

			LinkedList<String> reciKojeFale = new LinkedList<>();

			for (String a : k1.reci.keySet()) {
				if (r.getHashMap().containsKey(a.toLowerCase()))
					continue;
				else
					reciKojeFale.add(a);

			}
			System.out.println("ukupno " + reciKojeFale.size());

			k1.upisNovihReci(reciKojeFale, kon);
			k1.najcesceReci(k1.reci);
//sve reci=recnik+knjiga unikati
			LinkedList<String> sveReci = sveReci(r.getHashMap(), reciKojeFale);

//upis reci u fajl
			upisUfajlSvihReci(sveReci, "reciSve");

			kon.con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
//		System.out.println("Ukupno vreme: " + (System.currentTimeMillis() - timestamp));
	}

//pravi listu od svih reci iz knjige i recnika
	public static LinkedList<String> sveReci(HashMap<String, String> mapar, LinkedList<String> reciIzKnjige) {
		LinkedList<String> sve = new LinkedList<>();
		sve = (LinkedList<String>) reciIzKnjige.clone();
		for (String a : mapar.keySet()) {
			sve.add(a);
		}
		return sve;

	}

	public static void upisUfajlSvihReci(LinkedList<String> lista, String fajl) {
		try {
			Collections.sort(lista, Collator.getInstance());
			FileWriter fw = new FileWriter(fajl);
			for (int i = 0; i < lista.size(); i++) {
				if (i == lista.size() - 1) {
					fw.write(lista.get(i));
					continue;
				}
				fw.write(lista.get(i) + "\n");
			}

			fw.flush();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
