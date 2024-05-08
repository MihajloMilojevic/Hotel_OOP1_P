package views;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

import app.AppState;
import database.Database;
import database.SelectCondition;
import exceptions.DuplicateIndexException;
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
import models.User;
import models.enums.Gender;
import models.enums.UserRole;

public class KT2 {

	public KT2() {
		AppState appState = AppState.getInstance();
		try {
			appState.getSettings().load();
			System.out.println("Podesavanja aplikacije ucitana.\n");
		} catch (IOException e) {
			System.err.println("Greska prilikom ucitavanja podesavanja aplikacije");
			return;
		}
		Database db = appState.getDatabase();
		try {
			
		Admin admin = new Admin("Pera", "Peric", Gender.MALE, LocalDate.of(1971, 3, 17), "0641234567", "Bulevar Oslobođenja 17, Novi Sad", "pera", "pera123", "Master", 5, 140_000);	
	    db.getUsers().insert(admin);
	    System.out.println("Dodat administratos: " + admin.getName() + " " + admin.getSurname() + " - " + admin.getId());
	    
	    Receptionist recepcioner1 = new Receptionist("Mika", "Mikić", Gender.MALE, LocalDate.of(1975, 6, 1), "+381628361185", "Bulevar despota Stefana 7, Novi Sad", "mika", "Mika123", "Master", 1, 100_000);
		Receptionist recepcioner2 = new Receptionist("Nikola", "Nikolić", Gender.MALE, LocalDate.of(1980, 9, 17), "+381612599941", "Maksima Gorkog 12, Novi Sad", "nikola", "Nikola123", "Master", 3, 110_000);
		db.getUsers().insert(recepcioner1);
		db.getUsers().insert(recepcioner2);
		System.out.println(admin.getName() +" je dodao recepcionera: " + recepcioner1.getName() + " " + recepcioner1.getSurname() + " - " + recepcioner1.getId());
		System.out.println(admin.getName() +" je dodao recepcionera: " + recepcioner2.getName() + " " + recepcioner2.getSurname() + " - " + recepcioner2.getId());
		
		Maid sobarica = new Maid("Jana", "Janić", Gender.FEMALE, LocalDate.of(1995, 2, 15), "+381628361185", "Bulevar Jovana Dučića 7, Novi Sad", "jana", "Jana123", "Viša škola", 1, 75_000);
		db.getUsers().insert(sobarica);
		System.out.println(admin.getName() +" je dodao sobaricu: " + sobarica.getName() + " " + sobarica.getSurname() + " - " + sobarica.getId());
		
		System.out.println("\n\nSvi zaposleni:\n");
		int count = 0;
		for (User a : db.getUsers().select(new SelectCondition() {
                @Override
                public boolean check(Model row) {
                    User u = (User) row;
                    return u.getRole() != UserRole.GUEST;
                }
		})) {
			count++;
			System.out.println(count + ". Admininstrator: "  + a.getName() + " " + a.getSurname() + " - " + a.getId());
		}
		
		System.out.println();
		db.getUsers().deleteById(recepcioner2.getId());
		System.out.println(admin.getName() +" je obrisao recepcionera: " + recepcioner2.getName() + " " + recepcioner2.getSurname() + " - " + recepcioner2.getId());
		
		Guest gost1 = new Guest("Milica", "Milić", Gender.FEMALE, LocalDate.of(1990, 5, 25), "+381628361185", "Cara Dušana 7, Kraljevo", "milica", "Milica123");
		Guest gost2 = new Guest("Ana", "Anić", Gender.FEMALE, LocalDate.of(1992, 8, 10), "+381628361185", "Bulevar Kralja Aleksandra 20, Beograd", "ana", "Ana123");
		db.getUsers().insert(gost1);
		db.getUsers().insert(gost2);
		
		System.out.println(recepcioner1.getName() +" je dodao gosta: " + gost1.getName() + " " + gost1.getSurname() + " - " + gost1.getId());
		System.out.println(recepcioner1.getName() +" je dodao gosta: " + gost2.getName() + " " + gost2.getSurname() + " - " + gost2.getId());
		
		RoomType jednokrevetna = new RoomType("Jednokrevetna");
		RoomType dvokrevetnaSaJednimLezajem = new RoomType("Dvokrevetna sa jednim ležajem");
		RoomType dvokrevetnaSaDvaLezaja = new RoomType("Dvokrevetna sa dva ležaja");
		RoomType trokrevetnaSaTriLezaja = new RoomType("Trokrevetna sa tri ležaja");
		RoomType trokrevetnaSaDvaLezaja = new RoomType("Trokrevetna sa dva ležaja");
		RoomType apartman = new RoomType("Apartman");
		RoomType penthouse = new RoomType("Penthouse");
		
		db.getRoomTypes().insert(jednokrevetna);
		db.getRoomTypes().insert(dvokrevetnaSaJednimLezajem);
		db.getRoomTypes().insert(dvokrevetnaSaDvaLezaja);
		db.getRoomTypes().insert(trokrevetnaSaTriLezaja);
		db.getRoomTypes().insert(trokrevetnaSaDvaLezaja);
		db.getRoomTypes().insert(apartman);
		db.getRoomTypes().insert(penthouse);
		
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
		
		db.getRooms().insert(room101);
		db.getRooms().insert(room102);
		db.getRooms().insert(room103);
		db.getRooms().insert(room104);
		db.getRooms().insert(room105);
		db.getRooms().insert(room106);
		db.getRooms().insert(room107);
		
		db.getRooms().insert(room201);
		db.getRooms().insert(room202);
		db.getRooms().insert(room203);
		db.getRooms().insert(room204);
		db.getRooms().insert(room205);
		db.getRooms().insert(room206);
		db.getRooms().insert(room207);
		
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
		
		db.getReservationAdditions().insert(dorucak);
		db.getReservationAdditions().insert(rucak);
		db.getReservationAdditions().insert(vecera);
		db.getReservationAdditions().insert(bazen);
		db.getReservationAdditions().insert(spa);	
		
		System.out.println("\n\nSve dodatne usluge:\n");
		count = 0;
		for (ReservationAddition ra : db.getReservationAdditions().getRows()) {
			count++;
			System.out.println(count + ". " + ra.getName() + " - " + ra.getId());
		}
		
		db.getReservationAdditions().delete(spa);
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
		
		db.getPriceLists().insert(cenovnik);
		
		System.out.println("\n\nCenovnik za period od " + DateTimeFormatter.ofPattern("dd.MM.uuuu").format(cenovnik.getStartDate()) + " do " + DateTimeFormatter.ofPattern("dd.MM.uuuu").format(cenovnik.getEndDate()) + ":\n");
		System.out.println("Cene soba:");
		for (RoomType rt : cenovnik.getRoomTypePrices().keySet()) {
			System.out.println(rt.getName() + ": " + cenovnik.getRoomTypePrices().get(rt));
		}
		System.out.println("\nCene dodatnih usluga:");
		for (ReservationAddition ra : cenovnik.getReservationAdditionPrices().keySet()) {
			System.out.println(ra.getName() + ": " + cenovnik.getReservationAdditionPrices().get(ra));
		}
		
		ArrayList<Room> slobodneSobe = getRooms(LocalDate.of(2024, 8, 1), LocalDate.of(2024, 8, 31));
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
		try {
			rezervacija.setPrice(calculatePrice(rezervacija));
		} catch (Exception e) {
			System.err.println("Greska prilikom racunanja cene rezervacije");
		}
		db.getReservations().insert(rezervacija);
		
		System.out.println("\n\nRezervacija sobe broj " + rezervacija.getRoom().getNumber() + " za gosta " + rezervacija.getGuest().getName() + " " + rezervacija.getGuest().getSurname() + " od " + DateTimeFormatter.ofPattern("dd.MM.uuuu").format(rezervacija.getStartDate()) + " do " + DateTimeFormatter.ofPattern("dd.MM.uuuu").format(rezervacija.getEndDate()));
		System.out.println("Dodatne usluge: " + String.join(", ", rezervacija.getReservationAdditions().stream().map(ra -> ra.getName()).toArray(String[]::new)));
		System.out.println("Cena rezervacije: " + rezervacija.getPrice() + " dinara\n");
		
		slobodneSobe = getRooms(LocalDate.of(2024, 8, 1), LocalDate.of(2024, 8, 31));
		System.out.println("\n\nSlobodne sobe u avgustu 2024. godine nakon rezervacije:\n");
		count = 0;
		for (Room r : slobodneSobe) {
			count++;
			System.out
					.println(count + ". Soba broj " + r.getNumber() + " " + r.getType().getName() + " - " + r.getId());
		}
		
		slobodneSobe = getRooms(LocalDate.of(2024, 6, 1), LocalDate.of(2024, 6, 30));
		System.out.println("\n\nSlobodne sobe u junu 2024. godine:\n");
		count = 0;
		for (Room r : slobodneSobe) {
			count++;
			System.out
					.println(count + ". Soba broj " + r.getNumber() + " " + r.getType().getName() + " - " + r.getId());
		}
		
		Reservation rezervacija2 = new Reservation(room203, gost2, LocalDate.of(2024, 6, 6), LocalDate.of(2024, 6, 12));
		
		try {
			rezervacija2.setPrice(calculatePrice(rezervacija2));
		} catch (Exception e) {
			System.err.println("Greska prilikom racunanja cene rezervacije");
		}
		db.getReservations().insert(rezervacija2);
		
		System.out.println("\n\nRezervacija sobe broj " + rezervacija2.getRoom().getNumber() + " za gosta " + rezervacija2.getGuest().getName() + " " + rezervacija2.getGuest().getSurname() + " od " + DateTimeFormatter.ofPattern("dd.MM.uuuu").format(rezervacija2.getStartDate()) + " do " + DateTimeFormatter.ofPattern("dd.MM.uuuu").format(rezervacija2.getEndDate()));
		System.out.println("Dodatne usluge: nema");
		System.out.println("Cena rezervacije: " + rezervacija2.getPrice() + " dinara\n");

		slobodneSobe = getRooms(LocalDate.of(2024, 6, 1), LocalDate.of(2024, 6, 30));
		System.out.println("\n\nSlobodne sobe u junu 2024. godine nakon rezervacije:\n");
		count = 0;
		for (Room r : slobodneSobe) {
			count++;
			System.out
					.println(count + ". Soba broj " + r.getNumber() + " " + r.getType().getName() + " - " + r.getId());
		}
		
		System.out.println("\nAktivne rezervacije gosta: " + gost1.getName() + " " + gost1.getSurname() + ":");
		ArrayList<Reservation> aktivneRezervacije = getActiveReservations(gost1);
		count = 0;
		for (Reservation r : aktivneRezervacije) {
			count++;
			System.out.println(count + ". Soba broj " + r.getRoom().getNumber() + " od "
					+ DateTimeFormatter.ofPattern("dd.MM.uuuu").format(r.getStartDate()) + " do "
					+ DateTimeFormatter.ofPattern("dd.MM.uuuu").format(r.getEndDate()) + " " + r.getStatus().toString());
		}
	} catch (DuplicateIndexException e) {
		System.err.println(e.getMessage());
	} catch (Exception e) {
		System.err.println("Greska prilikom izvrsavanja KT2");
	}
		
		try {

			System.out.println();
			System.out.println();
			appState.save();
		} catch (Exception e) {
			System.err.println("Greska prilikom cuvanja podataka");
		}
	}
	
