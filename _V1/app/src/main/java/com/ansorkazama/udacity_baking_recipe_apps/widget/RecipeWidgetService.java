package com.ansorkazama.udacity_baking_recipe_apps.widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import com.ansorkazama.udacity_baking_recipe_apps.model.Recipe;

public class RecipeWidgetService extends IntentService {
    public static final String RESEP_WIDGET_ACTION_UPDATE =
            "com.ansorkazama.udacity_baking_recipe_apps.action.update";
    private static final String BDL_RECIPE_WIDGET_DATA =
            "com.ansorkazama.udacity_baking_recipe_apps.recipe_widget_data";

    public RecipeWidgetService() {
        super("RecipeWidgetService");
    }

    public static void startActionUpdateRecipeWidgets(Context context, Recipe recipe) {
        Intent intent = new Intent(context, RecipeWidgetService.class);
        intent.setAction(RESEP_WIDGET_ACTION_UPDATE);
        intent.putExtra(BDL_RECIPE_WIDGET_DATA, recipe);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (RESEP_WIDGET_ACTION_UPDATE.equals(action) &&
                    intent.getParcelableExtra(BDL_RECIPE_WIDGET_DATA) != null) {
                handleActionUpdateWidgets((Recipe)intent.getParcelableExtra(BDL_RECIPE_WIDGET_DATA));
            }
        }
    }

    private void handleActionUpdateWidgets(Recipe recipe) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, RecipeWidgetProvider.class));
        RecipeWidgetProvider.updateRecipeWidgets(this, appWidgetManager, recipe, appWidgetIds);
    }
}