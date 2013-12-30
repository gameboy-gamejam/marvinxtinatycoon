package com.gj.gb.model;

public class GBOven {
	public enum OvenStatus {
		VACANT, 
		COOKING, 
		FINISHED, 
		OVERCOOKED
	}
	
	private OvenStatus mStatus;
	private int mDishId;
	
	public GBOven(){
		this.mStatus = OvenStatus.VACANT;
		this.mDishId = -1;
	}

	public OvenStatus getStatus() {
		return mStatus;
	}

	public void setStatus(OvenStatus status) {
		this.mStatus = status;
	}

	public int getDishId() {
		return mDishId;
	}

	public void setDishId(int dishId) {
		this.mDishId = dishId;
	}
}
