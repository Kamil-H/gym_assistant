package com.gymassistant.Activities;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextThemeWrapper;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.gymassistant.Database.DimensionDB;
import com.gymassistant.Database.UserDB;
import com.gymassistant.DateConverter;
import com.gymassistant.Models.Dimension;
import com.gymassistant.Models.User;
import com.gymassistant.Preferences;
import com.gymassistant.R;

import java.util.Calendar;

/**
 * Created by KamilH on 2016-05-10.
 */
public class FillProfileActivity extends AppCompatActivity {
    private RadioButton maleRadioButton, femaleRadioButton, cmRadioButton, inchRadioButton, kgRadioButton, lbRadioButton;
    private EditText weightEditText, heightEditText, dateEditText;
    private Preferences preferences;
    private boolean isUpdate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_profile);

        setUpToolbar();

        preferences = new Preferences(this);
        isUpdate = readParameter();

        setUpRadioButtons();
        setUpEditTexts();
        setUpButton();

        initView();
    }

    private boolean readParameter() {
        Bundle extras = getIntent().getExtras();
        return extras != null && extras.getBoolean("isUpdate");
    }

    private void setUpRadioButtons(){
        maleRadioButton = (RadioButton) findViewById(R.id.maleRadioButton);
        femaleRadioButton = (RadioButton) findViewById(R.id.femaleRadioButton);
        cmRadioButton = (RadioButton) findViewById(R.id.cmRadioButton);
        inchRadioButton = (RadioButton) findViewById(R.id.inchRadioButton);
        kgRadioButton = (RadioButton) findViewById(R.id.kgRadioButton);
        lbRadioButton = (RadioButton) findViewById(R.id.lbRadioButton);
    }

    private void initView(){
        if(isUpdate){
            initEditTexts();
            initRadioButtons();
        } else {
            defaultRadioButtonInit();
        }
    }

    private void initRadioButtons(){
        User user = getUser();

        if(preferences.getLengthUnit().matches("cm")){
            cmRadioButton.performClick();
        } else {
            inchRadioButton.performClick();
        }

        if(preferences.getWeightUnit().matches("kg")){
            kgRadioButton.performClick();
        } else {
            lbRadioButton.performClick();
        }

        if(user.getGender().matches("m")){
            maleRadioButton.performClick();
        } else {
            femaleRadioButton.performClick();
        }
    }

    private void defaultRadioButtonInit(){
        maleRadioButton.performClick();
        cmRadioButton.performClick();
        kgRadioButton.performClick();
    }

    private void initEditTexts(){
        User user = getUser();
        dateEditText.setText(user.getBirthday());
        weightEditText.setText(String.valueOf(user.getWeight()));
        heightEditText.setText(String.valueOf(user.getHeight()));
    }

    private void setUpToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Ustawienia");
    }

    private void setUpEditTexts(){
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
        if (saveButton != null) {
            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(readValues()){
                        editFirstTime();
                        finishWithResult();
                    }
                }
            });
        }
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
                String date = String.format("%s-%s-%d", DateConverter.hasTwoLatters(dayOfMonth), DateConverter.hasTwoLatters(monthOfYear+1), year);
                dateEditText.setText(date);
            }
        },calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private boolean readValues(){
        double weight, height;
        String gender, lengthUnit, weightUnit, date;

        if(weightEditText.getText().toString().isEmpty()){
            weightEditText.setError(getString(R.string.please_give_weight));
            return false;
        } else {
            weight = Double.parseDouble(weightEditText.getText().toString());
        }
        if(heightEditText.getText().toString().isEmpty()){
            heightEditText.setError(getString(R.string.please_give_height));
            return false;
        } else {
            height = Double.parseDouble(heightEditText.getText().toString());
        }
        if(dateEditText.getText().toString().isEmpty()){
            dateEditText.setError(getString(R.string.pleas_give_birthday));
            return false;
        } else {
            date = dateEditText.getText().toString();
        }

        if(maleRadioButton.isChecked()){
            gender = "m";
        } else {
            gender = "f";
        }
        if(cmRadioButton.isChecked()){
            lengthUnit = "cm";
        } else {
            lengthUnit = "inch";
        }
        if(kgRadioButton.isChecked()){
            weightUnit = "kg";
        } else {
            weightUnit = "lb";
        }

        User user = new User(gender, date, height, weight);

        saveUnitPreferences(lengthUnit, weightUnit);
        addNewDimension(user.getWeight());
        addOrUpdateUser(user);

        return true;
    }

    private void addNewDimension(double weight){
        if(getUser().getWeight() != weight){
            Dimension dimension = new Dimension(weight, DateConverter.today(), 1);
            DimensionDB dimensionDB = new DimensionDB(this);
            dimensionDB.addDimension(dimension);
        }
    }

    private void addOrUpdateUser(User user){
        UserDB userDB = new UserDB(this);
        if(isUpdate){
            userDB.updateUser(user);
        } else {
            userDB.addUser(user);
        }
    }

    private User getUser(){
        UserDB userDB = new UserDB(this);
        return userDB.getUser();
    }

    private void editFirstTime(){
        preferences.setFirstTime(true);
    }

    private void saveUnitPreferences(String lengthUnit, String weightUnit){
        preferences.setLengthUnit(lengthUnit);
        preferences.setWeightUnit(weightUnit);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if(isUpdate){
                finishWithResult();
            } else {
                Toast.makeText(this, R.string.fill_all, Toast.LENGTH_LONG).show();
            }
        }
        return false;
    }
}