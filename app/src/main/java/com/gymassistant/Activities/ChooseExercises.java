package com.gymassistant.Activities;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ContextThemeWrapper;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.gymassistant.GlobalClass;
import com.gymassistant.Models.Category;
import com.gymassistant.Models.Exercise;
import com.gymassistant.Models.TrainingPlanExercise;
import com.gymassistant.R;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

import java.util.List;
import java.util.ArrayList;

public class ChooseExercises extends AppCompatActivity {
    private List<Category> categories;
    private List<Exercise> exercises;
    private Spinner muscleGroupSpinner, exerciseSpinner;
    private DiscreteSeekBar seriesSeekBar, repeatsSeekBar;
    private EditText seriesEditText, repeatsEditText;
    private ArrayAdapter<String> exerciseSpinnerAdapter;
    private CheckBox seriesCheckBox, repeatsCheckBox;
    private Button nextButton, backButton;
    private boolean isFirst = true, FILL = true, UPDATE = false;
    private int pageCounter = 0;
    private TrainingPlanExercise trainingPlanExercise;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_exercises);

        GlobalClass global = (GlobalClass) this.getApplicationContext();
        categories = global.getCategories();
        exercises = global.getExercises();

        seriesSeekBar = (DiscreteSeekBar) findViewById(R.id.seriesSeekBar);
        repeatsSeekBar = (DiscreteSeekBar) findViewById(R.id.repeatsSeekBar);

        seriesEditText = (EditText) findViewById(R.id.seriesEditText);
        repeatsEditText = (EditText) findViewById(R.id.repeatsEditText);

        setUpButtons();
        setUpSpinners();
        setUpCheckBoxes();
    }

    private void readValues(){
        String category = muscleGroupSpinner.getSelectedItem().toString();
        String exercise = exerciseSpinner.getSelectedItem().toString();
        int series, repeats;

        if(seriesCheckBox.isChecked()){
            series = Integer.parseInt(seriesEditText.getText().toString());
        } else {
            series = seriesSeekBar.getProgress();
        }

        if(repeatsCheckBox.isChecked()){
            repeats = Integer.parseInt(repeatsEditText.getText().toString());
        } else {
            repeats = repeatsSeekBar.getProgress();
        }
        Log.i("VALUES", "CATEGORY: " + category + " EXERCISE: " + exercise + " SERIES: " + String.valueOf(series) + " REPEATS: " + String.valueOf(repeats));
        trainingPlanExercise = new TrainingPlanExercise(category, exercise, series, repeats);
    }

    private void setUpButtons(){
        nextButton = (Button) findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pageCounter++;
                if(pageCounter > 0)
                    backButton.setText(getString(R.string.back_button));
                readValues();
                showCostDialog();
            }
        });

        backButton = (Button) findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    private void showCostDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.myDialog));
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_exercise, null);
        dialogBuilder.setView(dialogView);

        final TextView muscleGroupTextView = (TextView) dialogView.findViewById(R.id.muscleGroupTextView);
        muscleGroupTextView.setText(trainingPlanExercise.getMuscleGroup());

        final TextView exerciseTextView = (TextView) dialogView.findViewById(R.id.exerciseTextView);
        exerciseTextView.setText(trainingPlanExercise.getExercise());

        final TextView seriesRepeatsTextView = (TextView) dialogView.findViewById(R.id.seriesRepeatsTextView);
        seriesRepeatsTextView.setText(getString(R.string.series_x_repeats, trainingPlanExercise.getSeries(), trainingPlanExercise.getRepeats()));

        dialogBuilder.setTitle(getString(R.string.summary));
        //dialogBuilder.setMessage("Enter text below");
        dialogBuilder.setPositiveButton("ZAPISZ I DODAJ NASTĘPNE ĆWICZENIE", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            }
        });
        dialogBuilder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            }
        });
        dialogBuilder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }

    private void setUpCheckBoxes(){
        seriesCheckBox = (CheckBox) findViewById(R.id.seriesCheckBox);
        seriesCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    seriesEditText.setVisibility(View.VISIBLE);
                } else {
                    seriesEditText.setVisibility(View.INVISIBLE);
                }
            }
        });
        repeatsCheckBox = (CheckBox) findViewById(R.id.repeatsCheckBox);
        repeatsCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    repeatsEditText.setVisibility(View.VISIBLE);
                } else {
                    repeatsEditText.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    private void setUpSpinners(){
        muscleGroupSpinner = (Spinner) findViewById(R.id.muscleGroupSpinner);
        populateMuscleGroupSpinner();

        exerciseSpinner = (Spinner) findViewById(R.id.exerciseSpinner);
        muscleGroupSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String muscleGroup = muscleGroupSpinner.getSelectedItem().toString();
                if(isFirst){
                    populateExerciseSpinner(muscleGroup, FILL);
                    isFirst = false;
                } else {
                    populateExerciseSpinner(muscleGroup, UPDATE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void populateMuscleGroupSpinner(){
        List<String> muscleGroupsList = new ArrayList<String>();
        for (Category category : categories){
            muscleGroupsList.add(category.getCategory());
        }
        ArrayAdapter<String> muscleGroupAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, muscleGroupsList);
        muscleGroupSpinner.setAdapter(muscleGroupAdapter);
    }

    private void populateExerciseSpinner(String muscleGroup, boolean action){
        List<String> exercisesList = fillExercisesList(muscleGroup);
        if(action){
            exerciseSpinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, exercisesList);
            exerciseSpinner.setAdapter(exerciseSpinnerAdapter);
        } else {
            exerciseSpinnerAdapter.clear();
            exerciseSpinnerAdapter.addAll(exercisesList);
            exerciseSpinnerAdapter.notifyDataSetChanged();
        }
    }

    private List<String> fillExercisesList(String muscleGroup){
        List<String> exercisesList = new ArrayList<String>();
        for (Exercise exercise : exercises){
            if(exercise.getCategory().matches(muscleGroup)){
                exercisesList.add(exercise.getName());
            }
        }
        return exercisesList;
    }
}