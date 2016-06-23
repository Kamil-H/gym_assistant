package com.gymassistant.Fragments;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.gymassistant.Database.UserDB;
import com.gymassistant.Models.User;
import com.gymassistant.R;
import com.gymassistant.UIComponents.NumberDialog;

import org.joda.time.LocalDate;
import org.joda.time.Years;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * Created by KamilH on 2016-03-21.
 */
public class ProfileFragment extends Fragment {
    private View view;
    private FloatingActionButton calfFAB, thighFAB, fipFAB, waistFAB, bicepsFAB, chestFAB, weightFAB;
    private FloatingActionMenu floatingActionMenu;
    private FrameLayout frameLayout;
    private TextView ageTextView, heightTextView, weightTextView, bmiTextView, fatTextView, chestTextView, bicepsTextView, waistTextView, fipTextView, thighTextView, calfTextView;
    private User user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);

        getUser();
        setUpTextView();
        setUpFAM();
        fillTextViews();

        return view;
    }

    private void fillTextViews(){
        if(user.getBirthday() != null){
            ageTextView.setText(String.valueOf(getAge(user.getBirthday())));
        }
        heightTextView.setText(String.valueOf(user.getHeight()));
        weightTextView.setText(String.valueOf(user.getWeight()));
        bmiTextView.setText(String.format("%.2f", getBMI()));
    }

    private int getAge(String birth_date){
        DateTimeFormatter formatter = DateTimeFormat.forPattern("dd-MM-yyyy");
        LocalDate date = formatter.parseLocalDate(birth_date);
        Years age = Years.yearsBetween(date, LocalDate.now());
        return age.getYears();
    }

    private double getBMI(){
        return 10000 * user.getWeight() / (user.getHeight() * user.getHeight());
    }

    private void getUser(){
        UserDB userDB = new UserDB(getActivity());
        user = userDB.getUser();
    }

    private void setUpTextView(){
        ageTextView = (TextView) view.findViewById(R.id.ageTextView);
        heightTextView = (TextView) view.findViewById(R.id.heightTextView);
        weightTextView = (TextView) view.findViewById(R.id.weightTextView);
        bmiTextView = (TextView) view.findViewById(R.id.bmiTextView);

        fatTextView = (TextView) view.findViewById(R.id.fatTextView);
        chestTextView = (TextView) view.findViewById(R.id.chestTextView);
        bicepsTextView = (TextView) view.findViewById(R.id.bicepsTextView);
        waistTextView = (TextView) view.findViewById(R.id.waistTextView);
        fipTextView = (TextView) view.findViewById(R.id.fipTextView);
        thighTextView = (TextView) view.findViewById(R.id.thighTextView);
        calfTextView = (TextView) view.findViewById(R.id.calfTextView);
    }

    private void setUpFAM(){
        calfFAB = (FloatingActionButton) view.findViewById(R.id.calfFAB);
        thighFAB = (FloatingActionButton) view.findViewById(R.id.thighFAB);
        fipFAB = (FloatingActionButton) view.findViewById(R.id.fipFAB);
        waistFAB = (FloatingActionButton) view.findViewById(R.id.waistFAB);
        bicepsFAB = (FloatingActionButton) view.findViewById(R.id.bicepsFAB);
        chestFAB = (FloatingActionButton) view.findViewById(R.id.chestFAB);
        weightFAB = (FloatingActionButton) view.findViewById(R.id.weightFAB);

        calfFAB.setOnClickListener(clickListener);
        thighFAB.setOnClickListener(clickListener);
        fipFAB.setOnClickListener(clickListener);
        waistFAB.setOnClickListener(clickListener);
        bicepsFAB.setOnClickListener(clickListener);
        chestFAB.setOnClickListener(clickListener);
        weightFAB.setOnClickListener(clickListener);

        frameLayout = (FrameLayout) view.findViewById(R.id.frame_layout);
        frameLayout.getBackground().setAlpha(0);

        floatingActionMenu = (FloatingActionMenu) view.findViewById(R.id.menuFAM);
        floatingActionMenu.setOnMenuToggleListener(new FloatingActionMenu.OnMenuToggleListener() {
            @Override
            public void onMenuToggle(boolean opened) {
                if (opened)
                    frameLayout.getBackground().setAlpha(240);
                else
                    frameLayout.getBackground().setAlpha(0);
            }
        });
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.calfFAB:
                    showNumberDialog(getString(R.string.calf_FAB));
                    break;
                case R.id.thighFAB:
                    showNumberDialog(getString(R.string.thigh_FAB));
                    break;
                case R.id.fipFAB:
                    showNumberDialog(getString(R.string.fip_FAB));
                    break;
                case R.id.waistFAB:
                    showNumberDialog(getString(R.string.waist_FAB));
                    break;
                case R.id.bicepsFAB:
                    showNumberDialog(getString(R.string.biceps_FAB));
                    break;
                case R.id.chestFAB:
                    showNumberDialog(getString(R.string.chest_FAB));
                    break;
                case R.id.weightFAB:
                    showNumberDialog(getString(R.string.weight_FAB));
                    break;
            }
        }
    };

    private void showNumberDialog(final String title){
        DialogFragment newFragment = NumberDialog.newInstance(title, true, new NumberDialog.NumberSetListener() {
            @Override
            public void onNumberSet(String text) {
                addValueToDB(Double.parseDouble(text), title);
                closeFAM();
            }
        });
        newFragment.show(getActivity().getSupportFragmentManager(), "dialog");
    }

    private void addValueToDB(double value, String name){
        Log.i(name, String.valueOf(value));
        Toast.makeText(getActivity(), String.format("Dodano: %s: %f", name, value), Toast.LENGTH_LONG).show();
    }

    public void closeFAM(){
        floatingActionMenu.toggle(true);
    }
}
