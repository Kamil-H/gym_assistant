package com.gymassistant.RecyclerView;

/**
 * Created by KamilH on 2015-10-23.
 */

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gymassistant.Activities.TrainingAssistant;
import com.gymassistant.Models.Series;
import com.gymassistant.Models.Training;
import com.gymassistant.R;

import java.util.List;
import java.util.Set;
import java.util.HashSet;

public class TrainingDayAdapter extends RecyclerView.Adapter<TrainingDayAdapter.TrainingDayRowViewHolder> {
    private Context context;
    private List<Training> trainings;

    public TrainingDayAdapter(Context context, List<Training> trainings) {
        this.context = context;
        this.trainings = trainings;
    }

    @Override
    public int getItemCount() {
        return trainings.size();
    }

    @Override
    public TrainingDayRowViewHolder onCreateViewHolder(ViewGroup viewGroup, final int position) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.item_training_day, null);
        final TrainingDayRowViewHolder viewHolder = new TrainingDayRowViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToTrainingAssistantActivity(trainings.get(viewHolder.getAdapterPosition()).getId());
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TrainingDayRowViewHolder rowViewHolder, final int position) {
        rowViewHolder.dayName.setText(context.getString(R.string.day, position + 1));
        rowViewHolder.muscleGroupsTextView.setText(getNamesList(position));
    }

    private void goToTrainingAssistantActivity(int position){
        Intent intent = new Intent(context, TrainingAssistant.class);
        intent.putExtra("id", position);
        context.startActivity(intent);
    }

    private String getNamesList(int position){
        Training training = trainings.get(position);
        List<Series> seriesList = training.getSeriesList();
        Set<String> names = getNames(seriesList);
        String value = "";
        for(String s : names){
            value = value + " " + s + " " + "\n";
        }
        return value;
    }

    public Set<String> getNames(final List<Series> seriesList) {
        Set<String> areas = new HashSet<>();
        for(final Series series: seriesList) {
            areas.add(series.getExercise().getCategory());
        }
        return areas;
    }

    class TrainingDayRowViewHolder extends RecyclerView.ViewHolder {
        public View view;
        public TextView dayName, muscleGroupsTextView;

        public TrainingDayRowViewHolder(View view) {
            super(view);
            this.dayName = (TextView) view.findViewById(R.id.dayNameTextView);
            this.muscleGroupsTextView = (TextView) view.findViewById(R.id.exerciseTextView);
        }
    }
}