package app;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;

public class AppSettings {

	private HashMap<String, HashMap<String, String>> settings;
	private File settingsFile;
	private static final String settingsFilePath = "settings.ini";
	
	private static AppSettings instance;
	
	private AppSettings() {
		settingsFile = new File(settingsFilePath);
		try {
			File parent = settingsFile.getParentFile();
			if(parent != null && parent.isDirectory() && !parent.exists()) {
				parent.mkdirs();
			}
			settingsFile.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		settings = new HashMap<String, HashMap<String, String>>();
		
	}
	
	public void addSetting(String category, String key, String value) {
		HashMap<String, String> categorySettings = settings.get(category);
		if (categorySettings == null) {
			categorySettings = new HashMap<String, String>();
			settings.put(category, categorySettings);
		}
		categorySettings.put(key, value);
	}

	public void removeSetting(String category, String key) {
		HashMap<String, String> categorySettings = settings.get(category);
		if (categorySettings == null) {
			return;
		}
		categorySettings.remove(key);
	}
	
	public void removeCategory(String category) {
		settings.remove(category);
	}
	
	public void load() throws IOException {
		ArrayList<String> lines = (ArrayList<String>) Files.readAllLines(Path.of(settingsFile.getAbsolutePath()));
		HashMap<String, String> currentCategory = new HashMap<String, String>();
		String currentCategoryName = "defualt";
		settings.put(currentCategoryName, currentCategory);
		
		for (String line : lines) {
			line = line.trim();
			if (line.isBlank() || line.startsWith("#")) {
				continue;
			}
			if (line.startsWith("[")) {
				currentCategoryName = line.substring(1, line.length() - 1);
				currentCategory = new HashMap<String, String>();
				settings.put(currentCategoryName, currentCategory);
			} else {
				String[] parts = line.split("=");
				if (parts.length != 2) {
					continue;
				}
				currentCategory.put(parts[0].trim(), parts[1].trim());
			}
		}
	}
	
	public void save() throws IOException {
		if (settings.isEmpty()) {
			return;
		}
		ArrayList<String> lines = new ArrayList<String>();
		for (String key : settings.get("defualt").keySet()) {
			lines.add(key + " = " + settings.get("defualt").get(key));
		}
		for (String category : settings.keySet()) {
			if (category.equals("defualt")) {
				continue;
			}
			lines.add("");
			lines.add("[" + category + "]");
			lines.add("");
			HashMap<String, String> categorySettings = settings.get(category);
			for (String key : categorySettings.keySet()) {
				lines.add(key + " = " + categorySettings.get(key));
			}
		}
		Files.write(Path.of(settingsFile.getAbsolutePath()), lines, StandardCharsets.UTF_8);
		System.out.println("Settings saved.");
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

	public void test() {
        for (String category : settings.keySet()) {
            System.out.println("[" + category + "]");
            HashMap<String, String> categorySettings = settings.get(category);
            for (String key : categorySettings.keySet()) {
                System.out.println(key + " = " + categorySettings.get(key));
            }
        }
	}
}


/*

Manually create settings.ini file with the following content:

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
dbSettings.put("rooms_roomTypes_connection_file_path", "./data/rooms_roomTypes.csv");
dbSettings.put("rooms_roomAdditions_connection_file_path", "./data/rooms_roomAdditions.csv");
dbSettings.put("reservations_rooms_connection_file_path", "./data/reservations_rooms.csv");
dbSettings.put("reservations_guests_connection_file_path", "./data/reservations_guests.csv");
dbSettings.put("reservations_reservationAdditions_connection_file_path", "./data/reservations_reservationsAdditions.csv");
dbSettings.put("priceLists_roomTypes_connection_file_path", "./data/prices_roomTypes.csv");
dbSettings.put("priceLists_roomAdditions_connection_file_path", "./data/prices_roomAdditions.csv");
dbSettings.put("priceLists_reservationAdditions_connection_file_path", "./data/prices_reservationAdditions.csv");
settings.put("database", dbSettings);
*/