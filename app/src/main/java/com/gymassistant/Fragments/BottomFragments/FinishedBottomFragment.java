package com.gymassistant.Fragments.BottomFragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gymassistant.Database.StartedTrainingPlanDB;
import com.gymassistant.Models.StartedTrainingPlan;
import com.gymassistant.R;
import com.gymassistant.RecyclerView.TrainingFinishedExpandableAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KamilH on 2016-05-20.
 */
public class FinishedBottomFragment extends Fragment {
    private View view;
    private StartedTrainingPlanDB startedTrainingPlanDB;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_bottom_finished, container, false);

        startedTrainingPlanDB = new StartedTrainingPlanDB(getActivity());
        if(startedTrainingPlanDB.getRowCount() > 0){
            setUpExpandableRecyclerView();
        }

        return view;
    }

    private void setUpExpandableRecyclerView(){
        TrainingFinishedExpandableAdapter trainingFinishedExpandableAdapter = new TrainingFinishedExpandableAdapter(getActivity(),
                generateList(startedTrainingPlanDB.getAllStartedTrainingPlans()));
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        recyclerView.setAdapter(trainingFinishedExpandableAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private List<StartedTrainingPlan> generateList(List<StartedTrainingPlan> startedTrainingPlanList) {
        List<StartedTrainingPlan> parentList = new ArrayList<>();
        for (StartedTrainingPlan startedTrainingPlan : startedTrainingPlanList) {
            if(startedTrainingPlan.getEndDate() != null){
                ArrayList<StartedTrainingPlan> childList = new ArrayList<>();
                childList.add(startedTrainingPlan);
                startedTrainingPlan.setStartedTrainingPlanList(childList);
                parentList.add(startedTrainingPlan);
            }
        }
        return parentList;
    }
}
