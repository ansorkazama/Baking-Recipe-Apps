package com.ansorkazama.udacity_baking_recipe_apps.presenter;

import com.ansorkazama.udacity_baking_recipe_apps.model.Step;
import java.util.ArrayList;

public interface RecipeDetailPresenterViewContract {

    interface View {}

    interface Presenter {

        void stepClicked(ArrayList<Step> stepList, int currentStep,
                         String recipeName, android.view.View view);

    }
}

