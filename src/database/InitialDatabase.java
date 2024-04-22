package database;

import java.time.LocalDate;

import models.Admin;
import models.Guest;
import models.Maid;
import models.Receptionist;
import models.ReservationAddition;
import models.Room;
import models.RoomAddition;
import models.RoomType;
import models.enums.Gender;

public class InitialDatabase {

	public static void init(Database db) {
		
		/* ****************************** Users *************************************** */
		
		Admin mihajlo = new Admin("Mihajlo", "Milojević", Gender.MALE, LocalDate.of(2004, 5, 21), "+381649781191", "Braće Dronjak 6, Novi Sad", "admin", "admin", "Phd", 2, 500_000);
		db.getAdmins().Insert(mihajlo);
		
		Receptionist petar = new Receptionist("Petar", "Popović", Gender.MALE, LocalDate.of(2004, 6, 6), "+381628361185", "Bulevar despota Stefana 7, Novi Sad", "petar", "Petar123", "Bachelor", 1, 100_000);
		Receptionist sara1 = new Receptionist("Sara", "Stojkov", Gender.FEMALE, LocalDate.of(2004, 9, 17), "+381612599941", "Maksima Gorkog 12, Novi Sad", "sara", "Sara987", "Master", 3, 130_000);
		db.getReceptionists().Insert(petar);
		db.getReceptionists().Insert(sara1);
		
		Maid luka = new Maid("Luka", "Prlinčević", Gender.MALE, LocalDate.of(2004, 8, 26), "+381640862775", "Bulevar despota Stefana 7, Novi Sad", "luka", "Luka123", "Highschool", 0, 70_000);
		Maid nikola = new Maid("Nikola", "Rogonjić", Gender.MALE, LocalDate.of(2004, 9, 10), "+381644606859", "Samaila 5, Kraljevo", "nikola", "Nikola123", "Highschool", 0, 75_000);
		db.getMaids().Insert(luka);
		db.getMaids().Insert(nikola);
		
		Guest djordje = new Guest("Đorđe", "Milojević", Gender.MALE, LocalDate.of(2008, 5, 4), "+381642723956", "8. Mart 70, Kraljevo", "Djole", "Majmunce");
		Guest sofija = new Guest("Sofija", "Obradović", Gender.FEMALE, LocalDate.of(2004, 4, 21), "+381659786412", "Bulevar vojvode Stepe 17, Novi Sad", "Sofija", "Sofija123");
		Guest sara2 = new Guest("Sara", "Spasojević", Gender.FEMALE, LocalDate.of(2004, 4, 20), "+381628361185", "Ulica 9. maja 15, Kraljevo", "caja", "Sara123");
		db.getGuests().Insert(djordje);
		db.getGuests().Insert(sofija);
		db.getGuests().Insert(sara2);
	
		/* ****************************** Room Types *************************************** */
	
		RoomType single = new RoomType("Single");
		RoomType doubleSingleBed = new RoomType("Double Single Bed");
		RoomType doubleTwoBeds = new RoomType("Double Two Beds");
		RoomType tripleThreeBeds = new RoomType("Triple Three Beds");
		RoomType tripleTwoBeds = new RoomType("Triple Two Beds");
		RoomType apartment = new RoomType("Apartment");
		RoomType penthouse = new RoomType("Penthouse");
		db.getRoomTypes().Insert(single);
		db.getRoomTypes().Insert(doubleSingleBed);
		db.getRoomTypes().Insert(doubleTwoBeds);
		db.getRoomTypes().Insert(tripleThreeBeds);
		db.getRoomTypes().Insert(tripleTwoBeds);
		db.getRoomTypes().Insert(apartment);
		db.getRoomTypes().Insert(penthouse);
		
		/* ****************************** Room Additions *************************************** */
		
		RoomAddition balcony = new RoomAddition("Balcony");
		RoomAddition cityView = new RoomAddition("City View");
		RoomAddition jacuzzi = new RoomAddition("Jacuzzi");
		RoomAddition tv = new RoomAddition("TV");
		RoomAddition wifi = new RoomAddition("WiFi");
		RoomAddition climate = new RoomAddition("Climate");
		db.getRoomAdditions().Insert(balcony);
		db.getRoomAdditions().Insert(cityView);
		db.getRoomAdditions().Insert(jacuzzi);
		db.getRoomAdditions().Insert(tv);
		db.getRoomAdditions().Insert(wifi);
		db.getRoomAdditions().Insert(climate);
		
		/* ****************************** Reservation Additions *************************************** */
		
		ReservationAddition breakfast = new ReservationAddition("Breakfast");
		ReservationAddition lunch = new ReservationAddition("Lunch");
		ReservationAddition dinner = new ReservationAddition("Dinner");
		ReservationAddition allInclusive = new ReservationAddition("All Inclusive");
		ReservationAddition spa = new ReservationAddition("Spa");
		ReservationAddition gym = new ReservationAddition("Gym");
		ReservationAddition pool = new ReservationAddition("Pool");
		ReservationAddition parking = new ReservationAddition("Parking");
		ReservationAddition roomService = new ReservationAddition("Room Service");
		ReservationAddition extraBed = new ReservationAddition("Extra Bed");
		db.getReservationAdditions().Insert(breakfast);
		db.getReservationAdditions().Insert(lunch);
		db.getReservationAdditions().Insert(dinner);
		db.getReservationAdditions().Insert(allInclusive);
		db.getReservationAdditions().Insert(spa);
		db.getReservationAdditions().Insert(gym);
		db.getReservationAdditions().Insert(pool);
		db.getReservationAdditions().Insert(parking);
		db.getReservationAdditions().Insert(roomService);
		db.getReservationAdditions().Insert(extraBed);
		
		/* ****************************** Rooms *************************************** */
		
		Room room101 = new Room(101, single);
		Room room102 = new Room(102, single);
		Room room103 = new Room(103, single);
		Room room104 = new Room(104, single);
		Room room105 = new Room(105, doubleSingleBed);
		Room room106 = new Room(106, doubleSingleBed);
		Room room107 = new Room(107, doubleSingleBed);
		Room room108 = new Room(108, doubleSingleBed);
		Room room109 = new Room(109, doubleTwoBeds);
		Room room110 = new Room(110, doubleTwoBeds);
		Room room111 = new Room(111, doubleTwoBeds);
		Room room112 = new Room(112, doubleTwoBeds);
		Room room113 = new Room(113, tripleThreeBeds);
		Room room114 = new Room(114, tripleThreeBeds);
		Room room115 = new Room(115, tripleThreeBeds);
		Room room116 = new Room(116, tripleThreeBeds);
		Room room117 = new Room(117, tripleTwoBeds);
		Room room118 = new Room(118, tripleTwoBeds);
		Room room119 = new Room(119, tripleTwoBeds);
		Room room120 = new Room(120, tripleTwoBeds);
		Room room121 = new Room(121, apartment);
		Room room122 = new Room(122, apartment);
		Room room123 = new Room(123, apartment);
		Room room124 = new Room(124, apartment);
		
		Room room201 = new Room(201, single);
		Room room202 = new Room(202, single);
		Room room203 = new Room(203, single);
		Room room204 = new Room(204, single);
		Room room205 = new Room(205, doubleSingleBed);
		Room room206 = new Room(206, doubleSingleBed);
		Room room207 = new Room(207, doubleSingleBed);
		Room room208 = new Room(208, doubleSingleBed);
		Room room209 = new Room(209, doubleTwoBeds);
		Room room210 = new Room(210, doubleTwoBeds);
		Room room211 = new Room(211, doubleTwoBeds);
		Room room212 = new Room(212, doubleTwoBeds);
		Room room213 = new Room(213, tripleThreeBeds);
		Room room214 = new Room(214, tripleThreeBeds);
		Room room215 = new Room(215, tripleThreeBeds);
		Room room216 = new Room(216, tripleThreeBeds);
		Room room217 = new Room(217, tripleTwoBeds);
		Room room218 = new Room(218, tripleTwoBeds);
		Room room219 = new Room(219, tripleTwoBeds);
		Room room220 = new Room(220, tripleTwoBeds);
		Room room221 = new Room(221, apartment);
		Room room222 = new Room(222, apartment);
		Room room223 = new Room(223, apartment);
		Room room224 = new Room(224, apartment);
		
		Room room301 = new Room(301, single);
		Room room302 = new Room(302, single);
		Room room303 = new Room(303, single);
		Room room304 = new Room(304, single);
		Room room305 = new Room(305, doubleSingleBed);
		Room room306 = new Room(306, doubleSingleBed);
		Room room307 = new Room(307, doubleSingleBed);
		Room room308 = new Room(308, doubleSingleBed);
		Room room309 = new Room(309, doubleTwoBeds);
		Room room310 = new Room(310, doubleTwoBeds);
		Room room311 = new Room(311, doubleTwoBeds);
		Room room312 = new Room(312, doubleTwoBeds);
		Room room313 = new Room(313, tripleThreeBeds);
		Room room314 = new Room(314, tripleThreeBeds);
		Room room315 = new Room(315, tripleThreeBeds);
		Room room316 = new Room(316, tripleThreeBeds);
		Room room317 = new Room(317, tripleTwoBeds);
		Room room318 = new Room(318, tripleTwoBeds);
		Room room319 = new Room(319, tripleTwoBeds);
		Room room320 = new Room(320, tripleTwoBeds);
		Room room321 = new Room(321, apartment);
		Room room322 = new Room(322, apartment);
		Room room323 = new Room(323, apartment);
		Room room324 = new Room(324, apartment);
		
		Room room401 = new Room(401, single);
		Room room402 = new Room(402, single);
		Room room403 = new Room(403, single);
		Room room404 = new Room(404, single);
		Room room405 = new Room(405, doubleSingleBed);
		Room room406 = new Room(406, doubleSingleBed);
		Room room407 = new Room(407, doubleSingleBed);
		Room room408 = new Room(408, doubleSingleBed);
		Room room409 = new Room(409, doubleTwoBeds);
		Room room410 = new Room(410, doubleTwoBeds);
		Room room411 = new Room(411, doubleTwoBeds);
		Room room412 = new Room(412, doubleTwoBeds);
		Room room413 = new Room(413, tripleThreeBeds);
		Room room414 = new Room(414, tripleThreeBeds);
		Room room415 = new Room(415, tripleThreeBeds);
		Room room416 = new Room(416, tripleThreeBeds);
		Room room417 = new Room(417, tripleTwoBeds);
		Room room418 = new Room(418, tripleTwoBeds);
		Room room419 = new Room(419, tripleTwoBeds);
		Room room420 = new Room(420, tripleTwoBeds);
		Room room421 = new Room(421, apartment);
		Room room422 = new Room(422, apartment);
		Room room423 = new Room(423, apartment);
		Room room424 = new Room(424, apartment);
		
		Room room501 = new Room(501, single);
		Room room502 = new Room(502, single);
		Room room503 = new Room(503, single);
		Room room504 = new Room(504, single);
		Room room505 = new Room(505, doubleSingleBed);
		Room room506 = new Room(506, doubleSingleBed);
		Room room507 = new Room(507, doubleSingleBed);
		Room room508 = new Room(508, doubleSingleBed);
		Room room509 = new Room(509, doubleTwoBeds);
		Room room510 = new Room(510, doubleTwoBeds);
		Room room511 = new Room(511, doubleTwoBeds);
		Room room512 = new Room(512, doubleTwoBeds);
		Room room513 = new Room(513, tripleThreeBeds);
		Room room514 = new Room(514, tripleThreeBeds);
		Room room515 = new Room(515, tripleThreeBeds);
		Room room516 = new Room(516, tripleThreeBeds);
		Room room517 = new Room(517, tripleTwoBeds);
		Room room518 = new Room(518, tripleTwoBeds);
		Room room519 = new Room(519, tripleTwoBeds);
		Room room520 = new Room(520, tripleTwoBeds);
		Room room521 = new Room(521, apartment);
		Room room522 = new Room(522, apartment);
		Room room523 = new Room(523, apartment);
		Room room524 = new Room(524, apartment);
		
		Room room601 = new Room(601, penthouse);
		room601.addRoomAddition(balcony);
		room601.addRoomAddition(cityView);
		room601.addRoomAddition(jacuzzi);
		room601.addRoomAddition(tv);
		room601.addRoomAddition(wifi);
		room601.addRoomAddition(climate);
		
		Room room701 = new Room(701, penthouse);
		room701.addRoomAddition(balcony);
		room701.addRoomAddition(cityView);
		room701.addRoomAddition(jacuzzi);
		room701.addRoomAddition(tv);
		room701.addRoomAddition(wifi);
		room701.addRoomAddition(climate);
		
		Room room801 = new Room(801, penthouse);
		room801.addRoomAddition(balcony);
		room801.addRoomAddition(cityView);
		room801.addRoomAddition(jacuzzi);
		room801.addRoomAddition(tv);
		room801.addRoomAddition(wifi);
		room801.addRoomAddition(climate);
		
		db.getRooms().Insert(room101);
		db.getRooms().Insert(room102);
		db.getRooms().Insert(room103);
		db.getRooms().Insert(room104);
		db.getRooms().Insert(room105);
		db.getRooms().Insert(room106);
		db.getRooms().Insert(room107);
		db.getRooms().Insert(room108);
		db.getRooms().Insert(room109);
		db.getRooms().Insert(room110);
		db.getRooms().Insert(room111);
		db.getRooms().Insert(room112);
		db.getRooms().Insert(room113);
		db.getRooms().Insert(room114);
		db.getRooms().Insert(room115);
		db.getRooms().Insert(room116);
		db.getRooms().Insert(room117);
		db.getRooms().Insert(room118);
		db.getRooms().Insert(room119);
		db.getRooms().Insert(room120);
		db.getRooms().Insert(room121);
		db.getRooms().Insert(room122);
		db.getRooms().Insert(room123);
		db.getRooms().Insert(room124);
		
		db.getRooms().Insert(room201);
		db.getRooms().Insert(room202);
		db.getRooms().Insert(room203);
		db.getRooms().Insert(room204);
		db.getRooms().Insert(room205);
		db.getRooms().Insert(room206);
		db.getRooms().Insert(room207);
		db.getRooms().Insert(room208);
		db.getRooms().Insert(room209);
		db.getRooms().Insert(room210);
		db.getRooms().Insert(room211);
		db.getRooms().Insert(room212);
		db.getRooms().Insert(room213);
		db.getRooms().Insert(room214);
		db.getRooms().Insert(room215);
		db.getRooms().Insert(room216);
		db.getRooms().Insert(room217);
		db.getRooms().Insert(room218);
		db.getRooms().Insert(room219);
		db.getRooms().Insert(room220);
		db.getRooms().Insert(room221);
		db.getRooms().Insert(room222);
		db.getRooms().Insert(room223);
		db.getRooms().Insert(room224);
		
		db.getRooms().Insert(room301);
		db.getRooms().Insert(room302);
		db.getRooms().Insert(room303);
		db.getRooms().Insert(room304);
		db.getRooms().Insert(room305);
		db.getRooms().Insert(room306);
		db.getRooms().Insert(room307);
		db.getRooms().Insert(room308);
		db.getRooms().Insert(room309);
		db.getRooms().Insert(room310);
		db.getRooms().Insert(room311);
		db.getRooms().Insert(room312);
		db.getRooms().Insert(room313);
		db.getRooms().Insert(room314);
		db.getRooms().Insert(room315);
		db.getRooms().Insert(room316);
		db.getRooms().Insert(room317);
		db.getRooms().Insert(room318);
		db.getRooms().Insert(room319);
		db.getRooms().Insert(room320);
		db.getRooms().Insert(room321);
		db.getRooms().Insert(room322);
		db.getRooms().Insert(room323);
		db.getRooms().Insert(room324);
		
		db.getRooms().Insert(room401);
		db.getRooms().Insert(room402);
		db.getRooms().Insert(room403);
		db.getRooms().Insert(room404);
		db.getRooms().Insert(room405);
		db.getRooms().Insert(room406);
		db.getRooms().Insert(room407);
		db.getRooms().Insert(room408);
		db.getRooms().Insert(room409);
		db.getRooms().Insert(room410);
		db.getRooms().Insert(room411);
		db.getRooms().Insert(room412);
		db.getRooms().Insert(room413);
		db.getRooms().Insert(room414);
		db.getRooms().Insert(room415);
		db.getRooms().Insert(room416);
		db.getRooms().Insert(room417);
		db.getRooms().Insert(room418);
		db.getRooms().Insert(room419);
		db.getRooms().Insert(room420);
		db.getRooms().Insert(room421);
		db.getRooms().Insert(room422);
		db.getRooms().Insert(room423);
		db.getRooms().Insert(room424);
		
		db.getRooms().Insert(room501);
		db.getRooms().Insert(room502);
		db.getRooms().Insert(room503);
		db.getRooms().Insert(room504);
		db.getRooms().Insert(room505);
		db.getRooms().Insert(room506);
		db.getRooms().Insert(room507);
		db.getRooms().Insert(room508);
		db.getRooms().Insert(room509);
		db.getRooms().Insert(room510);
		db.getRooms().Insert(room511);
		db.getRooms().Insert(room512);
		db.getRooms().Insert(room513);
		db.getRooms().Insert(room514);
		db.getRooms().Insert(room515);
		db.getRooms().Insert(room516);
		db.getRooms().Insert(room517);
		db.getRooms().Insert(room518);
		db.getRooms().Insert(room519);
		db.getRooms().Insert(room520);
		db.getRooms().Insert(room521);
		db.getRooms().Insert(room522);
		db.getRooms().Insert(room523);
		db.getRooms().Insert(room524);
		
		db.getRooms().Insert(room601);
		
		db.getRooms().Insert(room701);
		
		db.getRooms().Insert(room801);
		
	}

}
