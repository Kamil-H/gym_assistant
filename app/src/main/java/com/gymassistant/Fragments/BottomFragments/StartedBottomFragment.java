package com.gymassistant.Fragments.BottomFragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gymassistant.Database.StartedTrainingPlanDB;
import com.gymassistant.R;
import com.gymassistant.RecyclerView.TrainingStartedExpandableAdapter;

/**
 * Created by KamilH on 2016-05-20.
 */
public class StartedBottomFragment extends Fragment {
    private View view;
    private String TAG = "StartedBottomFragment";
    private StartedTrainingPlanDB startedTrainingPlanDB;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_bottom_started, container, false);

        startedTrainingPlanDB = new StartedTrainingPlanDB(getActivity());
        if(startedTrainingPlanDB.getRowCount() > 0){
            setUpExpandableRecyclerView();
        }

        return view;
    }

    private void setUpExpandableRecyclerView(){
        TrainingStartedExpandableAdapter trainingStartedExpandableAdapter = new TrainingStartedExpandableAdapter(getActivity(),
                startedTrainingPlanDB.getActiveStartedTrainingPlans());
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        recyclerView.setAdapter(trainingStartedExpandableAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}
