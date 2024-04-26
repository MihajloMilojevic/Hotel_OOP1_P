package app;

import java.io.IOException;
import java.text.ParseException;

import database.Database;

public class AppState {

	
	private static AppState instance;
	
	private AppState() {
    }
	
	public void load() throws IOException, ParseException {
		getSettings().load();
		getDatabase().load();
		System.out.println("App state loaded.");
	}
	
	public void save() throws IOException, ParseException {
		getSettings().save();
		getDatabase().save();
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
