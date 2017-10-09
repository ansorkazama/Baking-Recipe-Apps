package com.ansorkazama.udacity_baking_recipe_apps.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.ansorkazama.udacity_baking_recipe_apps.util.OnItemClickListener;
import com.ansorkazama.udacity_baking_recipe_apps.R;
import com.ansorkazama.udacity_baking_recipe_apps.databinding.StepListItemBinding;
import com.ansorkazama.udacity_baking_recipe_apps.model.Step;

import java.util.ArrayList;

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.StepHolder> {
    private int mSelectedPosition = 0;
    private OnItemClickListener mStepOnItemClickListener;
    private ArrayList<Step> mStepList;

    public StepAdapter(ArrayList<Step> incomingStepSet,
                       OnItemClickListener<Step> StepOnItemClickListener) {
        this.mStepList = incomingStepSet;
        this.mStepOnItemClickListener = StepOnItemClickListener;
    }

    @Override
    public StepAdapter.StepHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        StepListItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.step_list_item, parent, false);
        return new StepHolder(binding);
    }

    @Override
    public void onBindViewHolder(StepAdapter.StepHolder holder, int position) {
        holder.mStep = mStepList.get(position);
        holder.mBinding.cvStepItemHolder.setSelected(mSelectedPosition == position);

        if(holder.mStep.getVideoURL()!=null && !holder.mStep.getVideoURL().matches("")) {
            Glide.with(holder.itemView.getContext())
                    .load(holder.mStep.getThumbnailURL())
                    .placeholder(R.drawable.video_no_thumb)
                    .error(R.drawable.video_no_thumb)
                    .dontAnimate()
                    .into(holder.mBinding.ivStepItemVideoThumb);
        } else {
            holder.mBinding.ivStepItemVideoThumb.setImageResource(R.drawable.no_video);
        }
        holder.mBinding.tvStepListStepNumber.setText(String.valueOf(holder.mStep.getId() + 1) + ": ");
        holder.mBinding.tvStepListStepShortDesc.setText(holder.mStep.getShortDescription());
    }

    @Override
    public int getItemCount() {
        return (mStepList == null) ? 0 : mStepList.size();
    }

    public class StepHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final StepListItemBinding mBinding;
        private Step mStep;

        public StepHolder(StepListItemBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;

            binding.cvStepItemHolder.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            notifyItemChanged(mSelectedPosition);
            mSelectedPosition = getLayoutPosition();
            notifyItemChanged(mSelectedPosition);
            mStepOnItemClickListener.onClick(mStep, v);
        }
    }
    public int getStepAdapterCurrentPosition(){
        return mSelectedPosition;
    }
    public void setStepAdapterCurrentPosition(int savedPosition){
        mSelectedPosition = savedPosition;
    }
}
