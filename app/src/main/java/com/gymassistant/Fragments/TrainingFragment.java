package com.gymassistant.Fragments;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.gymassistant.Activities.RegisterActivity;
import com.gymassistant.Activities.TrainingAssistant;
import com.gymassistant.Database.SeriesDB;
import com.gymassistant.Database.TrainingDB;
import com.gymassistant.Database.TrainingPlanDB;
import com.gymassistant.Models.Series;
import com.gymassistant.Models.Training;
import com.gymassistant.Models.TrainingPlan;
import com.gymassistant.R;
import com.gymassistant.RecyclerView.TrainingDayAdapter;
import com.gymassistant.WizardActivity;

/**
 * Created by KamilH on 2016-03-21.
 */
public class TrainingFragment extends Fragment {
    private View view;
    private Button addPlanButton, button;
    private TrainingDB trainingDB;
    private TrainingPlanDB trainingPlanDB;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        trainingDB = new TrainingDB(getActivity());
        trainingPlanDB = new TrainingPlanDB(getActivity());

        if (trainingPlanDB.getRowCount() == 0){
            initEmptyStateUI(inflater, container);
        } else {
            initFragmentTrainingUI(inflater, container);
        }

        return view;
    }

    private void initFragmentTrainingUI(LayoutInflater inflater, ViewGroup container){
        view = inflater.inflate(R.layout.fragment_training, container, false);

        setUpRecyclerView();

        button = (Button) view.findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trainingPlanDB.removeAll();
                trainingDB.removeAll();
                SeriesDB seriesDB = new SeriesDB(getActivity());
                seriesDB.removeAll();
            }
        });
    }

    private void initEmptyStateUI(LayoutInflater inflater, ViewGroup container) {
        view = inflater.inflate(R.layout.empty_state, container, false);
        addPlanButton = (Button) view.findViewById(R.id.addPlanButton);
        addPlanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToWizardActivity();
            }
        });
    }

    private void setUpRecyclerView(){
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        TrainingDayAdapter trainingDayAdapter = new TrainingDayAdapter(getActivity(), trainingDB.getAllTrainings());
        recyclerView.setAdapter(trainingDayAdapter);
    }

    private void goToWizardActivity(){
        Intent intent = new Intent(getActivity(), WizardActivity.class);
        startActivity(intent);
    }

    private void goToTrainingAssistantActivity(){
        Intent intent = new Intent(getActivity(), TrainingAssistant.class);
        startActivity(intent);
    }
}
