package main;

import javax.swing.JOptionPane;

import app.AppState;
import database.InitialDatabase;
import views.Login;

public class Main {

	public static void main(String[] args) {
		try {
			boolean init = false;
			// init = true;
			if(init) {
				InitialDatabase.init();
				return;
			}
			
			AppState.getInstance().load();
			Login frame = new Login();
			frame.setVisible(true);
			System.out.println("GUI started");
			
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}
}
