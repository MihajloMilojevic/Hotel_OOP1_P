package models;

import java.util.Date;
import java.util.HashMap;

public class PriceList extends Model {
	private static long lastId = 0;
	private long id;
	private Date startDate;
	private Date endDate;
	private HashMap<RoomType, Double> roomTypePrices;
	private HashMap<RoomAddition, Double> roomAdditionPrices;
	private HashMap<ReservationAddition, Double> reservationAdditionPrices;

	public PriceList() {
		this.id = ++lastId;
		this.startDate = null;
		this.endDate = null;
		this.roomTypePrices = new HashMap<RoomType, Double>();
		this.roomAdditionPrices = new HashMap<RoomAddition, Double>();
		this.reservationAdditionPrices = new HashMap<ReservationAddition, Double>();
	}

	public PriceList(Date startDate, Date endDate, HashMap<RoomType, Double> roomTypePrices,
			HashMap<RoomAddition, Double> roomAdditionPrices,
			HashMap<ReservationAddition, Double> reservationAdditionPrices) {
		this.id = ++lastId;
		this.startDate = startDate;
		this.endDate = endDate;
		this.roomTypePrices = roomTypePrices;
		this.roomAdditionPrices = roomAdditionPrices;
		this.reservationAdditionPrices = reservationAdditionPrices;
	}

	public PriceList(Date startDate, Date endDate) {
		this.id = ++lastId;
		this.startDate = startDate;
		this.endDate = endDate;
		this.roomTypePrices = new HashMap<RoomType, Double>();
		this.roomAdditionPrices = new HashMap<RoomAddition, Double>();
		this.reservationAdditionPrices = new HashMap<ReservationAddition, Double>();
	}

	@Override
	public Object get(String key) throws IllegalArgumentException {
		switch (key) {
		case "startDate":
			return (Object) getStartDate();
		case "endDate":
			return (Object) getEndDate();
		case "roomTypePrices":
			return (Object) getRoomTypePrices();
		case "roomAdditionPrices":
			return (Object) getRoomAdditionPrices();
		case "reservationAdditionPrices":
			return (Object) getReservationAdditionPrices();
		case "id":
			return (Object) getId();
		default:
			throw new IllegalArgumentException("Invalid key");
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void set(String key, Object value) throws IllegalArgumentException {
		switch (key) {
		case "startDate":
			setStartDate((Date) value);
			break;
		case "endDate":
			setEndDate((Date) value);
			break;
		case "roomTypePrices":
			setRoomTypePrices((HashMap<RoomType, Double>) value);
			break;
		case "roomAdditionPrices":
			setRoomAdditionPrices((HashMap<RoomAddition, Double>) value);
			break;
		case "reservationAdditionPrices":
			setReservationAdditionPrices((HashMap<ReservationAddition, Double>) value);
			break;
		default:
			throw new IllegalArgumentException("Invalid key");
		}
	}
	@Override
	public PriceList clone() {
		return new PriceList(getStartDate(), getEndDate(), getRoomTypePrices(), getRoomAdditionPrices(),
				getReservationAdditionPrices());
	}
	@Override
	public String toString() {
		StringBuilder roomTypePricesString = new StringBuilder();
		for (RoomType roomType : getRoomTypePrices().keySet()) {
            roomTypePricesString.append(roomType.toString() + ":" + getRoomTypePrices().get(roomType));
        }
		StringBuilder roomAdditionPricesString = new StringBuilder();
		for (RoomAddition roomAddition : getRoomAdditionPrices().keySet()) {
			roomAdditionPricesString.append(roomAddition.toString() + ":" + getRoomAdditionPrices().get(roomAddition));
		}
		StringBuilder reservationAdditionPricesString = new StringBuilder();
		for (ReservationAddition reservationAddition : getReservationAdditionPrices().keySet()) {
			reservationAdditionPricesString.append(
					reservationAddition.toString() + ":" + getReservationAdditionPrices().get(reservationAddition));
		}
		return String.join(";", new String[] {
            Long.toString(getId()),
            getStartDate().toString(),
            getEndDate().toString(),
            String.join(",", roomTypePricesString.toString()),
            String.join(",", roomAdditionPricesString.toString()),
            String.join(",", reservationAdditionPricesString.toString())
        });
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof PriceList))
			return false;
		PriceList other = (PriceList) obj;
		return getId() == other.getId() && getStartDate().equals(other.getStartDate())
                && getEndDate().equals(other.getEndDate()) && getRoomTypePrices().equals(other.getRoomTypePrices())
                && getRoomAdditionPrices().equals(other.getRoomAdditionPrices())
                && getReservationAdditionPrices().equals(other.getReservationAdditionPrices());
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
	 * @return the roomTypePrices
	 */
	public HashMap<RoomType, Double> getRoomTypePrices() {
		return roomTypePrices;
	}

	/**
	 * @param roomTypePrices the roomTypePrices to set
	 */
	public void setRoomTypePrices(HashMap<RoomType, Double> roomTypePrices) {
		this.roomTypePrices = roomTypePrices;
	}

	/**
	 * @return the roomAdditionPrices
	 */
	public HashMap<RoomAddition, Double> getRoomAdditionPrices() {
		return roomAdditionPrices;
	}

	/**
	 * @param roomAdditionPrices the roomAdditionPrices to set
	 */
	public void setRoomAdditionPrices(HashMap<RoomAddition, Double> roomAdditionPrices) {
		this.roomAdditionPrices = roomAdditionPrices;
	}

	/**
	 * @return the reservationAdditionPrices
	 */
	public HashMap<ReservationAddition, Double> getReservationAdditionPrices() {
		return reservationAdditionPrices;
	}

	/**
	 * @param reservationAdditionPrices the reservationAdditionPrices to set
	 */
	public void setReservationAdditionPrices(HashMap<ReservationAddition, Double> reservationAdditionPrices) {
		this.reservationAdditionPrices = reservationAdditionPrices;
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

}
