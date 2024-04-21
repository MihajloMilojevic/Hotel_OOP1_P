package main;

import java.io.IOException;
import java.text.ParseException;

import app.AppSettings;
import app.AppState;

public class Main {

	public static void main(String[] args) throws IOException, ParseException {
		AppState appState = AppState.getInstance();
		appState.Load();
		
		AppSettings settings = appState.getSettings();
		settings.AddSetting("General", "language", "en");
		settings.AddSetting("General", "theme", "dark");
		settings.AddSetting("General", "font", "Arial");
		settings.AddSetting("General", "fontSize", "12");
		settings.AddSetting("General", "autoSave", "true");
		settings.AddSetting("General", "autoSaveInterval", "5");
		settings.AddSetting("General", "lastOpenedFile", "data/test.txt");
		
		appState.Save();
	}
}
