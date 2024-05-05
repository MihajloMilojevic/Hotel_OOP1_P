package main;

import app.AppState;
import views.Login;

public class Main {

	public static void main(String[] args) {
		try {
			//InitialDatabase.init();
			
			AppState.getInstance().load();
			
			Login frame = new Login();
			frame.setVisible(true);
			System.out.println("GUI started");
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
