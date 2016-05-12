package com.gymassistant.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.gymassistant.Database.UserDB;
import com.gymassistant.MainActivity;
import com.gymassistant.Models.ServerResponse;
import com.gymassistant.Models.User;
import com.gymassistant.R;
import com.gymassistant.Rest.RestClient;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;

/**
 * Created by KamilH on 2016-03-23.
 */
public class LoginActivity extends AppCompatActivity {
    private Button loginButton, linkToRegisterScreenButton;
    private EditText usernameEditText, passwordEditText;
    private boolean userIsAfterRegister = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        readParameter();

        usernameEditText = (EditText) findViewById(R.id.usernameEditText);
        passwordEditText = (EditText) findViewById(R.id.passwordEditText);

        loginButton = (Button) findViewById(R.id.loginButton);
        linkToRegisterScreenButton = (Button) findViewById(R.id.linkToRegisterScreenButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        linkToRegisterScreenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToRegisterScreen();
            }
        });
    }

    private void readParameter(){
        if(getIntent().hasExtra("userIsAfterRegister")) {
            userIsAfterRegister = getIntent().getBooleanExtra("userIsAfterRegister", userIsAfterRegister);
            Log.d("LoginActivity", String.valueOf(userIsAfterRegister));
        }
    }

    private void goToRegisterScreen(){
        Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
        startActivity(intent);
        this.finish();
    }

    private void goToNextActivity(){
        if(userIsAfterRegister) {
            Intent intent = new Intent(getApplicationContext(), FillProfileActivity.class);
            startActivity(intent);
            this.finish();

        } else {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            this.finish();
        }
    }

    public void login() {
        Log.d("TAG", "Login");

        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        if (!validate(username, password)) {
            onLoginFailed();
            return;
        }
        connectServer(new User(username, password));
    }

    private void connectServer(User user){
        RestClient.UserInterface service = RestClient.getClient();
        Call<ServerResponse> call = service.login(user);
        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Response<ServerResponse> response) {
                if (response.isSuccess()) {
                    ServerResponse serverResponse = response.body();
                    if(serverResponse.isSuccess()){
                        onLoginSuccess(serverResponse.getId());
                    } else {
                        Toast.makeText(LoginActivity.this, "Błąd logowania", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Błąd połączenia z serwerem", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Throwable t) {
                Log.d("LoginActivity", "onFailure " + t.getMessage());
            }
        });
    }

    public void onLoginSuccess(int userId) {
        saveOrUpdateUser(userId);
        goToNextActivity();
    }

    private void saveOrUpdateUser(int userId){
        if(userIsAfterRegister){
            updateUserInDatabase(userId);
        } else {
            getUserInfo(userId);
        }
    }

    private void addUserToDatabase(User user){
        UserDB userDB = new UserDB(this);
        userDB.addUser(user);
    }

    private void updateUserInDatabase(int userId){
        UserDB userDB = new UserDB(this);
        User user = userDB.getUser();
        user.setUserId(userId);
        userDB.updateUser(user);
    }

    private void getUserInfo(int userId){
        RestClient.UserInterface service = RestClient.getClient();
        Call<User> call = service.getUser(userId);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Response<User> response) {
                if (response.isSuccess()) {
                    User user = response.body();
                    addUserToDatabase(user);
                } else {
                    Log.d("LoginActivity", "Nie udało się pobrać danych o użytkowniku");
                }
            }
            @Override
            public void onFailure(Throwable t) {
                Log.d("LoginActivity", "onFailure " + t.getMessage());
            }
        });
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), getString(R.string.login_failed), Toast.LENGTH_LONG).show();
    }

    public boolean validate(String email, String password) {
        boolean valid = true;


        if (email.isEmpty()) {
            usernameEditText.setError(getString(R.string.valid_username));
            valid = false;
        } else {
            usernameEditText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4) {
            passwordEditText.setError(getString(R.string.valid_password));
            valid = false;
        } else {
            passwordEditText.setError(null);
        }

        return valid;
    }
}
