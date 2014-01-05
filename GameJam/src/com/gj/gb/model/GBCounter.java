package com.gj.gb.model;

import java.util.List;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gj.gb.R;
import com.gj.gb.model.GBCustomer.GBOrder;
import com.gj.gb.util.ImageCache;

public class GBCounter {

	protected Activity activity;

	protected GBCustomer slot1 = null;
	protected GBCustomer slot2 = null;
	protected GBCustomer slot3 = null;

	protected ImageView avatar1, avatar2, avatar3;

	protected LinearLayout cloud1, cloud2, cloud3;
	
	protected boolean decide1, decide2, decide3;
	
	protected List<GBRecipe> recipes;

	public GBCounter(Activity activity, List<GBRecipe> recipes) {
		this.activity = activity;
		this.recipes = recipes;

		avatar1 = (ImageView) activity.findViewById(R.id.imageCustomerSlot1);
		avatar2 = (ImageView) activity.findViewById(R.id.imageCustomerSlot2);
		avatar3 = (ImageView) activity.findViewById(R.id.imageCustomerSlot3);
//
//		cloud1 = (LinearLayout) activity.findViewById(R.id.imageCustomerCloud1);
//		cloud2 = (LinearLayout) activity.findViewById(R.id.imageCustomerCloud2);
//		cloud3 = (LinearLayout) activity.findViewById(R.id.imageCustomerCloud3);
//		
		decide1 = false;
		decide2 = false;
		decide3 = false;
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
			if (customer.hasDecided()) {
				decide1 = true;
				slot1.decide(recipes);
				setDecide(customer, cloud1);
			}
		} else if (slot2 == null) {
			slot2 = customer;
			avatar2.setVisibility(View.VISIBLE);
			cloud2.setVisibility(View.VISIBLE);
			if (customer.hasDecided()) {
				decide2 = true;
				slot2.decide(recipes);
				setDecide(customer, cloud2);
			}
		} else if (slot3 == null) {
			slot3 = customer;
			avatar3.setVisibility(View.VISIBLE);
			cloud3.setVisibility(View.VISIBLE);
			if (customer.hasDecided()) {
				decide3 = true;
				slot3.decide(recipes);
				setDecide(customer, cloud3);
			}
		}
	}

	public void check() {
		if (slot1 != null) {
			if (slot1.hasDecided() && !decide1) {
				decide1 = true;
				slot1.decide(recipes);
				setDecide(slot1, cloud1);
			}
			
			if (slot1.isDisappointed()) {
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
			if (slot2.hasDecided() && !decide2) {
				decide2 = true;
				slot2.decide(recipes);
				setDecide(slot2, cloud2);
			}
			
			if (slot2.isDisappointed()) {
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
			if (slot3.hasDecided() && !decide3) {
				decide3 = true;
				slot3.decide(recipes);
				setDecide(slot3, cloud3);
			}
			
			if (slot3.isDisappointed()) {
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

	private void setDecide(GBCustomer customer, LinearLayout cloud) {
		GBOrder order = customer.getOrder();
		Log.w("test", "Customer " + customer.getId() + " orders " + order.getQuantity() + "x " + order.getDish());
		
		((ImageView) cloud.findViewById(R.id.imageOrderIcon))
				.setImageBitmap(ImageCache.getBitmap(activity, "recipe_"
						+ order.getDish()));
		((TextView) cloud.findViewById(R.id.textOrderQty)).setText("I want "
				+ order.getQuantity() + " of this.");
	}
}
