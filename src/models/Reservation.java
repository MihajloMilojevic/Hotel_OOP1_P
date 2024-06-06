package models;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;

import models.enums.ReservationStatus;
import utils.CSVDateParser;

public class Reservation extends Model {

	/*
	 * ****************************** ATTRIBUTES
	 * ***************************************
	 */

	private ReservationStatus status;
	private RoomType roomType;
	private Guest guest;
	private LocalDate startDate;
	private LocalDate endDate;
	private double price;
	private ArrayList<ReservationAddition> reservationAdditions;
	private ArrayList<RoomAddition> roomAdditions;
	private int numberOfGuests;
	private Room room;

	/*
	 * ****************************** CONSTRUCTORS
	 * ***************************************
	 */

	public Reservation() {
		super();
		this.status = ReservationStatus.PENDING;
		this.roomType = null;
		this.guest = null;
		this.startDate = null;
		this.endDate = null;
		this.price = 0;
		this.reservationAdditions = new ArrayList<ReservationAddition>();
		this.roomAdditions = new ArrayList<RoomAddition>();
		this.numberOfGuests = 0;
		this.room = null;
	}

	public Reservation(String id) {
		super(id);
		this.status = ReservationStatus.PENDING;
		this.roomType = null;
		this.guest = null;
		this.startDate = null;
		this.endDate = null;
		this.price = 0;
		this.reservationAdditions = new ArrayList<ReservationAddition>();
		this.roomAdditions = new ArrayList<RoomAddition>();
		this.numberOfGuests = 0;
		this.room = null;
	}

	public Reservation(RoomType roomType, Guest guest, LocalDate startDate, LocalDate endDate, double price,
			int numberOfGuests, ArrayList<ReservationAddition> reservationAdditions,
			ArrayList<RoomAddition> roomAdditions) {
		super();
		this.status = ReservationStatus.PENDING;
		this.roomType = roomType;
		this.guest = guest;
		this.startDate = startDate;
		this.endDate = endDate;
		this.price = price;
		this.reservationAdditions = reservationAdditions;
		this.roomAdditions = roomAdditions;
		this.numberOfGuests = numberOfGuests;
		this.room = null;
	}

	public Reservation(String id, RoomType roomType, Guest guest, LocalDate startDate, LocalDate endDate, double price,
			int numberOfGuests, ArrayList<ReservationAddition> reservationAdditions,
			ArrayList<RoomAddition> roomAdditions) {
		super(id);
		this.status = ReservationStatus.PENDING;
		this.roomType = roomType;
		this.guest = guest;
		this.startDate = startDate;
		this.endDate = endDate;
		this.price = price;
		this.reservationAdditions = reservationAdditions;
		this.roomAdditions = roomAdditions;
		this.numberOfGuests = numberOfGuests;
		this.room = null;
	}

	public Reservation(RoomType roomType, Guest guest, LocalDate startDate, LocalDate endDate, double price,
			int numberOfGuests) {
		super();
		this.status = ReservationStatus.PENDING;
		this.roomType = roomType;
		this.guest = guest;
		this.startDate = startDate;
		this.endDate = endDate;
		this.price = price;
		this.reservationAdditions = new ArrayList<ReservationAddition>();
		this.roomAdditions = new ArrayList<RoomAddition>();
		this.numberOfGuests = numberOfGuests;
		this.room = null;
	}

	public Reservation(String id, RoomType roomType, Guest guest, LocalDate startDate, LocalDate endDate, double price,
			int numberOfGuests) {
		super(id);
		this.status = ReservationStatus.PENDING;
		this.roomType = roomType;
		this.guest = guest;
		this.startDate = startDate;
		this.endDate = endDate;
		this.price = price;
		this.reservationAdditions = new ArrayList<ReservationAddition>();
		this.roomAdditions = new ArrayList<RoomAddition>();
		this.numberOfGuests = numberOfGuests;
		this.room = null;
	}

	public Reservation(RoomType roomType, Guest guest, LocalDate startDate, LocalDate endDate, int numberOfGuests) {
		super();
		this.status = ReservationStatus.PENDING;
		this.roomType = roomType;
		this.guest = guest;
		this.startDate = startDate;
		this.endDate = endDate;
		this.price = 0;
		this.reservationAdditions = new ArrayList<ReservationAddition>();
		this.roomAdditions = new ArrayList<RoomAddition>();
		this.numberOfGuests = numberOfGuests;
		this.room = null;
	}

