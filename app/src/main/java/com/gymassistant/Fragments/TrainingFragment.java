package com.gymassistant.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.gymassistant.Activities.TrainingPlanManagmentActivity;
import com.gymassistant.Database.StartedTrainingPlanDB;
import com.gymassistant.Database.TrainingDB;
import com.gymassistant.Models.StartedTrainingPlan;
import com.gymassistant.R;
import com.gymassistant.RecyclerView.TrainingDayExpandableAdapter;
import com.gymassistant.UIComponents.NumberDialog;

import java.util.List;

/**
 * Created by KamilH on 2016-03-21.
 */
public class TrainingFragment extends Fragment {
    private View view;
    private List<StartedTrainingPlan> startedTrainingPlanList;
    private FloatingActionButton fab;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        StartedTrainingPlanDB startedTrainingPlanDB = new StartedTrainingPlanDB(getActivity());
        startedTrainingPlanList = startedTrainingPlanDB.getActiveStartedTrainingPlans();

        if (startedTrainingPlanList.size() == 0){
            initEmptyStateUI(inflater, container);
        } else {
            initFragmentTrainingUI(inflater, container);
        }
        return view;
    }

    private void initFragmentTrainingUI(LayoutInflater inflater, ViewGroup container){
        view = inflater.inflate(R.layout.fragment_training, container, false);
        setUpList();
        setUpRecyclerView();

        fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToTrainingPlanManagmentActivity();
            }
        });
    }

    private void showSummaryDialog() {
        DialogFragment newFragment = NumberDialog.newInstance("SERRER", true, new NumberDialog.NumberSetListener() {
            @Override
            public void onNumberSet(String text) {
                Double d = Double.parseDouble(text);
                Log.i("INTEGER: ", String.valueOf(d.intValue()));
                Log.i("DOUBLE: ", String.valueOf(d));
            }
        });
        newFragment.show(getActivity().getSupportFragmentManager(), "dialog");
    }

    private void initEmptyStateUI(LayoutInflater inflater, ViewGroup container) {
        view = inflater.inflate(R.layout.empty_state, container, false);
        Button addPlanButton = (Button) view.findViewById(R.id.addPlanButton);
        addPlanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToTrainingPlanManagmentActivity();
            }
        });
    }

    public void setUpRecyclerView(){
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        TrainingDayExpandableAdapter trainingDayExpandableAdapter = new TrainingDayExpandableAdapter(getActivity(), startedTrainingPlanList);
        recyclerView.setAdapter(trainingDayExpandableAdapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener(){
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy){
                if (dy > 0)
                    fab.hide();
                else if (dy < 0)
                    fab.show();
            }
        });
    }

    private void setUpList(){
        TrainingDB trainingDB = new TrainingDB(getActivity());
        for(StartedTrainingPlan startedTrainingPlan : startedTrainingPlanList){
            startedTrainingPlan.getTrainingPlan().setTrainingList(
                    trainingDB.getTrainingsByTraningPlanId(startedTrainingPlan.getTrainingPlanId()));
        }
    }

    private void goToTrainingPlanManagmentActivity(){
        Intent intent = new Intent(getActivity(), TrainingPlanManagmentActivity.class);
        getActivity().startActivityForResult(intent, 1);
    }
}
