package com.sample.datapowerandroid;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.worklight.wlclient.WLRequestListener;
import com.worklight.wlclient.api.WLClient;
import com.worklight.wlclient.api.WLFailResponse;
import com.worklight.wlclient.api.WLProcedureInvocationData;
import com.worklight.wlclient.api.WLResourceRequest;
import com.worklight.wlclient.api.WLResponse;
import com.worklight.wlclient.api.WLResponseListener;

import org.json.JSONException;

import java.net.URI;
import java.net.URISyntaxException;

public class MainActivity extends AppCompatActivity {

    private Button buttonGetData = null;
    private Button buttonLogout = null;
    private static MainActivity _this;
    private static final String TAG = "MainActivity";
    private DataPowerChallengeHandler challengeHandler;
    private boolean useOAuth = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        _this = this;

        //Create the WLClient instance
        WLClient.createInstance(_this);

        //Workaround to support DataPower redirects
        WLClient.getInstance().setAllowHTTPClientCircularRedirect(true);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        buttonGetData = (Button)findViewById(R.id.getData);
        buttonLogout = (Button)findViewById(R.id.logout);

        buttonGetData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(useOAuth){
                    try {
                        URI adapterPath = new URI("/adapters/Protected/getSecretData");
                        WLResourceRequest request = new WLResourceRequest(adapterPath, WLResourceRequest.GET);
                        request.send(new WLResponseListener() {
                            @Override
                            public void onSuccess(WLResponse wlResponse) {
                                try {
                                    _this.showAlert("Success",wlResponse.getResponseJSON().getString("data"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFailure(WLFailResponse wlFailResponse) {
                                _this.showAlert("Failure",wlFailResponse.getErrorMsg());
                            }
                        });
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }
                }
                else{
                    WLProcedureInvocationData invocationData = new WLProcedureInvocationData("Protected","getSecretData");
                    WLClient.getInstance().invokeProcedure(invocationData, new WLResponseListener() {
                        @Override
                        public void onSuccess(WLResponse wlResponse) {
                            try {
                                _this.showAlert("Success",wlResponse.getResponseJSON().getString("data"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(WLFailResponse wlFailResponse) {
                            _this.showAlert("Failure",wlFailResponse.getErrorMsg());
                        }
                    });
                }

            }
        });

        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WLClient.getInstance().logout("WASLTPARealm", new WLRequestListener() {
                    @Override
                    public void onSuccess(WLResponse wlResponse) {
                        _this.showAlert("Success","Logged out");
                    }

                    @Override
                    public void onFailure(WLFailResponse wlFailResponse) {
                        _this.showAlert("Error","Failed to log out");
                    }
                });
            }
        });

        challengeHandler = new DataPowerChallengeHandler(this);
        WLClient.getInstance().registerChallengeHandler(challengeHandler);

    }

    public void showAlert(final String title, final String message){
        MainActivity.this.runOnUiThread(new Runnable() {
            public void run() {
                new AlertDialog.Builder(_this)
                        .setTitle(title)
                        .setMessage(message)
                        .setPositiveButton("OK", null)
                        .show();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        boolean back = data.getBooleanExtra(LoginActivity.Back, true);
        String username = data.getStringExtra(LoginActivity.UserNameExtra);
        String password = data.getStringExtra(LoginActivity.PasswordExtra);
        challengeHandler.submitLogin(resultCode, username, password, back);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
