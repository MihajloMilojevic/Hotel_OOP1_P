package database;

import java.io.File;

import app.AppSettings;
import models.Admin;
import models.Guest;
import models.Maid;
import models.PriceList;
import models.Receptionist;
import models.Reservation;
import models.ReservationAddition;
import models.Room;
import models.RoomAddition;
import models.RoomType;

public class Database {

	private Table<Guest> guests;
	private Table<Maid> maids;
	private Table<Receptionist> receptionists;
	private Table<Admin> admins;
	private Table<RoomType> roomTypes;
	private Table<RoomAddition> roomAdditions;
	private Table<ReservationAddition> reservationAdditions;
	private Table<Room> rooms;
	private Table<Reservation> reservations;
	private Table<PriceList> priceLists;

	private Database(AppSettings settings) {
		guests = new Table<Guest>(new File(settings.getSetting("database", "guests_file_path", "./data/default.csv")),
				new Guest());
		maids = new Table<Maid>(new File(settings.getSetting("database", "maids_file_path", "./data/default.csv")),
				new Maid());
		receptionists = new Table<Receptionist>(
				new File(settings.getSetting("database", "receptionists_file_path", "./data/default.csv")),
				new Receptionist());
		admins = new Table<Admin>(new File(settings.getSetting("database", "admins_file_path", "./data/default.csv")),
				new Admin());
		roomTypes = new Table<RoomType>(
				new File(settings.getSetting("database", "room_types_file_path", "./data/default.csv")),
				new RoomType());
		roomAdditions = new Table<RoomAddition>(
				new File(settings.getSetting("database", "room_additions_file_path", "./data/default.csv")),
				new RoomAddition());
		reservationAdditions = new Table<ReservationAddition>(
				new File(settings.getSetting("database", "reservation_additions_file_path", "./data/default.csv")),
				new ReservationAddition());
		rooms = new Table<Room>(new File(settings.getSetting("database", "rooms_file_path", "./data/default.csv")),
				new Room());
		reservations = new Table<Reservation>(
				new File(settings.getSetting("database", "reservations_file_path", "./data/default.csv")),
				new Reservation());
		priceLists = new Table<PriceList>(
				new File(settings.getSetting("database", "price_lists_file_path", "./data/default.csv")),
				new PriceList());
	}

	private static Database instance;

	public static Database getInstance(AppSettings settings) {
		if (instance == null) {
			instance = new Database(settings);
		}
		return instance;
	}

	/**
	 * @return the guests
	 */
	public Table<Guest> getGuests() {
		return guests;
	}

	/**
	 * @return the maids
	 */
	public Table<Maid> getMaids() {
		return maids;
	}

	/**
	 * @return the receptionists
	 */
	public Table<Receptionist> getReceptionists() {
		return receptionists;
	}

	/**
	 * @return the admins
	 */
	public Table<Admin> getAdmins() {
		return admins;
	}

	/**
	 * @return the roomTypes
	 */
	public Table<RoomType> getRoomTypes() {
		return roomTypes;
	}

	/**
	 * @return the roomAdditions
	 */
	public Table<RoomAddition> getRoomAdditions() {
		return roomAdditions;
	}

	/**
	 * @return the reservationAdditions
	 */
	public Table<ReservationAddition> getReservationAdditions() {
		return reservationAdditions;
	}

	/**
	 * @return the rooms
	 */
	public Table<Room> getRooms() {
		return rooms;
	}

	/**
	 * @return the reservations
	 */
	public Table<Reservation> getReservations() {
		return reservations;
	}

	/**
	 * @return the priceLists
	 */
	public Table<PriceList> getPriceLists() {
		return priceLists;
	}

	/**
	 * @return the instance
	 */
	public static Database getInstance() {
		return instance;
	}
}
