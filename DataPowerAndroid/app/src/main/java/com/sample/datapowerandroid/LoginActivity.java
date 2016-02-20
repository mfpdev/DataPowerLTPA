package com.sample.datapowerandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.worklight.wlclient.api.WLClient;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameField, passwordField;
    private Button loginButton, cancelButton;
    private static WLClient client;
    private static final String TAG = "LoginActivity";
    private static LoginActivity _this;
    private Intent result;

    public static final String Back = "back";
    public static final String UserNameExtra = "username";
    public static final String PasswordExtra = "password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _this = this;
        client = WLClient.getInstance();
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        usernameField = (EditText)findViewById(R.id.username);
        passwordField = (EditText)findViewById(R.id.password);
        loginButton = (Button)findViewById(R.id.login);
        cancelButton = (Button)findViewById(R.id.cancel);
        result = new Intent();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameField.getText().toString();
                String password = passwordField.getText().toString();

                result.putExtra(UserNameExtra, username);
                result.putExtra(PasswordExtra, password);
                result.putExtra(Back, false);
                setResult(RESULT_OK, result);
                finish();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result.putExtra(Back, true);
                setResult(RESULT_OK, result);
                finish();
            }
        });
    }

    public void onBackPressed() {
        result.putExtra(Back, true);
        setResult(RESULT_OK, result);
        finish();
    }

}
