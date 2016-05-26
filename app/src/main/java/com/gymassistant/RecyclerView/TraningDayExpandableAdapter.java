package com.gymassistant.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.Adapter.ExpandableRecyclerAdapter;
import com.bignerdranch.expandablerecyclerview.Model.ParentListItem;
import com.bignerdranch.expandablerecyclerview.ViewHolder.ChildViewHolder;
import com.bignerdranch.expandablerecyclerview.ViewHolder.ParentViewHolder;
import com.gymassistant.Models.StartedTrainingPlan;
import com.gymassistant.R;

import java.util.List;

/**
 * Created by KamilH on 2016-05-24.
 */
public class TraningDayExpandableAdapter extends ExpandableRecyclerAdapter<TraningDayExpandableAdapter.PlanParentViewHolder,
        TraningDayExpandableAdapter.PlanChildViewHolder> {
    private LayoutInflater mInflator;
    private Context context;
    private List<? extends ParentListItem> parentItemList;

    public TraningDayExpandableAdapter(Context context, @NonNull List<? extends ParentListItem> parentItemList) {
        super(parentItemList);
        this.context = context;
        this.mInflator = LayoutInflater.from(context);
        this.parentItemList = parentItemList;
    }

    @Override
    public PlanParentViewHolder onCreateParentViewHolder(ViewGroup parentViewGroup) {
        View parentView = mInflator.inflate(R.layout.item_parent, parentViewGroup, false);
        return new PlanParentViewHolder(parentView);
    }

    @Override
    public PlanChildViewHolder onCreateChildViewHolder(ViewGroup childViewGroup) {
        View childView = mInflator.inflate(R.layout.item_recyclerview, childViewGroup, false);
        return new PlanChildViewHolder(childView);
    }

    @Override
    public void onBindParentViewHolder(PlanParentViewHolder parentViewHolder, int position, ParentListItem parentListItem) {
        StartedTrainingPlan item = (StartedTrainingPlan) parentListItem;
        parentViewHolder.nameTextView.setText(item.getName());
    }

    @Override
    public void onBindChildViewHolder(PlanChildViewHolder childViewHolder, final int position, final Object childListItem) {
        final StartedTrainingPlan item = (StartedTrainingPlan) childListItem;

        TrainingDayAdapter trainingDayAdapter = new TrainingDayAdapter(context, item.getTrainingPlan().getTrainingList(), item.getId());
        childViewHolder.recyclerView.setLayoutManager(new LinearLayoutManager(context));
        childViewHolder.recyclerView.setAdapter(trainingDayAdapter);
    }

    public class PlanChildViewHolder extends ChildViewHolder {
        public View view;
        public RecyclerView recyclerView;

        public PlanChildViewHolder(View view) {
            super(view);
            this.recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        }
    }

    public class PlanParentViewHolder extends ParentViewHolder {
        public TextView nameTextView;
        private ImageView arrowImageView;

        private static final float INITIAL_POSITION = 0.0f;
        private static final float ROTATED_POSITION = 180f;

        public PlanParentViewHolder(View itemView) {
            super(itemView);

            nameTextView = (TextView) itemView.findViewById(R.id.exerciseNameTextView);
            arrowImageView = (ImageView) itemView.findViewById(R.id.arrowImageView);
        }

        @SuppressLint("NewApi")
        @Override
        public void setExpanded(boolean expanded) {
            super.setExpanded(expanded);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                if (expanded) {
                    arrowImageView.setRotation(ROTATED_POSITION);
                } else {
                    arrowImageView.setRotation(INITIAL_POSITION);
                }
            }
        }

        @Override
        public void onExpansionToggled(boolean expanded) {
            super.onExpansionToggled(expanded);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                RotateAnimation rotateAnimation;
                if (expanded) { // rotate clockwise
                    rotateAnimation = new RotateAnimation(ROTATED_POSITION,
                            INITIAL_POSITION,
                            RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                            RotateAnimation.RELATIVE_TO_SELF, 0.5f);
                } else { // rotate counterclockwise
                    rotateAnimation = new RotateAnimation(-1 * ROTATED_POSITION,
                            INITIAL_POSITION,
                            RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                            RotateAnimation.RELATIVE_TO_SELF, 0.5f);
                }

                rotateAnimation.setDuration(200);
                rotateAnimation.setFillAfter(true);
                arrowImageView.startAnimation(rotateAnimation);
            }
        }
    }
}