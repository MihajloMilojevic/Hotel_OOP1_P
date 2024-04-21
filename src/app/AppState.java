package app;

import java.io.IOException;
import java.text.ParseException;

import database.Database;

public class AppState {

	private AppSettings settings;
	private Database database;
	
	private static AppState instance;
	
	private AppState() {
		this.settings = AppSettings.getInstance();
        this.database = Database.getInstance(settings);
    }
	
	public void Load() throws IOException, ParseException {
		settings.Load();
		database.Load();
		System.out.println("App state loaded.");
	}
	
	public void Save() throws IOException, ParseException {
		settings.Save();
		database.Save();
		System.out.println("App state saved.");
	}

	public static AppState getInstance() {
		if (instance == null) {
			instance = new AppState();
		}
		return instance;
	}

	/**
	 * @return the settings
	 */
	public AppSettings getSettings() {
		return settings;
	}

	/**
	 * @return the database
	 */
	public Database getDatabase() {
		return database;
	}
}
