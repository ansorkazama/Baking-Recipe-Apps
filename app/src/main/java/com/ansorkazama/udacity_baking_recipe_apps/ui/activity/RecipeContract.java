package com.ansorkazama.udacity_baking_recipe_apps.ui.activity;

import com.ansorkazama.udacity_baking_recipe_apps.data.model.Recipe;
import com.ansorkazama.udacity_baking_recipe_apps.ui.BasePresenter;
import com.ansorkazama.udacity_baking_recipe_apps.ui.BaseView;

import java.util.ArrayList;


public interface RecipeContract {

    interface View extends BaseView<Presenter> {

        void setRecipeList(ArrayList<Recipe> recipeList);
        void testToRest();
    }

    interface Presenter extends BasePresenter {

        void loadRecipe();



    }
}
