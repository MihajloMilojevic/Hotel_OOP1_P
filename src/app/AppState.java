package app;

import java.io.IOException;
import java.text.ParseException;

import database.Database;

public class AppState {

	
	private static AppState instance;
	
	private AppState() {
    }
	
	public void Load() throws IOException, ParseException {
		getSettings().Load();
		getDatabase().Load();
		System.out.println("App state loaded.");
	}
	
	public void Save() throws IOException, ParseException {
		getSettings().Save();
		getDatabase().Save();
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
		return AppSettings.getInstance();
	}

	/**
	 * @return the database
	 */
	public Database getDatabase() {
		return Database.getInstance(getSettings());
	}
}
