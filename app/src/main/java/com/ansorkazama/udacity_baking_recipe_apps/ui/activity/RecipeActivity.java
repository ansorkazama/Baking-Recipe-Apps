package com.ansorkazama.udacity_baking_recipe_apps.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.design.widget.CoordinatorLayout;
import android.support.test.espresso.IdlingResource;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.ansorkazama.udacity_baking_recipe_apps.R;
import com.ansorkazama.udacity_baking_recipe_apps.adapter.RecipeListAdapter;
import com.ansorkazama.udacity_baking_recipe_apps.app.BakingApp;
import com.ansorkazama.udacity_baking_recipe_apps.app.SimpleIdlingResource;
import com.ansorkazama.udacity_baking_recipe_apps.data.model.Recipe;
import com.ansorkazama.udacity_baking_recipe_apps.util.Constants;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ansorkazama.udacity_baking_recipe_apps.util.Constants.KEY_BUNDLE;

public class RecipeActivity extends AppCompatActivity implements RecipeListAdapter.
        ItemSelectedListener, RecipeContract.View {

    private ArrayList<Recipe> mRecipeList = new ArrayList<>();
    private SharedPreferences mSharedPreferences;
    private RecipeContract.Presenter mPresenter;

    @BindView(R.id.recipe_list_recycler)
    RecyclerView mRecipeRecycler;

    @BindView(R.id.progress)
    SwipeRefreshLayout mProgressBar;

    @BindView(R.id.coordLayout)
    CoordinatorLayout mCoordLayout;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @Nullable
    private SimpleIdlingResource mIdlingResource;


    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new SimpleIdlingResource();
        }
        return mIdlingResource;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_recipe_list);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("All Recipes");

        if (savedInstanceState != null) {
            mRecipeList = savedInstanceState.getParcelableArrayList(KEY_BUNDLE);
            setRecipeList(mRecipeList);
        }

        mProgressBar.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.loadRecipe();
            }
        });
        // Get the IdlingResource instance
        getIdlingResource();


    }

    @Override
    protected void onStart() {

        super.onStart();

        if (mPresenter == null)
            new RecipePresenter(this);

        if (mRecipeList != null && mRecipeList.isEmpty()) {

            if (mIdlingResource != null) {
                mIdlingResource.setIdleState(false);
            }

            mPresenter.loadRecipe();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelableArrayList(KEY_BUNDLE, mRecipeList);
    }


    @Override
    public void setPresenter(RecipeContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showMessage(String message) {

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void showLoader(boolean show) {

        mProgressBar.setRefreshing(show);

    }


    @Override
    public void setRecipeList(ArrayList<Recipe> recipeList) {

        mRecipeList = recipeList;
        RecipeListAdapter recipeListAdapter = new RecipeListAdapter(recipeList, this);
        mRecipeRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecipeRecycler.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mRecipeRecycler.setAdapter(recipeListAdapter);

    }

    @Override
    public void testToRest() {

        if (mIdlingResource != null) {
            mIdlingResource.setIdleState(true);
        }

    }

    @Override
    public void onRecipeSelected(int position) {

        Intent intent = new Intent(this, RecipeStepActivity.class);
        intent.putExtra(Constants.RECIPE_EXTRA, mRecipeList.get(position));
        startActivity(intent);
    }


    @Override
    public void onBackPressed() {

        super.onBackPressed();
        finish();
    }
}
