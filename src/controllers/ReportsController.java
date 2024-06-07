package controllers;

import java.time.LocalDate;
import java.util.ArrayList;

import app.AppState;
import database.SelectCondition;
import models.Model;
import models.Reservation;

public class ReportsController {
	public static ArrayList<String> getDailyCheckins() {
		return new ArrayList<String>(
				AppState.getInstance().getDatabase().getReservations().select(new SelectCondition() {
					@Override
					public boolean check(Model model) {
						Reservation reservation = (Reservation) model;
						return !reservation.isDeleted() && reservation.getCheckInDate() != null
								&& reservation.getCheckInDate().equals(LocalDate.now());
					}
				}).stream().map(r -> r.getGuest().getName() + " " + r.getGuest().getSurname()).toList());
	}

	public static ArrayList<String> getDailyCheckouts() {
		return new ArrayList<String>(
				AppState.getInstance().getDatabase().getReservations().select(new SelectCondition() {
					@Override
					public boolean check(Model model) {
						Reservation reservation = (Reservation) model;
						return !reservation.isDeleted() && reservation.getCheckOutDate() != null
								&& reservation.getCheckOutDate().equals(LocalDate.now());
					}
				}).stream().map(r -> r.getGuest().getName() + " " + r.getGuest().getSurname()).toList());
	}

	public static ArrayList<String> getDailyNotYet() {
		return new ArrayList<String>(
				AppState.getInstance().getDatabase().getReservations().select(new SelectCondition() {
					@Override
					public boolean check(Model model) {
						Reservation reservation = (Reservation) model;
						return !reservation.isDeleted() && reservation.getCheckInDate() == null
								&& reservation.getStartDate().equals(LocalDate.now());
					}
				}).stream().map(r -> r.getGuest().getName() + " " + r.getGuest().getSurname()).toList());
	}
}
