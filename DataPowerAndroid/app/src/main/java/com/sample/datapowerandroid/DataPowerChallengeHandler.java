package com.sample.datapowerandroid;

import android.content.Intent;

import com.worklight.wlclient.api.WLFailResponse;
import com.worklight.wlclient.api.WLResponse;
import com.worklight.wlclient.api.challengehandler.ChallengeHandler;

import java.util.HashMap;

/**
 * Created by nathanh on 26/01/16.
 */
public class DataPowerChallengeHandler extends ChallengeHandler {


    private MainActivity parentActivity;
    private WLResponse cachedResponse;


    public DataPowerChallengeHandler(MainActivity activity) {
        super("WASLTPARealm");
        parentActivity = activity;
    }

    @Override
    public void onFailure(WLFailResponse response) {
        submitFailure(response);
    }

    @Override
    public void onSuccess(WLResponse response) {
        submitSuccess(response);
    }

    @Override
    public boolean isCustomResponse(WLResponse response) {
        if (response == null
                || response.getResponseText() == null ||
                response.getResponseText().indexOf("j_security_check") == -1) {
            return false;
        }
        return true;
    }

    @Override
    public void handleChallenge(WLResponse response){
        if (!isCustomResponse(response)) {
            submitSuccess(response);
        } else {
            cachedResponse = response;
            Intent login = new Intent(parentActivity, LoginActivity.class);
            parentActivity.startActivityForResult(login, 1);
        }

    }


    public void submitLogin(int resultCode, String userName, String password, boolean back){
        if (resultCode != MainActivity.RESULT_OK || back) {
            submitFailure(cachedResponse);
        } else {
            HashMap<String, String> params = new HashMap<String, String>();
            params.put("j_username", userName);
            params.put("j_password", password);
            submitLoginForm("/j_security_check", params, null, 0, "post");
        }
    }
}
