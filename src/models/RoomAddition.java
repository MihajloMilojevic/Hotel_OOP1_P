package models;

public class RoomAddition extends Model {
	private long id;
	private String name;	
	private static long lastId = 0;
	
	public RoomAddition(String name) {
		this.id = ++lastId;
        this.name = name;
    }
	public RoomAddition(long id, String name) {
		this.id = id;
		this.name = name;
	}
	@Override
	public Object get(String key) throws IllegalArgumentException {
		switch (key) {
			case "id":
				return getId();
			case "name":
				return getName();
			default:
				throw new IllegalArgumentException("Invalid key: " + key);
		}
	}
	@Override
	public void set(String key, Object value) {
		switch (key) {
		case "name":
			setName((String) value);
			break;
		default:
			throw new IllegalArgumentException("Invalid key: " + key);
		}
	}
	@Override
	public String toString() {
		return String.join(";", new String[] {String.valueOf(id), name});
	}
	@Override
	public RoomAddition clone() {
		return new RoomAddition(id, name);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || obj.getClass() != this.getClass()) {
			return false;
		}
		RoomAddition roomAddition = (RoomAddition) obj;
		return (this.getId() == roomAddition.getId() && this.getName().equals(roomAddition.getName()));
	}
	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
}
