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
public class RegisterActivity extends AppCompatActivity {
    private Button registerButton, linkToLoginScreenButton;
    private EditText usernameEditText, emailEditText, passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        usernameEditText = (EditText) findViewById(R.id.usernameEditText);
        emailEditText = (EditText) findViewById(R.id.emailEditText);
        passwordEditText = (EditText) findViewById(R.id.passwordEditText);

        registerButton = (Button) findViewById(R.id.registerButton);
        linkToLoginScreenButton = (Button) findViewById(R.id.linkToLoginScreenButton);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        linkToLoginScreenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToLoginScreen(false);
            }
        });
    }

    private void goToLoginScreen(boolean userIsAfterRegister){
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        intent.putExtra("userIsAfterRegister", userIsAfterRegister);
        Log.d("RegisterActivity", String.valueOf(userIsAfterRegister));
        startActivity(intent);
        this.finish();
    }

    public void signup() {
        Log.d("TAG", "Signup");

        String username = usernameEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        if (!validate(username, email, password)) {
            onSignupFailed();
            return;
        }

        connectServer(new User(email, password, username));
    }

    private void connectServer(final User user){
        RestClient.UserInterface service = RestClient.getClient();
        Call<ServerResponse> call = service.register(user);
        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Response<ServerResponse> response) {
                if (response.isSuccess()) {
                    ServerResponse serverResponse = response.body();
                    if(serverResponse.isSuccess()){
                        onSignupSuccess(user);
                        Log.d("RegisterActivity", "SUKCES!");
                    } else {
                        Toast.makeText(RegisterActivity.this, "Taki użytkownik już istnieje", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Log.d("RegisterActivity", response.message());
                    Toast.makeText(RegisterActivity.this, "Błąd połączenia z serwerem", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Throwable t) {
                Log.d("RegisterActivity", "onFailure " + t.getMessage());
            }
        });
    }


    public void onSignupSuccess(User user) {
        addUserToDatabase(user);
        goToLoginScreen(true);
    }

    private void addUserToDatabase(User user){
        UserDB userDB = new UserDB(this);
        userDB.addUser(user);
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), getString(R.string.register_failed), Toast.LENGTH_LONG).show();
    }

    public boolean validate(String username, String email, String password) {
        boolean valid = true;

        if (username.isEmpty() || username.length() < 3) {
            usernameEditText.setError(getString(R.string.valid_username));
            valid = false;
        } else {
            usernameEditText.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditText.setError(getString(R.string.valid_email));
            valid = false;
        } else {
            emailEditText.setError(null);
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