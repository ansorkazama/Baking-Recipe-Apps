package com.ansorkazama.udacity_baking_recipe_apps.ui.fragment;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.ansorkazama.udacity_baking_recipe_apps.R;
import com.ansorkazama.udacity_baking_recipe_apps.adapter.IngredientListAdapter;
import com.ansorkazama.udacity_baking_recipe_apps.data.model.Ingredient;
import com.ansorkazama.udacity_baking_recipe_apps.util.Constants;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.ansorkazama.udacity_baking_recipe_apps.util.Constants.ING_BUNDLE;

public class IngredientFragment extends Fragment {

    Parcelable mListState;
    private Unbinder unbinder;
    int itemPosition;
    private ArrayList<Ingredient> ingredientArrayList;

    @BindView(R.id.ingredient_recycler)
    RecyclerView ingredient_recycler;

    public IngredientFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().getParcelableArrayList(Constants.BDL_INGREDIENT) != null) {
            ingredientArrayList = getArguments().getParcelableArrayList(Constants.BDL_INGREDIENT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_ingredient, container, false);
        unbinder = ButterKnife.bind(this, view);
        populateList();

        return view;
    }

    private void populateList() {

        IngredientListAdapter ingredientListAdapter = new IngredientListAdapter(ingredientArrayList);
        ingredient_recycler.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        ingredient_recycler.setAdapter(ingredientListAdapter);
        ingredient_recycler.getLayoutManager().smoothScrollToPosition(ingredient_recycler,
                null, itemPosition);

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        super.onSaveInstanceState(outState);
        mListState = ingredient_recycler.getLayoutManager().onSaveInstanceState();
        itemPosition = ((LinearLayoutManager) ingredient_recycler.getLayoutManager())
                .findLastVisibleItemPosition();
        outState.putParcelable(ING_BUNDLE, mListState);
        outState.putInt("position", itemPosition);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        if (savedInstanceState != null) {
            mListState = savedInstanceState.getParcelable(ING_BUNDLE);
            itemPosition = savedInstanceState.getInt("position");
            ingredient_recycler.getLayoutManager().onRestoreInstanceState(mListState);
            ingredient_recycler.getLayoutManager().smoothScrollToPosition(ingredient_recycler,
                    null, itemPosition-1);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        unbinder.unbind();
    }
}
