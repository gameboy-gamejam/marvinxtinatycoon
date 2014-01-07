package com.gj.gb.screen.shop;

import com.gj.gb.model.GBNewCustomer;

public interface GBRestaurantDataListener {

	public void onDishServed(GBNewCustomer customer);
	
	public void onQueueUpdate(GBNewCustomer customer);
	
	public void onCustomerLeft(GBNewCustomer customer);
}
