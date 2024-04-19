package app;

import database.Database;

public class AppState {

	private AppSettings settings;
	private Database database;
	
	private static AppState instance;
	
	private AppState() {
		this.settings = AppSettings.getInstance();
        this.database = Database.getInstance(settings);
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
