package com.WWD.myapplication;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.evernote.android.job.JobManager;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;


public class App extends Application {
public static final String  nameOfFilePlayerWotObj  = "PlayerJSON";
public static final String  application_id          = "c5b28492a39dbf3654412f96f3c42e1f";
private PlayerWotSingleton  playerWotSingleton      = PlayerWotSingleton.getInstance();


    @Override
    public void onCreate() {
        super.onCreate();

        unserializePlayerWotSingleton(App.this,playerWotSingleton);
        //inicialize 'playerWotSingleton' from file 'nameOfFilePlayerWotObj'



        //start of job creator
        JobManager.create(this).addJobCreator(new WwdJobCreator());
    }



public static  void  unserializePlayerWotSingleton(Context context,PlayerWotSingleton playerWotSingleton){

    try {
        Gson gson = new Gson();
        // открываем поток для чтения
        BufferedReader br = new BufferedReader(new InputStreamReader(context.openFileInput(nameOfFilePlayerWotObj)));
        String str = "";
        // читаем содержимое
        while ((str = br.readLine()) != null) {
            try {
                JSONObject jsonRoot = new JSONObject(str);

                playerWotSingleton.status = jsonRoot.getString("status");
                playerWotSingleton.access_token = jsonRoot.getString("access_token");
                playerWotSingleton.nickname = jsonRoot.getString("nickname");
                playerWotSingleton.account_id = jsonRoot.getString("account_id");
                playerWotSingleton.expires_at = jsonRoot.getString("expires_at");
                playerWotSingleton.flagJobExecute = jsonRoot.getBoolean("flagJobExecute");

            } catch (Exception ex) {
            }

        }
    } catch (FileNotFoundException e) {
        e.printStackTrace();
    } catch (IOException e) {
        e.printStackTrace();
    }
}




}

