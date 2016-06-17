package com.gymassistant.RecyclerView;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gymassistant.Models.StartedTrainingPlan;
import com.gymassistant.R;
import com.gymassistant.UIComponents.ImageViewRotater;
import com.kyo.expandablelayout.ExpandableLayout;

import java.util.List;

/**
 * Created by KamilH on 2016-05-24.
 */
public class TrainingDayExpandableAdapter extends RecyclerView.Adapter<TrainingDayExpandableAdapter.ViewHolder> {
    private Context context;
    private List<StartedTrainingPlan> startedTrainingPlanList;

    public TrainingDayExpandableAdapter(Context context, List<StartedTrainingPlan> startedTrainingPlanList) {
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
        View view = inflater.inflate(R.layout.item_recyclerview, null);
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

        TrainingDayAdapter trainingDayAdapter = new TrainingDayAdapter(context, item.getTrainingPlan().getTrainingList(), item.getId());
        rowViewHolder.recyclerview.setLayoutManager(new LinearLayoutManager(context));
        rowViewHolder.recyclerview.setAdapter(trainingDayAdapter);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView;
        public ImageView arrowImageView;
        public ExpandableLayout expandableLayout;
        public RecyclerView recyclerview;

        public ViewHolder(View itemView) {
            super(itemView);
            nameTextView = (TextView) itemView.findViewById(R.id.startedTrainingPlanNameTextView);
            recyclerview = (RecyclerView) itemView.findViewById(R.id.recyclerview);
            arrowImageView = (ImageView) itemView.findViewById(R.id.arrowImageView);

            expandableLayout = (ExpandableLayout) itemView.findViewById(R.id.expandlayout);
        }
    }
}