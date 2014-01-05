package com.gj.gb.model;

public class GBNewCustomer {

	public enum GBCustomerState {
		
		ARRIVING,
		
		IN_QUEUE,
		
		DECIDING,
		
		WAITING,
		
		SERVED,
		
		RAGE_QUIT
	}
	
	private GBCustomerState state = GBCustomerState.ARRIVING;
	
	// the time this customer arrives at the restaurant
	private long arrivalTime = 0;
	
	// tolerable time to wait in queue
	private long queueTime = 0;
	// total time it takes to decide for order
	private long decideTime = 0;
	// tolerable time to wait for the order
	private long waitTime = 0;
	
	private long totalStayTime = 0;

	private boolean hasArrived = false;
	
	// you don't like the customer when they're angry and hungry
	private boolean angryAtQueue = false, angryAtWaiting = false;
	
	// bonus gold earned after satisfying this customer
	private int tip = 0;
	
	private String name;
	private String description;
	
	private int id;
	private int avatar;
	
	public GBNewCustomer(int avatar, String name, String description) {
		this.avatar = avatar;
		this.name = name;
		this.description = description;
	}
	
	// kung ung time na pinasa ay mas mataas sa time na expected
	// ibig sabihin dumating na sa shop 'tong customer na 'to
	public void arrive(long time) {
		hasArrived = arrivalTime <= time;
	}
	
	public void update(long elapsed) {
		if (hasArrived) {
			totalStayTime += elapsed;
			
			switch (state) {
				case ARRIVING:
					break;
				case IN_QUEUE:
					if (!angryAtQueue && totalStayTime >= queueTime) {
						angryAtQueue = true;
					}
					break;
				case DECIDING:
					if (totalStayTime >= decideTime) {
						state = GBCustomerState.WAITING;
						decide();
					}
					break;
				case WAITING:
					if (!angryAtWaiting && totalStayTime >= waitTime) {
						angryAtWaiting = true;
					}
					if (angryAtQueue && angryAtWaiting) {
						state = GBCustomerState.RAGE_QUIT;
					}
					break;
				case SERVED:
					break;
				case RAGE_QUIT:
					break;
			}
		}
	}
	
	private void decide() {
		// ai chever para malaman kung anu ung gustong foods
	}

	public void setState(GBCustomerState state) {
		this.state = state;
	}
	
	public GBCustomerState getState() {
		return this.state;
	}
}
