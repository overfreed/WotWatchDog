package com.WWD.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.Exception;
import java.text.SimpleDateFormat;
import java.util.Date;

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
        try{ buttonGetStatus(null);}catch (Exception ex){};
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

        Integer currentTimeStamp = Integer.parseInt(String.valueOf(System.currentTimeMillis() / 1000));
        if (Integer.parseInt(playerWotSingleton.expires_at) < currentTimeStamp)
            jobStatusString="\n Срок истек, выполните вход!";


        textViewStatusAcc.setText("Игрок: " + playerWotSingleton.nickname + " до: " + timeString+jobStatusString);
    }


    public void startOfJob(View view){
        playerWotSingleton.flagJobExecute=true;
        playerWotSingleton.serializePlayerWot(MainActivity.this);
        playerWotSingleton.bannedResources.clear();
        WwdJob.scheduleJob();
        Toast.makeText(this, "Задача запущена", LENGTH_SHORT).show();
       try{ buttonGetStatus(null);}catch (Exception ex){};
    }

    public void ButtonStopClick(View view) throws Exception {
        playerWotSingleton.flagJobExecute=false;
        playerWotSingleton.serializePlayerWot(MainActivity.this);
        playerWotSingleton.bannedResources.clear();
        Toast.makeText(this, "Задача остановлена", LENGTH_SHORT).show();
        try{ buttonGetStatus(null);}catch (Exception ex){};;
    }







    public void getJobLog(View view){
        TextView textView = findViewById(R.id.editText2);
        textView.setText(WwdJob.log);

    }



}
