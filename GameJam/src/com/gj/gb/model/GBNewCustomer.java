package com.gj.gb.model;

import java.util.List;

import com.gj.gb.util.Utils;

public class GBNewCustomer {

	public enum GBCustomerState {
		
		ARRIVING,
		
		ARRIVED,
		
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

	// you don't like the customer when they're angry and hungry
	private boolean angryAtQueue = false, angryAtWaiting = false;
	
	// bonus gold earned after satisfying this customer
	private int tip = 0;

	private int id;
	private int avatar;
	
	private String name;
	private String description;
	
	private List<GBRecipe> selection;

	private long offset = 0;
	
	private GBRecipe order = null;
	
	public GBNewCustomer(int avatar, String name, String description) {
		this.avatar = avatar;
		this.name = name;
		this.description = description;
	}
	
	public int getId() {
		return id;
	}

	public int getAvatar() {
		return avatar;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public void update(long elapsed) {
		totalStayTime += elapsed;
		
		switch (state) {
			case ARRIVING:
				if (totalStayTime >= arrivalTime) {
					state = GBCustomerState.ARRIVED;
				}
				break;
			case ARRIVED:
				break;
			case IN_QUEUE:
				offset = queueTime;
				if (!angryAtQueue && totalStayTime >= (queueTime + arrivalTime)) {
					angryAtQueue = true;
				}
				break;
			case DECIDING:
				long decidePlus = offset + arrivalTime;
				if (totalStayTime >= (decideTime + decidePlus)) {
					state = GBCustomerState.WAITING;
					decide();
				}
				break;
			case WAITING:
				long waitPlus = offset + arrivalTime + decideTime;
				if (!angryAtWaiting && totalStayTime >= (waitTime + waitPlus)) {
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
	
	private void decide() {
		// ai chever para malaman kung anu ung gustong foods
		int n = selection.size();
		int index = Utils.RANDOM.nextInt(n);
		
		order = selection.get(index);
	}

	public void setState(GBCustomerState state) {
		this.state = state;
	}
	
	public GBCustomerState getState() {
		return this.state;
	}
	
	public void setMenu(List<GBRecipe> recipes) {
		if (this.selection != null) {
			this.selection.clear();
		}
		this.selection = recipes;
	}

	public void setTimeBounds(long arrive) {
		this.arrivalTime = arrive;
		this.waitTime = getTolerableWaitingTime();
		this.queueTime = this.waitTime;
		this.decideTime = getDecidingTime();
	}

	private long getDecidingTime() {
		int retVal = 2 + Utils.RANDOM.nextInt(2);
		
		// the indecisive && prankster
		if (avatar == 1) {
			retVal += 2;
		}
		
		return retVal;
	}

	private int getTolerableWaitingTime() {
		int retVal = 15 + Utils.RANDOM.nextInt(2);
		
		// the impatient
		if (avatar == 5) {
			retVal -= 5;
		}
		
		return retVal * 1000;
	}
	
	public GBRecipe getOrder() {
		return order;
	}
}
