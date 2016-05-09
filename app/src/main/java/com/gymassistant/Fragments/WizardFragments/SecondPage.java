package com.gymassistant.Fragments.WizardFragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.gymassistant.GlobalClass;
import com.gymassistant.Models.Series;
import com.gymassistant.R;
import com.gymassistant.RecyclerView.TrainingPlanAdapter;
import com.gymassistant.WizardActivity;

import java.util.List;
import java.util.Map;

/**
 * Created by KamilH on 2016-03-21.
 */
public class SecondPage extends Fragment {
    private View view;
    private Button nextButton, backButton;
    private RecyclerView recyclerView;
    private Map<Integer, List<Series>> map;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_page_second, container, false);

        map = ((GlobalClass) getActivity().getApplication()).map;

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        setUpButtons();
        populateRecyclerView();

        return view;
    }

    private void populateRecyclerView(){
        int itemCount = ((WizardActivity)getActivity()).getItemCount();
        long trainingPlanId = ((WizardActivity)getActivity()).getTrainingPlanId();
        TrainingPlanAdapter trainingPlanRVAdapter = new TrainingPlanAdapter(getActivity(), itemCount, trainingPlanId, map);
        recyclerView.setAdapter(trainingPlanRVAdapter);
    }

    private void setUpButtons(){
        nextButton = (Button) view.findViewById(R.id.nextButton);
        backButton = (Button) view.findViewById(R.id.backButton);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((WizardActivity)getActivity()).finishWithResult(true);
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((WizardActivity)getActivity()).finishWithResult(false);
            }
        });
    }
}
