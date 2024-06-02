package controllers;

import java.time.LocalDate;
import java.util.ArrayList;

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

public class ReservationController {

	public static ArrayList<Reservation> getReservations() {
		ArrayList<Reservation> rooms = AppState.getInstance().getDatabase().getReservations()
				.select(new SelectCondition() {

					@Override
					public boolean check(Model row) {
						return !row.isDeleted();
					}
				});
		rooms.sort((r1, r2) -> r1.getStartDate().compareTo(r2.getStartDate()));
		return rooms;
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
		try {
			if (!isThereRoom(reservation)) {
				return ControllerActionStatus.NO_ROOM;
			}
			reservation.setPrice(calculateTotalPrice(reservation));
			reservation.setStatus(ReservationStatus.PENDING);
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
					.findFirst()
					.orElse(null);
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
}
