package com.example.myapplication;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import com.evernote.android.job.JobManager;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.lang.Exception;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static android.widget.Toast.LENGTH_SHORT;

public class MainActivity extends AppCompatActivity {
    static String f;
    PlayerWotSingleton playerWotSingleton = PlayerWotSingleton.getInstance();


    //my git comment 3
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//Если есть токен, то запускаем сервис, иначе просим авторизоваться
        try {
            if (!playerWotSingleton.access_token.equals("")) {
                buttonGetStatus(null);
            } else {
                //не важно что не тот вью. мы им не пользуемся
                buttonSignIn(null);
            }
        } catch (Exception ex) {

        }
    }



    public void buttonSignIn(View view) throws Exception {
        Intent intent = new Intent(MainActivity.this, OpenIdActivity.class);
        startActivity(intent);
    }


    public void buttonGetStatus(View view) throws Exception {
        TextView textViewStatusAcc = findViewById(R.id.textViewStatusAcc);

        SimpleDateFormat s = new SimpleDateFormat("yyyy.MM.dd 'at' HH:mm:ss");
        String timeString = s.format(new Date(Long.valueOf(playerWotSingleton.expires_at) * 1000));

        String jobStatusString="";
      if (playerWotSingleton.flagJobExecute)
          jobStatusString="\n Задача работает";
      else
          jobStatusString="\n Задача не активна";

        textViewStatusAcc.setText("Игрок: " + playerWotSingleton.nickname + " до: " + timeString+jobStatusString);
    }


    public void startOfJob(View view){
        playerWotSingleton.flagJobExecute=true;
        playerWotSingleton.serializePlayerWot(MainActivity.this);
        playerWotSingleton.bannedResources.clear();
        WwdJob.scheduleJob();
        Toast.makeText(this, "Задача запущена", LENGTH_SHORT).show();
    }

    public void ButtonStopClick(View view) throws Exception {
        playerWotSingleton.flagJobExecute=false;
        playerWotSingleton.serializePlayerWot(MainActivity.this);
        playerWotSingleton.bannedResources.clear();
        Toast.makeText(this, "Задача остановлена", LENGTH_SHORT).show();
    }







    public void getJobLog(View view){
        TextView textView = findViewById(R.id.editText2);
        textView.setText(WwdJob.log);

    }



}
