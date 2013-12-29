package com.gj.gb.gameview;

import java.util.ArrayList;
import java.util.List;

import com.gj.gb.R;
import com.gj.gb.model.GBRecipe;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
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
public class GBKitchenAdapter extends BaseAdapter {
	private List<GBRecipe> recipe;
	private Activity mActivity;
	private LayoutInflater mInflater;

	public GBKitchenAdapter(Activity activity) {
		this.mActivity = activity;
		this.mInflater = LayoutInflater.from(this.mActivity);
	}

	public void setRecipeList(List<GBRecipe> recipe) {
		this.recipe = recipe;
		notifyDataSetChanged();
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
			holder.imageItem = (ImageView) convertView.findViewById(R.id.item_image);
			convertView.setTag(holder);
		} else {
			holder = (RecordHolder) convertView.getTag();
		}

		GBRecipe item = recipe.get(position);
		// holder.txtTitle.setText(item.getTitle());
		item.getId();
		Bitmap userIcon = BitmapFactory.decodeResource(mActivity.getResources(),
				R.drawable.ic_launcher);
		 holder.imageItem.setImageBitmap(userIcon);
		return convertView;

	}

	static class RecordHolder {
		TextView txtTitle;
		ImageView imageItem;

	}
}