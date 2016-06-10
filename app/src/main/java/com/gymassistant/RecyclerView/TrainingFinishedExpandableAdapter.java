package com.gymassistant.RecyclerView;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.gymassistant.Database.StartedTrainingPlanDB;
import com.gymassistant.Models.StartedTrainingPlan;
import com.gymassistant.R;
import com.gymassistant.UIComponents.ImageViewRotater;
import com.kyo.expandablelayout.ExpandableLayout;

import java.util.List;

/**
 * Created by KamilH on 2016-05-24.
 */
public class TrainingFinishedExpandableAdapter extends RecyclerView.Adapter<TrainingFinishedExpandableAdapter.ViewHolder> {
    private Context context;
    private List<StartedTrainingPlan> startedTrainingPlanList;

    public TrainingFinishedExpandableAdapter(Context context, List<StartedTrainingPlan> startedTrainingPlanList) {
        this.context = context;
        this.startedTrainingPlanList = startedTrainingPlanList;
    }

    @Override
    public int getItemCount() {
        return startedTrainingPlanList.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, final int position) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.item_training_plan_finished, null);
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
        final StartedTrainingPlan item = startedTrainingPlanList.get(position);

        rowViewHolder.nameTextView.setText(item.getName());
        rowViewHolder.trainingNameTextView.setText(item.getName());
        rowViewHolder.trainingPlanNameTextView.setText(item.getTrainingPlan().getName());
        rowViewHolder.descriptionTextView.setText(item.getDescription());
        rowViewHolder.startDateTextView.setText(item.getStartDate());
        rowViewHolder.endDateTextView.setText(item.getEndDate());
        rowViewHolder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeRow(item);
                removeFromDatabase(item.getId());
            }
        });
    }

    public void removeFromDatabase(long id){
        StartedTrainingPlanDB startedTrainingPlanDB = new StartedTrainingPlanDB(context);
        startedTrainingPlanDB.deleteStartedTrainingPlan(id);
    }

    private void removeRow(StartedTrainingPlan startedTrainingPlan){
        startedTrainingPlanList.remove(startedTrainingPlan);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView trainingNameTextView, trainingPlanNameTextView, descriptionTextView, startDateTextView, endDateTextView;
        public Button deleteButton;
        public TextView nameTextView;
        private ImageView arrowImageView;
        public ExpandableLayout expandableLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            trainingNameTextView = (TextView) itemView.findViewById(R.id.exerciseNameTextView);
            trainingPlanNameTextView = (TextView) itemView.findViewById(R.id.trainingPlanNameTextView);
            descriptionTextView = (TextView) itemView.findViewById(R.id.descriptionTextView);
            startDateTextView = (TextView) itemView.findViewById(R.id.startDateTextView);
            endDateTextView = (TextView) itemView.findViewById(R.id.endDateTextView);
            deleteButton = (Button) itemView.findViewById(R.id.deleteButton);

            nameTextView = (TextView) itemView.findViewById(R.id.startedTrainingPlanNameTextView);
            arrowImageView = (ImageView) itemView.findViewById(R.id.arrowImageView);

            expandableLayout = (ExpandableLayout) itemView.findViewById(R.id.expandlayout);
        }
    }
}