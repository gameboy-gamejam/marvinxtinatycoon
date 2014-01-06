package com.gj.gb.model;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.util.Log;

import com.gj.gb.model.GBNewCustomer.GBCustomerState;

public class GBQueueManager {

	private List<GBNewCustomer> queue;
	
	private List<GBNewCustomer> left;

	private GBNewCustomer slot1 = null;
	private GBNewCustomer slot2 = null;
	private GBNewCustomer slot3 = null;

	public GBQueueManager(Activity activity) {
		queue = new ArrayList<GBNewCustomer>();
		left = new ArrayList<GBNewCustomer>();
	}

	public void addNewCustomer(GBNewCustomer customer) {
		int slot = getFreeSlot();

		if (slot >= 0) {
			setCustomer(slot, customer);
		} else {
			Log.w("test", "Customer " + customer.getId() + " was added to the queue");
			customer.setState(GBCustomerState.IN_QUEUE);
			queue.add(customer);
		}
	}

	public void removeCustomer(GBNewCustomer customer) {
		left.add(customer);
		int id = customer.getId();
		Log.w("test", "Customer " + id + " left the shop.");
		// check first the queue
		int n = queue.size();
		for (int i=0; i<n; i++) {
			GBNewCustomer c = queue.get(i);
			if (c.getId() == id) {
				queue.remove(c);
				return;
			}
		}
		
		// check the slots
		if (slot1 != null) {
			if (slot1.getId() == id) {
				slot1 = null;
				return;
			}
		}
		if (slot2 != null) {
			if (slot2.getId() == id) {
				slot2 = null;
				return;
			}
		}
		if (slot3 != null) {
			if (slot3.getId() == id) {
				slot3 = null;
				return;
			}
		}
	}

	private void setCustomer(int slot, GBNewCustomer customer) {
		switch (slot) {
		case 0:
			Log.w("test", "Customer " + customer.getId() + " is in Counter 1");
			slot1 = customer;
			customer.setState(GBCustomerState.DECIDING);
			break;
		case 1:
			Log.w("test", "Customer " + customer.getId() + " is in Counter 2");
			slot2 = customer;
			customer.setState(GBCustomerState.DECIDING);
			break;
		case 2:
			Log.w("test", "Customer " + customer.getId() + " is in Counter 3");
			slot3 = customer;
			customer.setState(GBCustomerState.DECIDING);
			break;
		}
	}

	private int getFreeSlot() {
		if (slot1 == null)
			return 0;
		if (slot2 == null)
			return 1;
		if (slot3 == null)
			return 2;
		return -1;
	}

	public GBNewCustomer[] getSlots() {
		return new GBNewCustomer[] { slot1, slot2, slot3 };
	}
	
	public void update(long elapse) {
		int slot = getFreeSlot();
		while (slot >= 0) {
			slot = getFreeSlot();
			if (queue.size() == 0) {
				break;
			}
			GBNewCustomer customer = queue.remove(0);
			setCustomer(slot, customer);
		}
	}

	public List<GBNewCustomer> getLeft() {
		return left;
	}
	
	public int getQueueSize() {
		return queue.size();
	}

	public boolean serve(GBRecipe recipe) {
		int id = recipe.getId();
		if (slot1 != null) {
			if (slot1.getOrder() != null && slot1.getOrder().id == id) {
				Log.w("test", "Customer " + slot1.getId() + " got served.");
				slot1.setState(GBCustomerState.SERVED);
				slot1 = null;
				return true;
			}
		} else if (slot2 != null) {
			if (slot2.getOrder() != null && slot2.getOrder().id == id) {
				Log.w("test", "Customer " + slot2.getId() + " got served.");
				slot2.setState(GBCustomerState.SERVED);
				slot2 = null;
				return true;
			}
		} else if (slot3 != null) {
			if (slot3.getOrder() != null && slot3.getOrder().id == id) {
				Log.w("test", "Customer " + slot3.getId() + " got served.");
				slot3.setState(GBCustomerState.SERVED);
				slot3 = null;
				return true;
			}
		}
		return false;
	}
}
