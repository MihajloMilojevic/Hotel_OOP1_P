package main;

import app.AppState;
import views.KT2;
import views.Login;

public class Main {

	public static void main(String[] args) {
		try {
			
			
			/* ********************************** Kontrolna Tacka 2 ********************************** */
			
			new KT2();
			
			
			/* ********************************** GUI ********************************** */
			AppState.getInstance().getDatabase().clear();
			AppState.getInstance().getDatabase().save();
			AppState.getInstance().load();
			Login frame = new Login();
			frame.setVisible(true);
			System.out.println("GUI started");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
