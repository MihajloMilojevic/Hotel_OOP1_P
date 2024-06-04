package controllers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;

import app.AppState;
import database.SelectCondition;
import exceptions.DuplicateIndexException;
import exceptions.NoElementException;
import exceptions.PriceException;
import models.Model;
import models.PriceList;
import models.Reservation;
import models.ReservationAddition;
import models.Room;
import models.RoomAddition;
import models.enums.ReservationStatus;
import utils.Pair;

public class ReservationController {

	public static ArrayList<Reservation> getReservations(SelectCondition condition) {
		ArrayList<Reservation> rooms = AppState.getInstance().getDatabase().getReservations().select(condition);
		rooms.sort((r1, r2) -> r1.getStartDate().compareTo(r2.getStartDate()));
		return rooms;
	}

	public static ArrayList<Reservation> getReservations() {
		return getReservations(new SelectCondition() {

			@Override
			public boolean check(Model row) {
				return !row.isDeleted();
			}
		});
	}

	public static ControllerActionStatus addReservation(Reservation reservation) {
		try {
			if (!isThereRoom(reservation)) {
				return ControllerActionStatus.NO_ROOM;
			}
			reservation.setPrice(calculateTotalPrice(reservation));
			AppState.getInstance().getDatabase().getReservations().insert(reservation);
			return ControllerActionStatus.SUCCESS;
		} catch (DuplicateIndexException e) {
			return ControllerActionStatus.DUPLICATE_INDEX;
		} catch (PriceException e) {
			return ControllerActionStatus.ERROR;
		} catch (Exception e) {
			return ControllerActionStatus.ERROR;
		}
	}

	public static ControllerActionStatus updateReservation(Reservation reservation) {
		if (!isThereRoom(reservation)) {
			return ControllerActionStatus.NO_ROOM;
		}
		try {
			reservation.setPrice(calculateTotalPrice(reservation));
		} catch (PriceException e) {
			return ControllerActionStatus.ERROR;
		}
		reservation.setStatus(ReservationStatus.PENDING);
		return saveChanges(reservation);
	}

	private static ControllerActionStatus saveChanges(Reservation reservation) {
		try {
			AppState.getInstance().getDatabase().getReservations().update(reservation);
			return ControllerActionStatus.SUCCESS;
		} catch (NoElementException e) {
			e.printStackTrace();
			return ControllerActionStatus.NO_RECORD;
		} catch (Exception e) {
			return ControllerActionStatus.ERROR;
		}
	}

	public static ControllerActionStatus deleteReservation(Reservation reservation) {
		try {
			AppState.getInstance().getDatabase().getReservations().delete(reservation);
			return ControllerActionStatus.SUCCESS;
		} catch (Exception e) {
			return ControllerActionStatus.ERROR;
		}
	}

	public static ControllerActionStatus addReservationAddition(ReservationAddition reservationAddition) {
		try {
			AppState.getInstance().getDatabase().getReservationAdditions().insert(reservationAddition);
			return ControllerActionStatus.SUCCESS;
		} catch (DuplicateIndexException e) {
			return ControllerActionStatus.DUPLICATE_INDEX;
		} catch (Exception e) {
			return ControllerActionStatus.ERROR;
		}
	}

	public static boolean isThereRoom(Reservation reservation) {
		return AppState.getInstance().getDatabase().getRooms().select(new SelectCondition() {

			@Override
			public boolean check(Model row) {
				Room room = (Room) row;
				for (RoomAddition ra : reservation.getRoomAdditions()) {
					if (!room.getRoomAdditions().contains(ra)) {
						return false;
					}
				}
				return room.getType().equals(reservation.getRoomType());
			}
		}).size() > 0;
	}

	public static ControllerActionStatus updateReservationAddition(ReservationAddition reservationAddition) {
		try {
			ArrayList<Reservation> reservation = AppState.getInstance().getDatabase().getReservations()
					.select(new SelectCondition() {

						@Override
						public boolean check(Model row) {
							Reservation r = (Reservation) row;
							return r.getReservationAdditions().stream().map(ReservationAddition::getId).toList()
									.contains(reservationAddition.getId());
						}
					});
			reservation.forEach(r -> {
				ArrayList<ReservationAddition> newReservationAdditions = new ArrayList<>();
				for (ReservationAddition ra : r.getReservationAdditions()) {
					if (ra.getId().equals(reservationAddition.getId())) {
						newReservationAdditions.add(reservationAddition);
					} else {
						newReservationAdditions.add(ra);
					}
				}
				r.setReservationAdditions(newReservationAdditions);
				ControllerActionStatus status = updateReservation(r);
				if (status != ControllerActionStatus.SUCCESS) {
					throw new RuntimeException("Failed to update reservation");
				}
			});
			AppState.getInstance().getDatabase().getReservationAdditions().update(reservationAddition);
			return ControllerActionStatus.SUCCESS;
		} catch (NoElementException e) {
			return ControllerActionStatus.NO_RECORD;
		} catch (Exception e) {
			return ControllerActionStatus.ERROR;
		}
	}

	public static ControllerActionStatus deleteReservationAddition(ReservationAddition reservationAddition) {
		try {
			AppState.getInstance().getDatabase().getReservations().select(new SelectCondition() {

				@Override
				public boolean check(Model row) {
					Reservation r = (Reservation) row;
					return r.getReservationAdditions().contains(reservationAddition);
				}
			}).forEach(r -> {
				r.removeReservationAddition(reservationAddition);
				ControllerActionStatus status = updateReservation(r);
				if (status != ControllerActionStatus.SUCCESS) {
					throw new RuntimeException("Failed to update reservation");
				}
			});
			AppState.getInstance().getDatabase().getReservationAdditions().delete(reservationAddition);
			return ControllerActionStatus.SUCCESS;
		} catch (Exception e) {
			return ControllerActionStatus.ERROR;
		}
	}

