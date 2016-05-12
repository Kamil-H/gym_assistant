package com.gymassistant.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.gymassistant.Database.UserDB;
import com.gymassistant.MainActivity;
import com.gymassistant.Models.User;
import com.gymassistant.R;

import org.joda.time.DateTime;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by KamilH on 2016-05-10.
 */
public class FillProfileActivity extends AppCompatActivity {
    private View view;
    private EditText surnameEditText, nameEditText;
    private RadioGroup radioGroup;
    private Spinner yearSpinner, monthSpinner, daySpinner;
    private Button saveButton;
    private RadioButton maleRadioButton;
    private UserDB userDB;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_profile);

        initSpinners();
        userDB = new UserDB(this);
        user = userDB.getUser();

        maleRadioButton = (RadioButton) findViewById(R.id.maleRadioButton);
        nameEditText = (EditText) findViewById(R.id.nameEditText);
        surnameEditText = (EditText) findViewById(R.id.surnameEditText);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        saveButton = (Button) findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readValues();
            }
        });
    }

    private void goToMainActivity(){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        this.finish();
    }

    private void initSpinners(){
        yearSpinner = (Spinner) findViewById(R.id.yearSpinner);
        monthSpinner = (Spinner) findViewById(R.id.monthSpinner);
        daySpinner = (Spinner) findViewById(R.id.daySpinner);

        populateYearSpinner();
        populateMonthSpinner();
        populateDaySpinner();
    }

    private void populateYearSpinner(){
        ArrayList<String> years = new ArrayList<String>();
        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = thisYear; i >= 1920; i--) {
            years.add(Integer.toString(i));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, years);
        yearSpinner.setAdapter(adapter);
    }

    private void populateMonthSpinner(){
        ArrayList<String> months = new ArrayList<String>();
        /*DateFormatSymbols symbols = new DateFormatSymbols();
        String[] monthNames = symbols.getMonths();
        for(int i = 1; i < monthNames.length; i++){
            months.add(monthNames[i]);
        }*/
        int numDays = 13;
        for (int i = 1; i < numDays; i++) {
            months.add(Integer.toString(i));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, months);
        monthSpinner.setAdapter(adapter);
    }

    private void populateDaySpinner(){
        ArrayList<String> days = new ArrayList<String>();
        int numDays = 32;
        for (int i = 1; i < numDays; i++) {
            days.add(Integer.toString(i));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, days);
        daySpinner.setAdapter(adapter);
    }

    private void readValues(){
        String name = nameEditText.getText().toString();
        String surname = surnameEditText.getText().toString();
        String gender;
        String birthday;

        if(maleRadioButton.isChecked()){
            gender = "M";
        } else {
            gender = "F";
        }
        birthday = String.format("%d-%d-%d", Integer.valueOf(daySpinner.getSelectedItem().toString()),
                Integer.valueOf(monthSpinner.getSelectedItem().toString()), Integer.valueOf(yearSpinner.getSelectedItem().toString()));

        user.setFirstName(name);
        user.setSurname(surname);
        user.setGender(gender);
        user.setBirthday(birthday);

        updateUser();
    }

    private void updateUser(){
        userDB.updateUser(user);
    }
}
