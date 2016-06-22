package com.gymassistant.RecyclerView;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ParentViewHolder;
import com.gymassistant.Database.StartedTrainingPlanDB;
import com.gymassistant.Models.StartedTrainingPlan;
import com.gymassistant.Models.TrainingDone;
import com.gymassistant.R;
import com.gymassistant.UIComponents.ImageViewRotater;
import com.kyo.expandablelayout.ExpandableLayout;

import java.util.List;

/**
 * Created by KamilH on 2016-05-26.
 */
public class HistoryExpandableAdapter extends RecyclerView.Adapter<HistoryExpandableAdapter.ViewHolder> {
    private Context context;
    private List<TrainingDone> trainingDoneList;

    public HistoryExpandableAdapter(Context context, List<TrainingDone> trainingDoneList) {
        this.context = context;
        this.trainingDoneList = trainingDoneList;
    }

    @Override
    public int getItemCount() {
        return trainingDoneList.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, final int position) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.item_parent_history, null);
        final ViewHolder viewHolder = new ViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageViewRotater.rotateImageView(viewHolder.expandableLayout.isExpanded(), viewHolder.arrowImageView);
                viewHolder.expandableLayout.toggleExpansion();
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder rowViewHolder, final int position) {
        TrainingDone item = trainingDoneList.get(position);
        StartedTrainingPlanDB startedTrainingPlanDB = new StartedTrainingPlanDB(context);
        StartedTrainingPlan startedTrainingPlan = startedTrainingPlanDB.getStartedTrainingPlan(item.getStartedTrainingPlanId());

        rowViewHolder.trainingDayTextView.setText(String.valueOf(item.getDay()));
        rowViewHolder.trainingNameTextView.setText(startedTrainingPlan.getName());
        rowViewHolder.trainingPlanNameTextView.setText(startedTrainingPlan.getTrainingPlan().getName());
        rowViewHolder.dateTextView.setText(item.getDate());
        rowViewHolder.timeTextView.setText(item.getTime());

        HistoryChildAdapter historyChildAdapter = new HistoryChildAdapter(context, item.getSeriesDoneGroupList());
        rowViewHolder.recyclerView.setLayoutManager(new LinearLayoutManager(context));
        rowViewHolder.recyclerView.setAdapter(historyChildAdapter);
    }

    public class ViewHolder extends ParentViewHolder {
        public TextView trainingNameTextView, trainingPlanNameTextView, dateTextView, timeTextView, trainingDayTextView;
        private ImageView arrowImageView;
        public ExpandableLayout expandableLayout;
        public RecyclerView recyclerView;

        public ViewHolder(View itemView) {
            super(itemView);

            trainingNameTextView = (TextView) itemView.findViewById(R.id.trainingNameTextView);
            trainingPlanNameTextView = (TextView) itemView.findViewById(R.id.trainingPlanNameTextView);
            dateTextView = (TextView) itemView.findViewById(R.id.dateTextView);
            timeTextView = (TextView) itemView.findViewById(R.id.timeTextView);
            arrowImageView = (ImageView) itemView.findViewById(R.id.arrowImageView);
            trainingDayTextView = (TextView) itemView.findViewById(R.id.trainingDayTextView);
            expandableLayout = (ExpandableLayout) itemView.findViewById(R.id.expandlayout);
            recyclerView = (RecyclerView) itemView.findViewById(R.id.recyclerview);
        }
    }
}