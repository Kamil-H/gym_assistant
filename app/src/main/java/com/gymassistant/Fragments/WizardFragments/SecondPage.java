package com.gymassistant.Fragments.WizardFragments;

import android.support.v4.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.util.SparseArrayCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.ContextThemeWrapper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.gymassistant.Activities.WizardActivity;
import com.gymassistant.Database.ExerciseDB;
import com.gymassistant.Models.Category;
import com.gymassistant.Models.Exercise;
import com.gymassistant.Models.Series;
import com.gymassistant.R;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

import java.util.ArrayList;
import java.util.List;

public class SecondPage extends Fragment {
    private View view;
    private List<Category> categories;
    private List<Exercise> exercises;
    private Spinner muscleGroupSpinner, exerciseSpinner, daySpinner;
    private DiscreteSeekBar seriesSeekBar, repeatsSeekBar;
    private EditText seriesEditText, repeatsEditText;
    private ArrayAdapter<Exercise> exerciseSpinnerAdapter;
    private CheckBox seriesCheckBox, repeatsCheckBox;
    private Button nextButton, backButton;
    private boolean isFirst = true, FILL = true, UPDATE = false;
    private int pageCounter = 0, numOfSeries;
    private List<Series> seriesList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_page_second, container, false);

        ExerciseDB exerciseDB = new ExerciseDB(getActivity());
        exercises = exerciseDB.getAllExercises();
        categories = exerciseDB.getCategories();

        isFirst = true;

        seriesList = new ArrayList<Series>();

        seriesSeekBar = (DiscreteSeekBar) view.findViewById(R.id.seriesSeekBar);
        repeatsSeekBar = (DiscreteSeekBar) view.findViewById(R.id.repeatsSeekBar);

        seriesEditText = (EditText) view.findViewById(R.id.seriesEditText);
        repeatsEditText = (EditText) view.findViewById(R.id.repeatsEditText);

        setUpButtons();
        setUpSpinners();
        setUpCheckBoxes();
        
        return view;
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
        return new Series(null, 0, exercise, exercise.getId(), 0, repeats, 0);
    }

    private void showSummaryDialog(final Series series) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(new ContextThemeWrapper(getActivity(), R.style.myDialog));
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_exercise, null);
        dialogBuilder.setView(dialogView);

        final TextView muscleGroupTextView = (TextView) dialogView.findViewById(R.id.dayNameTextView);
        muscleGroupTextView.setText(series.getExercise().getCategory());

        final TextView exerciseTextView = (TextView) dialogView.findViewById(R.id.traningNameTextView);
        exerciseTextView.setText(String.format("%s %s", series.getExercise().getName(), series.getExercise().getSecondName()));

        final TextView seriesRepeatsTextView = (TextView) dialogView.findViewById(R.id.seriesRepeatsTextView);
        seriesRepeatsTextView.setText(getString(R.string.series_x_repeats, numOfSeries, series.getRepeat()));

        dialogBuilder.setPositiveButton(getString(R.string.add_next_exercise), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                addToSeriesList(series);
                addSeriesListToMap();
                resetValues();
                Toast.makeText(getActivity(), "Ćwiczenie zostało dodane!", Toast.LENGTH_LONG).show();
            }
        });
        dialogBuilder.setNeutralButton(getString(R.string.save_and_close), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                addToSeriesList(series);
                addSeriesListToMap();
                ((WizardActivity)getActivity()).navigateToNextPage();
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

    private void addSeriesListToMap(){
        SparseArrayCompat<List<Series>> map = ((WizardActivity)getActivity()).getMap();
        int key = daySpinner.getSelectedItemPosition();
        if(map.get(key) == null){
            map.put(key, seriesList);
        } else {
            map.get(key).addAll(seriesList);
        }
        seriesList = new ArrayList<Series>();
        ((WizardActivity)getActivity()).setMap(map);
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

    private void setUpButtons(){
        nextButton = (Button) view.findViewById(R.id.nextButton);
        backButton = (Button) view.findViewById(R.id.backButton);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSummaryDialog(readValues());
                pageCounter++;
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((WizardActivity)getActivity()).navigateToPreviousPage();
            }
        });
    }

    private void setUpCheckBoxes(){
        seriesCheckBox = (CheckBox) view.findViewById(R.id.seriesCheckBox);
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
        repeatsCheckBox = (CheckBox) view.findViewById(R.id.repeatsCheckBox);
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
        muscleGroupSpinner = (Spinner) view.findViewById(R.id.muscleGroupSpinner);
        populateMuscleGroupSpinner();

        exerciseSpinner = (Spinner) view.findViewById(R.id.exerciseSpinner);
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
        daySpinner = (Spinner) view.findViewById(R.id.daySpinner);
        populateDaySpinner();
    }

    private void populateDaySpinner(){
        List<String> days = new ArrayList<String>();
        for (int i = 0; i < ((WizardActivity)getActivity()).getItemCount(); i++){
            days.add(getActivity().getString(R.string.day, i + 1));
        }
        ArrayAdapter<String> daysAdapter = new ArrayAdapter<String>(getActivity(), R.layout.item_spinner, R.id.text, days);
        daySpinner.setAdapter(daysAdapter);
    }

    private void populateMuscleGroupSpinner(){
        List<String> muscleGroupsList = new ArrayList<String>();
        for (Category category : categories){
            muscleGroupsList.add(category.getCategory());
        }
        ArrayAdapter<String> muscleGroupAdapter = new ArrayAdapter<String>(getActivity(), R.layout.item_spinner, R.id.text, muscleGroupsList);
        muscleGroupSpinner.setAdapter(muscleGroupAdapter);
    }

    private void populateExerciseSpinner(String muscleGroup, boolean action){
        List<Exercise> exercisesList = fillExercisesList(muscleGroup);
        if(action){
            exerciseSpinnerAdapter = new ArrayAdapter<Exercise>(getActivity(), R.layout.item_spinner, R.id.text, exercisesList);
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
}