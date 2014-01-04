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
			return new GBCustomer(2, "TIME CONSCIOUS");
		case 65:
			return new GBCustomer(3, "PRANKSTER");
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
			return new GBCustomer(4, "INDECISIVE");
		case 78:
		case 79:
			return new GBCustomer(5, "POPSTAR");
		case 80:
		case 81:
		case 82:
		case 83:
		case 84:
			return new GBCustomer(6, "THE BULLIED");
		case 85:
		case 86:
		case 87:
		case 88:
		case 89:
			return new GBCustomer(7, "CHEAPSKATE");
		case 90:
		case 91:
		case 92:
		case 93:
		case 94:
			return new GBCustomer(8, "THE RICH KID");
		case 95:
		case 96:
		case 97:
		case 98:
		case 99:
			return new GBCustomer(9, "PROBLEMATIC");
		default:
			return new GBCustomer(1, "AVERAGE JOE");
		}
	}
	
	public static List<GBCustomer> getCustomerList(int max, int n) {
		List<GBCustomer> customers = new ArrayList<GBCustomer>();
		
		for (int i=0; i<n; i++) {
			customers.add(getCustomerById(Utils.RANDOM.nextInt(max)));
		}
		
		return customers;
	}
}
