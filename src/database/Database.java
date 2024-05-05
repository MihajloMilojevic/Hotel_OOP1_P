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
import models.User;
import models.enums.UserRole;

public class Database {
	private HashMap<String, Table<? extends Model>> tables;
	private ArrayList<Connection<? extends Model, ? extends Model>> connections;

	private Database(AppSettings settings) {
		tables = new HashMap<String, Table<? extends Model>>();
		connections = new ArrayList<Connection<? extends Model, ? extends Model>>();

		/* ****************************** TABLES *************************************** */

		
		tables.put("users", new Table<User>("users_file_path", new CustomTableParser() {

			@Override
			public Model parse(String csvString) throws ParseException {
				String[] parts = csvString.split(";");
				UserRole role = UserRole.valueOf(parts[1]);
				switch (role) {
					case ADMIN:
						return new Admin().fromCSV(csvString);
					case RECEPTIONIST:
						return new Receptionist().fromCSV(csvString);
					case MAID:
						return new Maid().fromCSV(csvString);
					case GUEST:
						return new Guest().fromCSV(csvString);
					default:
						throw new ParseException("Invalid role", 0);
				}
					
			}

			@Override
			public String stringify(Model model) throws ParseException {
				return model.toString();
			}

			
		}));
		tables.put("roomTypes", new Table<RoomType>("room_types_file_path", new RoomType()));
		tables.put("roomAdditions", new Table<RoomAddition>("room_additions_file_path", new RoomAddition()));
		tables.put("reservationAdditions", new Table<ReservationAddition>("reservation_additions_file_path", new ReservationAddition()));
		tables.put("rooms", new Table<Room>("rooms_file_path", new Room()));
		tables.put("reservations", new Table<Reservation>("reservations_file_path", new Reservation()));
		tables.put("priceLists", new Table<PriceList>("price_lists_file_path", new PriceList()));

		/* ****************************** CONNECTIONS *************************************** */

		connections.add(new Connection<Room, RoomType>(getRooms(), getRoomTypes(),
				new File(settings.getSetting("database", "rooms_roomTypes_connection_file_path", "./data/default.csv")),
				new ConnectionActions<Room, RoomType>() {
					@Override
					public void load(Table<Room> table1, Table<RoomType> table2, String path)
							throws IOException, ParseException {
						List<String> lines = Files.readAllLines(Path.of(path), StandardCharsets.UTF_8);
						for (String line : lines) {
							String[] parts = line.split(";");
							if (parts.length != 2) {
								throw new ParseException("Invalid csv record", 0);
							}
							Room room = table1.selectById(parts[0]);
							RoomType roomType = table2.selectById(parts[1]);
							room.setType(roomType);
						}
					}

					@Override
					public void save(Table<Room> table1, Table<RoomType> table2, String path)
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
					public void load(Table<Room> table1, Table<RoomAddition> table2, String path)
							throws IOException, ParseException {
						List<String> lines = Files.readAllLines(Path.of(path), StandardCharsets.UTF_8);
						for (String line : lines) {
							String[] parts = line.split(";");
							if (parts.length != 2) {
								throw new ParseException("Invalid csv record", 0);
							}
							Room room = table1.selectById(parts[0]);
							RoomAddition roomAddition = table2.selectById(parts[1]);
							room.addRoomAddition(roomAddition);
						}
					}

					@Override
					public void save(Table<Room> table1, Table<RoomAddition> table2, String path)
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
		connections.add(new Connection<Room, User>(getRooms(), getUsers(),
				new File(settings.getSetting("database", "rooms_maids_connection_file_path", "./data/default.csv")),
				new ConnectionActions<Room, User>() {
					@Override
					public void load(Table<Room> table1, Table<User> table2, String path)
							throws IOException, ParseException {
						List<String> lines = Files.readAllLines(Path.of(path), StandardCharsets.UTF_8);
						for (String line : lines) {
							String[] parts = line.split(";");
							if (parts.length != 2) {
								throw new ParseException("Invalid csv record", 0);
							}
							Room room = table1.selectById(parts[0]);
							Maid maid = (Maid)table2.selectById(parts[1]);
							room.setMaid(maid);
						}
					}

					@Override
					public void save(Table<Room> table1, Table<User> table2, String path)
							throws IOException, ParseException {
						List<String> lines = new ArrayList<String>();
						for (Room room : table1.getRows()) {
							if (room.getMaid() == null)
								continue;
							lines.add(room.getId() + ";" + room.getMaid().getId());
						}
						Files.write(Path.of(path), lines, StandardCharsets.UTF_8);
					}
				}));
		connections.add(new Connection<Reservation, Room>(
				getReservations(), getRooms(), new File(settings.getSetting("database",
						"reservations_rooms_connection_file_path", "./data/default.csv")),
				new ConnectionActions<Reservation, Room>() {
					@Override
					public void load(Table<Reservation> table1, Table<Room> table2, String path)
							throws IOException, ParseException {
						List<String> lines = Files.readAllLines(Path.of(path), StandardCharsets.UTF_8);
						for (String line : lines) {
							String[] parts = line.split(";");
							if (parts.length != 2) {
								throw new ParseException("Invalid csv record", 0);
							}
							Reservation reservation = table1.selectById(parts[0]);
							Room room = table2.selectById(parts[1]);
							reservation.setRoom(room);
						}
					}

					@Override
					public void save(Table<Reservation> table1, Table<Room> table2, String path)
							throws IOException, ParseException {
						List<String> lines = new ArrayList<String>();
						for (Reservation reservation : table1.getRows()) {
							lines.add(reservation.getId() + ";" + reservation.getRoom().getId());
						}
						Files.write(Path.of(path), lines, StandardCharsets.UTF_8);
					}
				}));
		connections.add(new Connection<Reservation, User>(
				getReservations(), getUsers(), new File(settings.getSetting("database",
						"reservations_guests_connection_file_path", "./data/default.csv")),
				new ConnectionActions<Reservation, User>() {
					@Override
					public void load(Table<Reservation> table1, Table<User> table2, String path)
							throws IOException, ParseException {
						List<String> lines = Files.readAllLines(Path.of(path), StandardCharsets.UTF_8);
						for (String line : lines) {
							String[] parts = line.split(";");
							if (parts.length != 2) {
								throw new ParseException("Invalid csv record", 0);
							}
							Reservation reservation = table1.selectById(parts[0]);
							Guest guest = (Guest)table2.selectById(parts[1]);
							reservation.setGuest(guest);
						}
					}

					@Override
					public void save(Table<Reservation> table1, Table<User> table2, String path)
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
					public void load(Table<Reservation> table1, Table<ReservationAddition> table2, String path)
							throws IOException, ParseException {
						List<String> lines = Files.readAllLines(Path.of(path), StandardCharsets.UTF_8);
						for (String line : lines) {
							String[] parts = line.split(";");
							if (parts.length != 2) {
								throw new ParseException("Invalid csv record", 0);
							}
							Reservation reservation = table1.selectById(parts[0]);
							ReservationAddition reservationAddition = table2.selectById(parts[1]);
							reservation.addReservationAddition(reservationAddition);
						}
					}

					@Override
					public void save(Table<Reservation> table1, Table<ReservationAddition> table2, String path)
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
					public void load(Table<PriceList> table1, Table<RoomType> table2, String path)
							throws IOException, ParseException {
						List<String> lines = Files.readAllLines(Path.of(path), StandardCharsets.UTF_8);
						for (String line : lines) {
							String[] parts = line.split(";");
							if (parts.length != 3) {
								throw new ParseException("Invalid csv record", 0);
							}
							PriceList priceList = table1.selectById(parts[0]);
							RoomType roomType = table2.selectById(parts[1]);
							priceList.getRoomTypePrices().put(roomType, Double.parseDouble(parts[2]));
						}
					}

					@Override
					public void save(Table<PriceList> table1, Table<RoomType> table2, String path)
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

		connections.add(new Connection<PriceList, ReservationAddition>(
				getPriceLists(), getReservationAdditions(), new File(settings.getSetting("database",
						"priceLists_reservationAdditions_connection_file_path", "./data/default.csv")),
				new ConnectionActions<PriceList, ReservationAddition>() {
					@Override
					public void load(Table<PriceList> table1, Table<ReservationAddition> table2, String path)
							throws IOException, ParseException {
						List<String> lines = Files.readAllLines(Path.of(path), StandardCharsets.UTF_8);
						for (String line : lines) {
							String[] parts = line.split(";");
							if (parts.length != 3) {
								throw new ParseException("Invalid csv record", 0);
							}
							PriceList priceList = table1.selectById(parts[0]);
							ReservationAddition reservationAddition = table2.selectById(parts[1]);
							priceList.getReservationAdditionPrices().put(reservationAddition,
									Double.parseDouble(parts[2]));
						}
					}

					@Override
					public void save(Table<PriceList> table1, Table<ReservationAddition> table2, String path)
							throws IOException, ParseException {
						List<String> lines = new ArrayList<String>();
						for (PriceList priceList : table1.getRows()) {
							for (ReservationAddition reservationAddition : priceList.getReservationAdditionPrices()
									.keySet()) {
								lines.add(priceList.getId() + ";" + reservationAddition.getId() + ";" + Double
										.toString(priceList.getReservationAdditionPrices().get(reservationAddition)));
							}
						}
						Files.write(Path.of(path), lines, StandardCharsets.UTF_8);
					}
				}));

		/* ****************************** INDECIES *************************************** */
		getUsers().addIndex("username");
		getRooms().addIndex("number");
		getRoomTypes().addIndex("name");
		getRoomAdditions().addIndex("name");
		getReservationAdditions().addIndex("name");
	}

	public void load() throws IOException, ParseException {
		for (Table<? extends Model> table : tables.values()) {
			table.load();
		}
		for (Connection<? extends Model, ? extends Model> connection : connections) {
			connection.load();
		}
	}

	public void save() throws IOException, ParseException {
		for (Table<? extends Model> table : tables.values()) {
			table.save();
		}
		for (Connection<? extends Model, ? extends Model> connection : connections) {
			connection.save();
		}
		System.out.println("Database saved.");
	}

	public void clear() {
		for (Table<? extends Model> table : tables.values()) {
			table.clear();
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
	 * @return the users
	 */
	@SuppressWarnings("unchecked")
	public Table<User> getUsers() {
		return (Table<User>) tables.get("users");
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
		if (instance == null) {
			instance = new Database(AppSettings.getInstance());
		}
		return instance;
	}
}
