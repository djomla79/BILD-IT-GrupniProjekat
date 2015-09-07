package uvod;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.InputMismatchException;
import java.util.Scanner;

public class WorldCities {

public static void main(String[] args) throws ClassNotFoundException, SQLException {
	
	/** Pozivanje metode */
	startPretrage(); 

}
    /** Metoda za pokretanje pretrage */
	public static void startPretrage() throws ClassNotFoundException, SQLException {
		
		Scanner input = new Scanner(System.in);
		
		boolean isOn = true;
		
		do {
			try {
				
				System.out.print("Za pretragu gradova unesite '1'\nZa pretragu drzava  unesite '2'\nUnos: ");
				
				int broj = input.nextInt();
				
				while(broj <= 0 || broj > 2) {
					System.out.println("Pogresan unos!");
					System.out.print("Za pretragu gradova unesite '1'\nZa pretragu drzava  unesite '2'\nUnos: ");
					broj = input.nextInt();
				}
				
				if(broj == 1) {  // ako je unos 1
					grad();      // pozivanje metode za pretragu gradova iz baze
				} else {         // ako je unos 2
					drzava();    // pozivanje metode za pretragu drzave iz baze
				}
				isOn = false;    // uslov ispunjen, petlja prekida sa radom
				
			} catch(InputMismatchException ex) { // 'hvatanje' i ispis greske
				System.out.println("Greska, pogresan unos!");
				input.nextLine();
			}
			
		} while(isOn);               // petlja radi dok se uslov ne ispuni
		
		input.close();
		
	}
    /** Metoda za pretragu gradova u bazi podataka */
	public static void grad() throws ClassNotFoundException, SQLException {
		
		Scanner input = new Scanner(System.in);
		
		int brojac = 0;
		
		/** Ucitavanje drajvera */
		Class.forName("com.mysql.jdbc.Driver");
		
		/** Povezivanje sa bazom */
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost/world", "root", "");
		System.out.println("Drajver i baza ucitani!\n");
		
		/** Kreiranje objekta statement */
		Statement st = con.createStatement();
		
		/** Unos za pretragu */
		System.out.print("Pretraga/(unos grada): ");
		String unos = input.nextLine();
		
		/** Kreiranje objekta ResultSet i
		 * pozivanje metode za pretragu iz baze */
		ResultSet res = st.executeQuery(" SELECT * FROM city WHERE Name='" + unos + "'");
		
		/** Petlja radi dok u bazi postoji unos */
		while(res.next()) {
			
			brojac++;
			
			System.out.println("\nID Broj: " + res.getString("ID")); 
			System.out.println("Grad: " + res.getString("Name"));
			System.out.println("Drzava: " + res.getString("CountryCode"));
			System.out.println("Distrikt/Regija: " + res.getString("District"));
			System.out.println("Stanovnistvo: " + res.getString("Population") + "\n");
			
		}
		/** Ako ne postoji unos, ispis poruke */
		if(brojac == 0) {
			
			System.out.println("Unos " + unos + " nije pronadjen!");
		}
		
		con.close();
		
		input.close();
		
}
	/** Metoda za pretragu drzava u bazi podataka */
	public static void drzava() throws ClassNotFoundException, SQLException {
		
		Scanner input = new Scanner(System.in);
		
		int brojac = 0;
		
		/** Ucitavanje drajvera */
		Class.forName("com.mysql.jdbc.Driver");
		
		/** Povezivanje sa bazom */
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost/world", "root", "");
		System.out.println("Drajver i baza ucitani!\n");
		
		/** Kreiranje objekta statement */
		Statement st = con.createStatement();
		
		/** Unos za pretragu */
		System.out.print("Pretraga/(unos drzave): ");
		String unos = input.nextLine();
		
		/** Kreiranje objekta ResultSet i
		 * pozivanje metode za pretragu iz baze */
		ResultSet res = st.executeQuery("SELECT * FROM country WHERE Name='" + unos + "'");
		
		/** Petlja radi dok u bazi postoji unos */
		while(res.next()) {
			
			brojac++;
			
			System.out.println("\nDrzava: " + res.getString("Name")); 
			System.out.println("Kontinent: " + res.getString("Continent")); 
			System.out.println("Stanovnistvo: " + res.getString("Population") + "\n");
			
		} 
		/** Ako ne postoji unos, ispis poruke */
		if(brojac == 0) {
			
			System.out.println("Unos " + unos + " nije pronadjen!");
		}
		
		con.close();
		
		input.close();
		
	}
}
