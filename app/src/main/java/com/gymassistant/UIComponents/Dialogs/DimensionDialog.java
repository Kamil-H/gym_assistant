package com.gymassistant.UIComponents.Dialogs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.gymassistant.Database.DimensionDB;
import com.gymassistant.Database.DimensionTypeDB;
import com.gymassistant.Database.UserDB;
import com.gymassistant.DateConverter;
import com.gymassistant.Fragments.ProfileFragment;
import com.gymassistant.Models.Dimension;
import com.gymassistant.Models.DimensionType;
import com.gymassistant.R;

import java.util.List;

/**
 * Created by KamilH on 2016-06-23.
 */
public class DimensionDialog extends DialogFragment {
    private View view;
    private EditText valueEditText;
    private Spinner dimensionSpinner;
    private TextView valueUnitTextView;
    private DimensionType dimensionType;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setStyle(DialogFragment.STYLE_NO_FRAME, R.style.myDialog);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dialog_dimension, container, false);

        setUpEditText();
        setUpButtons();
        setUpSpinner();

        valueUnitTextView = (TextView) view.findViewById(R.id.valueUnitTextView);

        return view;
    }

    private void updateUser(double weight){
        UserDB userDB = new UserDB(getActivity());
        userDB.updateWeight(weight);
    }

    private void refreshProfileFragment(){
        ProfileFragment profileFragment = (ProfileFragment)getTargetFragment();
        if(dimensionType.getId() == 1){
            profileFragment.fillTextViews();
        }
        profileFragment.setUpRecyclerView();
    }

    private void closeDialog(){
        this.dismiss();
    }

    private Double getValue(){
        return Double.parseDouble(valueEditText.getText().toString());
    }

    private void addNewDimension(){
        Dimension dimension = new Dimension(getValue(), DateConverter.today(), dimensionType.getId());
        DimensionDB dimensionDB = new DimensionDB(getActivity());
        dimensionDB.addDimension(dimension);

        if(dimensionType.getId() == 1){
            updateUser(getValue());
        }
    }

    private void setUpButtons(){
        Button backButton = (Button) view.findViewById(R.id.backButton);
        Button nextButton = (Button) view.findViewById(R.id.nextButton);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeDialog();
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!valueEditText.getText().toString().isEmpty()){
                    addNewDimension();
                    refreshProfileFragment();
                    closeDialog();
                } else {
                    Toast.makeText(getActivity(), getActivity().getString(R.string.fill_measurment), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private List<DimensionType> setUpDimensionTypesList(){
        DimensionTypeDB dimensionTypeDB = new DimensionTypeDB(getActivity());
        return dimensionTypeDB.getAllDimensionTypes();
    }

    private void setUpSpinner(){
        dimensionSpinner = (Spinner) view.findViewById(R.id.dimensionSpinner);
        ArrayAdapter<DimensionType> dimensionTypeAdapter = new ArrayAdapter<DimensionType>(getActivity(), R.layout.item_spinner_allcaps, R.id.text, setUpDimensionTypesList());
        dimensionSpinner.setAdapter(dimensionTypeAdapter);

        dimensionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                dimensionType = (DimensionType)dimensionSpinner.getSelectedItem();
                valueUnitTextView.setText(dimensionType.getUnit());
                valueEditText.setText("");
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void setUpEditText(){
        valueEditText = (EditText) view.findViewById(R.id.valueEditText);
        valueEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    String title = String.format("%s", dimensionSpinner.getSelectedItem());
                    DialogFragment newFragment = NumberDialog.newInstance(title, true, new NumberDialog.NumberSetListener() {
                        @Override
                        public void onNumberSet(String text) {
                            valueEditText.setText(text);
                        }
                    });
                    newFragment.show(getActivity().getSupportFragmentManager(), "dialog");
                    valueEditText.clearFocus();
                }
            }
        });
    }
}
