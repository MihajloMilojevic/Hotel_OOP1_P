package controllers;

import java.util.ArrayList;

import app.AppState;
import database.SelectCondition;
import exceptions.DuplicateIndexException;
import exceptions.NoElementException;
import models.Model;
import models.Reservation;
import models.ReservationAddition;

public class ReservationController {

	
	public static ArrayList<Reservation> getReservations() {
		ArrayList<Reservation> rooms = AppState.getInstance().getDatabase().getReservations().select(new SelectCondition() {
			
			@Override
			public boolean check(Model row) {
				return !row.isDeleted();
			}
		});
		rooms.sort((r1, r2) -> r1.getStartDate().compareTo(r2.getStartDate()));
		return rooms;
	}

	public static void addReservation(Reservation reservation) throws DuplicateIndexException { 
		AppState.getInstance().getDatabase().getReservations().insert(reservation);
	}

	public static void updateReservation(Reservation reservation) throws NoElementException {
		AppState.getInstance().getDatabase().getReservations().update(reservation);
	}

	public static void deleteReservation(Reservation reservation) throws NoElementException {
		AppState.getInstance().getDatabase().getReservations().delete(reservation);
	}
	
	public static void addReservationAddition(ReservationAddition reservationAddition) throws DuplicateIndexException { 
		AppState.getInstance().getDatabase().getReservationAdditions().insert(reservationAddition);
	}

	public static void updateReservationAddition(ReservationAddition reservationAddition) throws NoElementException {
		ArrayList<Reservation> reservation = AppState.getInstance().getDatabase().getReservations().select(new SelectCondition() {

			@Override
			public boolean check(Model row) {
				Reservation r = (Reservation) row;
				return r.getReservationAdditions().stream().map(ReservationAddition::getId).toList().contains(reservationAddition.getId());
			}
		});
		reservation.forEach(r -> {
			ArrayList<ReservationAddition> newReservationAdditions = new ArrayList<>();
			for (ReservationAddition ra : r.getReservationAdditions()) {
				if (ra.getId().equals(r.getId())) {
					newReservationAdditions.add(reservationAddition);
				} else {
					newReservationAdditions.add(ra);
				}
			}
			r.setReservationAdditions(newReservationAdditions);
			try {
				updateReservation(r);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		AppState.getInstance().getDatabase().getReservationAdditions().update(reservationAddition);
	}

	public static void deleteReservationAddition(ReservationAddition reservationAddition) throws NoElementException {
		AppState.getInstance().getDatabase().getReservations().select(new SelectCondition() {

			@Override
			public boolean check(Model row) {
				Reservation r = (Reservation) row;
				return r.getReservationAdditions().contains(reservationAddition);
			}
		}).forEach(r -> {
			r.removeReservationAddition(reservationAddition);
			try {
				updateReservation(r);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		AppState.getInstance().getDatabase().getReservationAdditions().delete(reservationAddition);
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
}
