package main;

import java.io.IOException;
import java.text.ParseException;

import app.AppSettings;
import app.AppState;

public class Main {

	public static void main(String[] args) throws IOException, ParseException {
		AppState appState = AppState.getInstance();
		appState.Load();
		
		
		
		appState.Save();
	}
}
