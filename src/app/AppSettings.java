package app;

import java.util.HashMap;

public class AppSettings {

	private HashMap<String, HashMap<String, String>> settings;
	
	private static AppSettings instance;
	
	private AppSettings() {
		settings = new HashMap<String, HashMap<String, String>>();
		HashMap<String, String> dbSettings = new HashMap<String, String>();
		dbSettings.put("guests_file_path", "./data/guests.csv");
		dbSettings.put("maids_file_path", "./data/maids.csv");
		dbSettings.put("receptionists_file_path", "./data/receptionists.csv");
		dbSettings.put("admins_file_path", "./data/admins.csv");
		dbSettings.put("room_types_file_path", "./data/room_types.csv");
		dbSettings.put("room_additions_file_path", "./data/room_additions.csv");
		dbSettings.put("reservation_additions_file_path", "./data/reservation_additions.csv");
		dbSettings.put("rooms_file_path", "./data/rooms.csv");
		dbSettings.put("reservations_file_path", "./data/reservations.csv");
		dbSettings.put("price_lists_file_path", "./data/price_lists.csv");
		settings.put("database", dbSettings);
	}
	
	public String getSetting(String category, String key, String defaultValue) {
		HashMap<String, String> categorySettings = settings.get(category);
		if (categorySettings == null) {
			return defaultValue;
		}
		String value = categorySettings.get(key);
		if (value == null) {
			return defaultValue;
		}
		return value;
	}
	
	public static AppSettings getInstance() {
		if (instance == null) {
			instance = new AppSettings();
		}
		return instance;
	}

}
