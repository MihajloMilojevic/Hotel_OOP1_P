package controllers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;

import app.AppState;
import database.SelectCondition;
import models.Model;
import models.PriceList;

public class PriceListController {
	
	public static ArrayList<PriceList> getPrices() {
		return AppState.getInstance().getDatabase().getPriceLists().select(new SelectCondition() {

			@Override
			public boolean check(Model row) {
				return !row.isDeleted();
			}
		});
	}

	public static ArrayList<PriceList> getPricesForPeriod(LocalDate startDate, LocalDate endDate) {
     	ArrayList<PriceList> res = AppState.getInstance().getDatabase().getPriceLists().select(new SelectCondition() {
     		@Override
     		public boolean check(Model row) {
     			PriceList priceList = (PriceList) row;
     			return !priceList.isDeleted() && ((priceList.getEndDate() == null && priceList.getStartDate().isBefore(startDate)) || (priceList.getStartDate().isBefore(endDate) && priceList.getEndDate().isAfter(startDate)));
     		}
     	});
		res.sort(new Comparator<PriceList>() {
			@Override
			public int compare(PriceList p1, PriceList p2) {
				return p1.getStartDate().compareTo(p2.getStartDate());
			}
		});
		return res;
	}
	
	public static ControllerActionStatus updatePriceList(PriceList priceList) {
		try {
			if (priceList == null || !priceList.isValid()) {
				return ControllerActionStatus.INCOPLETE_DATA;
			}
			AppState.getInstance().getDatabase().getPriceLists().update(priceList);
			return ControllerActionStatus.SUCCESS;
		} catch (Exception e) {
			return ControllerActionStatus.ERROR;
		}
	}

}