	public Reservation(String id, RoomType roomType, Guest guest, LocalDate startDate, LocalDate endDate,
			int numberOfGuests) {
		super(id);
		this.status = ReservationStatus.PENDING;
		this.roomType = roomType;
		this.guest = guest;
		this.startDate = startDate;
		this.endDate = endDate;
		this.price = 0;
		this.reservationAdditions = new ArrayList<ReservationAddition>();
		this.roomAdditions = new ArrayList<RoomAddition>();
		this.numberOfGuests = numberOfGuests;
		this.room = null;
	}

	/*
	 * ****************************** METHODS
	 * ***************************************
	 */

	@Override
	public boolean isValid() {
		if (this.roomType == null || !this.roomType.isValid())
			return false;
		if (this.guest == null || !this.guest.isValid())
			return false;
		if (this.startDate == null || this.endDate == null || this.startDate.isAfter(this.endDate))
			return false;
		if (this.price < 0)
			return false;
		if (this.reservationAdditions == null)
			return false;
		if (this.numberOfGuests <= 0)
			return false;
		if (this.room != null && !this.room.isValid())
			return false;
		boolean containsExtraBed = false;
		for (ReservationAddition reservationAddition : this.reservationAdditions) {
			if (!reservationAddition.isValid())
				return false;
			if (reservationAddition.getName().equals("Extra Bed"))
				containsExtraBed = true;
		}
		if (this.roomAdditions == null)
			return false;
		for (RoomAddition roomAddition : this.roomAdditions) {
			if (!roomAddition.isValid())
				return false;
		}
		if (containsExtraBed && this.numberOfGuests > this.roomType.getMaxCapacity() + 1)
			return false;
		return super.isValid();
	}

