package controllers;

import app.AppState;
import controllers.enums.LoginStatus;
import database.Database;
import models.User;

public class UserController {

	public static LoginStatus login(String username, String password) {
		Database db = AppState.getInstance().getDatabase();
        User user = (User) db.getUsers().selectByIndex("username", username);
        if (user == null) {
            return LoginStatus.NO_USER;
        }
		if (!user.getPassword().equals(password)) {
			return LoginStatus.WRONG_PASSWORD;
		}
		AppState.getInstance().setUser(user);
		return LoginStatus.SUCCESS;
    }
}
