package com.ansorkazama.udacity_baking_recipe_apps.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.ansorkazama.udacity_baking_recipe_apps.R;
import com.ansorkazama.udacity_baking_recipe_apps.adapter.StepListAdapter;
import com.ansorkazama.udacity_baking_recipe_apps.data.model.Step;
import com.ansorkazama.udacity_baking_recipe_apps.util.Constants;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.ansorkazama.udacity_baking_recipe_apps.util.Constants.STEP_BUNDLE;

public class StepFragment extends Fragment implements StepListAdapter.StepSelectedListener {


    Parcelable mListState;
    int itemPosition;
    private ArrayList<Step> stepArrayList;
    private LinearLayoutManager mLinearManager;
    private StepListener stepListener;
    private Unbinder unbinder;

    @BindView(R.id.step_recycler)
    RecyclerView step_recycler;

    public interface StepListener {
        void itemClicked(Step step);
    }

    public StepFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().getParcelableArrayList(Constants.BDL_STEP) != null) {
            stepArrayList = getArguments().getParcelableArrayList(Constants.BDL_STEP);
        }
        if (savedInstanceState != null) {
            mListState = savedInstanceState.getParcelable(STEP_BUNDLE);
        }
        setRetainInstance(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_step, container, false);
        unbinder = ButterKnife.bind(this, view);

        if (savedInstanceState != null) {
            mListState = savedInstanceState.getParcelable(STEP_BUNDLE);
            itemPosition =savedInstanceState.getInt("position");
        }

        mLinearManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        populateList();

        return view;
    }

    public void restoreState() {

        if (mListState != null) {
            step_recycler.getLayoutManager().onRestoreInstanceState(mListState);
            step_recycler.getLayoutManager().scrollToPosition(itemPosition);
        }
    }

    private void populateList() {

        StepListAdapter stepListAdapter = new StepListAdapter(stepArrayList, this);
        step_recycler.setLayoutManager(mLinearManager);
        step_recycler.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        step_recycler.setAdapter(stepListAdapter);
        restoreState();
    }

    @Override
    public void onStepSelected(int position) {

        Step step = stepArrayList.get(position);
        stepListener.itemClicked(step);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            stepListener = (StepListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(context.toString()
                    + " must implement StepListener");
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        mListState = step_recycler.getLayoutManager().onSaveInstanceState();
        outState.putParcelable(STEP_BUNDLE, mListState);
        itemPosition = ((LinearLayoutManager) step_recycler.getLayoutManager())
                .findLastVisibleItemPosition();
        outState.putInt("position", itemPosition);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroy() {

        super.onDestroy();
        unbinder.unbind();
    }
}
