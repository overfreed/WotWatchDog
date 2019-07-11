package com.example.myapplication;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Time;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.Future;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ScheduledExecutorService;








public class MyServiceGetWotStatus extends Service {

    public String logString="";

    public MyServiceGetWotStatus() { }
    ScheduledFuture sF;
    int mStartMode;       // indicates how to behave if the service is killed
    IBinder mBinder;      // interface for clients that bind
    boolean mAllowRebind; // indicates whether onRebind should be used
    Handler handler;
    static String f;
    int CountOfPeriod=300;
    int MaxAttempt=100;
    int delayMS=1600;

    PlayerWotSingleton playerWotSingleton=PlayerWotSingleton.getInstance();


    private void runOnUiThread(Runnable runnable) {
        handler.post(runnable);
    }



   private void GetInfo(){}





private String GetOnServer(){


 new AsyncTask<Void, String, String>() {


        @Override
        protected String doInBackground(Void... voids) {
            String s = "";
            try {

                s = doGet("https://api.worldoftanks.ru/wot/stronghold/clanreserves/?application_id="+playerWotSingleton.application_id+"&access_token="+playerWotSingleton.access_token);
               // s="{\"status\":\"ok\",\"meta\":{\"count\":8},\"data\":[{\"name\":\"Дополнительный инструктаж\",\"bonus_type\":\"к опыту экипажа\",\"disposable\":false,\"in_stock\":[{\"status\":\"ready_to_activate\",\"action_time\":3600,\"active_till\":null,\"level\":9,\"activated_at\":null,\"amount\":3,\"bonus_values\":[{\"value\":2.0,\"battle_type\":\"В клановых и турнирных боях\"},{\"value\":1.5,\"battle_type\":\"В случайных боях\"}],\"x_level_only\":false},{\"status\":\"ready_to_activate\",\"action_time\":7200,\"active_till\":null,\"level\":10,\"activated_at\":null,\"amount\":1,\"bonus_values\":[{\"value\":2.0,\"battle_type\":\"В клановых и турнирных боях\"},{\"value\":1.5,\"battle_type\":\"В случайных боях\"}],\"x_level_only\":false}],\"type\":\"ADDITIONAL_BRIEFING\",\"icon\":\"https:\\/\\/wgsh-wotru.wargaming.net\\/cdn_static\\/reserves\\/additional-briefing.png\"},{\"name\":\"Авиаудар\",\"bonus_type\":\"к промресурсу\",\"disposable\":true,\"in_stock\":[{\"status\":null,\"action_time\":null,\"active_till\":null,\"level\":3,\"activated_at\":null,\"amount\":2,\"bonus_values\":[{\"value\":0.15,\"battle_type\":\"В Вылазках и Битвах за Укрепрайон\"}],\"x_level_only\":false},{\"status\":null,\"action_time\":null,\"active_till\":null,\"level\":5,\"activated_at\":null,\"amount\":2,\"bonus_values\":[{\"value\":0.3,\"battle_type\":\"В Вылазках и Битвах за Укрепрайон\"}],\"x_level_only\":false},{\"status\":null,\"action_time\":null,\"active_till\":null,\"level\":6,\"activated_at\":null,\"amount\":3,\"bonus_values\":[{\"value\":0.35,\"battle_type\":\"В Вылазках и Битвах за Укрепрайон\"}],\"x_level_only\":false},{\"status\":null,\"action_time\":null,\"active_till\":null,\"level\":7,\"activated_at\":null,\"amount\":3,\"bonus_values\":[{\"value\":0.4,\"battle_type\":\"В Вылазках и Битвах за Укрепрайон\"}],\"x_level_only\":false},{\"status\":null,\"action_time\":null,\"active_till\":null,\"level\":8,\"activated_at\":null,\"amount\":3,\"bonus_values\":[{\"value\":0.5,\"battle_type\":\"В Вылазках и Битвах за Укрепрайон\"}],\"x_level_only\":false},{\"status\":null,\"action_time\":null,\"active_till\":null,\"level\":9,\"activated_at\":null,\"amount\":3,\"bonus_values\":[{\"value\":0.55,\"battle_type\":\"В Вылазках и Битвах за Укрепрайон\"}],\"x_level_only\":false},{\"status\":null,\"action_time\":null,\"active_till\":null,\"level\":10,\"activated_at\":null,\"amount\":1,\"bonus_values\":[{\"value\":0.6,\"battle_type\":\"В Вылазках и Битвах за Укрепрайон\"}],\"x_level_only\":false}],\"type\":\"AIRSTRIKE\",\"icon\":\"https:\\/\\/wgsh-wotru.wargaming.net\\/cdn_static\\/reserves\\/airstrike.png\"},{\"name\":\"Артобстрел\",\"bonus_type\":\"к промресурсу\",\"disposable\":true,\"in_stock\":[{\"status\":null,\"action_time\":null,\"active_till\":null,\"level\":5,\"activated_at\":null,\"amount\":2,\"bonus_values\":[{\"value\":0.3,\"battle_type\":\"В Вылазках и Битвах за Укрепрайон\"}],\"x_level_only\":false},{\"status\":null,\"action_time\":null,\"active_till\":null,\"level\":6,\"activated_at\":null,\"amount\":3,\"bonus_values\":[{\"value\":0.35,\"battle_type\":\"В Вылазках и Битвах за Укрепрайон\"}],\"x_level_only\":false},{\"status\":null,\"action_time\":null,\"active_till\":null,\"level\":7,\"activated_at\":null,\"amount\":3,\"bonus_values\":[{\"value\":0.4,\"battle_type\":\"В Вылазках и Битвах за Укрепрайон\"}],\"x_level_only\":false},{\"status\":null,\"action_time\":null,\"active_till\":null,\"level\":8,\"activated_at\":null,\"amount\":3,\"bonus_values\":[{\"value\":0.5,\"battle_type\":\"В Вылазках и Битвах за Укрепрайон\"}],\"x_level_only\":false},{\"status\":null,\"action_time\":null,\"active_till\":null,\"level\":9,\"activated_at\":null,\"amount\":3,\"bonus_values\":[{\"value\":0.55,\"battle_type\":\"В Вылазках и Битвах за Укрепрайон\"}],\"x_level_only\":false},{\"status\":null,\"action_time\":null,\"active_till\":null,\"level\":10,\"activated_at\":null,\"amount\":1,\"bonus_values\":[{\"value\":0.6,\"battle_type\":\"В Вылазках и Битвах за Укрепрайон\"}],\"x_level_only\":false}],\"type\":\"ARTILLERY_STRIKE\",\"icon\":\"https:\\/\\/wgsh-wotru.wargaming.net\\/cdn_static\\/reserves\\/artillery-strike.png\"},{\"name\":\"Боевые выплаты\",\"bonus_type\":\"к кредитам\",\"disposable\":false,\"in_stock\":[{\"status\":\"ready_to_activate\",\"action_time\":7200,\"active_till\":null,\"level\":6,\"activated_at\":null,\"amount\":1,\"bonus_values\":[{\"value\":0.3,\"battle_type\":\"В клановых и турнирных боях\"},{\"value\":0.15,\"battle_type\":\"В случайных боях\"}],\"x_level_only\":false},{\"status\":\"ready_to_activate\",\"action_time\":7200,\"active_till\":null,\"level\":9,\"activated_at\":null,\"amount\":2,\"bonus_values\":[{\"value\":0.5,\"battle_type\":\"В клановых и турнирных боях\"},{\"value\":0.25,\"battle_type\":\"В случайных боях\"}],\"x_level_only\":false},{\"status\":\"ready_to_activate\",\"action_time\":7200,\"active_till\":null,\"level\":10,\"activated_at\":null,\"amount\":1,\"bonus_values\":[{\"value\":0.75,\"battle_type\":\"В клановых и турнирных боях\"},{\"value\":0.3,\"battle_type\":\"В случайных боях\"}],\"x_level_only\":false}],\"type\":\"BATTLE_PAYMENTS\",\"icon\":\"https:\\/\\/wgsh-wotru.wargaming.net\\/cdn_static\\/reserves\\/battle-payments.png\"},{\"name\":\"Большегрузный транспорт\",\"bonus_type\":\"к промресурсу\",\"disposable\":true,\"in_stock\":[{\"status\":null,\"action_time\":null,\"active_till\":null,\"level\":5,\"activated_at\":null,\"amount\":2,\"bonus_values\":[{\"value\":0.3,\"battle_type\":\"В Вылазках и Битвах за Укрепрайон\"}],\"x_level_only\":false},{\"status\":null,\"action_time\":null,\"active_till\":null,\"level\":6,\"activated_at\":null,\"amount\":3,\"bonus_values\":[{\"value\":0.35,\"battle_type\":\"В Вылазках и Битвах за Укрепрайон\"}],\"x_level_only\":false},{\"status\":null,\"action_time\":null,\"active_till\":null,\"level\":7,\"activated_at\":null,\"amount\":3,\"bonus_values\":[{\"value\":0.4,\"battle_type\":\"В Вылазках и Битвах за Укрепрайон\"}],\"x_level_only\":false},{\"status\":null,\"action_time\":null,\"active_till\":null,\"level\":8,\"activated_at\":null,\"amount\":3,\"bonus_values\":[{\"value\":0.5,\"battle_type\":\"В Вылазках и Битвах за Укрепрайон\"}],\"x_level_only\":false},{\"status\":null,\"action_time\":null,\"active_till\":null,\"level\":9,\"activated_at\":null,\"amount\":3,\"bonus_values\":[{\"value\":0.55,\"battle_type\":\"В Вылазках и Битвах за Укрепрайон\"}],\"x_level_only\":false},{\"status\":null,\"action_time\":null,\"active_till\":null,\"level\":10,\"activated_at\":null,\"amount\":1,\"bonus_values\":[{\"value\":0.6,\"battle_type\":\"В Вылазках и Битвах за Укрепрайон\"}],\"x_level_only\":false}],\"type\":\"HIGH_CAPACITY_TRANSPORT\",\"icon\":\"https:\\/\\/wgsh-wotru.wargaming.net\\/cdn_static\\/reserves\\/high-capacity-transport.png\"},{\"name\":\"Военные учения\",\"bonus_type\":\"к свободному опыту\",\"disposable\":false,\"in_stock\":[{\"status\":\"ready_to_activate\",\"action_time\":7200,\"active_till\":null,\"level\":4,\"activated_at\":null,\"amount\":1,\"bonus_values\":[{\"value\":1.0,\"battle_type\":\"Во всех боях\"}],\"x_level_only\":false},{\"status\":\"ready_to_activate\",\"action_time\":3600,\"active_till\":null,\"level\":9,\"activated_at\":null,\"amount\":2,\"bonus_values\":[{\"value\":2.5,\"battle_type\":\"Во всех боях\"}],\"x_level_only\":false},{\"status\":\"ready_to_activate\",\"action_time\":7200,\"active_till\":null,\"level\":10,\"activated_at\":null,\"amount\":1,\"bonus_values\":[{\"value\":2.5,\"battle_type\":\"Во всех боях\"}],\"x_level_only\":false}],\"type\":\"MILITARY_MANEUVERS\",\"icon\":\"https:\\/\\/wgsh-wotru.wargaming.net\\/cdn_static\\/reserves\\/military-maneuvers.png\"},{\"name\":\"Реквизиция\",\"bonus_type\":\"к промресурсу\",\"disposable\":true,\"in_stock\":[{\"status\":null,\"action_time\":null,\"active_till\":null,\"level\":5,\"activated_at\":null,\"amount\":2,\"bonus_values\":[{\"value\":0.3,\"battle_type\":\"В Битвах за Укрепрайон\"}],\"x_level_only\":false},{\"status\":null,\"action_time\":null,\"active_till\":null,\"level\":6,\"activated_at\":null,\"amount\":3,\"bonus_values\":[{\"value\":0.35,\"battle_type\":\"В Битвах за Укрепрайон\"}],\"x_level_only\":false},{\"status\":null,\"action_time\":null,\"active_till\":null,\"level\":7,\"activated_at\":null,\"amount\":3,\"bonus_values\":[{\"value\":0.4,\"battle_type\":\"В Битвах за Укрепрайон\"}],\"x_level_only\":false},{\"status\":null,\"action_time\":null,\"active_till\":null,\"level\":8,\"activated_at\":null,\"amount\":3,\"bonus_values\":[{\"value\":0.5,\"battle_type\":\"В Битвах за Укрепрайон\"}],\"x_level_only\":false},{\"status\":null,\"action_time\":null,\"active_till\":null,\"level\":9,\"activated_at\":null,\"amount\":3,\"bonus_values\":[{\"value\":0.55,\"battle_type\":\"В Битвах за Укрепрайон\"}],\"x_level_only\":false},{\"status\":null,\"action_time\":null,\"active_till\":null,\"level\":10,\"activated_at\":null,\"amount\":1,\"bonus_values\":[{\"value\":0.6,\"battle_type\":\"В Битвах за Укрепрайон\"}],\"x_level_only\":false}],\"type\":\"REQUISITION\",\"icon\":\"https:\\/\\/wgsh-wotru.wargaming.net\\/cdn_static\\/reserves\\/requisition.png\"},{\"name\":\"Тактическая подготовка\",\"bonus_type\":\"к боевому опыту\",\"disposable\":false,\"in_stock\":[{\"status\":\"ready_to_activate\",\"action_time\":7200,\"active_till\":null,\"level\":6,\"activated_at\":null,\"amount\":1,\"bonus_values\":[{\"value\":0.2,\"battle_type\":\"Во всех боях\"}],\"x_level_only\":false},{\"status\":\"ready_to_activate\",\"action_time\":3600,\"active_till\":null,\"level\":9,\"activated_at\":null,\"amount\":3,\"bonus_values\":[{\"value\":0.5,\"battle_type\":\"Во всех боях\"}],\"x_level_only\":false},{\"status\":\"ready_to_activate\",\"action_time\":7200,\"active_till\":null,\"level\":10,\"activated_at\":null,\"amount\":1,\"bonus_values\":[{\"value\":0.5,\"battle_type\":\"Во всех боях\"}],\"x_level_only\":false}],\"type\":\"TACTICAL_TRAINING\",\"icon\":\"https:\\/\\/wgsh-wotru.wargaming.net\\/cdn_static\\/reserves\\/tactical-training.png\"}]}\"";

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

//f="{\"status\":\"ok\",\"meta\":{\"count\":8},\"data\":[{\"name\":\"Дополнительный инструктаж\",\"bonus_type\":\"к опыту экипажа\",\"disposable\":false,\"in_stock\":[{\"status\":\"ready_to_activate\",\"action_time\":3600,\"active_till\":null,\"level\":9,\"activated_at\":null,\"amount\":3,\"bonus_values\":[{\"value\":2.0,\"battle_type\":\"В клановых и турнирных боях\"},{\"value\":1.5,\"battle_type\":\"В случайных боях\"}],\"x_level_only\":false},{\"status\":\"ready_to_activate\",\"action_time\":7200,\"active_till\":null,\"level\":10,\"activated_at\":null,\"amount\":1,\"bonus_values\":[{\"value\":2.0,\"battle_type\":\"В клановых и турнирных боях\"},{\"value\":1.5,\"battle_type\":\"В случайных боях\"}],\"x_level_only\":false}],\"type\":\"ADDITIONAL_BRIEFING\",\"icon\":\"https:\\/\\/wgsh-wotru-static.wgcdn.co\\/cdn_static\\/reserves\\/additional-briefing.png\"},{\"name\":\"Авиаудар\",\"bonus_type\":\"к промресурсу\",\"disposable\":true,\"in_stock\":[{\"status\":null,\"action_time\":null,\"active_till\":null,\"level\":3,\"activated_at\":null,\"amount\":2,\"bonus_values\":[{\"value\":0.15,\"battle_type\":\"В Вылазках и Битвах за Укрепрайон\"}],\"x_level_only\":false},{\"status\":null,\"action_time\":null,\"active_till\":null,\"level\":5,\"activated_at\":null,\"amount\":2,\"bonus_values\":[{\"value\":0.3,\"battle_type\":\"В Вылазках и Битвах за Укрепрайон\"}],\"x_level_only\":false},{\"status\":null,\"action_time\":null,\"active_till\":null,\"level\":6,\"activated_at\":null,\"amount\":3,\"bonus_values\":[{\"value\":0.35,\"battle_type\":\"В Вылазках и Битвах за Укрепрайон\"}],\"x_level_only\":false},{\"status\":null,\"action_time\":null,\"active_till\":null,\"level\":7,\"activated_at\":null,\"amount\":3,\"bonus_values\":[{\"value\":0.4,\"battle_type\":\"В Вылазках и Битвах за Укрепрайон\"}],\"x_level_only\":false},{\"status\":null,\"action_time\":null,\"active_till\":null,\"level\":8,\"activated_at\":null,\"amount\":3,\"bonus_values\":[{\"value\":0.5,\"battle_type\":\"В Вылазках и Битвах за Укрепрайон\"}],\"x_level_only\":false},{\"status\":null,\"action_time\":null,\"active_till\":null,\"level\":9,\"activated_at\":null,\"amount\":3,\"bonus_values\":[{\"value\":0.55,\"battle_type\":\"В Вылазках и Битвах за Укрепрайон\"}],\"x_level_only\":false},{\"status\":null,\"action_time\":null,\"active_till\":null,\"level\":10,\"activated_at\":null,\"amount\":1,\"bonus_values\":[{\"value\":0.6,\"battle_type\":\"В Вылазках и Битвах за Укрепрайон\"}],\"x_level_only\":false}],\"type\":\"AIRSTRIKE\",\"icon\":\"https:\\/\\/wgsh-wotru-static.wgcdn.co\\/cdn_static\\/reserves\\/airstrike.png\"},{\"name\":\"Артобстрел\",\"bonus_type\":\"к промресурсу\",\"disposable\":true,\"in_stock\":[{\"status\":null,\"action_time\":null,\"active_till\":null,\"level\":5,\"activated_at\":null,\"amount\":2,\"bonus_values\":[{\"value\":0.3,\"battle_type\":\"В Вылазках и Битвах за Укрепрайон\"}],\"x_level_only\":false},{\"status\":null,\"action_time\":null,\"active_till\":null,\"level\":6,\"activated_at\":null,\"amount\":3,\"bonus_values\":[{\"value\":0.35,\"battle_type\":\"В Вылазках и Битвах за Укрепрайон\"}],\"x_level_only\":false},{\"status\":null,\"action_time\":null,\"active_till\":null,\"level\":7,\"activated_at\":null,\"amount\":3,\"bonus_values\":[{\"value\":0.4,\"battle_type\":\"В Вылазках и Битвах за Укрепрайон\"}],\"x_level_only\":false},{\"status\":null,\"action_time\":null,\"active_till\":null,\"level\":8,\"activated_at\":null,\"amount\":3,\"bonus_values\":[{\"value\":0.5,\"battle_type\":\"В Вылазках и Битвах за Укрепрайон\"}],\"x_level_only\":false},{\"status\":null,\"action_time\":null,\"active_till\":null,\"level\":9,\"activated_at\":null,\"amount\":3,\"bonus_values\":[{\"value\":0.55,\"battle_type\":\"В Вылазках и Битвах за Укрепрайон\"}],\"x_level_only\":false},{\"status\":null,\"action_time\":null,\"active_till\":null,\"level\":10,\"activated_at\":null,\"amount\":1,\"bonus_values\":[{\"value\":0.6,\"battle_type\":\"В Вылазках и Битвах за Укрепрайон\"}],\"x_level_only\":false}],\"type\":\"ARTILLERY_STRIKE\",\"icon\":\"https:\\/\\/wgsh-wotru-static.wgcdn.co\\/cdn_static\\/reserves\\/artillery-strike.png\"},{\"name\":\"Боевые выплаты\",\"bonus_type\":\"к кредитам\",\"disposable\":false,\"in_stock\":[{\"status\":\"active\",\"action_time\":7200,\"active_till\":1560622808,\"level\":6,\"activated_at\":1560615608,\"amount\":5,\"bonus_values\":[{\"value\":0.3,\"battle_type\":\"В клановых и турнирных боях\"},{\"value\":0.15,\"battle_type\":\"В случайных боях\"}],\"x_level_only\":false},{\"status\":\"cannot_be_activated\",\"action_time\":7200,\"active_till\":null,\"level\":9,\"activated_at\":null,\"amount\":2,\"bonus_values\":[{\"value\":0.5,\"battle_type\":\"В клановых и турнирных боях\"},{\"value\":0.25,\"battle_type\":\"В случайных боях\"}],\"x_level_only\":false},{\"status\":\"cannot_be_activated\",\"action_time\":7200,\"active_till\":null,\"level\":10,\"activated_at\":null,\"amount\":1,\"bonus_values\":[{\"value\":0.75,\"battle_type\":\"В клановых и турнирных боях\"},{\"value\":0.3,\"battle_type\":\"В случайных боях\"}],\"x_level_only\":false}],\"type\":\"BATTLE_PAYMENTS\",\"icon\":\"https:\\/\\/wgsh-wotru-static.wgcdn.co\\/cdn_static\\/reserves\\/battle-payments.png\"},{\"name\":\"Большегрузный транспорт\",\"bonus_type\":\"к промресурсу\",\"disposable\":true,\"in_stock\":[{\"status\":null,\"action_time\":null,\"active_till\":null,\"level\":5,\"activated_at\":null,\"amount\":2,\"bonus_values\":[{\"value\":0.3,\"battle_type\":\"В Вылазках и Битвах за Укрепрайон\"}],\"x_level_only\":false},{\"status\":null,\"action_time\":null,\"active_till\":null,\"level\":6,\"activated_at\":null,\"amount\":3,\"bonus_values\":[{\"value\":0.35,\"battle_type\":\"В Вылазках и Битвах за Укрепрайон\"}],\"x_level_only\":false},{\"status\":null,\"action_time\":null,\"active_till\":null,\"level\":7,\"activated_at\":null,\"amount\":3,\"bonus_values\":[{\"value\":0.4,\"battle_type\":\"В Вылазках и Битвах за Укрепрайон\"}],\"x_level_only\":false},{\"status\":null,\"action_time\":null,\"active_till\":null,\"level\":8,\"activated_at\":null,\"amount\":3,\"bonus_values\":[{\"value\":0.5,\"battle_type\":\"В Вылазках и Битвах за Укрепрайон\"}],\"x_level_only\":false},{\"status\":null,\"action_time\":null,\"active_till\":null,\"level\":9,\"activated_at\":null,\"amount\":3,\"bonus_values\":[{\"value\":0.55,\"battle_type\":\"В Вылазках и Битвах за Укрепрайон\"}],\"x_level_only\":false},{\"status\":null,\"action_time\":null,\"active_till\":null,\"level\":10,\"activated_at\":null,\"amount\":1,\"bonus_values\":[{\"value\":0.6,\"battle_type\":\"В Вылазках и Битвах за Укрепрайон\"}],\"x_level_only\":false}],\"type\":\"HIGH_CAPACITY_TRANSPORT\",\"icon\":\"https:\\/\\/wgsh-wotru-static.wgcdn.co\\/cdn_static\\/reserves\\/high-capacity-transport.png\"},{\"name\":\"Военные учения\",\"bonus_type\":\"к свободному опыту\",\"disposable\":false,\"in_stock\":[{\"status\":\"ready_to_activate\",\"action_time\":3600,\"active_till\":null,\"level\":9,\"activated_at\":null,\"amount\":2,\"bonus_values\":[{\"value\":2.5,\"battle_type\":\"Во всех боях\"}],\"x_level_only\":false},{\"status\":\"ready_to_activate\",\"action_time\":7200,\"active_till\":null,\"level\":10,\"activated_at\":null,\"amount\":1,\"bonus_values\":[{\"value\":2.5,\"battle_type\":\"Во всех боях\"}],\"x_level_only\":false}],\"type\":\"MILITARY_MANEUVERS\",\"icon\":\"https:\\/\\/wgsh-wotru-static.wgcdn.co\\/cdn_static\\/reserves\\/military-maneuvers.png\"},{\"name\":\"Реквизиция\",\"bonus_type\":\"к промресурсу\",\"disposable\":true,\"in_stock\":[{\"status\":null,\"action_time\":null,\"active_till\":null,\"level\":5,\"activated_at\":null,\"amount\":2,\"bonus_values\":[{\"value\":0.3,\"battle_type\":\"В Битвах за Укрепрайон\"}],\"x_level_only\":false},{\"status\":null,\"action_time\":null,\"active_till\":null,\"level\":6,\"activated_at\":null,\"amount\":3,\"bonus_values\":[{\"value\":0.35,\"battle_type\":\"В Битвах за Укрепрайон\"}],\"x_level_only\":false},{\"status\":null,\"action_time\":null,\"active_till\":null,\"level\":7,\"activated_at\":null,\"amount\":3,\"bonus_values\":[{\"value\":0.4,\"battle_type\":\"В Битвах за Укрепрайон\"}],\"x_level_only\":false},{\"status\":null,\"action_time\":null,\"active_till\":null,\"level\":8,\"activated_at\":null,\"amount\":3,\"bonus_values\":[{\"value\":0.5,\"battle_type\":\"В Битвах за Укрепрайон\"}],\"x_level_only\":false},{\"status\":null,\"action_time\":null,\"active_till\":null,\"level\":9,\"activated_at\":null,\"amount\":3,\"bonus_values\":[{\"value\":0.55,\"battle_type\":\"В Битвах за Укрепрайон\"}],\"x_level_only\":false},{\"status\":null,\"action_time\":null,\"active_till\":null,\"level\":10,\"activated_at\":null,\"amount\":1,\"bonus_values\":[{\"value\":0.6,\"battle_type\":\"В Битвах за Укрепрайон\"}],\"x_level_only\":false}],\"type\":\"REQUISITION\",\"icon\":\"https:\\/\\/wgsh-wotru-static.wgcdn.co\\/cdn_static\\/reserves\\/requisition.png\"},{\"name\":\"Тактическая подготовка\",\"bonus_type\":\"к боевому опыту\",\"disposable\":false,\"in_stock\":[{\"status\":\"cannot_be_activated\",\"action_time\":7200,\"active_till\":null,\"level\":6,\"activated_at\":null,\"amount\":1,\"bonus_values\":[{\"value\":0.2,\"battle_type\":\"Во всех боях\"}],\"x_level_only\":false},{\"status\":\"cannot_be_activated\",\"action_time\":3600,\"active_till\":null,\"level\":9,\"activated_at\":null,\"amount\":3,\"bonus_values\":[{\"value\":0.5,\"battle_type\":\"Во всех боях\"}],\"x_level_only\":false},{\"status\":\"cannot_be_activated\",\"action_time\":7200,\"active_till\":null,\"level\":10,\"activated_at\":null,\"amount\":1,\"bonus_values\":[{\"value\":0.5,\"battle_type\":\"Во всех боях\"}],\"x_level_only\":false}],\"type\":\"TACTICAL_TRAINING\",\"icon\":\"https:\\/\\/wgsh-wotru-static.wgcdn.co\\/cdn_static\\/reserves\\/tactical-training.png\"}]}";

return f;
}









    @Override
    public void onCreate() {






        handler = new Handler();
        super.onCreate();

        //Инициализация бродкаст ресивера
            IntentFilter filter = new IntentFilter();
            filter.addAction("android.provider.Telephony.SMS_RECEIVED");
            filter.addAction(android.telephony.TelephonyManager.ACTION_PHONE_STATE_CHANGED);
            filter.addAction("GetMessageFromMyActivity"); //further more
            filter.addAction("your_action_strings"); //further more

            registerReceiver(receiver, filter);



// Поток запросов к серверу
         sF=Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {

                String str="";
                Date currentDate=new Date();
                String status="";
                String error="";
                DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
                String dateText = dateFormat.format(currentDate);
                int Attempt=0;
                boolean ifNotic=false;

             //Попытка получить корректный ответ
              while (!status.equals("ok")&&(Attempt<MaxAttempt)) {
                   Attempt=Attempt+1;

                   try {
                       TimeUnit.MILLISECONDS.sleep(delayMS);
                   } catch (InterruptedException e) {
                       e.printStackTrace();
                   }
                   //Получение строки JSON
                   str= GetOnServer();
                   //Получение текущей даты
                   dateText = dateFormat.format(currentDate);

                   try {JSONObject jsonRoot=new JSONObject(str);
                       status= jsonRoot.getString("status");
                       error= str.substring(str.indexOf("message"),str.indexOf("code"));
                   }catch (Exception s){ }



           }

int a=1;
                //Парсинг ответа

                if (status.equals("ok")) {
                    try {
                        JSONObject jsonRoot = new JSONObject(str);
                        status = jsonRoot.getString("status");
                        JSONArray jsonArray = jsonRoot.getJSONArray("data");

                        JSONObject jsonDataRoot;
                        JSONArray jsonArrayDataRootRecuisites;
                        String StrReservName;
                        String[] recuisitesOfReserve;
                        String timeActiveTill;
                        String timeActivatedAt;


                        JSONObject JsonReserveObject;

                        for (int x = 0; x < jsonArray.length(); x++) {

                            jsonDataRoot = jsonArray.getJSONObject(x);
                            StrReservName = jsonDataRoot.getString("name");
                            JSONArray InStockArray = jsonDataRoot.getJSONArray("in_stock");

                            //Создание объекта ReservsWot
                            ReserveWot reserveWot;


                            for (int y = 0; y < InStockArray.length(); y++) {
                                JsonReserveObject = InStockArray.getJSONObject(y);

                                timeActiveTill = JsonReserveObject.getString("active_till");


                                if (timeActiveTill != "null") {

                                    timeActivatedAt=JsonReserveObject.getString("activated_at");
                                    SimpleDateFormat s = new SimpleDateFormat("yyyy.MM.dd 'at' HH:mm:ss");
                                    String timeString = s.format(new Date(Long.valueOf(timeActivatedAt) * 1000));

                                    //Уведомление
                                    notic(x, true, timeString + " " + Integer.toString(Attempt) + " time", StrReservName + ", РЕЗЕРВ АКТИВИРОВАН!!!");
                                    logString+= timeString + " " + Integer.toString(Attempt) + " time "+ ", Резерв "+StrReservName+" активирован \n";
                                    ifNotic=true;
                                }
                            }
                        }
                        ;


                    } catch (Exception s) {

                        String Ex = s.toString();
                    }



                }




//Уведомление, Если резервы не активны
              if ((Attempt!=MaxAttempt)&&(ifNotic==false)) {
                  logString+= dateText + " " + Integer.toString(Attempt) + " time "+status + ", Резервы не активны \n";
              }else if (ifNotic==false){
                  logString+= dateText + " " + Integer.toString(Attempt) + " time: "+status + " error:"+error+" , Сервер не отвечает!\n";
                }
            }
        }, 0, CountOfPeriod, TimeUnit.SECONDS);


    }

    public  void notic(int id,boolean sound,String titleStr,String bodyStr){

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(titleStr)
                .setContentText(bodyStr);

        Notification notification = builder.build();

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(id, notification);



  if (sound) {
      try {
          Uri notify = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
          Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notify);
          r.play();
      } catch (Exception e) {
          e.printStackTrace();
      }
  }



    }




    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {





        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this,
                0, notificationIntent,
                PendingIntent.FLAG_CANCEL_CURRENT);


        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setContentIntent(contentIntent)
                .setContentTitle("WotStatus")
                .setSmallIcon(R.drawable.ic_stat_name)
               .setContentText("my string");

