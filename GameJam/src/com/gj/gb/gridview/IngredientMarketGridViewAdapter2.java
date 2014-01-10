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
import com.gj.gb.factory.GBIngredientsFactory;
import com.gj.gb.logic.GBEconomics;
import com.gj.gb.model.GBIngredient;
import com.gj.gb.util.ImageCache;

public class IngredientMarketGridViewAdapter2 extends ArrayAdapter<Integer> {

	Context context;

	public IngredientMarketGridViewAdapter2(Context context,
			List<Integer> objects) {
		super(context, R.layout.part_ingredient_item, R.id.textDummy, objects);

		this.context = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		IngredientHolder holder = null;

		if (convertView == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			convertView = inflater.inflate(R.layout.part_ingredient_item,
					parent, false);

			holder = new IngredientHolder();
			holder.imageIcon = (ImageView) convertView
					.findViewById(R.id.imageIngredientIcon);
			holder.textPrice = (TextView) convertView
					.findViewById(R.id.textRarity); // reuse na lang

			convertView.setTag(holder);
		} else {
			holder = (IngredientHolder) convertView.getTag();
		}

		GBIngredient ingredient = GBIngredientsFactory
				.getIngredientById(getItem(position));
		int reprice = GBEconomics.recomputePrice(ingredient.getPrice());
		holder.textPrice.setText(reprice + "G");
		holder.textPrice.setTextColor(GBEconomics.getRateColor());

		holder.imageIcon.setImageBitmap(ImageCache.getBitmap(context,
				"ingredient_" + (ingredient.getId() + 1)));

		return super.getView(position, convertView, parent);
	}

	static class IngredientHolder {
		TextView textPrice;
		ImageView imageIcon;
	}
}
