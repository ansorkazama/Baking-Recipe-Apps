package com.ansorkazama.udacity_baking_recipe_apps.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.ansorkazama.udacity_baking_recipe_apps.R;
import com.ansorkazama.udacity_baking_recipe_apps.fragment.RecipeDetailFragment;
import com.ansorkazama.udacity_baking_recipe_apps.presenter.RecipeDetailPresenter;
import com.ansorkazama.udacity_baking_recipe_apps.fragment.StepFragment;
import com.ansorkazama.udacity_baking_recipe_apps.model.Recipe;
import com.ansorkazama.udacity_baking_recipe_apps.model.Step;
import java.util.ArrayList;

public class RecipeDetailActivity extends AppCompatActivity implements RecipeDetailPresenter.Callbacks {
    private static final String BDL_RECIPE_DATA =
            "com.ansorkazama.udacity_baking_recipe_apps.recipe_data";

    public static Intent newIntent(Context packageContext, Recipe recipe) {
        Intent intent = new Intent(packageContext, RecipeDetailActivity.class);
        intent.putExtra(BDL_RECIPE_DATA, recipe);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_masterdetail);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);
        Recipe recipe = getIntent().getExtras().getParcelable(BDL_RECIPE_DATA);

        if (fragment == null) {
            fragment = RecipeDetailFragment.newInstance(recipe);
            fm.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();

            if (getResources().getBoolean(R.bool.isTablet)) {
                Fragment newDetail = StepFragment.newInstance(recipe.getSteps().get(0));
                getSupportFragmentManager().beginTransaction()
                    .replace(R.id.detail_fragment_container, newDetail)
                    .commit();
            }
        }
    }

    @Override
    public void stepSelected(ArrayList<Step> stepList, int currentStep, String recipeName) {
        if (!getResources().getBoolean(R.bool.isTablet)) {
            Intent intent = StepActivity.newIntent(this, stepList, currentStep, recipeName);
            startActivity(intent);
        } else {
            Fragment newDetail = StepFragment.newInstance(stepList.get(currentStep));
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.detail_fragment_container, newDetail)
                    .commit();
        }
    }
}
