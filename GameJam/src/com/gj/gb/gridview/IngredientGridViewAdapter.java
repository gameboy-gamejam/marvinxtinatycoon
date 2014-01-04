package com.gj.gb.gridview;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gj.gb.R;
import com.gj.gb.model.GBIngredient;
import com.gj.gb.util.ImageCache;

public class IngredientGridViewAdapter extends ArrayAdapter<GBIngredient> {

	Context context;
	List<GBIngredient> data;
	
	public IngredientGridViewAdapter(Context context, List<GBIngredient> objects) {
		super(context, R.layout.part_ingredient_item, R.id.textDummy, objects);
		
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
			holder.textRarity = (TextView) convertView.findViewById(R.id.textRarity);
			
			convertView.setTag(holder);
		} else {
			holder = (IngredientHolder) convertView.getTag();
		}
		
		GBIngredient ingredient = getItem(position);

		holder.textRarity.setText(ingredient.getRarity() + "☆");
		holder.imageIcon.setImageBitmap(ImageCache.getBitmap(context, "ingredient_"+(ingredient.getId()+1)));
		
		return super.getView(position, convertView, parent);
	}
	
	static class IngredientHolder {
		TextView textRarity;
		ImageView imageIcon;
	}
}
