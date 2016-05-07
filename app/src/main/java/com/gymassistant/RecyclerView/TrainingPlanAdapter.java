package com.gymassistant.RecyclerView;

/**
 * Created by KamilH on 2015-10-23.
 */

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.gymassistant.Activities.ChooseExercises;
import com.gymassistant.R;

public class TrainingPlanAdapter extends RecyclerView.Adapter<TrainingPlanAdapter.TrainingPlanRowViewHolder> {
    private Context context;
    private int itemCount;
    private long trainingPlanId;

    public TrainingPlanAdapter(Context context, int itemCount, long trainingPlanId) {
        this.context = context;
        this.itemCount = itemCount;
        this.trainingPlanId = trainingPlanId;
    }

    @Override
    public int getItemCount() {
        return itemCount;
    }

    @Override
    public TrainingPlanRowViewHolder onCreateViewHolder(ViewGroup viewGroup, final int position) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.item_training_plan, null);

        TrainingPlanRowViewHolder viewHolder = new TrainingPlanRowViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TrainingPlanRowViewHolder rowViewHolder, final int position) {
        rowViewHolder.dayName.setText(context.getString(R.string.day, position + 1));
        rowViewHolder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToChoosExerciseActivity(position);
            }
        });
    }

    private void goToChoosExerciseActivity(int day){
        Intent intent = new Intent(context, ChooseExercises.class);
        Bundle bundle = new Bundle();
        bundle.putInt("day", day);
        bundle.putLong("trainingPlanId", trainingPlanId);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    class TrainingPlanRowViewHolder extends RecyclerView.ViewHolder {
        public View view;
        public TextView dayName;
        public Button button;

        public TrainingPlanRowViewHolder(View view) {
            super(view);
            this.dayName = (TextView) view.findViewById(R.id.dayName);
            this.button = (Button) view.findViewById(R.id.button);
        }
    }
}