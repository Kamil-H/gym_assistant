package com.gymassistant.Fragments;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.gymassistant.Activities.RegisterActivity;
import com.gymassistant.Activities.TrainingAssistant;
import com.gymassistant.R;
import com.gymassistant.WizardActivity;

/**
 * Created by KamilH on 2016-03-21.
 */
public class TrainingFragment extends Fragment {
    private View view;
    private Button addPlanButton, button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        int i = 0;

        if (i == 0){
            view = inflater.inflate(R.layout.empty_state, container, false);
            addPlanButton = (Button) view.findViewById(R.id.addPlanButton);
            addPlanButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goToWizardActivity();
                }
            });

        } else {
            view = inflater.inflate(R.layout.fragment_training, container, false);
            button = (Button) view.findViewById(R.id.button2);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goToTrainingAssistantActivity();
                }
            });
        }

        return view;
    }

    private void goToWizardActivity(){
        Intent intent = new Intent(getActivity(), WizardActivity.class);
        startActivity(intent);
    }

    private void goToTrainingAssistantActivity(){
        Intent intent = new Intent(getActivity(), TrainingAssistant.class);
        startActivity(intent);
    }
}
