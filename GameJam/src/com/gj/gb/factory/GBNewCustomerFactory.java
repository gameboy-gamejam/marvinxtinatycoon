package com.gj.gb.factory;

import java.util.ArrayList;
import java.util.List;

import com.gj.gb.model.GBNewCustomer;
import com.gj.gb.util.Utils;

public class GBNewCustomerFactory {

	private static GBNewCustomer getCustomer(int num) {
		switch (num) {
		case 0:
			return new GBNewCustomer(2, "PRANKSTER", "Spends precious time in the counter only not to buy any food. Beware!");
		case 1:
			return new GBNewCustomer(3, "INDECISIVE", "Takes time before he can decide on what to order.");
		case 2:
			return new GBNewCustomer(4, "CHEAPSKATE", "Doesn't want to spend money. Buys the cheapest dish in the menu. Worst, no tip!");
		case 3:
			return new GBNewCustomer(5, "IMPATIENT", "You should pay attention her. She doesn't like to wait.");
		case 4:
			return new GBNewCustomer(6, "RICH KID", "A show-off, always order the most expensive dish in the menu.");
		default:
			return new GBNewCustomer(1, "AVERAGE JOE", "The normal guy. Buys food. Leaves tip. Nothig more, nothing less.");
		}
	}
	
	public static List<GBNewCustomer> getRandomCustomers(int n) {
		List<GBNewCustomer> customers = new ArrayList<GBNewCustomer>();
		
		// first customer will arrive in 5 seconds after opening
		int arrive = 5000;
		int max = 75000;
		
		int maxNext = 90/n;
		
		for (int i=0; i<n; i++) {
			
			GBNewCustomer customer = getCustomer(Utils.RANDOM.nextInt(10));
			customer.setTimeBounds(arrive);
			customer.setId(i);
			
			int next = 1 + Utils.RANDOM.nextInt(maxNext);
			next *= 1000;
			
			arrive += next;
			if (next > max) {
				next = max;
			}
			
			customers.add(customer);
		}
		
		return customers;
	}
	

	public static List<GBNewCustomer> getAllCustomerType() {
		List<GBNewCustomer> customers = new ArrayList<GBNewCustomer>();
		
		customers.add(getCustomer(5));
		customers.add(getCustomer(0));
		customers.add(getCustomer(1));
		customers.add(getCustomer(2));
		customers.add(getCustomer(3));
		customers.add(getCustomer(4));
		
		return customers;
	}
}
