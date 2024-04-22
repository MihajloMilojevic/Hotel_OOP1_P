package database;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import app.AppSettings;
import models.Admin;
import models.Guest;
import models.Maid;
import models.Model;
import models.PriceList;
import models.Receptionist;
import models.Reservation;
import models.ReservationAddition;
import models.Room;
import models.RoomAddition;
import models.RoomType;

public class Database {
	private HashMap<String, Table<? extends Model>> tables;
	private ArrayList<Connection<? extends Model, ? extends Model>> connections;

	private Database(AppSettings settings) {
		tables = new HashMap<String, Table<? extends Model>>();
		connections = new ArrayList<Connection<? extends Model, ? extends Model>>();

		/* ****************************** TABLES *************************************** */

		tables.put("guests", new Table<Guest>(
				new File(settings.getSetting("database", "guests_file_path", "./data/default.csv")), new Guest()));
		tables.put("maids", new Table<Maid>(
				new File(settings.getSetting("database", "maids_file_path", "./data/default.csv")), new Maid()));
		tables.put("receptionists",
				new Table<Receptionist>(
						new File(settings.getSetting("database", "receptionists_file_path", "./data/default.csv")),
						new Receptionist()));
		tables.put("admins", new Table<Admin>(
				new File(settings.getSetting("database", "admins_file_path", "./data/default.csv")), new Admin()));
		tables.put("roomTypes",
				new Table<RoomType>(
						new File(settings.getSetting("database", "room_types_file_path", "./data/default.csv")),
						new RoomType()));
		tables.put("roomAdditions",
				new Table<RoomAddition>(
						new File(settings.getSetting("database", "room_additions_file_path", "./data/default.csv")),
						new RoomAddition()));
		tables.put("reservationAdditions",
				new Table<ReservationAddition>(new File(
						settings.getSetting("database", "reservation_additions_file_path", "./data/default.csv")),
						new ReservationAddition()));
		tables.put("rooms", new Table<Room>(
				new File(settings.getSetting("database", "rooms_file_path", "./data/default.csv")), new Room()));
		tables.put("reservations",
				new Table<Reservation>(
						new File(settings.getSetting("database", "reservations_file_path", "./data/default.csv")),
						new Reservation()));
		tables.put("priceLists",
				new Table<PriceList>(
						new File(settings.getSetting("database", "price_lists_file_path", "./data/default.csv")),
						new PriceList()));

		/* * ****************************** CONNECTIONS *************************************** */

		connections.add(new Connection<Room, RoomType>(getRooms(), getRoomTypes(),
				new File(settings.getSetting("database", "rooms_roomTypes_connection_file_path", "./data/default.csv")),
				new ConnectionActions<Room, RoomType>() {
					@Override
					public void Load(Table<Room> table1, Table<RoomType> table2, String path)
							throws IOException, ParseException {
						List<String> lines = Files.readAllLines(Path.of(path), StandardCharsets.UTF_8);
						for (String line : lines) {
							String[] parts = line.split(";");
							if (parts.length != 2) {
								throw new ParseException("Invalid csv record", 0);
							}
							Room room = table1.SelectById(parts[0], false);
							RoomType roomType = table2.SelectById(parts[1], false);
							room.setType(roomType);
						}
					}

					@Override
					public void Save(Table<Room> table1, Table<RoomType> table2, String path)
							throws IOException, ParseException {
						List<String> lines = new ArrayList<String>();
						for (Room room : table1.getRows()) {
							lines.add(room.getId() + ";" + room.getType().getId());
						}
						Files.write(Path.of(path), lines, StandardCharsets.UTF_8);
					}
				}));
		connections.add(new Connection<Room, RoomAddition>(
				getRooms(), getRoomAdditions(), new File(settings.getSetting("database",
						"rooms_roomAdditions_connection_file_path", "./data/default.csv")),
				new ConnectionActions<Room, RoomAddition>() {
					@Override
					public void Load(Table<Room> table1, Table<RoomAddition> table2, String path)
							throws IOException, ParseException {
						List<String> lines = Files.readAllLines(Path.of(path), StandardCharsets.UTF_8);
						for (String line : lines) {
							String[] parts = line.split(";");
							if (parts.length != 2) {
								throw new ParseException("Invalid csv record", 0);
							}
							Room room = table1.SelectById(parts[0], false);
							RoomAddition roomAddition = table2.SelectById(parts[1], false);
							room.addRoomAddition(roomAddition);
						}
					}

					@Override
					public void Save(Table<Room> table1, Table<RoomAddition> table2, String path)
							throws IOException, ParseException {
						List<String> lines = new ArrayList<String>();
						for (Room room : table1.getRows()) {
							for (RoomAddition roomAddition : room.getRoomAdditions()) {
								lines.add(room.getId() + ";" + roomAddition.getId());
							}
						}
						Files.write(Path.of(path), lines, StandardCharsets.UTF_8);
					}
				}));
		connections.add(new Connection<Room, Maid>(
					getRooms(), getMaids(), new File(settings.getSetting("database", "rooms_maids_connection_file_path",
                        "./data/default.csv")),
					new ConnectionActions<Room, Maid>() {
						@Override
                        public void Load(Table<Room> table1, Table<Maid> table2, String path)
                                throws IOException, ParseException {
                            List<String> lines = Files.readAllLines(Path.of(path), StandardCharsets.UTF_8);
                            for (String line : lines) {
                                String[] parts = line.split(";");
                                if (parts.length != 2) {
                                    throw new ParseException("Invalid csv record", 0);
                                }
                                Room room = table1.SelectById(parts[0], false);
                                Maid maid = table2.SelectById(parts[1], false);
                                room.setMaid(maid);
                            }
                        }

                        @Override
                        public void Save(Table<Room> table1, Table<Maid> table2, String path)
                                throws IOException, ParseException {
                            List<String> lines = new ArrayList<String>();
                            for (Room room : table1.getRows()) {
                            	if(room.getMaid() == null) continue;
                                lines.add(room.getId() + ";" + room.getMaid().getId());
                            }
                            Files.write(Path.of(path), lines, StandardCharsets.UTF_8);
                        }
					}
				));
		connections.add(new Connection<Reservation, Room>(
				getReservations(), getRooms(), new File(settings.getSetting("database",
						"reservations_rooms_connection_file_path", "./data/default.csv")),
				new ConnectionActions<Reservation, Room>() {
					@Override
					public void Load(Table<Reservation> table1, Table<Room> table2, String path)
							throws IOException, ParseException {
						List<String> lines = Files.readAllLines(Path.of(path), StandardCharsets.UTF_8);
						for (String line : lines) {
							String[] parts = line.split(";");
							if (parts.length != 2) {
								throw new ParseException("Invalid csv record", 0);
							}
							Reservation reservation = table1.SelectById(parts[0], false);
							Room room = table2.SelectById(parts[1], false);
							reservation.setRoom(room);
						}
					}

					@Override
					public void Save(Table<Reservation> table1, Table<Room> table2, String path)
							throws IOException, ParseException {
						List<String> lines = new ArrayList<String>();
						for (Reservation reservation : table1.getRows()) {
							lines.add(reservation.getId() + ";" + reservation.getRoom().getId());
						}
						Files.write(Path.of(path), lines, StandardCharsets.UTF_8);
					}
				}));
		connections.add(new Connection<Reservation, Guest>(
				getReservations(), getGuests(), new File(settings.getSetting("database",
						"reservations_guests_connection_file_path", "./data/default.csv")),
				new ConnectionActions<Reservation, Guest>() {
					@Override
					public void Load(Table<Reservation> table1, Table<Guest> table2, String path)
							throws IOException, ParseException {
						List<String> lines = Files.readAllLines(Path.of(path), StandardCharsets.UTF_8);
						for (String line : lines) {
							String[] parts = line.split(";");
							if (parts.length != 2) {
								throw new ParseException("Invalid csv record", 0);
							}
							Reservation reservation = table1.SelectById(parts[0], false);
							Guest guest = table2.SelectById(parts[1], false);
							reservation.setGuest(guest);
						}
					}

					@Override
					public void Save(Table<Reservation> table1, Table<Guest> table2, String path)
							throws IOException, ParseException {
						List<String> lines = new ArrayList<String>();
						for (Reservation reservation : table1.getRows()) {
							lines.add(reservation.getId() + ";" + reservation.getGuest().getId());
						}
						Files.write(Path.of(path), lines, StandardCharsets.UTF_8);
					}
				}));
		connections.add(new Connection<Reservation, ReservationAddition>(
				getReservations(), getReservationAdditions(), new File(settings.getSetting("database",
						"reservations_reservationAdditions_connection_file_path", "./data/default.csv")),
				new ConnectionActions<Reservation, ReservationAddition>() {
					@Override
					public void Load(Table<Reservation> table1, Table<ReservationAddition> table2, String path)
							throws IOException, ParseException {
						List<String> lines = Files.readAllLines(Path.of(path), StandardCharsets.UTF_8);
						for (String line : lines) {
							String[] parts = line.split(";");
							if (parts.length != 2) {
								throw new ParseException("Invalid csv record", 0);
							}
							Reservation reservation = table1.SelectById(parts[0], false);
							ReservationAddition reservationAddition = table2.SelectById(parts[1], false);
							reservation.addReservationAddition(reservationAddition);
						}
					}

					@Override
					public void Save(Table<Reservation> table1, Table<ReservationAddition> table2, String path)
							throws IOException, ParseException {
						List<String> lines = new ArrayList<String>();
						for (Reservation reservation : table1.getRows()) {
							for (ReservationAddition reservationAddition : reservation.getReservationAdditions()) {
								lines.add(reservation.getId() + ";" + reservationAddition.getId());
							}
						}
						Files.write(Path.of(path), lines, StandardCharsets.UTF_8);
					}
				}));
		connections.add(new Connection<PriceList, RoomType>(
				getPriceLists(), getRoomTypes(), new File(settings.getSetting("database",
						"priceLists_roomTypes_connection_file_path", "./data/default.csv")),
				new ConnectionActions<PriceList, RoomType>() {
			@Override
			public void Load(Table<PriceList> table1, Table<RoomType> table2, String path)
					throws IOException, ParseException {
				List<String> lines = Files.readAllLines(Path.of(path), StandardCharsets.UTF_8);
				for (String line : lines) {
					String[] parts = line.split(";");
					if (parts.length != 3) {
						throw new ParseException("Invalid csv record", 0);
					}
					PriceList priceList = table1.SelectById(parts[0], false);
					RoomType roomType = table2.SelectById(parts[1], false);
					priceList.getRoomTypePrices().put(roomType, Double.parseDouble(parts[2]));
				}
			}

			@Override
			public void Save(Table<PriceList> table1, Table<RoomType> table2, String path)
					throws IOException, ParseException {
				List<String> lines = new ArrayList<String>();
				for (PriceList priceList : table1.getRows()) {
					for (RoomType roomType : priceList.getRoomTypePrices().keySet()) {
						lines.add(priceList.getId() + ";" + roomType.getId() + ";"
								+ Double.toString(priceList.getRoomTypePrices().get(roomType)));
					}
				}
				Files.write(Path.of(path), lines, StandardCharsets.UTF_8);
			}
		}));
		connections.add(new Connection<PriceList, RoomAddition>(
				getPriceLists(), getRoomAdditions(), new File(settings.getSetting("database",
						"priceLists_roomAdditions_connection_file_path", "./data/default.csv")),
				new ConnectionActions<PriceList, RoomAddition>() {
			@Override
			public void Load(Table<PriceList> table1, Table<RoomAddition> table2, String path)
					throws IOException, ParseException {
				List<String> lines = Files.readAllLines(Path.of(path), StandardCharsets.UTF_8);
				for (String line : lines) {
					String[] parts = line.split(";");
					if (parts.length != 3) {
						throw new ParseException("Invalid csv record", 0);
					}
					PriceList priceList = table1.SelectById(parts[0], false);
					RoomAddition roomAddition = table2.SelectById(parts[1], false);
					priceList.getRoomAdditionPrices().put(roomAddition, Double.parseDouble(parts[2]));
				}
			}

			@Override
			public void Save(Table<PriceList> table1, Table<RoomAddition> table2, String path)
					throws IOException, ParseException {
				List<String> lines = new ArrayList<String>();
				for (PriceList priceList : table1.getRows()) {
					for (RoomAddition roomAddition : priceList.getRoomAdditionPrices().keySet()) {
						lines.add(priceList.getId() + ";" + roomAddition.getId() + ";"
								+ Double.toString(priceList.getRoomAdditionPrices().get(roomAddition)));
					}
				}
				Files.write(Path.of(path), lines, StandardCharsets.UTF_8);
			}
		}));
		connections.add(new Connection<PriceList, ReservationAddition>(
				getPriceLists(), getReservationAdditions(), new File(settings.getSetting("database",
						"priceLists_reservationAdditions_connection_file_path", "./data/default.csv")),
				new ConnectionActions<PriceList, ReservationAddition>() {
			@Override
			public void Load(Table<PriceList> table1, Table<ReservationAddition> table2, String path)
					throws IOException, ParseException {
				List<String> lines = Files.readAllLines(Path.of(path), StandardCharsets.UTF_8);
				for (String line : lines) {
					String[] parts = line.split(";");
					if (parts.length != 3) {
						throw new ParseException("Invalid csv record", 0);
					}
					PriceList priceList = table1.SelectById(parts[0], false);
					ReservationAddition reservationAddition = table2.SelectById(parts[1], false);
					priceList.getReservationAdditionPrices().put(reservationAddition, Double.parseDouble(parts[2]));
				}
			}

			@Override
			public void Save(Table<PriceList> table1, Table<ReservationAddition> table2, String path)
					throws IOException, ParseException {
				List<String> lines = new ArrayList<String>();
				for (PriceList priceList : table1.getRows()) {
					for (ReservationAddition reservationAddition : priceList.getReservationAdditionPrices().keySet()) {
						lines.add(priceList.getId() + ";" + reservationAddition.getId() + ";"
								+ Double.toString(priceList.getReservationAdditionPrices().get(reservationAddition)));
					}
				}
				Files.write(Path.of(path), lines, StandardCharsets.UTF_8);
			}
		}));
		
		/* ****************************** INDECIES *************************************** */
		getAdmins().AddIndex("username");
		getReceptionists().AddIndex("username");
		getMaids().AddIndex("username");
		getGuests().AddIndex("username");
		getRooms().AddIndex("number");
		getRoomTypes().AddIndex("name");
		getRoomAdditions().AddIndex("name");
		getReservationAdditions().AddIndex("name");
	}

