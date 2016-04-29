package com.gymassistant.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;

import com.gymassistant.GlobalClass;
import com.gymassistant.Models.Category;
import com.gymassistant.Models.Exercise;
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
    }

    private void setUpButtons(){
        nextButton = (Button) findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readValues();
            }
        });

        backButton = (Button) findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
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