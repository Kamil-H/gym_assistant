package com.gymassistant.Activities;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.gymassistant.Database.DimensionDB;
import com.gymassistant.Database.UserDB;
import com.gymassistant.DateConverter;
import com.gymassistant.Models.Dimension;
import com.gymassistant.Models.User;
import com.gymassistant.R;

import java.util.Calendar;

/**
 * Created by KamilH on 2016-05-10.
 */
public class FillProfileActivity extends AppCompatActivity {
    private EditText weightEditText, heightEditText, dateEditText;
    private RadioButton maleRadioButton;
    private String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_profile);

        setUpToolbar();

        TextView heightUnitTextView = (TextView) findViewById(R.id.heightUnitTextView);
        TextView weightUnitTextView = (TextView) findViewById(R.id.weightUnitTextView);
        heightUnitTextView.setText("CM");
        weightUnitTextView.setText("KG");

        maleRadioButton = (RadioButton) findViewById(R.id.maleRadioButton);

        setUpEditTextes();
        setUpButton();
    }

    private void setUpToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Uzupełnij swój profil");
    }

    private void setUpEditTextes(){
        heightEditText = (EditText) findViewById(R.id.heightEditText);
        weightEditText = (EditText) findViewById(R.id.weightEditText);
        dateEditText = (EditText) findViewById(R.id.dateEditText);
        dateEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    showDateDialog();
                }
            }
        });
    }

    private void setUpButton(){
        Button saveButton = (Button) findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readValues();
                editFirstTime();
                finishWithResult();
            }
        });
    }

    public void finishWithResult(){
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_OK, returnIntent);
        this.finish();
    }

    private void showDateDialog(){
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(new ContextThemeWrapper(this, android.R.style.Theme_Holo_Dialog), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                date = String.format("%s-%s-%d", DateConverter.hasTwoLatters(dayOfMonth), DateConverter.hasTwoLatters(monthOfYear+1), year);
                dateEditText.setText(date);
            }
        },calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private void readValues(){
        String gender;
        String birthday = date;
        double weight, height;

        if(weightEditText.getText().toString().isEmpty()){
            weightEditText.setError(getString(R.string.please_give_weight));
            return;
        } else {
            weight = Double.parseDouble(weightEditText.getText().toString());
        }
        if(heightEditText.getText().toString().isEmpty()){
            heightEditText.setError(getString(R.string.please_give_height));
            return;
        } else {
            height = Double.parseDouble(heightEditText.getText().toString());
        }

        if(maleRadioButton.isChecked()){
            gender = "M";
        } else {
            gender = "F";
        }

        User user = new User(gender, birthday, height, weight);

        addUser(user);
        addNewDimension(weight);
    }

    private void addNewDimension(double weight){
        Dimension dimension = new Dimension(weight, DateConverter.today(), 1);
        DimensionDB dimensionDB = new DimensionDB(this);
        dimensionDB.addDimension(dimension);
    }

    private void addUser(User user){
        UserDB userDB = new UserDB(this);
        userDB.addUser(user);
    }

    private void editFirstTime(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("firstTime", true);
        editor.apply();
    }
}
