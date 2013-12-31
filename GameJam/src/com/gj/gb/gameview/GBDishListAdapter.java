package com.gj.gb.gameview;

import java.util.ArrayList;
import java.util.List;

import com.gj.gb.R;
import com.gj.gb.model.GBRecipe;
import com.gj.gb.util.Utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 
 * @author manish.s
 * 
 */
public class GBDishListAdapter extends BaseAdapter {
	private List<GBRecipe> recipe;
	private Activity mActivity;
	private LayoutInflater mInflater;
	private TextView mName;
	private TextView mDescription;
	private TextView mPrice;
	private int mDishId;

	public GBDishListAdapter(Activity activity) {
		this.mActivity = activity;
		this.mInflater = LayoutInflater.from(this.mActivity);
		this.mDishId = -1;
	}

	public void setDishId(int dishId) {
		this.mDishId = dishId;
	}
	
	public int getDishId() {
		return mDishId;
	}

	public void setRecipeList(List<GBRecipe> recipe) {
		this.recipe = recipe;
		notifyDataSetChanged();
	}

	public void setInfoView(TextView name, TextView description, TextView price) {
		this.mName = name;
		this.mDescription = description;
		this.mPrice = price;
	}

	@Override
	public int getCount() {
		return recipe.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		RecordHolder holder;

		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.part_gridview, null);
			holder = new RecordHolder();
			// holder.txtTitle = (TextView) row.findViewById(R.id.item_text);
			holder.imageItem = (ImageView) convertView
					.findViewById(R.id.item_image);
			convertView.setTag(holder);
		} else {
			holder = (RecordHolder) convertView.getTag();
		}

		final GBRecipe item = recipe.get(position);
		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mName.setText(item.getName());
				mDescription.setText(item.getDescription());
				mPrice.setText(item.getPrice() + " Gold");
				setDishId(item.getId());
			}
		});

		Bitmap userIcon = Utils.getBitmapFromDrawable(mActivity, "recipe",
				item.getId());
		holder.imageItem.setImageBitmap(userIcon);
		return convertView;

	}

	static class RecordHolder {
		TextView txtTitle;
		ImageView imageItem;

	}
}