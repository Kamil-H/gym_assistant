package com.gymassistant.Fragments.WizardFragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.gymassistant.Database.TrainingPlanDB;
import com.gymassistant.Models.TrainingPlan;
import com.gymassistant.R;
import com.gymassistant.WizardActivity;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

/**
 * Created by KamilH on 2016-03-21.
 */
public class FirstPage extends Fragment {
    private View view;
    private Button nextButton, backButton;
    private RadioButton newPlanRadioButton, existingPlanRadioButton;
    private RadioGroup radioGroup;
    private EditText startDateEditText;
    private DiscreteSeekBar trainingDaySeekBar, trainingPlanLengthSeekBar;
    private Spinner existingPlansSpinner;
    private TextView textView8;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_page_first, container, false);

        existingPlansSpinner = (Spinner) view.findViewById(R.id.existingPlansSpinner);
        startDateEditText = (EditText) view.findViewById(R.id.startDateEditText);
        trainingDaySeekBar = (DiscreteSeekBar) view.findViewById(R.id.trainingDaySeekBar);
        trainingPlanLengthSeekBar = (DiscreteSeekBar) view.findViewById(R.id.trainingPlanLengthSeekBar);
        textView8 = (TextView) view.findViewById(R.id.textView8);

        setUpButtons();
        setUpRadioButtons();

        return view;
    }

    private void setUpButtons(){
        nextButton = (Button) view.findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((WizardActivity)getActivity()).setItemCount(trainingDaySeekBar.getProgress());
                ((WizardActivity)getActivity()).navigateToNextPage();
            }
        });

        backButton = (Button) view.findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((WizardActivity)getActivity()).finishWithResult(false);
            }
        });
    }

    private void setUpRadioButtons(){
        newPlanRadioButton = (RadioButton) view.findViewById(R.id.newPlanRadioButton);
        existingPlanRadioButton = (RadioButton) view.findViewById(R.id.existingPlanRadioButton);
        radioGroup = (RadioGroup) view.findViewById(R.id.radioGroup);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(newPlanRadioButton.isChecked()){
                    textView8.setText(getString(R.string.choose_number_training_days));
                    trainingDaySeekBar.setVisibility(View.VISIBLE);
                    existingPlansSpinner.setVisibility(View.INVISIBLE);
                } else {
                    textView8.setText(getString(R.string.choose_existing_plan));
                    trainingDaySeekBar.setVisibility(View.INVISIBLE);
                    existingPlansSpinner.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}
