package com.ansorkazama.udacity_baking_recipe_apps.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;

import com.ansorkazama.udacity_baking_recipe_apps.fragment.RecipeListFragment;
import com.ansorkazama.udacity_baking_recipe_apps.presenter.RecipeListPresenter;
import com.ansorkazama.udacity_baking_recipe_apps.model.Recipe;
import com.ansorkazama.udacity_baking_recipe_apps.widget.RecipeWidgetService;

public class MainActivity extends BasisActivity implements RecipeListPresenter.Callbacks {
    @Override
    protected Fragment createFragment() {
        return new RecipeListFragment();
    }

    @Override
    public void recipeSelected(Recipe recipe) {
        Intent intent = RecipeDetailActivity.newIntent(this, recipe);
        RecipeWidgetService.startActionUpdateRecipeWidgets(this, recipe);
        startActivity(intent);
    }
}
