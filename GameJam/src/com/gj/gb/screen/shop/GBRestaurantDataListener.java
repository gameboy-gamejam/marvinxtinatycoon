package com.gj.gb.screen.shop;

import com.gj.gb.model.GBNewCustomer;

public interface GBRestaurantDataListener {

	public int QUEUE_ADDED = 1;
	
	public int QUEUE_REMOVED = 2;
	
	public void onDishServed(GBNewCustomer customer);
	
	public void onQueueUpdate(GBNewCustomer customer, int status);
	
	public void onCustomerLeft(GBNewCustomer customer);
}
