package models;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.HashMap;

import utils.CSVDateParser;

public class PriceList extends Model {
	
	/* ******************************  ATTRIBUTES  *************************************** */
	
	private LocalDate startDate;
	private LocalDate endDate;
	private HashMap<RoomType, Double> roomTypePrices;
	private HashMap<RoomAddition, Double> roomAdditionPrices;
	private HashMap<ReservationAddition, Double> reservationAdditionPrices;
	
	/* ******************************  CONSTRUCTORS  *************************************** */

	public PriceList() {
		super();
		this.startDate = null;
		this.endDate = null;
		this.roomTypePrices = new HashMap<RoomType, Double>();
		this.roomAdditionPrices = new HashMap<RoomAddition, Double>();
		this.reservationAdditionPrices = new HashMap<ReservationAddition, Double>();
	}
	public PriceList(String id) {
		super(id);
		this.startDate = null;
		this.endDate = null;
		this.roomTypePrices = new HashMap<RoomType, Double>();
		this.roomAdditionPrices = new HashMap<RoomAddition, Double>();
		this.reservationAdditionPrices = new HashMap<ReservationAddition, Double>();
	}

	public PriceList(LocalDate startDate, LocalDate endDate, HashMap<RoomType, Double> roomTypePrices,
			HashMap<RoomAddition, Double> roomAdditionPrices,
			HashMap<ReservationAddition, Double> reservationAdditionPrices) {
		super();
		this.startDate = startDate;
		this.endDate = endDate;
		this.roomTypePrices = roomTypePrices;
		this.roomAdditionPrices = roomAdditionPrices;
		this.reservationAdditionPrices = reservationAdditionPrices;
	}

	public PriceList(String id, LocalDate startDate, LocalDate endDate, HashMap<RoomType, Double> roomTypePrices,
			HashMap<RoomAddition, Double> roomAdditionPrices,
			HashMap<ReservationAddition, Double> reservationAdditionPrices) {
		super(id);
		this.startDate = startDate;
		this.endDate = endDate;
		this.roomTypePrices = roomTypePrices;
		this.roomAdditionPrices = roomAdditionPrices;
		this.reservationAdditionPrices = reservationAdditionPrices;
	}

	public PriceList(LocalDate startDate, LocalDate endDate) {
		super();
		this.startDate = startDate;
		this.endDate = endDate;
		this.roomTypePrices = new HashMap<RoomType, Double>();
		this.roomAdditionPrices = new HashMap<RoomAddition, Double>();
		this.reservationAdditionPrices = new HashMap<ReservationAddition, Double>();
	}

	public PriceList(String id, LocalDate startDate, LocalDate endDate) {
		super(id);
		this.startDate = startDate;
		this.endDate = endDate;
		this.roomTypePrices = new HashMap<RoomType, Double>();
		this.roomAdditionPrices = new HashMap<RoomAddition, Double>();
		this.reservationAdditionPrices = new HashMap<ReservationAddition, Double>();
	}

	/* ******************************  METHODS  *************************************** */
	
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
			return super.get(key);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void set(String key, Object value) throws IllegalArgumentException {
		switch (key) {
		case "startDate":
			setStartDate((LocalDate) value);
			break;
		case "endDate":
			setEndDate((LocalDate) value);
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
		    super.set(key, value);
		}
	}
	@Override
	public Object clone() throws CloneNotSupportedException {
		HashMap<RoomType, Double> roomTypePricesClone = new HashMap<RoomType, Double>();
		for (RoomType roomType : getRoomTypePrices().keySet()) {
			roomTypePricesClone.put((RoomType)roomType.clone(), getRoomTypePrices().get(roomType));
		}
		HashMap<RoomAddition, Double> roomAdditionPricesClone = new HashMap<RoomAddition, Double>();
		for (RoomAddition roomAddition : getRoomAdditionPrices().keySet()) {
			roomAdditionPricesClone.put((RoomAddition) roomAddition.clone(), getRoomAdditionPrices().get(roomAddition));
		}
		HashMap<ReservationAddition, Double> reservationAdditionPricesClone = new HashMap<ReservationAddition, Double>();
		for (ReservationAddition reservationAddition : getReservationAdditionPrices().keySet()) {
			reservationAdditionPricesClone.put((ReservationAddition) reservationAddition.clone(),
					getReservationAdditionPrices().get(reservationAddition));
		}
		return new PriceList(getId(), LocalDate.from(getStartDate()), LocalDate.from(getEndDate()), roomTypePricesClone, roomAdditionPricesClone, reservationAdditionPricesClone);
	}
	@Override
	public String toString() {
		
		return String.join(";", new String[] {
			super.toString(),
            getStartDate().toString(),
            getEndDate().toString(),
        });
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		PriceList other = (PriceList) obj;
		return getStartDate().equals(other.getStartDate())
                && getEndDate().equals(other.getEndDate()) && getRoomTypePrices().equals(other.getRoomTypePrices())
                && getRoomAdditionPrices().equals(other.getRoomAdditionPrices())
                && getReservationAdditionPrices().equals(other.getReservationAdditionPrices());
	}
	
	@Override
	public void update(Model newModel) throws IllegalArgumentException {
		super.update(newModel);
		if (!(newModel instanceof PriceList))
			throw new IllegalArgumentException("Not  a PriceList object");
		PriceList priceList = (PriceList) newModel;
		setStartDate(priceList.getStartDate());
		setEndDate(priceList.getEndDate());
		setRoomTypePrices(priceList.getRoomTypePrices());
		setRoomAdditionPrices(priceList.getRoomAdditionPrices());
	}
	
	@Override
	public Model fromCSV(String csv) throws ParseException {
		super.fromCSV(csv);
		String[] values = csv.split(";");
		if (values.length < 3) throw new ParseException("Invalid RoomType string", 1);
		this.startDate = CSVDateParser.parseString(values[1]);
		this.endDate = CSVDateParser.parseString(values[2]);
		return this;
	}
	
	/* ******************************  GETTERS AND SETTERS  *************************************** */
	
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

	
}
