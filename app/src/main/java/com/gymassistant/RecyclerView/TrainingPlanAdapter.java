package com.gymassistant.RecyclerView;

/**
 * Created by KamilH on 2015-10-23.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.gymassistant.Activities.ChooseExercises;
import com.gymassistant.GlobalClass;
import com.gymassistant.Models.Exercise;
import com.gymassistant.Models.Series;
import com.gymassistant.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TrainingPlanAdapter extends RecyclerView.Adapter<TrainingPlanAdapter.TrainingPlanRowViewHolder> {
    private Context context;
    private int itemCount;
    private long trainingPlanId;
    private List<List<Series>> listOfSeriesList;
    public Map<Integer, List<Series>> map;

    public TrainingPlanAdapter(Context context, int itemCount, long trainingPlanId, Map<Integer, List<Series>> map) {
        this.context = context;
        this.itemCount = itemCount;
        this.trainingPlanId = trainingPlanId;
        this.map = map;
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
        rowViewHolder.trainingTextView.setText(fillTrainingTextView(position));
    }

    private String fillTrainingTextView(int position){
        if(map != null && map.size() > 0 && map.containsKey(position)){
            List<Series> seriesList = map.get(position);
            String value = "";
            for (Series series : seriesList){
                Exercise exercise = series.getExercise();
                String text = String.format(context.getString(R.string.training_plan_item), exercise.getCategory(), exercise.getName(), exercise.getSecondName(), series.getRepeat());
                value = value + text;
            }
            return value;
        }
        return "";
    }

    private void goToChoosExerciseActivity(int day){
        Intent intent = new Intent(context, ChooseExercises.class);
        Bundle bundle = new Bundle();
        bundle.putInt("day", day);
        bundle.putLong("trainingPlanId", trainingPlanId);
        intent.putExtras(bundle);
        ((Activity)context).startActivityForResult(intent, 1);
    }

    class TrainingPlanRowViewHolder extends RecyclerView.ViewHolder {
        public View view;
        public TextView dayName;
        public Button button;
        public TextView trainingTextView;

        public TrainingPlanRowViewHolder(View view) {
            super(view);
            this.dayName = (TextView) view.findViewById(R.id.dayName);
            this.trainingTextView = (TextView) view.findViewById(R.id.trainingTextView);
            this.button = (Button) view.findViewById(R.id.button);
        }
    }
}