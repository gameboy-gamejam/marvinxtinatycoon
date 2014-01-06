package com.gj.gb.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import android.content.Context;
import android.graphics.Bitmap;

public class ImageCache {

	private static HashMap<String, Bitmap> cache = new HashMap<String, Bitmap>();

	public static Bitmap getBitmap(Context context, String id) {
		Bitmap retVal = cache.get(id);
		
		if (retVal == null) {
			retVal = Utils.getBitmapFromDrawable(context, id);
			cache.put(id, retVal);
		}
		
		return retVal;
	}
	
	public static void cleanup() {
		int n = cache.size();
		Set<String> keys = cache.keySet();
		Iterator<String> iterator = keys.iterator();
		while (iterator.hasNext()) {
			String key = iterator.next();
			if (key != null) {
				cache.get(key).recycle();
			}
		}
		cache.clear();
		cache = null;
	}
}
