package controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import app.AppState;
import controllers.enums.LoginStatus;
import database.Database;
import database.SelectCondition;
import exceptions.DuplicateIndexException;
import exceptions.NoElementException;
import models.Employee;
import models.Model;
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

	public static void logout() {
		AppState.getInstance().setUser(null);
	}

	public static ArrayList<Employee> getEmployees() {
		List<Employee> fromDb = AppState.getInstance().getDatabase().getUsers().select(new SelectCondition() {

			@Override
			public boolean check(Model row) {
				return row instanceof Employee;
			}
            
        }).stream().map(row -> (Employee) row).toList();
		ArrayList<Employee> employees = new ArrayList<Employee>();
		employees.addAll(fromDb);
		Collections.sort(employees, (o1, o2) -> o2.getRole().compareTo(o1.getRole()));
		return employees;
	}

	public static void deleteUser(User user) {
		AppState.getInstance().getDatabase().getUsers().delete(user);
	}

	public static void updateUser(User user) throws NoElementException {
		AppState.getInstance().getDatabase().getUsers().update(user);
	}

	public static void addUser(User user) throws DuplicateIndexException {
		AppState.getInstance().getDatabase().getUsers().insert(user);
	}
}
