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
    Intent myService;
    PlayerWotSingleton playerWotSingleton = PlayerWotSingleton.getInstance();


    //my git comment 3
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //Установка app_id
        playerWotSingleton.application_id=App.application_id;

        //Инициализация бродкаст ресивера
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.provider.Telephony.SMS_RECEIVED");
        filter.addAction(android.telephony.TelephonyManager.ACTION_PHONE_STATE_CHANGED);
        filter.addAction("SetMessageFromMyActivity"); //further more
        filter.addAction("your_action_strings"); //further more

        registerReceiver(receiver, filter);


        //настройки приложения




//Если есть токен, то запускаем сервис, иначе просим авторизоваться
        View buttonStartServiceView = findViewById(R.id.buttonStartService);
        try {
            if (!playerWotSingleton.access_token.equals("")) {
                buttonGetStatus(null);
            } else {
                //не важно что не тот вью. мы им не пользуемся
                buttonSignIn(buttonStartServiceView);
            }
        } catch (Exception ex) {

        }







    }


    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals("SetMessageFromMyActivity")) {
                // Инициализируем компонент
                TextView textView = findViewById(R.id.editText2);
                // задаём текст
              //  textView.setText(textView.getText() + intent.getStringExtra("logString"));
                textView.setText(intent.getStringExtra("logString"));

            } else if (action.equals(android.telephony.TelephonyManager.ACTION_PHONE_STATE_CHANGED)) {
                //action for phone state changed
            }
        }
    };


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


    public static boolean hasConnection(final Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiInfo;
        wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiInfo != null && wifiInfo.isConnected()) {
            return true;
        }

        wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (wifiInfo != null && wifiInfo.isConnected()) {
            return true;
        }
        wifiInfo = cm.getActiveNetworkInfo();
        if (wifiInfo != null && wifiInfo.isConnected()) {
            return true;
        }
        return false;
    }

    public static String doGet(String url)
            throws Exception {

        URL obj = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) obj.openConnection();

        //add reuqest header
        connection.setRequestMethod("GET");
        connection.setRequestProperty("User-Agent", "Mozilla/5.0");
        connection.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
        connection.setRequestProperty("Content-Type", "application/json");

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = bufferedReader.readLine()) != null) {
            response.append(inputLine);
        }
        bufferedReader.close();

//      print result


        return response.toString();
    }


    public String asyncWebGet(final String webRequest) {
        new AsyncTask<Void, String, String>() {


            @Override
            protected String doInBackground(Void... voids) {
                String s = "";
                try {
                    s = doGet(webRequest);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return s;
            }

            @Override
            protected void onPostExecute(final String result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        f = result;
                        // tvRez.setText(result);
                    }
                });
            }
        }.execute();

        return f;
    }


    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }


    public void buttonStartService(View view) throws Exception {


        if (hasConnection(this))
            Toast.makeText(this, "Сервис запущен", LENGTH_SHORT).show();
        else
            Toast.makeText(this, "Отсутсвует интернет соединение. Сервис запустится после появления интернета автоматически.", LENGTH_SHORT).show();

        myService = new Intent(this, MyServiceGetWotStatus.class);

        startService(myService);




    }


    public void ButtonStopClick(View view) throws Exception {

       // stopService(myService);


        playerWotSingleton.flagJobExecute=false;
        playerWotSingleton.serializePlayerWot(MainActivity.this);

        Toast.makeText(this, "Сервис остановлен", LENGTH_SHORT).show();



    }


    public void buttonSendBroadcast(View view) throws Exception {

        Intent intent = new Intent();
        intent.setAction("GetMessageFromMyActivity");
        intent.putExtra("MyMessage", "Отдай логи");
        intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        sendBroadcast(intent);

    }

    public void buttonSignIn(View view) throws Exception {

        Intent intent = new Intent(MainActivity.this, OpenIdActivity.class);
        startActivity(intent);


    }


    public void buttonHistory(View view) throws Exception {

    }


    public void buttonGetStatus(View view) throws Exception {

        TextView textViewStatusAcc = findViewById(R.id.textViewStatusAcc);

        SimpleDateFormat s = new SimpleDateFormat("yyyy.MM.dd 'at' HH:mm:ss");
        String timeString = s.format(new Date(Long.valueOf(playerWotSingleton.expires_at) * 1000));


        textViewStatusAcc.setText("Игрок: " + playerWotSingleton.nickname + " до: " + timeString);


    }

    public void startOfJob(View view){
        playerWotSingleton.flagJobExecute=true;
        playerWotSingleton.serializePlayerWot(MainActivity.this);
        WwdJob.scheduleJob();

    }


    public void getJobLog(View view){
        TextView textView = findViewById(R.id.editText2);
        // задаём текст
        //  textView.setText(textView.getText() + intent.getStringExtra("logString"));
        textView.setText(WwdJob.log);

    }



}
