package com.gymassistant.Fragments.WizardFragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.List;

import com.gymassistant.Models.TrainingPlanModel;
import com.gymassistant.R;
import com.gymassistant.RecyclerView.TrainingPlanAdapter;
import com.gymassistant.WizardActivity;

/**
 * Created by KamilH on 2016-03-21.
 */
public class SecondPage extends Fragment {
    private View view;
    private Button nextButton, backButton;
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_page_second, container, false);

        nextButton = (Button) view.findViewById(R.id.nextButton);
        backButton = (Button) view.findViewById(R.id.backButton);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((WizardActivity)getActivity()).navigateToNextPage();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((WizardActivity)getActivity()).navigateToPreviousPage();
            }
        });

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        populateRecyclerView();

        return view;
    }

    private void populateRecyclerView(){
        List<TrainingPlanModel> trainingPlanModelList = ((WizardActivity)getActivity()).getDayNameList();
        TrainingPlanAdapter trainingPlanRVAdapter = new TrainingPlanAdapter(getActivity(), trainingPlanModelList);
        recyclerView.setAdapter(trainingPlanRVAdapter);
    }
}
