package com.sample.datapowerandroid;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {
    private EditText usernameField, passwordField;
    private Button loginButton, cancelButton;
    private static final String TAG = "LoginActivity";
    private BroadcastReceiver loginSuccessReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameField = (EditText)findViewById(R.id.username);
        passwordField = (EditText)findViewById(R.id.password);
        loginButton = (Button)findViewById(R.id.login);
        cancelButton = (Button)findViewById(R.id.cancel);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameField.getText().toString();
                String password = passwordField.getText().toString();
                DataPowerChallengeHandler.getInstance().submitLogin(username,password);
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataPowerChallengeHandler.getInstance().cancel();
            }
        });


        loginSuccessReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d(TAG, "loginSuccessReceiver");
                finish();
            }
        };

    }

    @Override
    protected void onStart() {
        super.onStart();
        LocalBroadcastManager.getInstance(this).registerReceiver(loginSuccessReceiver, new IntentFilter(Constants.ACTION_LOGIN_SUCCESS));
    }

    @Override
    protected void onStop() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(loginSuccessReceiver);
        super.onStop();
    }

    @Override
    public void onBackPressed() {
        DataPowerChallengeHandler.getInstance().cancel();
    }
}