	public static ArrayList<ReservationAddition> getReservationAdditions() {
		return AppState.getInstance().getDatabase().getReservationAdditions().select(new SelectCondition() {

			@Override
			public boolean check(Model row) {
				return !row.isDeleted();
			}
		});
	}

	public static ReservationAddition getReservationAdditionByName(String name) {
		return AppState.getInstance().getDatabase().getReservationAdditions().selectByIndex("name", name);
	}

	public static float calculateTotalPrice(Reservation reservation) throws PriceException {
		if (reservation == null || reservation.getStartDate() == null || reservation.getEndDate() == null
				|| reservation.getRoomType() == null) {
			return 0;
		}
		ArrayList<PriceList> priceLists = PriceListController.getPricesForPeriod(reservation.getStartDate(),
				reservation.getEndDate());
		float totalPrice = 0;
		LocalDate currentDate = reservation.getStartDate();
		while (!currentDate.isAfter(reservation.getEndDate())) {
			final LocalDate testDate = currentDate;
			PriceList priceList = priceLists.stream()
					.filter(p -> ((p.getEndDate() == null && p.getStartDate().isBefore(testDate))
							|| (p.getStartDate().isBefore(testDate) && p.getEndDate().isAfter(testDate))))
					.findFirst().orElse(null);
			if (priceList != null) {
				totalPrice += priceList.getPrice(reservation.getRoomType());
				for (ReservationAddition ra : reservation.getReservationAdditions()) {
					totalPrice += priceList.getPrice(ra);
				}
			}
			currentDate = currentDate.plusDays(1);
		}
		return totalPrice;
	}

	public static void rejectExpiredReservations() {
		AppState.getInstance().getDatabase().getReservations().select(new SelectCondition() {

			@Override
			public boolean check(Model row) {
				Reservation reservation = (Reservation) row;
				return reservation.getEndDate().isBefore(LocalDate.now())
						&& reservation.getStatus() == ReservationStatus.PENDING;
			}
		}).forEach(r -> {
			r.setStatus(ReservationStatus.REJECTED);
			r.setPrice(0);
			saveChanges(r);
		});
	}

	public static ControllerActionStatus approveReservation(Reservation reservation) {
		if (reservation == null) {
			return ControllerActionStatus.INCOPLETE_DATA;
		}
		if (reservation.getStatus() != ReservationStatus.PENDING) {
			return ControllerActionStatus.INCORECT_STATUS;
		}
		// check if room is available

		if (findAvailableRooms(reservation).size() == 0) {
			return ControllerActionStatus.NO_ROOM;
		}
		reservation.setStatus(ReservationStatus.APPROVED);
		return saveChanges(reservation);
	}

	public static ControllerActionStatus rejectReservation(Reservation reservation) {
		if (reservation == null) {
			return ControllerActionStatus.INCOPLETE_DATA;
		}
		if (reservation.getStatus() != ReservationStatus.PENDING) {
			return ControllerActionStatus.INCORECT_STATUS;
		}
		reservation.setStatus(ReservationStatus.REJECTED);
		reservation.setPrice(0);
		return saveChanges(reservation);
	}

	private static ArrayList<Room> findAvailableRooms(Reservation reservation) {
		// get a list of all approved reservations that overlap with the current
		// reservation
		ArrayList<Reservation> reservations = AppState.getInstance().getDatabase().getReservations()
				.select(new SelectCondition() {

					@Override
					public boolean check(Model row) {
						Reservation r = (Reservation) row;
						return r.getStatus() == ReservationStatus.APPROVED && !r.isDeleted()
								&& r.getStartDate().isBefore(reservation.getEndDate())
								&& r.getEndDate().isAfter(reservation.getStartDate());
					}
				});
		// get a set of all rooms
		HashSet<Room> allRooms = new HashSet<Room>(
				AppState.getInstance().getDatabase().getRooms().select(new SelectCondition() {

					@Override
					public boolean check(Model row) {
						return !row.isDeleted();
					}
				}));
		ArrayList<Pair<Reservation, ArrayList<Room>>> possibleRooms = new ArrayList<Pair<Reservation, ArrayList<Room>>>();
		// for each reservation, get a list of rooms that setisfy the reservation
		// requirements
		for (Reservation r : reservations) {
			ArrayList<Room> availableRooms = new ArrayList<Room>();
			for (Room room : allRooms) {
				if (room.getType().equals(r.getRoomType())
						&& room.getRoomAdditions().containsAll(r.getRoomAdditions())) {
					availableRooms.add(room);
				}
			}
			possibleRooms.add(new Pair<Reservation, ArrayList<Room>>(r, availableRooms));
		}
		possibleRooms.sort((p1, p2) -> p1.getSecond().size() - p2.getSecond().size());
		for (Pair<Reservation, ArrayList<Room>> p : possibleRooms) {
			for (Room room : p.getSecond()) {
				if (allRooms.contains(room)) {
					allRooms.remove(room);
					break;
				}
			}
		}
		return new ArrayList<Room>(allRooms.stream().filter(room -> room.getType().equals(reservation.getRoomType())
				&& room.getRoomAdditions().containsAll(reservation.getRoomAdditions())).toList());
	}
}