	public static ArrayList<Room> getRooms(LocalDate start, LocalDate end) {
		Database db = AppState.getInstance().getDatabase();
		ArrayList<Reservation> reservations = db.getReservations().select(new SelectCondition() {
			
			@Override
			public boolean check(Model row) {
				Reservation r = (Reservation) row;
				return r.getStartDate().isBefore(end) && r.getEndDate().isAfter(start);
			}
		});
		Set<Room> rooms = new HashSet<Room>(db.getRooms().getRows());
		for (Reservation r : reservations) {
			rooms.remove(r.getRoom());
		}
		ArrayList<Room> result = new ArrayList<Room>(rooms);
		result.sort(Comparator.comparing(Room::getNumber));
		return result;
	}
	public static ArrayList<Reservation> getActiveReservations(Guest gost) {
		Database db = AppState.getInstance().getDatabase();
		ArrayList<Reservation> result = db.getReservations().select(new SelectCondition() {

			@Override
			public boolean check(Model row) {
				Reservation r = (Reservation) row;
				return r.getGuest().getId() == gost.getId();
			}
		});
		result.sort(Comparator.comparing(Reservation::getStartDate));
		return result;
	}
	public static double calculatePrice(Reservation reservation) throws Exception {
		double price = 0;
		LocalDate d = reservation.getStartDate();
		PriceList current = getActivePriceList(reservation.getStartDate());
		//System.out.println(current.getStartDate() + " " + current.getEndDate() + " " + current.getRoomTypePrices().size() + " " + current.getReservationAdditionPrices().size());
		while(true) {
			if(d.isAfter(current.getEndDate())) 
				current = getActivePriceList(d);
			RoomType type = reservation.getRoom().getType();
			if(!current.getRoomTypePrices().containsKey(type))
				throw new Exception("Nema cene za tip sobe");
			
			price += current.getRoomTypePrices().get(type);
			for (ReservationAddition ra : reservation.getReservationAdditions()) {
				if(!current.getReservationAdditionPrices().containsKey(ra))
					throw new Exception("Nema cene za dodatnu uslugu");
				price += current.getReservationAdditionPrices().get(ra);
			}
			d = d.plusDays(1);
			if (d.isAfter(reservation.getEndDate())) {
				break;
			}
		}
		return price;
	}
	public static PriceList getActivePriceList(LocalDate d) {
		for(PriceList p: AppState.getInstance().getDatabase().getPriceLists().getRows()) {
			if ((d.isAfter(p.getStartDate()) && d.isBefore(p.getEndDate())) || d.isEqual(p.getStartDate())
					|| d.isEqual(p.getEndDate())) {
				return p;
			}
		}
		return null;
	}
}
