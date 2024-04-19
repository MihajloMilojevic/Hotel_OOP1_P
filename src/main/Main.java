package main;

import java.io.IOException;
import java.time.LocalDate;

import app.AppState;
import models.Admin;
import models.enums.Gender;

public class Main {

	public static void main(String[] args) throws IOException {
		AppState appState = AppState.getInstance();
		/*
		ArrayList<String> lines = new ArrayList<String>();
		lines.add("HELLO WORLD");
		lines.add("Č Ć Ž Š Đ");
		lines.add("1234567890");
		lines.add(".,;:!?-+*");
		lines.add("абвфкајфѕђѕцњњиеохцљј");
		Files.write(Path.of("./data/test.txt"), lines, Charset.forName("UTF-8"));
		*/

		
		
		Admin admin = new Admin(
			"Mihajlo", 
			"Milojević", 
			Gender.MALE, 
			LocalDate.of(2004, 5, 31), 
			"+381649781191",
			"Braće Dronjak 6, 21000 Novi Sad",
			"mihajlo",
			"mihajlo",
			"Student",
			2,
			500_000.00
		);
		System.out.println("Inserting admin: " + admin);
		appState
			.getDatabase()
			.getAdmins()
			.Insert(admin);

		appState
            .getDatabase()
            .getAdmins().Save();

		/*
		appState
            .getDatabase()
            .getAdmins().Load();
        */
		for (Admin a : appState.getDatabase().getAdmins().getRows()) {
			System.out.println(a);
		}
	}
}
