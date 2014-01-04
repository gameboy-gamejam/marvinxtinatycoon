package com.gj.gb.model;

import java.util.List;

import android.util.SparseIntArray;

import com.gj.gb.util.Utils;

public class GBCustomer {

	private int id;

	private String name;

	protected int avatar;

	private String description;

	/* decreases as times goes down */
	protected int satisfaction = 100;

	protected int arriveTime;
	
	private int waitTime;

	private int tip;

	private boolean hasArrived = false;

	private boolean hasDecided = false;
	
	private boolean isDisappointed = false;

	private GBOrder order = null;

	public GBCustomer(int avatar, String name, String description) {
		this.avatar = avatar;
		this.name = name;
		this.description = description;
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
		setWaitTime();
	}
	
	private void setWaitTime() {
		int min = 10;
		int max = 15;
		
		if (avatar == 3) {
			min = 15;
			max = 20;
		} else if (avatar == 9) {
			min = 5;
			max = 10;
		}
		
		waitTime = decideTime + Utils.RANDOM.nextInt(max) + min;
	}

	public void update(int time) {
		hasArrived = (time > arriveTime);
		hasDecided = (time > decideTime);
		isDisappointed = (time > waitTime);
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean hasArrived() {
		return hasArrived;
	}

	public boolean hasDecided() {
		return hasDecided;
	}

	public GBOrder getOrder() {
		if (order == null)
			order = new GBOrder();
		return order;
	}

	public class GBOrder {

		protected SparseIntArray dishList;

		public GBOrder() {
			dishList = new SparseIntArray();
		}

		public void addDish(int dish, int quantity) {
			dishList.put(dish, quantity);
		}

		public void consume() {
			dishList.clear();
			dishList = null;
		}
	}

	public void decide(List<GBRecipe> recipes) {
		int n = recipes.size();
		switch (avatar) {
		case 1:
		case 2:
			int id = recipes.get(Utils.RANDOM.nextInt(n)).getId();
			getOrder().addDish(id, Utils.RANDOM.nextInt(2)+1);
			hasDecided = true;
			break;
		case 3:
			break;
		case 4:
			break;
		case 5:
			break;
		case 6:
			break;
		case 7:
			hasDecided = true;
			break;
		case 8:
			break;
		case 9:
			break;
		}
	}

	public boolean isDisappointed() {
		return isDisappointed;
	}
}
