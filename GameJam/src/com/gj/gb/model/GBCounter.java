package com.gj.gb.model;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gj.gb.R;
import com.gj.gb.model.GBNewCustomer.GBCustomerState;
import com.gj.gb.util.ImageCache;

public class GBCounter {

	protected Activity activity;

	protected GBNewCustomer slot1 = null;
	protected GBNewCustomer slot2 = null;
	protected GBNewCustomer slot3 = null;

	protected ImageView avatar1, avatar2, avatar3;

	protected LinearLayout cloud1, cloud2, cloud3;
	
	protected boolean decide1, decide2, decide3;
	
	protected List<GBRecipe> recipes;

	private List<GBNewCustomer> queue;	
	private List<GBNewCustomer> left;

	public GBCounter(Activity activity, List<GBRecipe> recipes) {
		this.activity = activity;
		this.recipes = recipes;

		avatar1 = (ImageView) activity.findViewById(R.id.imageCustomerSlot1);
		avatar2 = (ImageView) activity.findViewById(R.id.imageCustomerSlot2);
		avatar3 = (ImageView) activity.findViewById(R.id.imageCustomerSlot3);

		cloud1 = (LinearLayout) activity.findViewById(R.id.imageCustomerCloud1);
		cloud2 = (LinearLayout) activity.findViewById(R.id.imageCustomerCloud2);
		cloud3 = (LinearLayout) activity.findViewById(R.id.imageCustomerCloud3);
		
		decide1 = false;
		decide2 = false;
		decide3 = false;

		queue = new ArrayList<GBNewCustomer>();
		left = new ArrayList<GBNewCustomer>();
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

	public void addCustomer(GBNewCustomer customer) {
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
		if (slot1 != null) {
			if (slot1.getState() == GBCustomerState.WAITING  && !decide1) {
				decide1 = true;
				setDecide(slot1, cloud1);
			}
			
			if (slot1.getState() == GBCustomerState.LEAVING) {
				Log.w("test", "Customer " + slot1.getId()
						+ " left the restaurant");
				slot1 = null;
				avatar1.setVisibility(View.INVISIBLE);
				cloud1.setVisibility(View.INVISIBLE);
				decide1 = false;
				resetDecide(cloud1);
			}
		}

		if (slot2 != null) {
			if (slot2.getState() == GBCustomerState.WAITING  && !decide2) {
				decide2 = true;
				setDecide(slot2, cloud2);
			}
			
			if (slot2.getState() == GBCustomerState.LEAVING) {
				Log.w("test", "Customer " + slot2.getId()
						+ " left the restaurant");
				slot2 = null;
				avatar2.setVisibility(View.INVISIBLE);
				cloud2.setVisibility(View.INVISIBLE);
				decide2 = false;
				resetDecide(cloud2);
			}
		}

		if (slot3 != null) {
			if (slot3.getState() == GBCustomerState.WAITING  && !decide3) {
				decide3 = true;
				setDecide(slot3, cloud3);
			}
			
			if (slot3.getState() == GBCustomerState.LEAVING) {
				Log.w("test", "Customer " + slot3.getId()
						+ " left the restaurant");
				slot3 = null;
				avatar3.setVisibility(View.INVISIBLE);
				cloud3.setVisibility(View.INVISIBLE);
				decide3 = false;
				resetDecide(cloud3);
			}
		}
	}

	private void resetDecide(LinearLayout cloud) {
		((ImageView) cloud.findViewById(R.id.imageOrderIcon))
				.setImageBitmap(null);
		((TextView) cloud.findViewById(R.id.textOrderQty)).setText("....");
	}

	private void setDecide(GBNewCustomer customer, LinearLayout cloud) {
		((ImageView) cloud.findViewById(R.id.imageOrderIcon))
				.setImageBitmap(ImageCache.getBitmap(activity, "recipe_"
						+ customer.getOrder().getId()));
		((TextView) cloud.findViewById(R.id.textOrderQty)).setText("I want 1 of this.");
	}
}
