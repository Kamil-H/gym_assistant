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
import com.gymassistant.R;
import com.gymassistant.RecyclerView.TrainingManagementAdapter;

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
            setUpRecyclerView();
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

    private void setUpRecyclerView(){
        TrainingManagementAdapter trainingManagementAdapter = new TrainingManagementAdapter(getActivity(), trainingPlanDB.getExistingTrainingPlans());
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        recyclerView.setAdapter(trainingManagementAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
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

    public void refresh(){
        if(trainingPlanDB.getRowCount() > 0){
            setUpRecyclerView();
        }
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