package main;

import java.util.HashMap;

import models.enums.ReservationStatus;

public class Main {

	public static void main(String[] args) {
		System.out.println(ReservationStatus.valueOf("PENDING"));
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("key1", "value1");
		map.put("key2", "value2");
		map.put("key3", "value3");
		map.put("key4", "value4");
		System.out.println(map.toString());
	}

}
