package com.gj.gb.screen;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.gj.gb.R;
import com.gj.gb.factory.GBNewCustomerFactory;
import com.gj.gb.gridview.CustomerGridViewAdapter;
import com.gj.gb.gridview.IngredientGridViewAdapter;
import com.gj.gb.gridview.RecipeGridViewAdapter;
import com.gj.gb.logic.GBEconomics;
import com.gj.gb.model.GBGameData;
import com.gj.gb.model.GBIngredient;
import com.gj.gb.model.GBNewCustomer;
import com.gj.gb.model.GBRecipe;
import com.gj.gb.util.GBDataManager;
import com.gj.gb.util.ImageCache;

public class GBCommonGridList extends Activity {

	protected GBGameData data;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		data = GBDataManager.getGameData();

		int type = getIntent().getIntExtra("type", -1);
		setContent(type);

		findViewById(R.id.buttonClose).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						finish();
					}
				});
	}

	private void setContent(int type) {
		GridView grid = null;
		switch (type) {
		case R.id.buttonIngredients:
			setContentView(R.layout.scene_ingredients_grid);
			grid = (GridView) findViewById(R.id.gridList);
			grid.setAdapter(new IngredientGridViewAdapter(this, data
					.getIngredients()));
			grid.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					updateIngredientInfo(position);
				}
			});
			updateIngredientInfo(0);
			break;
		case R.id.buttonRecipe:
			setContentView(R.layout.scene_recipe_grid);
			grid = (GridView) findViewById(R.id.gridList);
			grid.setAdapter(new RecipeGridViewAdapter(this, data
					.getAvailableRecipes()));
			grid.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					updateRecipeInfo(position);
				}
			});
			updateRecipeInfo(0);
			break;
		case R.id.buttonCustomer:
			setContentView(R.layout.scene_customer_grid);
			grid = (GridView) findViewById(R.id.gridList);
			grid.setAdapter(new CustomerGridViewAdapter(this,
					GBNewCustomerFactory.getAllCustomerType()));
			grid.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					updateCustomerInfo(position);
				}
			});
			updateCustomerInfo(0);
			break;
		default:
			finish();
		}
	}

	protected void updateCustomerInfo(int position) {
		GBNewCustomer customer = GBNewCustomerFactory.getAllCustomerType().get(
				position);

		((TextView) findViewById(R.id.textCustomerName)).setText(customer
				.getName());
		((TextView) findViewById(R.id.textCustomerDescription))
				.setText(customer.getDescription());
		((ImageView) findViewById(R.id.imageCustomerIcon))
				.setImageBitmap(ImageCache.getBitmap(this, "customer_"
						+ (customer.getAvatar() - 1)));
	}

	protected void updateRecipeInfo(int position) {
		GBRecipe recipe = null;

		if (data.getAvailableRecipes().size() > 0) {
			recipe = data.getAvailableRecipes().get(position);
		}

		if (recipe == null) {
			((TextView) findViewById(R.id.textRecipeName)).setText("");
			((TextView) findViewById(R.id.textRecipeDescription)).setText("");
			((TextView) findViewById(R.id.textRecipePrice)).setText("");
			((ImageView) findViewById(R.id.imageRecipeIcon))
					.setImageBitmap(null);
		} else {

			((TextView) findViewById(R.id.textRecipeName)).setText(recipe
					.getName());
			((TextView) findViewById(R.id.textRecipeDescription))
					.setText(recipe.getDescription());
			((TextView) findViewById(R.id.textRecipePrice)).setText("Price: " + GBEconomics
					.getRecipePrice(recipe) + "G");
			((ImageView) findViewById(R.id.imageRecipeIcon))
					.setImageBitmap(ImageCache.getBitmap(this, "recipe_"
							+ recipe.getId()));
		}
	}

	protected void updateIngredientInfo(int position) {
		GBIngredient ingredient = null;

		if (data.getIngredients().size() > 0) {
			ingredient = data.getIngredients().get(position);
		}

		if (ingredient == null) {
			((TextView) findViewById(R.id.textIngredientName)).setText("");
			((TextView) findViewById(R.id.textIngredientPrice)).setText("");
			((TextView) findViewById(R.id.textIngredientQty)).setText("");
			((TextView) findViewById(R.id.textIngredientDescription))
					.setText("");
			((ImageView) findViewById(R.id.imageIcon)).setImageBitmap(null);
		} else {

			((TextView) findViewById(R.id.textIngredientName))
					.setText(ingredient.getName());
			((TextView) findViewById(R.id.textIngredientPrice)).setText("Price: "
					+ ingredient.getPrice() + "G");
			((TextView) findViewById(R.id.textIngredientQty))
					.setText("Quantity: " + ingredient.getQuantity() + "x");
			((ImageView) findViewById(R.id.imageIcon))
					.setImageBitmap(ImageCache.getBitmap(this, "ingredient_"
							+ (ingredient.getId() + 1)));
		}
	}

	@Override
	public void onBackPressed() {
		finish();
	}
}
