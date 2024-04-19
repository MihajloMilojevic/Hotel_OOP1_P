package models;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;

import models.enums.ReservationStatus;
import utils.CSVDateParser;

public class Reservation extends Model {
	
	/* ******************************  ATTRIBUTES  *************************************** */
	
	private ReservationStatus status;
	private Room room;
	private Guest guest;
	private LocalDate startDate;
	private LocalDate endDate;
	private double price;
	private ArrayList<ReservationAddition> reservationAdditions;
	
	/* ******************************  CONSTRUCTORS  *************************************** */

	public Reservation() {
		super();
		this.status = ReservationStatus.PENDING;
		this.room = null;
		this.guest = null;
		this.startDate = null;
		this.endDate = null;
		this.price = 0;
		this.reservationAdditions = new ArrayList<ReservationAddition>();
	}
	
	public Reservation(String id) {
		super(id);
		this.status = ReservationStatus.PENDING;
		this.room = null;
		this.guest = null;
		this.startDate = null;
		this.endDate = null;
		this.price = 0;
		this.reservationAdditions = new ArrayList<ReservationAddition>();
	}

	public Reservation(Room room, Guest guest, LocalDate startDate, LocalDate endDate, double price,
			ArrayList<ReservationAddition> reservationAdditions) {
		super();
		this.status = ReservationStatus.PENDING;
		this.room = room;
		this.guest = guest;
		this.startDate = startDate;
		this.endDate = endDate;
		this.price = price;
		this.reservationAdditions = reservationAdditions;
	}
	
	public Reservation(String id, Room room, Guest guest, LocalDate startDate, LocalDate endDate, double price,
			ArrayList<ReservationAddition> reservationAdditions) {
		super(id);
		this.status = ReservationStatus.PENDING;
		this.room = room;
		this.guest = guest;
		this.startDate = startDate;
		this.endDate = endDate;
		this.price = price;
		this.reservationAdditions = reservationAdditions;
	}
	
	public Reservation(Room room, Guest guest, LocalDate startDate, LocalDate endDate, double price) {
		super();
		this.status = ReservationStatus.PENDING;
		this.room = room;
		this.guest = guest;
		this.startDate = startDate;
		this.endDate = endDate;
		this.price = price;
		this.reservationAdditions = new ArrayList<ReservationAddition>();
	}

	public Reservation(String id, Room room, Guest guest, LocalDate startDate, LocalDate endDate, double price) {
		super(id);
		this.status = ReservationStatus.PENDING;
		this.room = room;
		this.guest = guest;
		this.startDate = startDate;
		this.endDate = endDate;
		this.price = price;
		this.reservationAdditions = new ArrayList<ReservationAddition>();
	}

	
	/* ******************************  METHODS  *************************************** */
	
	@Override
	public Object get(String key) throws IllegalArgumentException {
		switch (key) {
		case "status":
			return (Object) getStatus();
		case "room":
			return (Object) getRoom();
		case "guest":
			return (Object) getGuest();
		case "startDate":
			return (Object) getStartDate();
		case "endDate":
			return (Object) getEndDate();
		case "price":
			return (Object) getPrice();
		case "reservationAdditions":
			return (Object) getreservationAdditions();
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
		case "room":
			setRoom((Room) value);
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
			setreservationAdditions((ArrayList<ReservationAddition>) value);
			break;
		default:
			super.set(key, value);
		}
	}
	@Override
	public Object clone() throws CloneNotSupportedException {
		ArrayList<ReservationAddition> reservationAdditionsClone = new ArrayList<ReservationAddition>();
		for (ReservationAddition reservationAddition : getreservationAdditions()) {
			reservationAdditionsClone.add((ReservationAddition)reservationAddition.clone());
		}
		return new Reservation(getId(), (Room)getRoom().clone(), (Guest)getGuest().clone(), LocalDate.from(getStartDate()), LocalDate.from(getEndDate()), getPrice(), reservationAdditionsClone);
	}
	@Override
	public String toString() {
		return String.join(";", new String[] {
			super.toString(),	
			getStatus().toString(),
            getStartDate().toString(),
            getEndDate().toString(),
            String.valueOf(getPrice()),
		});
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
		return status == other.status && room.equals(other.room) && guest.equals(other.guest)
				&& startDate.equals(other.startDate) && endDate.equals(other.endDate) && price == other.price
				&& reservationAdditions.equals(other.reservationAdditions);
	}
	@Override
	public void update(Model model) throws IllegalArgumentException {
		super.update(model);
		if (!(model instanceof Reservation)) {
			throw new IllegalArgumentException("Not a Reservation instance");
		}
		Reservation reservation = (Reservation) model;
		setStatus(reservation.getStatus());
		setRoom(reservation.getRoom());
		setGuest(reservation.getGuest());
		setStartDate(reservation.getStartDate());
		setEndDate(reservation.getEndDate());
		setPrice(reservation.getPrice());
		setreservationAdditions(reservation.getreservationAdditions());
	}
	@Override
	public Model fromCSV(String csv) throws ParseException {
		super.fromCSV(csv);
		String[] values = csv.split(";");
		if (values.length < 5) throw new ParseException("Invalid RoomType string", 1);
		getStatus().toString();
        getStartDate().toString();
        getEndDate().toString();
        String.valueOf(getPrice());
        this.status = ReservationStatus.valueOf(values[1]);
		this.startDate = CSVDateParser.parseString(values[2]);
		this.endDate = CSVDateParser.parseString(values[3]);
		this.price = Double.parseDouble(values[4]);
		return this;
	}
	
	/* ******************************  GETTERS & SETTERS  *************************************** */
	
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
	public Room getRoom() {
		return room;
	}

	/**
	 * @param room the room to set
	 */
	public void setRoom(Room room) {
		this.room = room;
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
	public ArrayList<ReservationAddition> getreservationAdditions() {
		return reservationAdditions;
	}

	/**
	 * @param reservationAdditions the reservationAdditions to set
	 */
	public void setreservationAdditions(ArrayList<ReservationAddition> reservationAdditions) {
		this.reservationAdditions = reservationAdditions;
	}

}