        Notification notification = builder.build();


        startForeground(1394,notification);



        return mStartMode;
    }
    @Override
    public IBinder onBind(Intent intent) {
        int a;
      a=1;
      // A client is binding to the service with bindService()
        return mBinder;
    }
    @Override
    public boolean onUnbind(Intent intent) {
        // All clients have unbound with unbindService()
        return mAllowRebind;
    }
    @Override
    public void onRebind(Intent intent) {
        // A client is binding to the service with bindService(),
        // after onUnbind() has already been called
    }
    @Override
    public void onDestroy() {
        sF.cancel(true);
        unregisterReceiver(receiver);

    }


    public static boolean hasConnection(final Context context)
    {
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiInfo;
        wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiInfo != null && wifiInfo.isConnected())
        {
            return true;
        }
        wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (wifiInfo != null && wifiInfo.isConnected())
        {
            return true;
        }
        wifiInfo = cm.getActiveNetworkInfo();
        if (wifiInfo != null && wifiInfo.isConnected())
        {
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
        connection.setRequestProperty("User-Agent", "Mozilla/5.0" );
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


    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(action.equals("GetMessageFromMyActivity")){


                Intent intent1 = new Intent();
                intent1.setAction("SetMessageFromMyActivity");
                intent1.putExtra("logString",logString);
                intent1.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
                sendBroadcast(intent1);

               // logString="";




            }
            else if(action.equals(android.telephony.TelephonyManager.ACTION_PHONE_STATE_CHANGED)){
                //action for phone state changed
            }
        }
    };


}
