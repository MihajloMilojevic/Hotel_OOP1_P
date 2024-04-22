package views;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

import app.AppState;
import database.Database;
import database.SelectCondition;
import models.Admin;
import models.Guest;
import models.Maid;
import models.Model;
import models.PriceList;
import models.Receptionist;
import models.Reservation;
import models.ReservationAddition;
import models.Room;
import models.RoomType;
import models.enums.Gender;

public class KT2 {

	public KT2() {
		AppState appState = AppState.getInstance();
		try {
			appState.getSettings().Load();
			System.out.println(appState.getSettings().getSetting("database", "admins_file_path", "Greska"));
			System.out.println("Podesavanja aplikacije ucitana.\n");
		} catch (IOException e) {
			System.err.println("Greska prilikom ucitavanja podesavanja aplikacije");
			return;
		}
		Database db = appState.getDatabase();
		
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
		System.out.println();
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
		
		Room room101 = new Room(101, jednokrevetna);
		Room room102 = new Room(102, dvokrevetnaSaJednimLezajem);
		Room room103 = new Room(103, dvokrevetnaSaDvaLezaja);
		Room room104 = new Room(104, trokrevetnaSaTriLezaja);
		Room room105 = new Room(105, trokrevetnaSaDvaLezaja);
		Room room106 = new Room(106, apartman);
		Room room107 = new Room(107, penthouse);
		
		Room room201 = new Room(201, jednokrevetna);
		Room room202 = new Room(202, dvokrevetnaSaJednimLezajem);
		Room room203 = new Room(203, dvokrevetnaSaDvaLezaja);
		Room room204 = new Room(204, trokrevetnaSaTriLezaja);
		Room room205 = new Room(205, trokrevetnaSaDvaLezaja);
		Room room206 = new Room(206, apartman);
		Room room207 = new Room(207, penthouse);
		
		db.getRooms().Insert(room101);
		db.getRooms().Insert(room102);
		db.getRooms().Insert(room103);
		db.getRooms().Insert(room104);
		db.getRooms().Insert(room105);
		db.getRooms().Insert(room106);
		db.getRooms().Insert(room107);
		
		db.getRooms().Insert(room201);
		db.getRooms().Insert(room202);
		db.getRooms().Insert(room203);
		db.getRooms().Insert(room204);
		db.getRooms().Insert(room205);
		db.getRooms().Insert(room206);
		db.getRooms().Insert(room207);
		
		System.out.println("\n\nSve sobe:\n");
		count = 0;
		for (Room r : db.getRooms().getRows()) {
			count++;
			System.out.println(
					count + ". Soba broj " + r.getNumber() + " " + r.getType().getName() + " - " + r.getId());
		}
		room101.setType(trokrevetnaSaDvaLezaja);
		System.out.println("\n\nSoba broj 101 je promenjena u tip: " + room101.getType().getName());
		
		ReservationAddition dorucak = new ReservationAddition("Doručak");
		ReservationAddition rucak = new ReservationAddition("Ručak");
		ReservationAddition vecera = new ReservationAddition("Večera");
		ReservationAddition bazen = new ReservationAddition("Bazen");
		ReservationAddition spa = new ReservationAddition("Spa");
		
		db.getReservationAdditions().Insert(dorucak);
		db.getReservationAdditions().Insert(rucak);
		db.getReservationAdditions().Insert(vecera);
		db.getReservationAdditions().Insert(bazen);
		db.getReservationAdditions().Insert(spa);	
		
		System.out.println("\n\nSve dodatne usluge:\n");
		count = 0;
		for (ReservationAddition ra : db.getReservationAdditions().getRows()) {
			count++;
			System.out.println(count + ". " + ra.getName() + " - " + ra.getId());
		}
		
		db.getReservationAdditions().Delete(spa);
		System.out.println("\n\nObrisana dodatna usluga: " + spa.getName());
	
		PriceList cenovnik = new PriceList(LocalDate.of(2024, 1, 1), LocalDate.of(2024, 12, 13));
		cenovnik.getRoomTypePrices().put(jednokrevetna, 2500.0);
		cenovnik.getRoomTypePrices().put(dvokrevetnaSaJednimLezajem, 4000.0);
		cenovnik.getRoomTypePrices().put(dvokrevetnaSaDvaLezaja, 4500.0);
		cenovnik.getRoomTypePrices().put(trokrevetnaSaTriLezaja, 6000.0);
		cenovnik.getRoomTypePrices().put(trokrevetnaSaDvaLezaja, 5500.0);
		cenovnik.getRoomTypePrices().put(apartman, 7000.0);
		cenovnik.getRoomTypePrices().put(penthouse, 10000.0);
		
		cenovnik.getReservationAdditionPrices().put(dorucak, 500.0);
		cenovnik.getReservationAdditionPrices().put(rucak, 700.0);
		cenovnik.getReservationAdditionPrices().put(vecera, 800.0);
		cenovnik.getReservationAdditionPrices().put(bazen, 1000.0);
		
		db.getPriceLists().Insert(cenovnik);
		
		System.out.println("\n\nCenovnik za period od " + DateTimeFormatter.ofPattern("dd.MM.uuuu").format(cenovnik.getStartDate()) + " do " + DateTimeFormatter.ofPattern("dd.MM.uuuu").format(cenovnik.getEndDate()) + ":\n");
		System.out.println("Cene soba:");
		for (RoomType rt : cenovnik.getRoomTypePrices().keySet()) {
			System.out.println(rt.getName() + ": " + cenovnik.getRoomTypePrices().get(rt));
		}
		System.out.println("\nCene dodatnih usluga:");
		for (ReservationAddition ra : cenovnik.getReservationAdditionPrices().keySet()) {
			System.out.println(ra.getName() + ": " + cenovnik.getReservationAdditionPrices().get(ra));
		}
		
		ArrayList<Room> slobodneSobe = GetRooms(LocalDate.of(2024, 8, 1), LocalDate.of(2024, 8, 31));
		System.out.println("\n\nSlobodne sobe u avgustu 2024. godine:\n");
		count = 0;
		for (Room r : slobodneSobe) {
			count++;
			System.out
					.println(count + ". Soba broj " + r.getNumber() + " " + r.getType().getName() + " - " + r.getId());
		}
		
		Reservation rezervacija = new Reservation(room105, gost1, LocalDate.of(2024, 8, 13), LocalDate.of(2024, 8, 23));
		rezervacija.addReservationAddition(dorucak);
		rezervacija.addReservationAddition(vecera);
		double cena = cenovnik.getRoomTypePrices().get(rezervacija.getRoom().getType());
		for (ReservationAddition ra : rezervacija.getReservationAdditions()) {
			cena += cenovnik.getReservationAdditionPrices().get(ra);
		}
		rezervacija.setPrice(cena * rezervacija.getStartDate().until(rezervacija.getEndDate()).getDays());
		db.getReservations().Insert(rezervacija);
		
		System.out.println("\n\nRezervacija sobe broj " + rezervacija.getRoom().getNumber() + " za gosta " + rezervacija.getGuest().getName() + " " + rezervacija.getGuest().getSurname() + " od " + DateTimeFormatter.ofPattern("dd.MM.uuuu").format(rezervacija.getStartDate()) + " do " + DateTimeFormatter.ofPattern("dd.MM.uuuu").format(rezervacija.getEndDate()));
		System.out.println("Dodatne usluge: " + String.join(", ", rezervacija.getReservationAdditions().stream().map(ra -> ra.getName()).toArray(String[]::new)));
		System.out.println("Cena rezervacije: " + rezervacija.getPrice() + " dinara\n");
		
		slobodneSobe = GetRooms(LocalDate.of(2024, 8, 1), LocalDate.of(2024, 8, 31));
		System.out.println("\n\nSlobodne sobe u avgustu 2024. godine nakon rezervacije:\n");
		count = 0;
		for (Room r : slobodneSobe) {
			count++;
			System.out
					.println(count + ". Soba broj " + r.getNumber() + " " + r.getType().getName() + " - " + r.getId());
		}
		
		slobodneSobe = GetRooms(LocalDate.of(2024, 6, 1), LocalDate.of(2024, 6, 30));
		System.out.println("\n\nSlobodne sobe u junu 2024. godine:\n");
		count = 0;
		for (Room r : slobodneSobe) {
			count++;
			System.out
					.println(count + ". Soba broj " + r.getNumber() + " " + r.getType().getName() + " - " + r.getId());
		}
		
		Reservation rezervacija2 = new Reservation(room203, gost2, LocalDate.of(2024, 6, 6), LocalDate.of(2024, 6, 12));
		cena = cenovnik.getRoomTypePrices().get(rezervacija2.getRoom().getType());
		rezervacija2.setPrice(cena * rezervacija2.getStartDate().until(rezervacija2.getEndDate()).getDays());
		db.getReservations().Insert(rezervacija2);
		
		System.out.println("\n\nRezervacija sobe broj " + rezervacija2.getRoom().getNumber() + " za gosta " + rezervacija2.getGuest().getName() + " " + rezervacija2.getGuest().getSurname() + " od " + DateTimeFormatter.ofPattern("dd.MM.uuuu").format(rezervacija2.getStartDate()) + " do " + DateTimeFormatter.ofPattern("dd.MM.uuuu").format(rezervacija2.getEndDate()));
		System.out.println("Dodatne usluge: nema");
		System.out.println("Cena rezervacije: " + rezervacija2.getPrice() + " dinara\n");

		slobodneSobe = GetRooms(LocalDate.of(2024, 6, 1), LocalDate.of(2024, 6, 30));
		System.out.println("\n\nSlobodne sobe u junu 2024. godine nakon rezervacije:\n");
		count = 0;
		for (Room r : slobodneSobe) {
			count++;
			System.out
					.println(count + ". Soba broj " + r.getNumber() + " " + r.getType().getName() + " - " + r.getId());
		}
		
		System.out.println("\nAktivne rezervacije gosta: " + gost1.getName() + " " + gost1.getSurname() + ":");
		ArrayList<Reservation> aktivneRezervacije = GetActiveReservations(gost1);
		count = 0;
		for (Reservation r : aktivneRezervacije) {
			count++;
			System.out.println(count + ". Soba broj " + r.getRoom().getNumber() + " od "
					+ DateTimeFormatter.ofPattern("dd.MM.uuuu").format(r.getStartDate()) + " do "
					+ DateTimeFormatter.ofPattern("dd.MM.uuuu").format(r.getEndDate()));
		}
		
		try {

			System.out.println();
			System.out.println();
			appState.Save();
		} catch (IOException | ParseException e) {
			System.err.println("Greska prilikom cuvanja podataka");
		}
	}
	
	public static ArrayList<Room> GetRooms(LocalDate start, LocalDate end) {
		Database db = AppState.getInstance().getDatabase();
		ArrayList<Reservation> reservations = db.getReservations().Select(new SelectCondition() {
			
			@Override
			public boolean check(Model row) {
				Reservation r = (Reservation) row;
				return r.getStartDate().isBefore(end) && r.getEndDate().isAfter(start);
			}
		}, false);
		Set<Room> rooms = new HashSet<Room>(db.getRooms().getRows(false));
		for (Reservation r : reservations) {
			rooms.remove(r.getRoom());
		}
		ArrayList<Room> result = new ArrayList<Room>(rooms);
		result.sort(Comparator.comparing(Room::getNumber));
		return result;
	}
	public static ArrayList<Reservation> GetActiveReservations(Guest gost) {
		Database db = AppState.getInstance().getDatabase();
		ArrayList<Reservation> result = db.getReservations().Select(new SelectCondition() {

			@Override
			public boolean check(Model row) {
				Reservation r = (Reservation) row;
				return r.getGuest().getId() == gost.getId();
			}
		}, false);
		result.sort(Comparator.comparing(Reservation::getStartDate));
		return result;
	}
}
