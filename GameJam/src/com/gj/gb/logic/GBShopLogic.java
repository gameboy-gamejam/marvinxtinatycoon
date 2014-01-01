package com.gj.gb.logic;

import java.util.ArrayList;
import java.util.List;

import com.gj.gb.model.GBCustomer;
import com.gj.gb.util.Utils;

public class GBShopLogic {

	private static int arrivalTime = 3;
	
	public static List<GBCustomer> getCustomers(int total) {
		List<GBCustomer> list = new ArrayList<GBCustomer>();
		
		// resets time to 3
		arrivalTime = 3;
		
		for (int i=0; i<total; i++) {
			list.add(createRandomCustomer(i));
		}
		
		return list;
	}

	private static GBCustomer createRandomCustomer(int id) {
		GBCustomer customer = new GBCustomer();

		customer.setId(id);
		// there are 3 types of customer
		customer.setAvatar(Utils.RANDOM.nextInt(3));
		// customer can arrive from 3 seconds upon start til 
		customer.setArriveTime(getArrivalTime());
		// customer has at most 5 seconds to decide if he already needs to buy
		customer.setDecideTime(Utils.RANDOM.nextInt(5));

		return customer;
	}

	private static int getArrivalTime() {
		if (arrivalTime == 3) {
			arrivalTime += Utils.RANDOM.nextInt(3);
		} else {
			arrivalTime += Utils.RANDOM.nextInt(10);
		}
		if (arrivalTime >= 100) arrivalTime = 100;
		return arrivalTime;
	}
}
