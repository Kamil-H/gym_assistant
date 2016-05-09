package com.gymassistant.Activities;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ContextThemeWrapper;
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
import android.widget.Toast;

import com.gymassistant.Database.ExerciseDB;
import com.gymassistant.Database.SeriesDB;
import com.gymassistant.Database.TrainingDB;
import com.gymassistant.GlobalClass;
import com.gymassistant.Models.Category;
import com.gymassistant.Models.Exercise;
import com.gymassistant.Models.Series;
import com.gymassistant.Models.Training;
import com.gymassistant.R;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChooseExercises extends AppCompatActivity {
    private List<Category> categories;
    private List<Exercise> exercises;
    private Spinner muscleGroupSpinner, exerciseSpinner;
    private DiscreteSeekBar seriesSeekBar, repeatsSeekBar;
    private EditText seriesEditText, repeatsEditText;
    private ArrayAdapter<Exercise> exerciseSpinnerAdapter;
    private CheckBox seriesCheckBox, repeatsCheckBox;
    private Button nextButton, backButton;
    private boolean isFirst = true, FILL = true, UPDATE = false;
    private int pageCounter = 0, numOfSeries, day;
    private long trainingPlanId, trainingId;
    private List<Series> seriesList;
    private TrainingDB trainingDB;
    private Training training;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_exercises);

        ExerciseDB exerciseDB = new ExerciseDB(this);
        exercises = exerciseDB.getAllExercises();
        categories = exerciseDB.getCategories();

        seriesList = new ArrayList<Series>();
        trainingDB = new TrainingDB(this);

        seriesSeekBar = (DiscreteSeekBar) findViewById(R.id.seriesSeekBar);
        repeatsSeekBar = (DiscreteSeekBar) findViewById(R.id.repeatsSeekBar);

        seriesEditText = (EditText) findViewById(R.id.seriesEditText);
        repeatsEditText = (EditText) findViewById(R.id.repeatsEditText);

        readParameters();
        setUpButtons();
        setUpSpinners();
        setUpCheckBoxes();
        addNewTraining();
    }

    private void readParameters(){
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            day = extras.getInt("day");
            Log.i("ChooseExercises", String.valueOf(day));
            trainingPlanId = extras.getLong("trainingPlanId");
        }
    }

    private Series readValues(){
        Exercise exercise = (Exercise) exerciseSpinner.getSelectedItem();

        int repeats;

        if(seriesCheckBox.isChecked()){
            numOfSeries = Integer.parseInt(seriesEditText.getText().toString());
        } else {
            numOfSeries = seriesSeekBar.getProgress();
        }

        if(repeatsCheckBox.isChecked()){
            repeats = Integer.parseInt(repeatsEditText.getText().toString());
        } else {
            repeats = repeatsSeekBar.getProgress();
        }
        return new Series(training, (int)trainingId, exercise, exercise.getId(), 0, repeats, 0);
    }

    private void setUpButtons(){
        nextButton = (Button) findViewById(R.id.nextButton);
        backButton = (Button) findViewById(R.id.backButton);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pageCounter > 0)
                    backButton.setText(getString(R.string.back_button));
                showSummaryDialog(readValues());
                pageCounter++;
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pageCounter--;
                if(pageCounter == 0)
                    backButton.setText(getString(R.string.cancel));
                deleteTraining();
            }
        });
    }

    private void showSummaryDialog(final Series series) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.myDialog));
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_exercise, null);
        dialogBuilder.setView(dialogView);

        final TextView muscleGroupTextView = (TextView) dialogView.findViewById(R.id.muscleGroupTextView);
        muscleGroupTextView.setText(series.getExercise().getCategory());

        final TextView exerciseTextView = (TextView) dialogView.findViewById(R.id.exerciseTextView);
        exerciseTextView.setText(String.format("%s %s", series.getExercise().getName(), series.getExercise().getSecondName()));

        final TextView seriesRepeatsTextView = (TextView) dialogView.findViewById(R.id.seriesRepeatsTextView);
        seriesRepeatsTextView.setText(getString(R.string.series_x_repeats, numOfSeries, series.getRepeat()));

        dialogBuilder.setPositiveButton(getString(R.string.add_next_exercise), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                addToSeriesList(series);
                resetValues();
                Toast.makeText(getApplicationContext(), "Ćwiczenie zostało dodane!", Toast.LENGTH_LONG).show();
            }
        });
        dialogBuilder.setNeutralButton(getString(R.string.save_and_close), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                addToSeriesList(series);
                saveSeriesToDb();
                finishWithResult(true);
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

    public void finishWithResult(boolean value){
        Intent returnIntent = new Intent();
        addSeriesListToMap();
        if(value){
            setResult(Activity.RESULT_OK, returnIntent);
        } else {
            setResult(Activity.RESULT_CANCELED, returnIntent);
        }
        this.finish();
    }

    private void addSeriesListToMap(){
        ((GlobalClass) getApplication()).map.put(day, seriesList);
    }

    private void saveSeriesToDb(){
        SeriesDB seriesDB = new SeriesDB(this);
        seriesDB.addSeriesList(seriesList);
    }

    private void addToSeriesList(Series series){
        for(int i = 0; i < numOfSeries; i++)
            seriesList.add(series);
        Log.i("Series:", String.valueOf(seriesList.size()));
    }

    private void resetValues(){
        numOfSeries = 1;
        repeatsSeekBar.setProgress(1);
        seriesSeekBar.setProgress(1);
        if(repeatsCheckBox.isChecked()){
            repeatsCheckBox.setChecked(false);
        }
        if(seriesCheckBox.isChecked()){
            seriesCheckBox.setChecked(false);
        }
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
        List<Exercise> exercisesList = fillExercisesList(muscleGroup);
        if(action){
            exerciseSpinnerAdapter = new ArrayAdapter<Exercise>(this, android.R.layout.simple_spinner_item, exercisesList);
            exerciseSpinner.setAdapter(exerciseSpinnerAdapter);
        } else {
            exerciseSpinnerAdapter.clear();
            exerciseSpinnerAdapter.addAll(exercisesList);
            exerciseSpinnerAdapter.notifyDataSetChanged();
        }
    }

    private List<Exercise> fillExercisesList(String muscleGroup){
        List<Exercise> exercisesList = new ArrayList<Exercise>();
        for (Exercise exercise : exercises){
            if(exercise.getCategory().matches(muscleGroup)){
                exercisesList.add(exercise);
            }
        }
        return exercisesList;
    }

    private void addNewTraining(){
        this.training = new Training((int)trainingPlanId, day, null);
        trainingId = trainingDB.addTraining(training);
        Log.i("Added", String.format("Training ID: %d", trainingId));
    }

    private void deleteTraining(){
        trainingDB.deleteTraining(trainingId);
        Log.i("Deleted", String.format("Training ID: %d", trainingId));
    }
}