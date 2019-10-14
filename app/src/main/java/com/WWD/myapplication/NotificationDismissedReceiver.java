package com.WWD.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class NotificationDismissedReceiver extends BroadcastReceiver {

    PlayerWotSingleton playerWotSingleton = PlayerWotSingleton.getInstance();

    @Override
    public void onReceive(Context context, Intent intent) {
        int notificationId = intent.getExtras().getInt("notificationId");
        String timeStamp = intent.getExtras().getString("timeStamp");
        playerWotSingleton.resourseBanned(timeStamp);
    }
}
