package com.ansorkazama.udacity_baking_recipe_apps.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.ansorkazama.udacity_baking_recipe_apps.R;
import com.ansorkazama.udacity_baking_recipe_apps.fragment.StepFragment;
import com.ansorkazama.udacity_baking_recipe_apps.databinding.ActivityStepViewpagerBinding;
import com.ansorkazama.udacity_baking_recipe_apps.model.Step;
import java.util.ArrayList;

public class StepActivity extends AppCompatActivity {
    private static final String BDL_STEP_DATA =
            "com.ansorkazama.udacity_baking_recipe_apps.step_data";
    private static final String BDL_CURRENT_RECIPE =
            "com.ansorkazama.udacity_baking_recipe_apps.current_recipe";
    private static final String BDL_CURRENT_STEP =
            "com.ansorkazama.udacity_baking_recipe_apps.current_step";
    private ViewPager mViewPager;

    private ActivityStepViewpagerBinding binding;

    public static Intent newIntent(Context packageContext, ArrayList<Step> stepList,
                                   int currentStep, String recipeName) {
        Intent intent = new Intent(packageContext, StepActivity.class);
        intent.putExtra(BDL_STEP_DATA, stepList);
        intent.putExtra(BDL_CURRENT_STEP, currentStep);
        intent.putExtra(BDL_CURRENT_RECIPE, recipeName);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_step_viewpager);

        final ArrayList<Step> stepList = getIntent().getExtras().getParcelableArrayList(BDL_STEP_DATA);
        final int currentStep = getIntent().getExtras().getInt(BDL_CURRENT_STEP);
        String currentRecipeName = getIntent().getExtras().getString(BDL_CURRENT_RECIPE);

        setSupportActionBar(binding.tbToolbar.toolbar);
        binding.tbToolbar.toolbar.setTitle(currentRecipeName);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tl_activity_step_viewpager);
        for(Step step : stepList) {
            tabLayout.addTab(tabLayout.newTab().setText(
                    String.format(getString(R.string.step_number_format), (step.getId() + 1))));
        }
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        //Fullscreen mode for non-tablet landscape orientation
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            binding.tbToolbar.toolbar.setVisibility(View.GONE);
            binding.tlActivityStepViewpager.setVisibility(View.GONE);
        }

        mViewPager = (ViewPager) findViewById(R.id.vp_activity_step_viewpager);
        FragmentManager fm = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fm) {
                                  @Override
                                  public Fragment getItem(int position) {
                                      return StepFragment.newInstance(stepList.get(position));
                                  }
                                  @Override
                                  public int getCount() {
                                      return stepList.size();
                                  }
                              });
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
        mViewPager.setCurrentItem(currentStep);
    }
}
