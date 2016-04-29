package com.gymassistant.RecyclerView;

/**
 * Created by KamilH on 2015-10-23.
 */

import java.util.List;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.gymassistant.Activities.ChooseExercises;
import com.gymassistant.Models.TrainingPlanModel;
import com.gymassistant.R;

public class TrainingPlanAdapter extends RecyclerView.Adapter<TrainingPlanRowViewHolder> {
    private Context context;
    private List<TrainingPlanModel> itemsList;

    public TrainingPlanAdapter(Context context, List<TrainingPlanModel> itemsList) {
        this.context = context;
        this.itemsList = itemsList;
    }

    @Override
    public int getItemCount() {
        if (itemsList == null) {
            return 0;
        } else {
            return itemsList.size();
        }
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
        TrainingPlanModel items = itemsList.get(position);

        rowViewHolder.dayName.setText(items.getDayName());
        rowViewHolder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToChoosExerciseActivity();
            }
        });
    }

    private void goToChoosExerciseActivity(){
        Intent intent = new Intent(context, ChooseExercises.class);
        context.startActivity(intent);
    }
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