package com.gj.gb.model;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.gj.gb.R;

public class GBCounter {

	protected GBCustomer slot1 = null;
	protected GBCustomer slot2 = null;
	protected GBCustomer slot3 = null;

	protected ImageView avatar1, avatar2, avatar3;
	
	protected LinearLayout cloud1, cloud2, cloud3;
	
	public GBCounter(Activity activity) {
		avatar1 = (ImageView) activity.findViewById(R.id.imageCustomerSlot1);
		avatar2 = (ImageView) activity.findViewById(R.id.imageCustomerSlot2);
		avatar3 = (ImageView) activity.findViewById(R.id.imageCustomerSlot3);
		
		cloud1 = (LinearLayout) activity.findViewById(R.id.imageCustomerCloud1);
		cloud2 = (LinearLayout) activity.findViewById(R.id.imageCustomerCloud2);
		cloud3 = (LinearLayout) activity.findViewById(R.id.imageCustomerCloud3);
	}
	
	public boolean hasFree() {
		if (slot1 == null)
			return true;
		if (slot2 == null)
			return true;
		if (slot3 == null)
			return true;
		return false;
	}

	public void addCustomer(GBCustomer customer) {
		if (slot1 == null) {
			slot1 = customer;
			avatar1.setVisibility(View.VISIBLE);
			cloud1.setVisibility(View.VISIBLE);
		} else if (slot2 == null) {
			slot2 = customer;
			avatar2.setVisibility(View.VISIBLE);
			cloud2.setVisibility(View.VISIBLE);
		} else if (slot3 == null) {
			slot3 = customer;
			avatar3.setVisibility(View.VISIBLE);
			cloud3.setVisibility(View.VISIBLE);
		}
	}
	
	public void check() {
		if (slot1 != null && slot1.isDisappointed()) {
			Log.w("test", "Customer " + slot1.getId() + " left the restaurant");
			slot1 = null;
			avatar1.setVisibility(View.INVISIBLE);
			cloud1.setVisibility(View.INVISIBLE);
		}
		if (slot2 != null && slot2.isDisappointed()) {
			Log.w("test", "Customer " + slot2.getId() + " left the restaurant");
			slot2 = null;
			avatar2.setVisibility(View.INVISIBLE);
			cloud2.setVisibility(View.INVISIBLE);
		}
		if (slot3 != null && slot3.isDisappointed()) {
			Log.w("test", "Customer " + slot3.getId() + " left the restaurant");
			slot3 = null;
			avatar3.setVisibility(View.INVISIBLE);
			cloud3.setVisibility(View.INVISIBLE);
		}
	}
}
