package com.gymassistant.RecyclerView;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gymassistant.Models.Series;
import com.gymassistant.R;

import java.util.List;
import java.util.Collections;

/**
 * Created by KamilH on 2016-05-12.
 */
public class TrainingPlanExerciseAdapter extends RecyclerView.Adapter<TrainingPlanExerciseAdapter.TrainingPlanExerciseRowViewHolder> {
    private Context context;
    private List<Series> seriesList;
    private TrainingPlanAdapter trainingPlanAdapter;
    private int parentPosition;

    public TrainingPlanExerciseAdapter(Context context, List<Series> seriesList, TrainingPlanAdapter trainingPlanAdapter, int parentPosition) {
        this.context = context;
        this.seriesList = seriesList;
        this.trainingPlanAdapter = trainingPlanAdapter;
        this.parentPosition = parentPosition;
    }

    @Override
    public int getItemCount() {
        return seriesList.size();
    }

    @Override
    public TrainingPlanExerciseRowViewHolder onCreateViewHolder(ViewGroup viewGroup, final int position) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.item_training_plan_exercise, null);

        return new TrainingPlanExerciseRowViewHolder(view);
    }

    public void remove(int position){
        seriesList.remove(position);
        notifyItemRemoved(position);
        if(seriesList.size() == 0){
            trainingPlanAdapter.remove(parentPosition);
        }
    }

    public void swap(int firstPosition, int secondPosition){
        Collections.swap(seriesList, firstPosition, secondPosition);
        notifyItemMoved(firstPosition, secondPosition);
    }

    @Override
    public void onBindViewHolder(TrainingPlanExerciseRowViewHolder rowViewHolder, final int position) {
        Series series = seriesList.get(position);

        rowViewHolder.muscleGroupsTextView.setText(series.getExercise().getCategory());
        rowViewHolder.exerciseTextView.setText(String.format("%s %s", series.getExercise().getName(), series.getExercise().getSecondName()));
        rowViewHolder.repeatsTextView.setText(context.getString(R.string.repeats_with_param, series.getRepeat()));
    }

    class TrainingPlanExerciseRowViewHolder extends RecyclerView.ViewHolder {
        public View view;
        public TextView exerciseTextView, muscleGroupsTextView, repeatsTextView;

        public TrainingPlanExerciseRowViewHolder(View view) {
            super(view);
            this.muscleGroupsTextView = (TextView) view.findViewById(R.id.muscleGroupTextView);
            this.exerciseTextView = (TextView) view.findViewById(R.id.exerciseNameTextView);
            this.repeatsTextView = (TextView) view.findViewById(R.id.repeatsTextView);
        }
    }
}