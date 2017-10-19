package com.ansorkazama.udacity_baking_recipe_apps.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;


import com.ansorkazama.udacity_baking_recipe_apps.R;
import com.ansorkazama.udacity_baking_recipe_apps.data.model.Recipe;
import com.ansorkazama.udacity_baking_recipe_apps.util.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.ansorkazama.udacity_baking_recipe_apps.util.Constants.ING_FRAG;
import static com.ansorkazama.udacity_baking_recipe_apps.util.Constants.STEP_FRAG;

public class RecipeFragment extends Fragment {


    Fragment mStepFragment;
    Fragment mIngredientFragment;
    private Unbinder unbinder;
    private Recipe recipe;

    @BindView(R.id.step_frag)
    FrameLayout mStepView;
    @BindView(R.id.ingredient_frag)
    FrameLayout mIngredientView;

    public RecipeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().getParcelable(Constants.RECIPE_EXTRA) != null) {
            recipe = getArguments().getParcelable(Constants.RECIPE_EXTRA);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_recipe, container, false);
        unbinder = ButterKnife.bind(this, view);

        setUpStepAndIngredient(savedInstanceState);
        return view;
    }


    private void setUpStepAndIngredient(Bundle savedInstanceState) {


        if (savedInstanceState != null) {

            mIngredientFragment = getActivity().getSupportFragmentManager()
                    .getFragment(savedInstanceState, ING_FRAG);
            mStepFragment = getActivity().getSupportFragmentManager()
                    .getFragment(savedInstanceState, STEP_FRAG);

        } else {

            Bundle ingredientBundle = new Bundle();
            ingredientBundle.putParcelableArrayList(Constants.BDL_INGREDIENT, recipe.getIngredients());

            Bundle stepBundle = new Bundle();
            stepBundle.putParcelableArrayList(Constants.BDL_STEP, recipe.getSteps());

            mIngredientFragment = new IngredientFragment();
            mIngredientFragment.setArguments(ingredientBundle);

            mStepFragment = new StepFragment();
            mStepFragment.setArguments(stepBundle);
            getActivity().getSupportFragmentManager().beginTransaction()
                    .add(R.id.ingredient_frag, mIngredientFragment).commit();
            getActivity().getSupportFragmentManager().beginTransaction()
                    .add(R.id.step_frag, mStepFragment).commit();

        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        super.onSaveInstanceState(outState);

        if (mIngredientFragment.isAdded()) {
            getActivity().getSupportFragmentManager().putFragment(outState,
                    ING_FRAG, mIngredientFragment);
        }
        if (mStepFragment.isAdded()) {
            getActivity().getSupportFragmentManager().putFragment(outState,
                    STEP_FRAG, mStepFragment);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        unbinder.unbind();
    }

}
