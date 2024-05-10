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

	public static void addRoomAddition(RoomAddition roomAddition) throws DuplicateIndexException { 
		AppState.getInstance().getDatabase().getRoomAdditions().insert(roomAddition);
	}

	public static void updateRoomAddition(RoomAddition roomAddition) throws NoElementException {
		ArrayList<Room> rooms = AppState.getInstance().getDatabase().getRooms().select(new SelectCondition() {

			@Override
			public boolean check(Model row) {
				Room room = (Room) row;
				return room.getRoomAdditions().stream().map(RoomAddition::getId).toList().contains(roomAddition.getId());
			}
		});
		rooms.forEach(room -> {
			ArrayList<RoomAddition> newRoomAdditions = new ArrayList<>();
			for (RoomAddition ra : room.getRoomAdditions()) {
				if (ra.getId().equals(roomAddition.getId())) {
					newRoomAdditions.add(roomAddition);
				} else {
					newRoomAdditions.add(ra);
				}
			}
			room.setRoomAdditions(newRoomAdditions);
			try {
				updateRoom(room);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		AppState.getInstance().getDatabase().getRoomAdditions().update(roomAddition);
	}

	public static void deleteRoomAddition(RoomAddition roomAddition) throws NoElementException {
		AppState.getInstance().getDatabase().getRooms().select(new SelectCondition() {

			@Override
			public boolean check(Model row) {
				Room room = (Room) row;
				return room.getRoomAdditions().contains(roomAddition);
			}
		}).forEach(room -> {
			room.removeRoomAddition(roomAddition);
			try {
				updateRoom(room);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		AppState.getInstance().getDatabase().getRoomAdditions().delete(roomAddition);
	}

	public static void addRoomType(RoomType roomType) throws DuplicateIndexException {
		AppState.getInstance().getDatabase().getRoomTypes().insert(roomType);
	}

	public static void updateRoomType(RoomType roomType) throws NoElementException {
		AppState.getInstance().getDatabase().getRoomTypes().update(roomType);
	}
	
	public static boolean deleteRoomType(RoomType roomType) {
		if (AppState.getInstance().getDatabase().getRooms().select(new SelectCondition() {

			@Override
			public boolean check(Model row) {
				Room room = (Room) row;
				return room.getType().equals(roomType);
			}
		}).size() > 0) {
			return false;
		}
		AppState.getInstance().getDatabase().getRoomTypes().delete(roomType);
		return true;
	}
	
 	public static ArrayList<RoomType> getRoomTypes() {
		return AppState.getInstance().getDatabase().getRoomTypes().select(new SelectCondition() {

			@Override
			public boolean check(Model row) {
				return !row.isDeleted();
			}
		});
	}
	public static ArrayList<RoomAddition> getRoomAdditions() {
		return AppState.getInstance().getDatabase().getRoomAdditions().select(new SelectCondition() {

			@Override
			public boolean check(Model row) {
				return !row.isDeleted();
			}
		});
	}
	public static RoomAddition getRoomAdditionByName(String name) {
		return AppState.getInstance().getDatabase().getRoomAdditions().selectByIndex("name", name);
	}
}
