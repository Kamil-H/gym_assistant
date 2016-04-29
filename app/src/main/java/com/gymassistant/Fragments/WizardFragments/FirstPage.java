package com.gymassistant.Fragments.WizardFragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import java.util.List;
import java.util.ArrayList;

import com.gymassistant.Models.TrainingPlanModel;
import com.gymassistant.R;
import com.gymassistant.WizardActivity;

/**
 * Created by KamilH on 2016-03-21.
 */
public class FirstPage extends Fragment {
    private View view;
    private Button nextButton, backButton;
    private CheckBox monday, tuesday, wednesday, thursday, friday, saturday, sunday;
    private List<CheckBox> textViewsList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_page_first, container, false);

        nextButton = (Button) view.findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveDayNameList();
                ((WizardActivity)getActivity()).navigateToNextPage();
            }
        });

        backButton = (Button) view.findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        monday = (CheckBox) view.findViewById(R.id.monday);
        tuesday = (CheckBox) view.findViewById(R.id.tuesday);
        wednesday = (CheckBox) view.findViewById(R.id.wednesday);
        thursday = (CheckBox) view.findViewById(R.id.thursday);
        friday = (CheckBox) view.findViewById(R.id.friday);
        saturday = (CheckBox) view.findViewById(R.id.saturday);
        sunday = (CheckBox) view.findViewById(R.id.sunday);

        textViewsList = new ArrayList<>();

        textViewsList.add(monday);
        textViewsList.add(tuesday);
        textViewsList.add(wednesday);
        textViewsList.add(thursday);
        textViewsList.add(friday);
        textViewsList.add(saturday);
        textViewsList.add(sunday);

        return view;
    }

    private List<TrainingPlanModel> checkTextViews(){
        List<TrainingPlanModel> daysName = new ArrayList<>();
        for(CheckBox checkBox : textViewsList){
            if(checkBox.isChecked()){
                TrainingPlanModel trainingPlanModel = new TrainingPlanModel(getString(getResources().getIdentifier(getResources().getResourceEntryName(checkBox.getId()),
                        "string", getActivity().getPackageName())));
                daysName.add(trainingPlanModel);
            }
        }
        return daysName;
    }

    private void saveDayNameList(){
        ((WizardActivity)getActivity()).setDayNameList(checkTextViews());
    }
}
