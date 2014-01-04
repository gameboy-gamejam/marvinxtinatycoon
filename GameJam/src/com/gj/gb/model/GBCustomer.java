package com.gj.gb.model;

public class GBCustomer {

	private int id;
	
	private String name;
	
	protected int avatar;
	
	/* decreases as times goes down */
	protected int satisfaction = 100;
	
	protected int arriveTime;
	
	private int tip;
	
	public GBCustomer(int avatar, String name) {
		this.avatar = avatar;
		this.name = name;
	}
	
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getTip() {
		return tip;
	}

	public void setTip(int tip) {
		this.tip = tip;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
}
