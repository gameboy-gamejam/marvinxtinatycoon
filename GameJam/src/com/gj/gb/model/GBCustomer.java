package com.gj.gb.model;

public class GBCustomer {

	protected int id;
	
	protected int avatar;
	
	/* decreases as times goes down */
	protected int satisfaction = 100;
	
	protected int arriveTime;
	
	public int getArriveTime() {
		return arriveTime;
	}

	public void setArriveTime(int arriveTime) {
		this.arriveTime = arriveTime;
	}

	public int getDecideTime() {
		return decideTime;
	}

	public void setDecideTime(int decideTime) {
		this.decideTime = decideTime;
	}

	protected int decideTime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAvatar() {
		return avatar;
	}

	public void setAvatar(int avatar) {
		this.avatar = avatar;
	}

	public int getSatisfaction() {
		return satisfaction;
	}

	public void setSatisfaction(int satisfaction) {
		this.satisfaction = satisfaction;
	}
}
