package controllers;

import java.util.ArrayList;

import app.AppState;
import database.SelectCondition;
import exceptions.DuplicateIndexException;
import exceptions.NoElementException;
import models.Model;
import models.Room;
import models.RoomAddition;
import models.RoomType;

public class RoomController {

	public static ArrayList<Room> getRooms() {
		ArrayList<Room> rooms = AppState.getInstance().getDatabase().getRooms().select(new SelectCondition() {
			
			@Override
			public boolean check(Model row) {
				return !row.isDeleted();
			}
		});
		rooms.sort((r1, r2) -> r1.getNumber() - r2.getNumber());
		return rooms;
	}

	public static void addRoom(Room room) throws DuplicateIndexException { 
		AppState.getInstance().getDatabase().getRooms().insert(room);
	}

	public static void updateRoom(Room room) throws NoElementException {
		AppState.getInstance().getDatabase().getRooms().update(room);
	}

	public static void deleteRoom(Room room) throws NoElementException {
		AppState.getInstance().getDatabase().getRooms().delete(room);
	}

	public static ArrayList<RoomType> getRoomTypes() {
		return AppState.getInstance().getDatabase().getRoomTypes().getRows();
	}
	public static ArrayList<RoomAddition> getRoomAdditions() {
		return AppState.getInstance().getDatabase().getRoomAdditions().getRows();
	}
	public static RoomAddition getRoomAdditionByName(String name) {
		return AppState.getInstance().getDatabase().getRoomAdditions().selectByIndex("name", name);
	}
}
