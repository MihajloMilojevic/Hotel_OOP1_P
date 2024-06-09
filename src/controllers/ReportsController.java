package controllers;

import java.time.LocalDate;
import java.util.ArrayList;

import app.AppState;
import database.SelectCondition;
import models.Model;
import models.Reservation;
import models.RoomType;

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

	private static String[] monthsNames = new String[] { "January", "February", "March", "April", "May", "June", "July",
			"August", "September", "October", "November", "December" };

	public static ArrayList<Revenue> getRevenue() {
		int monthToday = LocalDate.now().getMonthValue();
		String months[] = new String[12];
		for (int i = 0; i < 12; i++) {
			months[11-i] = monthsNames[LocalDate.now().minusMonths(i).getMonthValue() - 1];
		}
		ArrayList<Revenue> revenue = new ArrayList<Revenue>();
		for (RoomType type : AppState.getInstance().getDatabase().getRoomTypes().getRows()) {
			Revenue r = new Revenue(type, months);
			revenue.add(r);
		}
		ArrayList<Reservation> reservations = AppState.getInstance().getDatabase().getReservations()
				.select(new SelectCondition() {
					@Override
					public boolean check(Model model) {
						Reservation reservation = (Reservation) model;
						return !reservation.isDeleted() && reservation.getRoom() != null
								&& reservation.getStartDate().isAfter(LocalDate.now().minusMonths(12));
					}
				});

		for (Reservation reservation : reservations) {

			int month = reservation.getStartDate().getMonthValue();			
			// get the index of the month where 0 is the month 12 months ago and 11 is the
			// current month
			int index = (monthToday - month + 12) % 12;
			double price = reservation.getPrice();
			Revenue r = null;
			for (Revenue rev : revenue) {
				if (rev.getType().getId() == reservation.getRoom().getType().getId()) {
					r = rev;
					break;
				}
			}
			if (r == null) {
				continue;
			}
			r.getMonthlyRevenue()[11 - index] += price;
		}

		return revenue;
	}

	public static class Revenue {
		private RoomType type;
		private double[] monthlyRevenue;
		private String[] months;

		public Revenue(RoomType type, String[] months) {
			this.type = type;
			this.monthlyRevenue = new double[12];
			for (int i = 0; i < 12; i++) {
				this.monthlyRevenue[i] = 0;
			}
			this.months = months;
		}

		/**
		 * @return the type
		 */
		public RoomType getType() {
			return type;
		}

		/**
		 * @return the monthlyRevenue
		 */
		public double[] getMonthlyRevenue() {
			return monthlyRevenue;
		}

		/**
		 * @return the months
		 */
		public String[] getMonths() {
			return months;
		}


	}
}
