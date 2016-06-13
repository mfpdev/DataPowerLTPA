package com.sample.datapowerandroid;

import android.app.Application;

import com.worklight.wlclient.api.WLClient;

/**
 * Created by nathanh on 26/04/16.
 */
public class DataPowerApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        WLClient client = WLClient.createInstance(this);
        client.registerChallengeHandler(DataPowerChallengeHandler.getInstance());
    }
}
