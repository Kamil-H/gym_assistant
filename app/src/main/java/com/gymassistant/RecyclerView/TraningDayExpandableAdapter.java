package com.gymassistant.RecyclerView;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

        TrainingDayAdapter trainingDayAdapter = new TrainingDayAdapter(context, item.getTrainingPlan().getTrainingList());
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

        public PlanParentViewHolder(View itemView) {
            super(itemView);
            nameTextView = (TextView) itemView.findViewById(R.id.traningNameTextView);
        }
    }
}