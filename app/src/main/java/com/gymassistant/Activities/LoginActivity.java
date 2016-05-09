package com.gymassistant.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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

    private void goToRegisterScreen(){
        Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
        startActivity(intent);
        this.finish();
    }

    private void goToMainActivity(){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        this.finish();
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
        Call<ServerResponse> call = service.register(user);
        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Response<ServerResponse> response) {
                if (response.isSuccess()) {
                    ServerResponse serverResponse = response.body();
                    if(serverResponse.isSuccess()){
                        onLoginSuccess();
                        Log.d("LoginActivity", "SUKCES!");
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

    public void onLoginSuccess() {
        goToMainActivity();
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
