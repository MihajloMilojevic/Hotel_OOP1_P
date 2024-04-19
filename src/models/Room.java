package models;

import java.text.ParseException;
import java.util.ArrayList;

import models.enums.RoomStatus;

public class Room extends Model {
	
	/* ******************************  ATTRIBUTES  *************************************** */
	
	private int number;
	private RoomType type;
	private RoomStatus status;
	private ArrayList<RoomAddition> roomAdditions;
	
	/* ******************************  CONSTRUCTORS  *************************************** */	
	
	public Room() {
		super();
		this.number = 0;
		this.type = null;
		this.status = RoomStatus.FREE;
		this.roomAdditions = new ArrayList<RoomAddition>();
	}
	
	public Room(String id) {
		super(id);
		this.number = 0;
		this.type = null;
		this.status = RoomStatus.FREE;
		this.roomAdditions = new ArrayList<RoomAddition>();
	}

	public Room(int number, RoomType type, RoomStatus status, ArrayList<RoomAddition> roomAdditions) {
		super();
		this.number = number;
		this.type = type;
		this.status = status;
		this.roomAdditions = roomAdditions;
	}
	
	public Room(String id, int number, RoomType type, RoomStatus status, ArrayList<RoomAddition> roomAdditions) {
		super(id);
		this.number = number;
		this.type = type;
		this.status = status;
		this.roomAdditions = roomAdditions;
	}

	public Room(int number, RoomType type, RoomStatus status) {
		super();
		this.number = number;
		this.type = type;
		this.status = status;
		this.roomAdditions = new ArrayList<RoomAddition>();
	}
	public Room(String id, int number, RoomType type, RoomStatus status) {
		super(id);
		this.number = number;
		this.type = type;
		this.status = status;
		this.roomAdditions = new ArrayList<RoomAddition>();
	}
	public Room(int number, RoomType type, String status, ArrayList<RoomAddition> roomAdditions) {
		super();
		this.number = number;
		this.type = type;
		this.status = RoomStatus.valueOf(status);
		this.roomAdditions = roomAdditions;
	}
	public Room(String id, int number, RoomType type, String status, ArrayList<RoomAddition> roomAdditions) {
		super(id);
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
	
	/* ******************************  METHODS  *************************************** */

	public void addRoomAddition(RoomAddition roomAddition) {
		this.roomAdditions.add(roomAddition);
	}

	public void removeRoomAddition(RoomAddition roomAddition) {
		this.roomAdditions.remove(roomAddition);
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
				return super.get(key);
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
			super.set(key, value);
		}
	}
	
	@Override
	public String toString() {
		return String.join(";", new String[] { 
				super.toString(),
				String.valueOf(number), 
				status.toString()
			});
	}
	@Override
	public Object clone() throws CloneNotSupportedException {
		ArrayList<RoomAddition> roomAdditionsClone = new ArrayList<RoomAddition>();
		for (RoomAddition roomAddition : this.roomAdditions) {
			roomAdditions.add((RoomAddition)roomAddition.clone());
		}
		return new Room(id, number, (RoomType)type.clone(), status, roomAdditionsClone);
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
    @Override
	public Model fromCSV(String csv) throws ParseException {
		super.fromCSV(csv);
		String[] values = csv.split(";");
		if (values.length < 3) throw new ParseException("Invalid RoomType string", 1);
		this.number = Integer.parseInt(values[1]);
		this.status = RoomStatus.valueOf(values[2]);
		return this;
	}
    
    
    /* ******************************  GETTERS AND SETTERS  *************************************** */
    
    
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
