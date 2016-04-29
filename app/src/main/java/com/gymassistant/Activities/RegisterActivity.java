package com.gymassistant.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.gymassistant.R;

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
                goToLoginScreen();
            }
        });
    }

    private void goToLoginScreen(){
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
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

        final ProgressDialog progressDialog = new ProgressDialog(RegisterActivity.this, R.style.AppTheme);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(getString(R.string.registration));
        progressDialog.show();

        // TODO: Implement your own signup logic here.

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        onSignupSuccess();
                        progressDialog.dismiss();
                    }
                }, 3000);
    }


    public void onSignupSuccess() {
        goToLoginScreen();
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