package com.gj.gb.gridview;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.gj.gb.R;
import com.gj.gb.model.GBCustomer;
import com.gj.gb.model.GBNewCustomer;

public class CustomerGridViewAdapter extends ArrayAdapter<GBNewCustomer> {

	Context context;
	List<GBNewCustomer> data;
	
	public CustomerGridViewAdapter(Context context, List<GBNewCustomer> objects) {
		super(context, R.layout.part_gridview, R.id.textDummy, objects);
		
		this.context = context;
		this.data = objects;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		IngredientHolder holder = null;
		
		if (convertView == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			convertView = inflater.inflate(R.layout.part_ingredient_item, parent, false);
			
			holder = new IngredientHolder();
			holder.imageIcon = (ImageView) convertView.findViewById(R.id.imageIngredientIcon);
			
			convertView.setTag(holder);
		} else {
			holder = (IngredientHolder) convertView.getTag();
		}
		
		GBNewCustomer customer = getItem(position);

//		holder.imageIcon.setImageBitmap(ImageCache.getBitmap(context, "ingredient_"+(ingredient.getId()+1)));
		
		return super.getView(position, convertView, parent);
	}
	
	static class IngredientHolder {
		ImageView imageIcon;
	}
}
