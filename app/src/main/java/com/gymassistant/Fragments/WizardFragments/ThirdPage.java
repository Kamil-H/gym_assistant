package com.gymassistant.Fragments.WizardFragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.util.SparseArrayCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gymassistant.Database.SeriesDB;
import com.gymassistant.Database.TrainingDB;
import com.gymassistant.Database.TrainingPlanDB;
import com.gymassistant.GlobalClass;
import com.gymassistant.Models.Series;
import com.gymassistant.Models.Training;
import com.gymassistant.Models.TrainingPlan;
import com.gymassistant.R;
import com.gymassistant.RecyclerView.TrainingPlanAdapter;
import com.gymassistant.Activities.WizardActivity;

import java.util.List;
import java.util.Map;

/**
 * Created by KamilH on 2016-03-21.
 */
public class ThirdPage extends Fragment {
    private View view;
    private Button nextButton, backButton;
    private RecyclerView recyclerView;
    private SparseArrayCompat<List<Series>> map;
    private Training training;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_page_third, container, false);

        map = ((WizardActivity)getActivity()).getMap();

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        setUpButtons();
        populateRecyclerView();

        return view;
    }

    private void saveToDatabase(String planName, String planDescription){
        long trainingPlanId = addNewTrainingPlan(planName, planDescription);
        for (int i = 0; i < map.size(); i++){
            long trainingId = addNewTraining(trainingPlanId, map.keyAt(i));
            List<Series> seriesList = map.get(i);
            saveSeriesToDb(seriesList, (int)trainingId);
        }
    }

    private long addNewTraining(long trainingPlanId, int day){
        TrainingDB trainingDB = new TrainingDB(getActivity());
        this.training = new Training((int)trainingPlanId, day, null);
        long trainingId = trainingDB.addTraining(training);
        Log.i("Added", String.format("Training ID: %d", trainingId));

        return trainingId;
    }

    private void saveSeriesToDb(List<Series> seriesList, int trainingId){
        SeriesDB seriesDB = new SeriesDB(getActivity());
        seriesDB.addSeriesList(seriesList, trainingId);
    }

    private long addNewTrainingPlan(String name, String description){
        TrainingPlanDB trainingPlanDB = new TrainingPlanDB(getActivity());
        long trainingPlanId = trainingPlanDB.addTrainingPlan(new TrainingPlan(map.size(), 0, false, name, description));
        Log.i("Added", String.format("TrainingPlan ID: %d", trainingPlanId));

        return trainingPlanId;
    }

    private void populateRecyclerView(){
        int itemCount = map.size();
        TrainingPlanAdapter trainingPlanRVAdapter = new TrainingPlanAdapter(getActivity(), itemCount, map);
        recyclerView.setAdapter(trainingPlanRVAdapter);
    }

    private void setUpButtons(){
        nextButton = (Button) view.findViewById(R.id.nextButton);
        backButton = (Button) view.findViewById(R.id.backButton);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSummaryDialog();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((WizardActivity)getActivity()).finishWithResult(false);
            }
        });
    }

    private void showSummaryDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(new ContextThemeWrapper(getActivity(), R.style.myDialog));
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_plan, null);
        dialogBuilder.setView(dialogView);

        final EditText planNameEditText = (EditText) dialogView.findViewById(R.id.planNameEditText);
        final EditText planDescriptionEditText = (EditText) dialogView.findViewById(R.id.planDescriptionEditText);

        dialogBuilder.setPositiveButton(getString(R.string.save_and_close), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String planName = planNameEditText.getText().toString();
                String planDescription = planDescriptionEditText.getText().toString();
                if(planName.isEmpty()){
                    planNameEditText.setError(getString(R.string.valid_username));
                } else {
                    saveToDatabase(planName, planDescription);
                    ((WizardActivity)getActivity()).finishWithResult(true);
                }
            }
        });
        dialogBuilder.setNegativeButton(getActivity().getString(R.string.cancel), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

            }
        });
        dialogBuilder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }
}