	public void Load() throws IOException, ParseException {
		for (Table<? extends Model> table : tables.values()) {
			table.Load();
		}
		for (Connection<? extends Model, ? extends Model> connection : connections) {
			connection.Load();
		}
	}
	
	public void Save() throws IOException, ParseException {
		for (Table<? extends Model> table : tables.values()) {
			table.Save();
		}
		for (Connection<? extends Model, ? extends Model> connection : connections) {
			connection.Save();
		}
		System.out.println("Database saved.");
	}
	
	public void Clear() {
		for (Table<? extends Model> table : tables.values()) {
			table.Clear();
		}
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
	@SuppressWarnings("unchecked")
	public Table<Guest> getGuests() {
		return (Table<Guest>) tables.get("guests");
	}

	/**
	 * @return the maids
	 */
	@SuppressWarnings("unchecked")
	public Table<Maid> getMaids() {
		return (Table<Maid>) tables.get("maids");
	}

	/**
	 * @return the receptionists
	 */
	@SuppressWarnings("unchecked")
	public Table<Receptionist> getReceptionists() {
		return (Table<Receptionist>) tables.get("receptionists");
	}

	/**
	 * @return the admins
	 */
	@SuppressWarnings("unchecked")
	public Table<Admin> getAdmins() {
		return (Table<Admin>) tables.get("admins");
	}

	/**
	 * @return the roomTypes
	 */
	@SuppressWarnings("unchecked")
	public Table<RoomType> getRoomTypes() {
		return (Table<RoomType>) tables.get("roomTypes");
	}

	/**
	 * @return the roomAdditions
	 */
	@SuppressWarnings("unchecked")
	public Table<RoomAddition> getRoomAdditions() {
		return (Table<RoomAddition>) tables.get("roomAdditions");
	}

	/**
	 * @return the reservationAdditions
	 */
	@SuppressWarnings("unchecked")
	public Table<ReservationAddition> getReservationAdditions() {
		return (Table<ReservationAddition>) tables.get("reservationAdditions");
	}

	/**
	 * @return the rooms
	 */
	@SuppressWarnings("unchecked")
	public Table<Room> getRooms() {
		return (Table<Room>) tables.get("rooms");
	}

	/**
	 * @return the reservations
	 */
	@SuppressWarnings("unchecked")
	public Table<Reservation> getReservations() {
		return (Table<Reservation>) tables.get("reservations");
	}

	/**
	 * @return the priceLists
	 */
	@SuppressWarnings("unchecked")
	public Table<PriceList> getPriceLists() {
		return (Table<PriceList>) tables.get("priceLists");
	}

	/**
	 * @return the instance
	 */
	public static Database getInstance() {
		return instance;
	}
}
