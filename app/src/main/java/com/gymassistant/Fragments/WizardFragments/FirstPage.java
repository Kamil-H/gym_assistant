package com.gymassistant.Fragments.WizardFragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    private ArrayAdapter<TrainingPlan> existingPlansAdapter;
    private boolean EXISTING = true, ALL = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_page_first, container, false);

        trainingDaySeekBar = (DiscreteSeekBar) view.findViewById(R.id.trainingDaySeekBar);
        deletedPlansCheckBox = (CheckBox) view.findViewById(R.id.deletedPlansCheckBox);
        deletedPlansCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    updateExistingPlansSpinner(ALL);
                } else {
                    updateExistingPlansSpinner(EXISTING);
                }
            }
        });
        textView8 = (TextView) view.findViewById(R.id.textView8);

        setUpSpinner();
        setUpButtons();
        setUpRadioButtons();

        return view;
    }

    private List<TrainingPlan> getAllTrainingPlans(){
        TrainingPlanDB trainingPlanDB = new TrainingPlanDB(getActivity());
        return trainingPlanDB.getAllTrainingPlans();
    }

    private List<TrainingPlan> getExistingTrainingPlans(){
        TrainingPlanDB trainingPlanDB = new TrainingPlanDB(getActivity());
        return trainingPlanDB.getExistingTrainingPlans();
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
        List<Long> ids = trainingDB.getTrainingIdsByTrainingPlanId(trainingPlanId);
        for(int i = 0; i < ids.size(); i++){
            map.set(i, seriesDB.getSeriesByTrainingId(ids.get(i)));
        }
        ((WizardActivity)getActivity()).setMap(map);
    }

    private void setUpSpinner(){
        existingPlansSpinner = (Spinner) view.findViewById(R.id.existingPlansSpinner);
        initExistingPlansSpinner();
    }

    private void initExistingPlansSpinner(){
        List<TrainingPlan> trainingPlanList = getExistingTrainingPlans();
        existingPlansAdapter = new ArrayAdapter<>(getActivity(), R.layout.item_spinner, R.id.text, trainingPlanList);
        existingPlansSpinner.setAdapter(existingPlansAdapter);
    }

    private void updateExistingPlansSpinner(boolean action){
        List<TrainingPlan> trainingPlanList;
        if(action){
            trainingPlanList = getExistingTrainingPlans();
        } else {
            trainingPlanList = getAllTrainingPlans();
        }
        existingPlansAdapter.clear();
        existingPlansAdapter.addAll(trainingPlanList);
        existingPlansAdapter.notifyDataSetChanged();
    }

    private void setUpButtons(){
        Button nextButton = (Button) view.findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(newPlanRadioButton.isChecked()){
                    int days = trainingDaySeekBar.getProgress();
                    initMap(days);
                    ((WizardActivity)getActivity()).setItemCount(days);
                    ((WizardActivity)getActivity()).navigateToNextPage();
                } else {
                    TrainingPlan trainingPlan = ((TrainingPlan) existingPlansSpinner.getSelectedItem());
                    long trainingPlanId = trainingPlan.getId();
                    int days = trainingPlan.getDays();
                    ((WizardActivity)getActivity()).setItemCount(days);
                    initMap(days);
                    fillMap(trainingPlanId);
                    ((WizardActivity)getActivity()).navigateToLastPage();
                }
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
