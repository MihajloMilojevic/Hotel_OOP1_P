package models;

import java.util.ArrayList;

import models.enums.RoomStatus;

public class Room extends Model {
	public int number;
	public RoomType type;
	public RoomStatus status;
	public ArrayList<RoomAddition> roomAdditions;
	
	public Room() {
		this.number = 0;
		this.type = null;
		this.status = RoomStatus.FREE;
		this.roomAdditions = new ArrayList<RoomAddition>();
	}

	public Room(int number, RoomType type, RoomStatus status, ArrayList<RoomAddition> roomAdditions) {
		this.number = number;
		this.type = type;
		this.status = status;
		this.roomAdditions = roomAdditions;
	}

	public Room(int number, RoomType type, RoomStatus status) {
		this.number = number;
		this.type = type;
		this.status = status;
		this.roomAdditions = new ArrayList<RoomAddition>();
	}
	public Room(int number, RoomType type, String status, ArrayList<RoomAddition> roomAdditions) {
		this.number = number;
		this.type = type;
		this.status = RoomStatus.valueOf(status);
		this.roomAdditions = roomAdditions;
	}

	public Room(int number, RoomType type, String status) {
		this.number = number;
		this.type = type;
		this.status = RoomStatus.valueOf(status);
		this.roomAdditions = new ArrayList<RoomAddition>();
	}
	
	@Override
	public Object get(String key) throws IllegalArgumentException {
		switch (key) {
			case "number":
				return (Object) this.number;
			case "type":
				return (Object) this.type;
			case "status":
				return (Object) this.status;
			default:
				throw new IllegalArgumentException("Invalid key");
		}
	}
	@Override
	public void set(String key, Object value) {
		switch (key) {
		case "number":
			this.number = (int) value;
			break;
		case "type":
			this.type = (RoomType) value;
			break;
		case "status":
			this.status = (RoomStatus) value;
			break;
		default:
			throw new IllegalArgumentException("Invalid key");
		}
	}
	
	public void addRoomAddition(RoomAddition roomAddition) {
		this.roomAdditions.add(roomAddition);
	}

	public void removeRoomAddition(RoomAddition roomAddition) {
		this.roomAdditions.remove(roomAddition);
	}
	@Override
	public String toString() {
		return String.join(";", new String[] { 
				String.valueOf(number), 
				type.getName(), 
				status.toString(),
				String.join(",", roomAdditions.stream().map(RoomAddition::getName).toArray(String[]::new))
			});
	}
	@Override
	public Room clone() {
		return new Room(number, type, status, roomAdditions);
	}
    @Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || obj.getClass() != this.getClass()) {
			return false;
		}
		Room room = (Room) obj;
		return (this.number == room.number && this.type.equals(room.type) && this.status.equals(room.status)
				&& this.roomAdditions.equals(room.roomAdditions));
	}
	/**
	 * @return the number
	 */
	public int getNumber() {
		return number;
	}

	/**
	 * @return the type
	 */
	public RoomType getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(RoomType type) {
		this.type = type;
	}

	/**
	 * @return the status
	 */
	public RoomStatus getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(RoomStatus status) {
		this.status = status;
	}

	/**
	 * @return the roomAdditions
	 */
	public ArrayList<RoomAddition> getRoomAdditions() {
		return roomAdditions;
	}

	/**
	 * @param roomAdditions the roomAdditions to set
	 */
	public void setRoomAdditions(ArrayList<RoomAddition> roomAdditions) {
		this.roomAdditions = roomAdditions;
	}

}
