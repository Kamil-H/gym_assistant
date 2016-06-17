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
import com.gymassistant.Database.StartedTrainingPlanDB;
import com.gymassistant.Models.SeriesDoneGroup;
import com.gymassistant.Models.StartedTrainingPlan;
import com.gymassistant.Models.TrainingDone;
import com.gymassistant.R;

import java.util.List;

/**
 * Created by KamilH on 2016-05-26.
 */
public class HistoryExpandableAdapter extends ExpandableRecyclerAdapter<HistoryExpandableAdapter.HistoryParentViewHolder,
        HistoryExpandableAdapter.HistoryChildViewHolder> {
    private LayoutInflater mInflator;
    private Context context;
    private List<? extends ParentListItem> parentItemList;

    public HistoryExpandableAdapter(Context context, @NonNull List<? extends ParentListItem> parentItemList) {
        super(parentItemList);
        this.context = context;
        this.mInflator = LayoutInflater.from(context);
        this.parentItemList = parentItemList;
    }

    @Override
    public HistoryParentViewHolder onCreateParentViewHolder(ViewGroup parentViewGroup) {
        View parentView = mInflator.inflate(R.layout.item_parent_history, parentViewGroup, false);
        return new HistoryParentViewHolder(parentView);
    }

    @Override
    public HistoryChildViewHolder onCreateChildViewHolder(ViewGroup childViewGroup) {
        View childView = mInflator.inflate(R.layout.item_child_history, childViewGroup, false);
        return new HistoryChildViewHolder(childView);
    }

    @Override
    public void onBindParentViewHolder(HistoryParentViewHolder parentViewHolder, int position, ParentListItem parentListItem) {
        TrainingDone item = (TrainingDone) parentListItem;
        StartedTrainingPlanDB startedTrainingPlanDB = new StartedTrainingPlanDB(context);
        StartedTrainingPlan startedTrainingPlan = startedTrainingPlanDB.getStartedTrainingPlan(item.getStartedTrainingPlanId());

        parentViewHolder.trainingDayTextView.setText(String.valueOf(startedTrainingPlan.getTrainingPlan().getDays()));
        parentViewHolder.trainingNameTextView.setText(startedTrainingPlan.getName());
        parentViewHolder.trainingPlanNameTextView.setText(startedTrainingPlan.getTrainingPlan().getName());
        parentViewHolder.dateTextView.setText(item.getDate());
        parentViewHolder.timeTextView.setText(item.getTime());
    }

    @Override
    public void onBindChildViewHolder(HistoryChildViewHolder childViewHolder, final int position, final Object childListItem) {
        final SeriesDoneGroup item = (SeriesDoneGroup) childListItem;

        childViewHolder.muscleGroupTextView.setText(item.getExercise().getCategory());
        childViewHolder.exerciseNameTextView.setText(String.format("%s %s", item.getExercise().getName(), item.getExercise().getSecondName()));

        HistoryAdapter historyAdapter = new HistoryAdapter(context, item.getSeriesDoneList());
        childViewHolder.recyclerView.setLayoutManager(new LinearLayoutManager(context));
        childViewHolder.recyclerView.setAdapter(historyAdapter);
    }

    public class HistoryChildViewHolder extends ChildViewHolder {
        public TextView muscleGroupTextView, exerciseNameTextView;
        public RecyclerView recyclerView;

        public HistoryChildViewHolder(View itemView) {
            super(itemView);
            muscleGroupTextView = (TextView) itemView.findViewById(R.id.muscleGroupTextView);
            exerciseNameTextView = (TextView) itemView.findViewById(R.id.exerciseNameTextView);
            recyclerView = (RecyclerView) itemView.findViewById(R.id.recyclerview);
        }
    }

    public class HistoryParentViewHolder extends ParentViewHolder {
        public TextView trainingNameTextView, trainingPlanNameTextView, dateTextView, timeTextView, trainingDayTextView;
        private ImageView arrowImageView;

        private static final float INITIAL_POSITION = 0.0f;
        private static final float ROTATED_POSITION = 180f;

        public HistoryParentViewHolder(View itemView) {
            super(itemView);

            trainingNameTextView = (TextView) itemView.findViewById(R.id.trainingNameTextView);
            trainingPlanNameTextView = (TextView) itemView.findViewById(R.id.trainingPlanNameTextView);
            dateTextView = (TextView) itemView.findViewById(R.id.dateTextView);
            timeTextView = (TextView) itemView.findViewById(R.id.timeTextView);
            arrowImageView = (ImageView) itemView.findViewById(R.id.arrowImageView);
            trainingDayTextView = (TextView) itemView.findViewById(R.id.trainingDayTextView);
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