package com.gymassistant.Fragments.BottomFragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gymassistant.Activities.WizardActivity;
import com.gymassistant.Database.TrainingPlanDB;
import com.gymassistant.Models.TrainingPlan;
import com.gymassistant.R;
import com.gymassistant.RecyclerView.TrainingPlanExpandableAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KamilH on 2016-05-20.
 */
public class PlansBottomFragment extends Fragment {
    private View view;
    private FloatingActionButton fab;
    private String TAG = "PlansBottomFragment";
    private TrainingPlanDB trainingPlanDB;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_bottom_plans, container, false);

        trainingPlanDB = new TrainingPlanDB(getActivity());
        if(trainingPlanDB.getRowCount() > 0){
            setUpExpandableRecyclerView();
        }

        fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToWizardActivity();
            }
        });

        return view;
    }

    private void setUpExpandableRecyclerView(){
        TrainingPlanExpandableAdapter trainingPlanExpandableAdapter = new TrainingPlanExpandableAdapter(getActivity(), generateList(trainingPlanDB.getAllTrainingPlans()));
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        recyclerView.setAdapter(trainingPlanExpandableAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private List<TrainingPlan> generateList(List<TrainingPlan> trainingPlanList) {
        for (TrainingPlan trainingPlan : trainingPlanList) {
            ArrayList<TrainingPlan> childList = new ArrayList<>();
            childList.add(trainingPlan);
            trainingPlan.setTrainingPlanList(childList);
        }
        return trainingPlanList;
    }

    public void refresh(){
        setUpExpandableRecyclerView();
    }

    private void goToWizardActivity(){
        Intent intent = new Intent(getActivity(), WizardActivity.class);
        getActivity().startActivityForResult(intent, 1);
    }

    @Override
    public void onResume() {
        super.onResume();
        refresh();
    }
}