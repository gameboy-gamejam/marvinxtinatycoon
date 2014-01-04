package com.gj.gb.util;

import java.util.HashMap;

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
		cache.clear();
		cache = null;
	}
}
