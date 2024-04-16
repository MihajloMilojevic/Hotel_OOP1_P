package models;

import java.util.ArrayList;
import java.util.Date;

import models.enums.ReservationStatus;

public class Reservation extends Model {
	private ReservationStatus status;
	private Room room;
	private Guest guest;
	private Date startDate;
	private Date endDate;
	private double price;
	private ArrayList<ReservationAddition> reservationAdditions;

	public Reservation() {
		this.status = ReservationStatus.PENDING;
		this.room = null;
		this.guest = null;
		this.startDate = null;
		this.endDate = null;
		this.price = 0;
		this.reservationAdditions = new ArrayList<ReservationAddition>();
	}

	public Reservation(Room room, Guest guest, Date startDate, Date endDate, double price,
			ArrayList<ReservationAddition> reservationAdditions) {
		this.status = ReservationStatus.PENDING;
		this.room = room;
		this.guest = guest;
		this.startDate = startDate;
		this.endDate = endDate;
		this.price = price;
		this.reservationAdditions = reservationAdditions;
	}

	public Reservation(Room room, Guest guest, Date startDate, Date endDate, double price) {
		this.status = ReservationStatus.PENDING;
		this.room = room;
		this.guest = guest;
		this.startDate = startDate;
		this.endDate = endDate;
		this.price = price;
		this.reservationAdditions = new ArrayList<ReservationAddition>();
	}

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
			return (Object) getReservationAdditions();
		default:
			throw new IllegalArgumentException("Invalid key: " + key);
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
			setStartDate((Date) value);
			break;
		case "endDate":
			setEndDate((Date) value);
			break;
		case "price":
			setPrice((double) value);
			break;
		case "reservationAdditions":
			setReservationAdditions((ArrayList<ReservationAddition>) value);
			break;
		default:
			throw new IllegalArgumentException("Invalid key: " + key);
		}
	}
	@Override
	public Reservation clone() {
		return new Reservation(getRoom(), getGuest(), getStartDate(), getEndDate(), getPrice(),
				getReservationAdditions());
	}
	@Override
	public String toString() {
		return String.join(";", new String[] {
				getStatus().toString(),
            Integer.toString(getRoom().getNumber()),
            getGuest().getUsername(),
            getStartDate().toString(),
            getEndDate().toString(),
            String.valueOf(getPrice()),
            String.join(",", getReservationAdditions().toString())
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
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(Date endDate) {
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

}
