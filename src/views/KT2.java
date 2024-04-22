package views;

import java.time.LocalDate;

import app.AppState;
import database.Database;
import models.Admin;
import models.Guest;
import models.Maid;
import models.Receptionist;
import models.RoomType;
import models.enums.Gender;

public class KT2 {

	public KT2() {
		Database db = AppState.getInstance().getDatabase();
		
		Admin admin = new Admin("Pera", "Peric", Gender.MALE, LocalDate.of(1971, 3, 17), "0641234567", "Bulevar Oslobođenja 17, Novi Sad", "pera", "pera123", "Master", 5, 140_000);	
	    db.getAdmins().Insert(admin);
	    System.out.println("Dodat administratos: " + admin.getName() + " " + admin.getSurname() + " - " + admin.getId());
	    
	    Receptionist recepcioner1 = new Receptionist("Mika", "Mikić", Gender.MALE, LocalDate.of(1975, 6, 1), "+381628361185", "Bulevar despota Stefana 7, Novi Sad", "mika", "Mika123", "Master", 1, 100_000);
		Receptionist recepcioner2 = new Receptionist("Nikola", "Nikolić", Gender.MALE, LocalDate.of(1980, 9, 17), "+381612599941", "Maksima Gorkog 12, Novi Sad", "nikola", "Nikola123", "Master", 3, 110_000);
		db.getReceptionists().Insert(recepcioner1);
		db.getReceptionists().Insert(recepcioner2);
		System.out.println(admin.getName() +" je dodao recepcionera: " + recepcioner1.getName() + " " + recepcioner1.getSurname() + " - " + recepcioner1.getId());
		System.out.println(admin.getName() +" je dodao recepcionera: " + recepcioner2.getName() + " " + recepcioner2.getSurname() + " - " + recepcioner2.getId());
		
		Maid sobarica = new Maid("Jana", "Janić", Gender.FEMALE, LocalDate.of(1995, 2, 15), "+381628361185", "Bulevar Jovana Dučića 7, Novi Sad", "jana", "Jana123", "Viša škola", 1, 75_000);
		db.getMaids().Insert(sobarica);
		System.out.println(admin.getName() +" je dodao sobaricu: " + sobarica.getName() + " " + sobarica.getSurname() + " - " + sobarica.getId());
		
		System.out.println("\n\nSvi zaposleni:\n");
		int count = 0;
		for (Admin a : db.getAdmins().getRows()) {
			count++;
			System.out.println(count + ". Admininstrator: "  + a.getName() + " " + a.getSurname() + " - " + a.getId());
		}
		for (Receptionist r : db.getReceptionists().getRows()) {
			count++;
			System.out.println(count + ". Recepcioner: " + r.getName() + " " + r.getSurname() + " - " + r.getId());
		}
		for (Maid m : db.getMaids().getRows()) {
			count++;
			System.out.println(count + ". Sobarica: " + m.getName() + " " + m.getSurname() + " - " + m.getId());
		}
		
		db.getReceptionists().DeleteById(recepcioner2.getId());
		System.out.println(admin.getName() +" je obrisao recepcionera: " + recepcioner2.getName() + " " + recepcioner2.getSurname() + " - " + recepcioner2.getId());
		
		Guest gost1 = new Guest("Milica", "Milić", Gender.FEMALE, LocalDate.of(1990, 5, 25), "+381628361185", "Cara Dušana 7, Kraljevo", "milica", "Milica123");
		Guest gost2 = new Guest("Ana", "Anić", Gender.FEMALE, LocalDate.of(1992, 8, 10), "+381628361185", "Bulevar Kralja Aleksandra 20, Beograd", "ana", "Ana123");
		db.getGuests().Insert(gost1);
		db.getGuests().Insert(gost2);
		
		System.out.println(recepcioner1.getName() +" je dodao gosta: " + gost1.getName() + " " + gost1.getSurname() + " - " + gost1.getId());
		System.out.println(recepcioner1.getName() +" je dodao gosta: " + gost2.getName() + " " + gost2.getSurname() + " - " + gost2.getId());
		
		RoomType jednokrevetna = new RoomType("Jednokrevetna");
		RoomType dvokrevetnaSaJednimLezajem = new RoomType("Dvokrevetna sa jednim ležajem");
		RoomType dvokrevetnaSaDvaLezaja = new RoomType("Dvokrevetna sa dva ležaja");
		RoomType trokrevetnaSaTriLezaja = new RoomType("Trokrevetna sa tri ležaja");
		RoomType trokrevetnaSaDvaLezaja = new RoomType("Trokrevetna sa dva ležaja");
		RoomType apartman = new RoomType("Apartman");
		RoomType penthouse = new RoomType("Penthouse");
		
		db.getRoomTypes().Insert(jednokrevetna);
		db.getRoomTypes().Insert(dvokrevetnaSaJednimLezajem);
		db.getRoomTypes().Insert(dvokrevetnaSaDvaLezaja);
		db.getRoomTypes().Insert(trokrevetnaSaTriLezaja);
		db.getRoomTypes().Insert(trokrevetnaSaDvaLezaja);
		db.getRoomTypes().Insert(apartman);
		db.getRoomTypes().Insert(penthouse);
		
		System.out.println("\n\nSvi tipovi soba:\n");
		count = 0;
		for (RoomType rt : db.getRoomTypes().getRows()) {
			count++;
			System.out.println(count + ". " + rt.getName() + " - " + rt.getId());
		}
		
		
	}
}
