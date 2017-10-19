package com.ansorkazama.udacity_baking_recipe_apps.ui.activity;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;

import com.ansorkazama.udacity_baking_recipe_apps.R;
import com.ansorkazama.udacity_baking_recipe_apps.data.model.Ingredient;
import com.ansorkazama.udacity_baking_recipe_apps.data.model.Recipe;
import com.ansorkazama.udacity_baking_recipe_apps.data.source.local.IngredientContract;
import com.ansorkazama.udacity_baking_recipe_apps.util.Constants;
import com.ansorkazama.udacity_baking_recipe_apps.widget.AppWidgetProvider;

import java.util.ArrayList;

import static com.ansorkazama.udacity_baking_recipe_apps.util.Constants.SHAREDPREF;

public class RecipeStepPresenter implements RecipeStepContract.Presenter {

    RecipeStepContract.View mView;
    SharedPreferences mSharedPreferences;


    public RecipeStepPresenter(RecipeStepContract.View view) {

        this.mView = view;
        mView.setPresenter(this);
        mSharedPreferences = getView().getSharedPreferences(SHAREDPREF, Context.MODE_PRIVATE);
    }

    @Override
    public void subscribe() {}

    @Override
    public void unsubscribe() {}

    @Override
    public void addIngredientToWidget(Recipe recipe) {

        ArrayList<Ingredient> ingredients = recipe.getIngredients();
        String recipeName = recipe.getName();

        mSharedPreferences.edit().putString(Constants.PREF_RECIPE, recipeName).apply();

        Uri uri1 = IngredientContract.CONTENT_URI;
        Cursor cursor = getView().getContentResolver().query(uri1, null, null, null, null);

        if (cursor != null) {

            //Delete the existing data
            while (cursor.moveToNext()) {
                Uri uri2 = IngredientContract.CONTENT_URI;
                getView().getContentResolver().delete(uri2,
                        IngredientContract.Columns._ID + "=?",
                        new String[]{cursor.getString(0)});
            }

            //Insert into database
            ContentValues values = new ContentValues();

            for (Ingredient ingredient : ingredients) {
                values.clear();
                values.put(IngredientContract.Columns.QTY, ingredient.getQuantity());
                values.put(IngredientContract.Columns.MSR, ingredient.getMeasure());
                values.put(IngredientContract.Columns.ING, ingredient.getIngredient());

                Uri uri = IngredientContract.CONTENT_URI;
                getView().getContentResolver().insert(uri, values);
            }
        }

        int[] ids = AppWidgetManager.getInstance(getView().getApplication())
                .getAppWidgetIds(new ComponentName(getView().getApplication(), AppWidgetProvider.class));

        AppWidgetProvider ingredientWidget = new AppWidgetProvider();
        ingredientWidget.onUpdate(getView(), AppWidgetManager.getInstance(getView()), ids);

        Context context = getView().getApplicationContext();
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);

        ComponentName thisWidget = new ComponentName(context, AppWidgetProvider.class);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.ing_widget_list);
        mView.showMessage("successfully added");
    }

    @Override
    public Activity getView() {
        return (Activity) mView;
    }

}
