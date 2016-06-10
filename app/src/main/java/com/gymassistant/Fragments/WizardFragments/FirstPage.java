package com.gymassistant.Fragments.WizardFragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.gymassistant.Activities.WizardActivity;
import com.gymassistant.Database.SeriesDB;
import com.gymassistant.Database.TrainingDB;
import com.gymassistant.Database.TrainingPlanDB;
import com.gymassistant.Models.Series;
import com.gymassistant.Models.TrainingPlan;
import com.gymassistant.R;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KamilH on 2016-03-21.
 */
public class FirstPage extends Fragment {
    private View view;
    private RadioButton newPlanRadioButton;
    private DiscreteSeekBar trainingDaySeekBar;
    private Spinner existingPlansSpinner;
    private TextView textView8;
    private CheckBox deletedPlansCheckBox;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_page_first, container, false);

        trainingDaySeekBar = (DiscreteSeekBar) view.findViewById(R.id.trainingDaySeekBar);
        deletedPlansCheckBox = (CheckBox) view.findViewById(R.id.deletedPlansCheckBox);
        deletedPlansCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){

                } else {

                }
            }
        });
        textView8 = (TextView) view.findViewById(R.id.textView8);

        setUpSpinner();
        setUpButtons();
        setUpRadioButtons();

        return view;
    }

    private List<TrainingPlan> getTrainingPlans(){
        TrainingPlanDB trainingPlanDB = new TrainingPlanDB(getActivity());
        List<TrainingPlan> trainingPlanList = new ArrayList<>();
        if(trainingPlanDB.getRowCount() > 0){
            trainingPlanList = trainingPlanDB.getAllTrainingPlans();
        }
        return trainingPlanList;
    }

    private void initMap(int size){
        List<List<Series>> map = new ArrayList<>();
        for(int i = 0; i < size; i++){
            map.add(null);
        }
        ((WizardActivity)getActivity()).setMap(map);
    }

    private void fillMap(long trainingPlanId){
        List<List<Series>> map = ((WizardActivity)getActivity()).getMap();
        TrainingDB trainingDB = new TrainingDB(getActivity());
        SeriesDB seriesDB = new SeriesDB(getActivity());
        List<Integer> ids = trainingDB.getTrainingIdsByTrainingPlanId(trainingPlanId);
        for(int i = 0; i < ids.size(); i++){
            map.add(i, seriesDB.getSeriesByTrainingId(ids.get(i)));
        }
        ((WizardActivity)getActivity()).setMap(map);
    }

    private void setUpSpinner(){
        existingPlansSpinner = (Spinner) view.findViewById(R.id.existingPlansSpinner);
        populateExistingPlansSpinner();
        existingPlansSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(((WizardActivity)getActivity()).getActualPage() == 0){
                    int days = ((TrainingPlan) existingPlansSpinner.getSelectedItem()).getDays();
                    long trainingPlanId = ((TrainingPlan) existingPlansSpinner.getSelectedItem()).getId();
                    initMap(days);
                    fillMap(trainingPlanId);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void populateExistingPlansSpinner(){
        List<TrainingPlan> trainingPlanList = getTrainingPlans();
        ArrayAdapter<TrainingPlan> existingPlansAdapter = new ArrayAdapter<>(getActivity(), R.layout.item_spinner, R.id.text, trainingPlanList);
        existingPlansSpinner.setAdapter(existingPlansAdapter);
    }

    private void setUpButtons(){
        Button nextButton = (Button) view.findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(newPlanRadioButton.isChecked()){
                    initMap(trainingDaySeekBar.getProgress());
                }
                ((WizardActivity)getActivity()).setItemCount(trainingDaySeekBar.getProgress());
                ((WizardActivity)getActivity()).navigateToNextPage();
            }
        });

        Button backButton = (Button) view.findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((WizardActivity)getActivity()).finishWithResult(false);
            }
        });
    }

    private void setUpRadioButtons(){
        newPlanRadioButton = (RadioButton) view.findViewById(R.id.newPlanRadioButton);
        RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.radioGroup);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(newPlanRadioButton.isChecked()){
                    textView8.setText(getString(R.string.choose_number_training_days));
                    trainingDaySeekBar.setVisibility(View.VISIBLE);
                    existingPlansSpinner.setVisibility(View.INVISIBLE);
                    deletedPlansCheckBox.setVisibility(View.INVISIBLE);
                } else {
                    textView8.setText(getString(R.string.choose_existing_plan));
                    trainingDaySeekBar.setVisibility(View.INVISIBLE);
                    existingPlansSpinner.setVisibility(View.VISIBLE);
                    deletedPlansCheckBox.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}
