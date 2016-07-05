package com.gymassistant.RecyclerView;

/**
 * Created by KamilH on 2015-10-23.
 */

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.gymassistant.Activities.ExercisesPreview;
import com.gymassistant.Models.SeriesDone;
import com.gymassistant.Preferences;
import com.gymassistant.R;
import com.gymassistant.UIComponents.Dialogs.NumberDialog;

import java.util.List;

public class TrainingAssistantAdapter extends RecyclerView.Adapter<TrainingAssistantAdapter.TrainingAssistantRowViewHolder> {
    private Context context;
    private List<SeriesDone> seriesDoneList;
    private List<SeriesDone> lastSeriesDoneList;

    public TrainingAssistantAdapter(Context context, List<SeriesDone> seriesDoneList, List<SeriesDone> lastSeriesDoneList) {
        this.context = context;
        this.seriesDoneList = seriesDoneList;
        this.lastSeriesDoneList = lastSeriesDoneList;
    }

    @Override
    public int getItemCount() {
        if (seriesDoneList == null) {
            return 0;
        } else {
            return seriesDoneList.size();
        }
    }

    @Override
    public TrainingAssistantAdapter.TrainingAssistantRowViewHolder onCreateViewHolder(ViewGroup viewGroup, final int position) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.item_training_assistant, viewGroup, false);

        return new TrainingAssistantRowViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final TrainingAssistantRowViewHolder rowViewHolder, final int position) {
        final SeriesDone currentSeriesDone = seriesDoneList.get(position);
        String unit = new Preferences(context).getWeightUnit();

        if(lastSeriesDoneList.size() > 0){
            final SeriesDone lastSeriesDone = lastSeriesDoneList.get(position);
            if(lastSeriesDone.isSaved()){
                rowViewHolder.loadLastTextView.setText(String.valueOf(lastSeriesDone.getWeight()));
                rowViewHolder.repeatsLastTextView.setText(String.valueOf(lastSeriesDone.getRepeat()));
                rowViewHolder.weightLastUnitTextView.setText(unit);
            }
        }

        rowViewHolder.muscleGroupTextView.setText(currentSeriesDone.getExercise().getCategory());
        rowViewHolder.exerciseTextView.setText(String.format("%s %s", currentSeriesDone.getExercise().getName(), currentSeriesDone.getExercise().getSecondName()));
        rowViewHolder.seriesTextView.setText(String.valueOf(currentSeriesDone.getOrder()));
        rowViewHolder.heightUnitTextView.setText(unit);

        rowViewHolder.repeatsEditText.setText(String.valueOf(currentSeriesDone.getRepeat()));
        rowViewHolder.repeatsEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    DialogFragment newFragment = NumberDialog.newInstance(context.getString(R.string.repeats), false, new NumberDialog.NumberSetListener() {
                        @Override
                        public void onNumberSet(String text) {
                            int repeats = Integer.valueOf(text);
                            currentSeriesDone.setRepeat(repeats);
                            rowViewHolder.repeatsEditText.setText(text);
                            rowViewHolder.repeatsEditText.clearFocus();
                        }
                    });
                    newFragment.show(((AppCompatActivity)context).getSupportFragmentManager(), "dialog");
                }
            }
        });
        rowViewHolder.loadEditText.setText(String.valueOf(currentSeriesDone.getWeight()));
        rowViewHolder.loadEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    DialogFragment newFragment = NumberDialog.newInstance(context.getString(R.string.load), true, new NumberDialog.NumberSetListener() {
                        @Override
                        public void onNumberSet(String text) {
                            double load = Double.parseDouble(text);
                            currentSeriesDone.setWeight(load);
                            rowViewHolder.loadEditText.setText(text);
                            rowViewHolder.loadEditText.clearFocus();
                        }
                    });
                    newFragment.show(((AppCompatActivity)context).getSupportFragmentManager(), "dialog");
                }
            }
        });
        rowViewHolder.exercisePreviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToExercisesPreviewScreen(currentSeriesDone.getExerciseId());
            }
        });

        rowViewHolder.saveCheckBox.setChecked(currentSeriesDone.isSaved());
        rowViewHolder.saveCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rowViewHolder.saveCheckBox.isChecked()){
                    currentSeriesDone.setSaved(true);
                } else {
                    currentSeriesDone.setSaved(false);
                }
            }
        });
    }

    private void goToExercisesPreviewScreen(long id){
        Bundle b = new Bundle();
        b.putLong("id", id);
        Intent intent = new Intent(context.getApplicationContext(), ExercisesPreview.class);
        intent.putExtras(b);
        context.startActivity(intent);
    }

    public class TrainingAssistantRowViewHolder extends RecyclerView.ViewHolder {
        public View view;
        public TextView muscleGroupTextView, exerciseTextView, seriesTextView, heightUnitTextView, repeatsLastTextView, loadLastTextView, weightLastUnitTextView;
        public EditText repeatsEditText, loadEditText;
        public Button exercisePreviewButton;
        public CheckBox saveCheckBox;

        public TrainingAssistantRowViewHolder(View view) {
            super(view);
            this.muscleGroupTextView = (TextView) view.findViewById(R.id.muscleGroupTextView);
            this.exerciseTextView = (TextView) view.findViewById(R.id.exerciseNameTextView);
            this.seriesTextView = (TextView) view.findViewById(R.id.seriesTextView);
            this.heightUnitTextView = (TextView) view.findViewById(R.id.heightUnitTextView);
            this.repeatsLastTextView = (TextView) view.findViewById(R.id.repeatsLastTextView);
            this.loadLastTextView = (TextView) view.findViewById(R.id.loadLastTextView);
            this.weightLastUnitTextView = (TextView) view.findViewById(R.id.weightLastUnitTextView);
            this.repeatsEditText = (EditText) view.findViewById(R.id.repeatsTextView);
            this.loadEditText = (EditText) view.findViewById(R.id.loadEditText);
            this.saveCheckBox = (CheckBox) view.findViewById(R.id.saveCheckBox);

            this.exercisePreviewButton = (Button) view.findViewById(R.id.exercisePreviewButton);
        }
    }
}