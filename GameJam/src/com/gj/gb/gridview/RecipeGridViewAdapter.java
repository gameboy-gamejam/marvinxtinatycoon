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
import com.gj.gb.model.GBRecipe;
import com.gj.gb.util.ImageCache;

public class RecipeGridViewAdapter extends ArrayAdapter<GBRecipe> {

	Context context;
	List<GBRecipe> data;
	
	public RecipeGridViewAdapter(Context context, List<GBRecipe> objects) {
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
		
		GBRecipe recipe = getItem(position);

		holder.imageIcon.setImageBitmap(ImageCache.getBitmap(context, "recipe_" + recipe.getId()));
		
		return super.getView(position, convertView, parent);
	}
	
	static class IngredientHolder {
		ImageView imageIcon;
	}
}
