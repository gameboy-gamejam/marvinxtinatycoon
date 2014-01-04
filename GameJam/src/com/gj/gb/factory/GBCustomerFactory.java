package com.gj.gb.factory;

import java.util.ArrayList;
import java.util.List;

import com.gj.gb.model.GBCustomer;
import com.gj.gb.util.Utils;

public class GBCustomerFactory {

	public static GBCustomer getCustomerById(int id) {
		switch (id) {
		case 51:
		case 52:
		case 53:
		case 54:
		case 55:
		case 56:
		case 57:
		case 58:
		case 59:
		case 60:
		case 61:
		case 62:
		case 63:
		case 64:
			return new GBCustomer(2, "TIME CONSCIOUS", "The faster you give her the order, the larger the tip she gives.");
		case 65:
			return new GBCustomer(3, "PRANKSTER", "Spends precious time in the counter only not to buy any food. Beware!");
		case 66:
		case 67:
		case 68:
		case 69:
		case 70:
		case 71:
		case 72:
		case 73:
		case 74:
		case 75:
		case 76:
		case 77:
			return new GBCustomer(4, "INDECISIVE", "Thinks twice on what to order. Very indecisive.");
		case 78:
		case 79:
			return new GBCustomer(5, "POPSTAR", "A celebrity! Gives a lot of tip. So much win.");
		case 80:
		case 81:
		case 82:
		case 83:
		case 84:
			return new GBCustomer(6, "THE BULLIED", "Feel sorry for this guy, he buys food for other people. The good part, he orders in bunch.");
		case 85:
		case 86:
		case 87:
		case 88:
		case 89:
			return new GBCustomer(7, "CHEAPSKATE", "Doesn't want to spend money. Buys the cheapest dish in the menu. Worst, no tip!");
		case 90:
		case 91:
		case 92:
		case 93:
		case 94:
			return new GBCustomer(8, "THE RICH KID", "Orders the most expensive dish in large quantity.");
		case 95:
		case 96:
		case 97:
		case 98:
		case 99:
			return new GBCustomer(9, "PROBLEMATIC", "Very impatient. Wants her order right away.");
		default:
			return new GBCustomer(1, "AVERAGE JOE", "The normal guy. Buys food. Leaves tip. Nothig more, nothing less");
		}
	}
	
	public static List<GBCustomer> getCustomerList(int max, int n) {
		List<GBCustomer> customers = new ArrayList<GBCustomer>();
		
		int arriveTime = 10;
		
		for (int i=0; i<n; i++) {
			GBCustomer customer = getCustomerById(Utils.RANDOM.nextInt(max));
			
			if (arriveTime == 3) {
				arriveTime += Utils.RANDOM.nextInt(3) + 1;
			} else {
				arriveTime += Utils.RANDOM.nextInt(8) + 1;
			}
			
			if (arriveTime >= 45) {
				arriveTime = 45;
			}
			
			customer.setArriveTime(arriveTime);
			customer.setDecideTime(arriveTime + getDecideTime(customer.getAvatar()));
			customer.setId(i);
			
			customers.add(customer);
		}
		
		return customers;
	}
	
	private static int getDecideTime(int avatar) {
		switch (avatar) {
		case 1:
		case 2:
			return Utils.RANDOM.nextInt(2);
		case 3:
		case 4:
			return Utils.RANDOM.nextInt(4) + 2;
		case 5:
		case 6:
		case 7:
		case 8:
		case 9:
			return Utils.RANDOM.nextInt(3);
		}
		return 0;
	}

	public static List<GBCustomer> getAllCustomerType() {
		List<GBCustomer> customers = new ArrayList<GBCustomer>();
		
		customers.add(getCustomerById(1));
		customers.add(getCustomerById(51));
		customers.add(getCustomerById(65));
		customers.add(getCustomerById(66));
		customers.add(getCustomerById(78));
		customers.add(getCustomerById(80));
		customers.add(getCustomerById(85));
		customers.add(getCustomerById(90));
		customers.add(getCustomerById(95));
		
		return customers;
	}
}
