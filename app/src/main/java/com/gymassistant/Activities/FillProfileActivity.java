package com.gymassistant.Activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.gymassistant.Database.UserDB;
import com.gymassistant.DateConverter;
import com.gymassistant.MainActivity;
import com.gymassistant.Models.User;
import com.gymassistant.R;

import org.joda.time.DateTime;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by KamilH on 2016-05-10.
 */
public class FillProfileActivity extends AppCompatActivity {
    private EditText surnameEditText, nameEditText, weightEditText, heightEditText, dateEditText;
    private RadioGroup radioGroup;
    private Button saveButton;
    private RadioButton maleRadioButton;
    private UserDB userDB;
    private User user;
    private String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_profile);

        userDB = new UserDB(this);
        user = userDB.getUser();

        maleRadioButton = (RadioButton) findViewById(R.id.maleRadioButton);
        heightEditText = (EditText) findViewById(R.id.heightEditText);
        weightEditText = (EditText) findViewById(R.id.weightEditText);
        nameEditText = (EditText) findViewById(R.id.nameEditText);
        surnameEditText = (EditText) findViewById(R.id.surnameEditText);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        dateEditText = (EditText) findViewById(R.id.dateEditText);
        dateEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    showDateDialog();
                }
            }
        });
        saveButton = (Button) findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readValues();
                goToMainActivity();
            }
        });
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

    private void goToMainActivity(){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        this.finish();
    }

    private void readValues(){
        String name = nameEditText.getText().toString();
        String surname = surnameEditText.getText().toString();
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

        user.setFirstName(name);
        user.setSurname(surname);
        user.setGender(gender);
        user.setBirthday(birthday);
        user.setWeight(weight);
        user.setHeight(height);

        Log.i("USER", user.toString());

        if(userDB.getRowCount() == 0){
            addUser();
        } else {
            updateUser();
        }
    }

    private void updateUser(){
        userDB.updateUser(user);
    }

    private void addUser(){
        userDB.addUser(user);
    }
}
