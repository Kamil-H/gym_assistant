package com.gymassistant.Fragments.WizardFragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.gymassistant.Activities.WizardActivity;
import com.gymassistant.Database.ExerciseDB;
import com.gymassistant.Models.Category;
import com.gymassistant.Models.Exercise;
import com.gymassistant.Models.Series;
import com.gymassistant.R;
import com.gymassistant.UIComponents.NumberDialog;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

import java.util.ArrayList;
import java.util.List;

public class SecondPage extends Fragment {
    private View view;
    private List<Category> categories;
    private List<Exercise> exercises;
    private Spinner muscleGroupSpinner, exerciseSpinner, daySpinner;
    private DiscreteSeekBar repeatsSeekBar, seriesSeekBar;
    private TextView seriesTextView, repeatsTextView;
    private ArrayAdapter<Exercise> exerciseSpinnerAdapter;
    private ArrayAdapter<String> daysAdapter;
    private CheckBox seriesCheckBox, repeatsCheckBox;
    private boolean isFirst = true, FILL = true, UPDATE = false;
    private int numOfSeries;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_page_second, container, false);

        ExerciseDB exerciseDB = new ExerciseDB(getActivity());
        exercises = exerciseDB.getAllExercises();
        categories = exerciseDB.getCategories();

        isFirst = true;

        setUpSeekBars();
        setUpButtons();
        setUpSpinners();
        setUpCheckBoxes();

        seriesTextView = (TextView) view.findViewById(R.id.seriesTextView);
        seriesTextView.setText(String.valueOf(seriesSeekBar.getProgress()));
        repeatsTextView = (TextView) view.findViewById(R.id.repeatsTextView);
        repeatsTextView.setText(String.valueOf(repeatsSeekBar.getProgress()));
        
        return view;
    }

    private Series readValues(){
        Exercise exercise = (Exercise) exerciseSpinner.getSelectedItem();

        numOfSeries = Integer.parseInt(seriesTextView.getText().toString());
        int repeats = Integer.parseInt(repeatsTextView.getText().toString());

        return new Series(null, 0, exercise, exercise.getId(), 0, repeats, 0);
    }

    private void showSummaryDialog(final Series series) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(new ContextThemeWrapper(getActivity(), R.style.myDialog));
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_exercise, null);
        dialogBuilder.setView(dialogView);

        final TextView muscleGroupTextView = (TextView) dialogView.findViewById(R.id.muscleGroupTextView);
        muscleGroupTextView.setText(series.getExercise().getCategory());

        final TextView exerciseTextView = (TextView) dialogView.findViewById(R.id.exerciseNameTextView);
        exerciseTextView.setText(String.format("%s %s", series.getExercise().getName(), series.getExercise().getSecondName()));

        final TextView seriesRepeatsTextView = (TextView) dialogView.findViewById(R.id.seriesRepeatsTextView);
        seriesRepeatsTextView.setText(getString(R.string.series_x_repeats, numOfSeries, series.getRepeat()));

        dialogBuilder.setPositiveButton(getString(R.string.add_next_exercise), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                addSeriesListToMap(createSeriesList(series));
                resetValues();
                Toast.makeText(getActivity(), "Ćwiczenie zostało dodane!", Toast.LENGTH_LONG).show();
            }
        });
        dialogBuilder.setNeutralButton(getString(R.string.save_and_close), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                addSeriesListToMap(createSeriesList(series));
                ((WizardActivity)getActivity()).removeEmptyObjects();
                ((WizardActivity)getActivity()).navigateToNextPage();
            }
        });
        dialogBuilder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {}
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }

    private void addSeriesListToMap(List<Series> seriesList){
        List<List<Series>> map = ((WizardActivity)getActivity()).getMap();
        int key = daySpinner.getSelectedItemPosition();
        if(map.get(key) == null){
            map.add(key, seriesList);
        } else {
            map.get(key).addAll(seriesList);
        }
        ((WizardActivity)getActivity()).setMap(map);
    }

    private List<Series> createSeriesList(Series series){
        List<Series> seriesList = new ArrayList<>();
        for(int i = 0; i < numOfSeries; i++){
            series.setOrder(i);
            seriesList.add(series);
        }
        return seriesList;
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
        Button nextButton = (Button) view.findViewById(R.id.nextButton);
        Button backButton = (Button) view.findViewById(R.id.backButton);
        ImageButton addDayButton = (ImageButton) view.findViewById(R.id.addDayButton);

        addDayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<List<Series>> map = ((WizardActivity)getActivity()).getMap();
                daysAdapter.add(getActivity().getString(R.string.day, map.size() + 1));
                daysAdapter.notifyDataSetChanged();
                map.add(null);
                ((WizardActivity)getActivity()).setMap(map);
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSummaryDialog(readValues());
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((WizardActivity)getActivity()).finishWithResult(false);
            }
        });
    }

    private void setUpSeekBars(){
        seriesSeekBar = (DiscreteSeekBar) view.findViewById(R.id.seriesSeekBar);
        repeatsSeekBar = (DiscreteSeekBar) view.findViewById(R.id.repeatsSeekBar);
        repeatsSeekBar.setOnProgressChangeListener(new DiscreteSeekBar.OnProgressChangeListener() {
            @Override
            public void onProgressChanged(DiscreteSeekBar seekBar, int value, boolean fromUser) {
                repeatsTextView.setText(String.valueOf(value));
                if(repeatsCheckBox.isChecked()){
                    repeatsCheckBox.setChecked(false);
                }
            }

            @Override
            public void onStartTrackingTouch(DiscreteSeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(DiscreteSeekBar seekBar) {

            }
        });
        seriesSeekBar.setOnProgressChangeListener(new DiscreteSeekBar.OnProgressChangeListener() {
            @Override
            public void onProgressChanged(DiscreteSeekBar seekBar, int value, boolean fromUser) {
                seriesTextView.setText(String.valueOf(value));
                if(seriesCheckBox.isChecked()){
                    seriesCheckBox.setChecked(false);
                }
            }

            @Override
            public void onStartTrackingTouch(DiscreteSeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(DiscreteSeekBar seekBar) {

            }
        });
    }

    private void setUpCheckBoxes(){
        seriesCheckBox = (CheckBox) view.findViewById(R.id.seriesCheckBox);
        seriesCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    DialogFragment newFragment = NumberDialog.newInstance(getString(R.string.choose_series), false, new NumberDialog.NumberSetListener() {
                        @Override
                        public void onNumberSet(String text) {
                            seriesTextView.setText(text);
                        }
                    });
                    newFragment.show(getActivity().getSupportFragmentManager(), "dialog");
                }
            }
        });
        repeatsCheckBox = (CheckBox) view.findViewById(R.id.repeatsCheckBox);
        repeatsCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    DialogFragment newFragment = NumberDialog.newInstance(getString(R.string.choose_repeats), false, new NumberDialog.NumberSetListener() {
                        @Override
                        public void onNumberSet(String text) {
                            repeatsTextView.setText(text);
                        }
                    });
                    newFragment.show(getActivity().getSupportFragmentManager(), "dialog");
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
        daysAdapter = new ArrayAdapter<String>(getActivity(), R.layout.item_spinner, R.id.text, days);
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