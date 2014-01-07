package com.gj.gb.screen.shop;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;

import com.gj.gb.model.GBNewCustomer;
import com.gj.gb.model.GBNewCustomer.GBCustomerState;
import com.gj.gb.model.GBRecipe;

public class GBQueueManager {

	private List<GBNewCustomer> queue;
	
	private List<GBNewCustomer> left;
	
	private GBNewCustomer slot1 = null;
	private GBNewCustomer slot2 = null;
	private GBNewCustomer slot3 = null;
	private GBNewCustomer slot4 = null;

	private GBCustomerSpriteManager spriteManager;
	
	public GBQueueManager(Activity activity) {
		this.queue = new ArrayList<GBNewCustomer>();
		this.left = new ArrayList<GBNewCustomer>();
		this.spriteManager = new GBCustomerSpriteManager(activity);
	}

	public void addNewCustomer(GBNewCustomer customer) {
		int slot = getFreeSlot();
		this.spriteManager.createSpriteFromCustomer(customer);
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
		spriteManager.makeCustomerSpriteInvisible(customer.getId());
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
		if (slot4 != null) {
			if (slot4.getId() == id) {
				slot4 = null;
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
			spriteManager.makeCustomerSpriteVisible(slot, slot1.getId());
			break;
		case 1:
			Log.w("test", "Customer " + customer.getId() + " is in Counter 2");
			slot2 = customer;
			customer.setState(GBCustomerState.DECIDING);
			spriteManager.makeCustomerSpriteVisible(slot, slot2.getId());
			break;
		case 2:
			Log.w("test", "Customer " + customer.getId() + " is in Counter 3");
			slot3 = customer;
			customer.setState(GBCustomerState.DECIDING);
			spriteManager.makeCustomerSpriteVisible(slot, slot3.getId());
			break;
		case 3:
			Log.w("test", "Customer " + customer.getId() + " is in Counter 4");
			slot4 = customer;
			customer.setState(GBCustomerState.DECIDING);
			spriteManager.makeCustomerSpriteVisible(slot, slot4.getId());
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
		if (slot4 == null)
			return 3;
		return -1;
	}

	public GBNewCustomer[] getSlots() {
		return new GBNewCustomer[] { slot1, slot2, slot3, slot4 };
	}
	
	public void update(long elapse) {
		int slot = getFreeSlot();
		while (slot >= 0) {
			if (queue.size() == 0 || slot == -1) {
				break;
			}
			GBNewCustomer customer = queue.remove(0);
			Log.w("test", "Customer " + customer.getId() + " was transferred from queue to #" + (slot+1));
			setCustomer(slot, customer);
			slot = getFreeSlot();
		}
		spriteManager.update(elapse);
	}

	public void render(Canvas canvas) {
		spriteManager.render(canvas);
	}

	public List<GBNewCustomer> getLeft() {
		return left;
	}
	
	public int getQueueSize() {
		return queue.size();
	}

	public boolean serve(GBRecipe recipe) {
		Log.w("test", "Serving " + recipe.getName());
		int id = recipe.getId();
		if (slot1 != null) {
			if (slot1.getOrder() != null && slot1.getOrder().getId() == id) {
				Log.w("test", "Customer " + slot1.getId() + " got served.");
				slot1.setState(GBCustomerState.SERVED);
				slot1 = null;
				return true;
			}
		} else if (slot2 != null) {
			if (slot2.getOrder() != null && slot2.getOrder().getId() == id) {
				Log.w("test", "Customer " + slot2.getId() + " got served.");
				slot2.setState(GBCustomerState.SERVED);
				slot2 = null;
				return true;
			}
		} else if (slot3 != null) {
			if (slot3.getOrder() != null && slot3.getOrder().getId() == id) {
				Log.w("test", "Customer " + slot3.getId() + " got served.");
				slot3.setState(GBCustomerState.SERVED);
				slot3 = null;
				return true;
			}
		} else if (slot4 != null) {
			if (slot4.getOrder() != null && slot4.getOrder().getId() == id) {
				Log.w("test", "Customer " + slot4.getId() + " got served.");
				slot4.setState(GBCustomerState.SERVED);
				slot4 = null;
				return true;
			}
		}
		return false;
	}

	public boolean onTouch(MotionEvent event) {
		return spriteManager.onTouch(event);
	}
}
