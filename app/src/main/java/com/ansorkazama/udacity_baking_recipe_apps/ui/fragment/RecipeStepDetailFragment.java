package com.ansorkazama.udacity_baking_recipe_apps.ui.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ansorkazama.udacity_baking_recipe_apps.R;
import com.ansorkazama.udacity_baking_recipe_apps.app.BakingApp;
import com.ansorkazama.udacity_baking_recipe_apps.data.model.Step;
import com.ansorkazama.udacity_baking_recipe_apps.util.Constants;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecipeStepDetailFragment extends Fragment {

    private SimpleExoPlayer player;
    private Step step;
    private String videoUrl;
    private long currentPosition = 0;
    private Unbinder unbinder;


    @Nullable
    @BindView(R.id.stepThumbnail)
    ImageView mThumbnail;

    @Nullable
    @BindView(R.id.txt_instruction)
    TextView mInstructionTxt;

    @BindView(R.id.mExoPlayerView)
    SimpleExoPlayerView mExoPlayerView;

    public RecipeStepDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().getParcelable(Constants.BDL_STEP) != null) {
            step = getArguments().getParcelable(Constants.BDL_STEP);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_recipe_step_detail, container, false);
        unbinder = ButterKnife.bind(this, view);

        if (savedInstanceState != null) {
            currentPosition = savedInstanceState.getLong(Constants.PLAYER_POSITION);
        }

        if (mInstructionTxt != null) {
            mInstructionTxt.setText(step.getDescription());
        }

        //Show only if there is thumbnail URL
        if (!step.getThumbnailURL().equals("")) {
            String url = step.getThumbnailURL();
            BakingApp.loadImageFromResourceInto(getActivity(), mThumbnail, url);
            mThumbnail.setVisibility(View.VISIBLE);
        }

        videoUrl = step.getVideoURL();

        if (!videoUrl.isEmpty()) {
            setUpExoPlayer();
        }
        return view;
    }

    private void setUpExoPlayer() {


        Handler mainHandler = new Handler();
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory =
                new AdaptiveTrackSelection.Factory(bandwidthMeter);
        TrackSelector trackSelector =
                new DefaultTrackSelector(videoTrackSelectionFactory);


        player = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector);
        mExoPlayerView.setPlayer(player);
        DefaultBandwidthMeter bandwidthMeter1 = new DefaultBandwidthMeter();
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(getActivity(),
                Util.getUserAgent(getActivity(), "BakingApp"), bandwidthMeter1);
        ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
        MediaSource videoSource = new ExtractorMediaSource(Uri.parse(videoUrl),
                dataSourceFactory, extractorsFactory, mainHandler, null);
        player.prepare(videoSource);

        if (currentPosition != 0) player.seekTo(currentPosition);

        player.setPlayWhenReady(true);

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (player != null) {
            outState.putLong(Constants.PLAYER_POSITION, player.getCurrentPosition());
        }
    }

    @Override
    public void onStop() {
        super.onStop();

        if (player != null) {
            player.stop();
            player.release();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        unbinder.unbind();
    }
}
