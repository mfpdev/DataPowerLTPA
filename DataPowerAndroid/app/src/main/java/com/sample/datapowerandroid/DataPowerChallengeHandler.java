package com.sample.datapowerandroid;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.worklight.wlclient.api.WLClient;
import com.worklight.wlclient.api.WLFailResponse;
import com.worklight.wlclient.api.WLResponse;
import com.worklight.wlclient.api.challengehandler.GatewayChallengeHandler;

import java.util.HashMap;

/**
 * Created by nathanh on 26/04/16.
 */
public class DataPowerChallengeHandler extends GatewayChallengeHandler{
    private static String securityCheckName = "LtpaBasedSSO";
    private static final String TAG = "ChallengeHandler";
    private static DataPowerChallengeHandler ourInstance = new DataPowerChallengeHandler();
    private LocalBroadcastManager broadcastManager;

    public static DataPowerChallengeHandler getInstance() {
        return ourInstance;
    }

    private DataPowerChallengeHandler() {
        super(securityCheckName);
        broadcastManager = LocalBroadcastManager.getInstance(WLClient.getInstance().getContext());
    }

    @Override
    public boolean canHandleResponse(WLResponse wlResponse) {
        Log.d(TAG,"isCustomResponse");
        if (wlResponse == null
                || wlResponse.getResponseText() == null ||
                wlResponse.getResponseText().indexOf("j_security_check") == -1) {
            return false;
        }
        return true;
    }

    @Override
    public void handleChallenge(WLResponse wlResponse) {
        Log.d(TAG,"handleChallenge");
        Intent intent = new Intent();
        intent.setAction(Constants.ACTION_LOGIN_REQUIRED);
        broadcastManager.sendBroadcast(intent);
    }

    @Override
    public void onSuccess(WLResponse wlResponse) {
        Log.d(TAG,"onSuccess");
        Intent intent = new Intent();
        intent.setAction(Constants.ACTION_LOGIN_SUCCESS);
        broadcastManager.sendBroadcast(intent);
        submitSuccess(wlResponse);
    }

    @Override
    public void onFailure(WLFailResponse wlFailResponse) {
        Log.d(TAG,"onFailure");
        Intent intent = new Intent();
        intent.setAction(Constants.ACTION_LOGIN_FAILURE);
        broadcastManager.sendBroadcast(intent);
        Log.d(securityCheckName, "handleFailure");
        super.cancel();
    }

    public void submitLogin(String userName, String password){
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("j_username", userName);
        params.put("j_password", password);
        submitLoginForm("/j_security_check", params, null, 0, "post");

    }

    public void cancel(){
        super.cancel();
        Intent intent = new Intent();
        intent.setAction(Constants.ACTION_LOGIN_SUCCESS);
        broadcastManager.sendBroadcast(intent);
    }
}