	@Override
	public Object get(String key) throws IllegalArgumentException {
		switch (key) {
		case "status":
			return (Object) getStatus();
		case "roomTyoe":
			return (Object) getRoomType();
		case "guest":
			return (Object) getGuest();
		case "startDate":
			return (Object) getStartDate();
		case "endDate":
			return (Object) getEndDate();
		case "price":
			return (Object) getPrice();
		case "reservationAdditions":
			return (Object) getReservationAdditions();
		case "roomAdditions":
			return (Object) getRoomAdditions();
		case "numberOfGuests":
			return (Object) getNumberOfGuests();
		case "room":
			return (Object) getRoom();
		default:
			return super.get(key);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void set(String key, Object value) throws IllegalArgumentException {
		switch (key) {
		case "status":
			setStatus((ReservationStatus) value);
			break;
		case "roomType":
			setRoomType((RoomType) value);
			break;
		case "guest":
			setGuest((Guest) value);
			break;
		case "startDate":
			setStartDate((LocalDate) value);
			break;
		case "endDate":
			setEndDate((LocalDate) value);
			break;
		case "price":
			setPrice((double) value);
			break;
		case "reservationAdditions":
			setReservationAdditions((ArrayList<ReservationAddition>) value);
			break;
		case "roomAdditions":
			setRoomAdditions(roomAdditions);
			break;
		case "numberOfGuests":
			setNumberOfGuests((int) value);
			break;
		case "room":
			setRoom((Room) value);
			break;
		default:
			super.set(key, value);
		}
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		ArrayList<ReservationAddition> reservationAdditionsClone = new ArrayList<ReservationAddition>();
		for (ReservationAddition reservationAddition : getReservationAdditions()) {
			reservationAdditionsClone.add((ReservationAddition) reservationAddition.clone());
		}
		ArrayList<RoomAddition> roomAdditionsClone = new ArrayList<RoomAddition>();
		for (RoomAddition roomAddition : getRoomAdditions()) {
			roomAdditionsClone.add((RoomAddition) roomAddition.clone());
		}
		Reservation r = new Reservation(getId(), getRoomType() != null ? (RoomType) getRoomType().clone() : null,
				getGuest() != null ? (Guest) getGuest().clone() : null,
				getStartDate() != null ? LocalDate.from(getStartDate()) : null,
				getEndDate() != null ? LocalDate.from(getEndDate()) : null, getPrice(), getNumberOfGuests(),
				reservationAdditionsClone, roomAdditionsClone);
		r.setStatus(getStatus());
		r.setRoom(getRoom() != null ? (Room) getRoom().clone() : null);
		if (this.isDeleted())
			r.delete();
		return r;
	}

	@Override
	public String toString() {
		return String.join(";",
				new String[] { super.toString(), getStatus().toString(), CSVDateParser.formatDate(getStartDate()),
						CSVDateParser.formatDate(getEndDate()), String.valueOf(getPrice()),
						String.valueOf(getNumberOfGuests()) });
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		Reservation other = (Reservation) obj;
		return status == other.status && roomType.equals(other.roomType) && guest.equals(other.guest)
				&& startDate.equals(other.startDate) && endDate.equals(other.endDate) && price == other.price
				&& numberOfGuests == other.numberOfGuests && reservationAdditions.equals(other.reservationAdditions)
				&& (room == null ? other.room == null : room.equals(other.room));
	}

	@Override
	public void update(Model model) throws IllegalArgumentException {
		super.update(model);
		if (!(model instanceof Reservation)) {
			throw new IllegalArgumentException("Not a Reservation instance");
		}
		Reservation reservation = (Reservation) model;
		setStatus(reservation.getStatus());
		setRoomType(reservation.getRoomType());
		setGuest(reservation.getGuest());
		setStartDate(reservation.getStartDate());
		setEndDate(reservation.getEndDate());
		setPrice(reservation.getPrice());
		setNumberOfGuests(reservation.getNumberOfGuests());
		setRoomAdditions(reservation.getRoomAdditions());
		setRoom(reservation.getRoom());
		setReservationAdditions(reservation.getReservationAdditions());
	}

	@Override
	public Model fromCSV(String csv) throws ParseException {
		super.fromCSV(csv);
		String[] values = csv.split(";");
		if (values.length < 7)
			throw new ParseException("Invalid Reservation csv string", 1);
		String.valueOf(getPrice());
		this.status = ReservationStatus.valueOf(values[2]);
		this.startDate = CSVDateParser.parseString(values[3]);
		this.endDate = CSVDateParser.parseString(values[4]);
		this.price = Double.parseDouble(values[5]);
		this.numberOfGuests = Integer.parseInt(values[6]);
		return this;
	}

	public void addReservationAddition(ReservationAddition reservationAddition) {
		this.reservationAdditions.add(reservationAddition);
	}

	public void removeReservationAddition(ReservationAddition reservationAddition) {
		this.reservationAdditions.remove(reservationAddition);
	}

	public void addRoomAddition(RoomAddition roomAddition) {
		this.roomAdditions.add(roomAddition);
	}

	public void removeRoomAddition(RoomAddition roomAddition) {
		this.roomAdditions.remove(roomAddition);
	}

	/*
	 * ****************************** GETTERS & SETTERS
	 * ***************************************
	 */

	/**
	 * @return the status
	 */
	public ReservationStatus getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(ReservationStatus status) {
		this.status = status;
	}

	/**
	 * @return the room
	 */
	public RoomType getRoomType() {
		return roomType;
	}

	/**
	 * @param room the room to set
	 */
	public void setRoomType(RoomType roomType) {
		this.roomType = roomType;
	}

	/**
	 * @return the guest
	 */
	public Guest getGuest() {
		return guest;
	}

	/**
	 * @param guest the guest to set
	 */
	public void setGuest(Guest guest) {
		this.guest = guest;
	}

	/**
	 * @return the startDate
	 */
	public LocalDate getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the endDate
	 */
	public LocalDate getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the price
	 */
	public double getPrice() {
		return price;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(double price) {
		this.price = price;
	}

	/**
	 * @return the reservationAdditions
	 */
	public ArrayList<ReservationAddition> getReservationAdditions() {
		return reservationAdditions;
	}

	/**
	 * @param reservationAdditions the reservationAdditions to set
	 */
	public void setReservationAdditions(ArrayList<ReservationAddition> reservationAdditions) {
		this.reservationAdditions = reservationAdditions;
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

	/**
	 * @return the numberOfGuests
	 */
	public int getNumberOfGuests() {
		return numberOfGuests;
	}

	/**
	 * @param numberOfGuests the numberOfGuests to set
	 */
	public void setNumberOfGuests(int numberOfGuests) {
		this.numberOfGuests = numberOfGuests;
	}

	/**
	 * @return the room
	 */
	public Room getRoom() {
		return room;
	}

	/**
	 * @param room the room to set
	 */
	public void setRoom(Room room) {
		this.room = room;
	}

}
