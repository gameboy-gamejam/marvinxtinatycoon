package com.gj.gb.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Random;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;

public class Utils {

	public static final Random RANDOM = new Random(System.currentTimeMillis());
	
	public static Bitmap getBitmapFromDrawable(Context context, String prefix, int id) {
		return getBitmapFromDrawable(context, prefix+"_"+id);
	}
	
	public static Bitmap getBitmapFromDrawable(Context context, String name) {
		int resid = context.getResources().getIdentifier(name, "drawable", context.getPackageName());
		BitmapDrawable drawable = (BitmapDrawable) context.getResources().getDrawable(resid);
		return drawable.getBitmap();
	}
    
    public static String formatNum(long number, String format) {
        NumberFormat formatter = new DecimalFormat(format);
        return formatter.format(number);
    }

	public static CharSequence formatDate(int currentDay, int currentMonth,
			int currentYear) {
		return "Year " + currentYear + " - " + parseMonth(currentMonth) + " " + currentDay;
	}

	private static String parseMonth(int mm) {
		switch (mm) {
		case 1: return "Jan";
		case 2: return "Feb";
		case 3: return "Mar";
		case 4: return "Apr";
		case 5: return "May";
		case 6: return "Jun";
		case 7: return "Jul";
		case 8: return "Aug";
		case 9: return "Sep";
		case 10: return "Oct";
		case 11: return "Nov";
		case 12: return "Dec";
		}
		return null;
	}
}