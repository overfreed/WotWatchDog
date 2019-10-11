package com.example.myapplication;

import android.content.Context;
import android.support.annotation.NonNull;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobRequest;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class WwdJob extends Job {

    public static final String TAG = "job_demo_tag";
    public static String log = "Begin of log";
    private PlayerWotSingleton  playerWotSingleton      = PlayerWotSingleton.getInstance();
   NotificationHelper nH;


    @Override
    @NonNull
    protected Result onRunJob(Params params) {
        nH= new  NotificationHelper(getContext());

    if (playerWotSingleton.flagJobExecute) {
        scheduleJob();
        wotCheckStatus();
        wotRequestJob();

    }
        return Result.SUCCESS;
    }


  public void wotCheckStatus(){
        try {
            Integer currentTimeStamp = Integer.parseInt(String.valueOf(System.currentTimeMillis() / 1000));

            if (Integer.parseInt(playerWotSingleton.expires_at) < currentTimeStamp)
                nH.createNotification(13011994, "WWD сервисы не активны!", "Выполните вход(активацию) сервисов!");
        }catch (Exception ex){}
    }


  public void  wotRequestJob() {
      String currentDate=new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
      String responseOfServer="";
      int i=0;
      String errorString="error of doGet()";
      int countOfTry=15;
      int delayMS=2000;
      String statusOfRespnse="";
      JSONObject jsonRoot=new JSONObject() ;
      // receiving a response from the server
      while (i<countOfTry){
          try { responseOfServer=doGet("https://api.worldoftanks.ru/wot/stronghold/clanreserves/?application_id="+App.application_id+"&access_token="+playerWotSingleton.access_token);

              ////exsample of resourse activated string for test
              //responseOfServer="{\"status\":\"ok\",\"meta\":{\"count\":8},\"data\":[{\"name\":\"Дополнительный инструктаж\",\"bonus_type\":\"к опыту экипажа\",\"disposable\":false,\"in_stock\":[{\"status\":\"ready_to_activate\",\"action_time\":3600,\"active_till\":null,\"level\":9,\"activated_at\":null,\"amount\":3,\"bonus_values\":[{\"value\":2.0,\"battle_type\":\"В клановых и турнирных боях\"},{\"value\":1.5,\"battle_type\":\"В случайных боях\"}],\"x_level_only\":false},{\"status\":\"ready_to_activate\",\"action_time\":7200,\"active_till\":null,\"level\":10,\"activated_at\":null,\"amount\":1,\"bonus_values\":[{\"value\":2.0,\"battle_type\":\"В клановых и турнирных боях\"},{\"value\":1.5,\"battle_type\":\"В случайных боях\"}],\"x_level_only\":false}],\"type\":\"ADDITIONAL_BRIEFING\",\"icon\":\"https:\\/\\/wgsh-wotru-static.wgcdn.co\\/cdn_static\\/reserves\\/additional-briefing.png\"},{\"name\":\"Авиаудар\",\"bonus_type\":\"к промресурсу\",\"disposable\":true,\"in_stock\":[{\"status\":null,\"action_time\":null,\"active_till\":null,\"level\":3,\"activated_at\":null,\"amount\":2,\"bonus_values\":[{\"value\":0.15,\"battle_type\":\"В Вылазках и Битвах за Укрепрайон\"}],\"x_level_only\":false},{\"status\":null,\"action_time\":null,\"active_till\":null,\"level\":5,\"activated_at\":null,\"amount\":2,\"bonus_values\":[{\"value\":0.3,\"battle_type\":\"В Вылазках и Битвах за Укрепрайон\"}],\"x_level_only\":false},{\"status\":null,\"action_time\":null,\"active_till\":null,\"level\":6,\"activated_at\":null,\"amount\":3,\"bonus_values\":[{\"value\":0.35,\"battle_type\":\"В Вылазках и Битвах за Укрепрайон\"}],\"x_level_only\":false},{\"status\":null,\"action_time\":null,\"active_till\":null,\"level\":7,\"activated_at\":null,\"amount\":3,\"bonus_values\":[{\"value\":0.4,\"battle_type\":\"В Вылазках и Битвах за Укрепрайон\"}],\"x_level_only\":false},{\"status\":null,\"action_time\":null,\"active_till\":null,\"level\":8,\"activated_at\":null,\"amount\":3,\"bonus_values\":[{\"value\":0.5,\"battle_type\":\"В Вылазках и Битвах за Укрепрайон\"}],\"x_level_only\":false},{\"status\":null,\"action_time\":null,\"active_till\":null,\"level\":9,\"activated_at\":null,\"amount\":3,\"bonus_values\":[{\"value\":0.55,\"battle_type\":\"В Вылазках и Битвах за Укрепрайон\"}],\"x_level_only\":false},{\"status\":null,\"action_time\":null,\"active_till\":null,\"level\":10,\"activated_at\":null,\"amount\":1,\"bonus_values\":[{\"value\":0.6,\"battle_type\":\"В Вылазках и Битвах за Укрепрайон\"}],\"x_level_only\":false}],\"type\":\"AIRSTRIKE\",\"icon\":\"https:\\/\\/wgsh-wotru-static.wgcdn.co\\/cdn_static\\/reserves\\/airstrike.png\"},{\"name\":\"Артобстрел\",\"bonus_type\":\"к промресурсу\",\"disposable\":true,\"in_stock\":[{\"status\":null,\"action_time\":null,\"active_till\":null,\"level\":5,\"activated_at\":null,\"amount\":2,\"bonus_values\":[{\"value\":0.3,\"battle_type\":\"В Вылазках и Битвах за Укрепрайон\"}],\"x_level_only\":false},{\"status\":null,\"action_time\":null,\"active_till\":null,\"level\":6,\"activated_at\":null,\"amount\":3,\"bonus_values\":[{\"value\":0.35,\"battle_type\":\"В Вылазках и Битвах за Укрепрайон\"}],\"x_level_only\":false},{\"status\":null,\"action_time\":null,\"active_till\":null,\"level\":7,\"activated_at\":null,\"amount\":3,\"bonus_values\":[{\"value\":0.4,\"battle_type\":\"В Вылазках и Битвах за Укрепрайон\"}],\"x_level_only\":false},{\"status\":null,\"action_time\":null,\"active_till\":null,\"level\":8,\"activated_at\":null,\"amount\":3,\"bonus_values\":[{\"value\":0.5,\"battle_type\":\"В Вылазках и Битвах за Укрепрайон\"}],\"x_level_only\":false},{\"status\":null,\"action_time\":null,\"active_till\":null,\"level\":9,\"activated_at\":null,\"amount\":3,\"bonus_values\":[{\"value\":0.55,\"battle_type\":\"В Вылазках и Битвах за Укрепрайон\"}],\"x_level_only\":false},{\"status\":null,\"action_time\":null,\"active_till\":null,\"level\":10,\"activated_at\":null,\"amount\":1,\"bonus_values\":[{\"value\":0.6,\"battle_type\":\"В Вылазках и Битвах за Укрепрайон\"}],\"x_level_only\":false}],\"type\":\"ARTILLERY_STRIKE\",\"icon\":\"https:\\/\\/wgsh-wotru-static.wgcdn.co\\/cdn_static\\/reserves\\/artillery-strike.png\"},{\"name\":\"Боевые выплаты\",\"bonus_type\":\"к кредитам\",\"disposable\":false,\"in_stock\":[{\"status\":\"active\",\"action_time\":7200,\"active_till\":1560622808,\"level\":6,\"activated_at\":1560615608,\"amount\":5,\"bonus_values\":[{\"value\":0.3,\"battle_type\":\"В клановых и турнирных боях\"},{\"value\":0.15,\"battle_type\":\"В случайных боях\"}],\"x_level_only\":false},{\"status\":\"cannot_be_activated\",\"action_time\":7200,\"active_till\":null,\"level\":9,\"activated_at\":null,\"amount\":2,\"bonus_values\":[{\"value\":0.5,\"battle_type\":\"В клановых и турнирных боях\"},{\"value\":0.25,\"battle_type\":\"В случайных боях\"}],\"x_level_only\":false},{\"status\":\"cannot_be_activated\",\"action_time\":7200,\"active_till\":null,\"level\":10,\"activated_at\":null,\"amount\":1,\"bonus_values\":[{\"value\":0.75,\"battle_type\":\"В клановых и турнирных боях\"},{\"value\":0.3,\"battle_type\":\"В случайных боях\"}],\"x_level_only\":false}],\"type\":\"BATTLE_PAYMENTS\",\"icon\":\"https:\\/\\/wgsh-wotru-static.wgcdn.co\\/cdn_static\\/reserves\\/battle-payments.png\"},{\"name\":\"Большегрузный транспорт\",\"bonus_type\":\"к промресурсу\",\"disposable\":true,\"in_stock\":[{\"status\":null,\"action_time\":null,\"active_till\":null,\"level\":5,\"activated_at\":null,\"amount\":2,\"bonus_values\":[{\"value\":0.3,\"battle_type\":\"В Вылазках и Битвах за Укрепрайон\"}],\"x_level_only\":false},{\"status\":null,\"action_time\":null,\"active_till\":null,\"level\":6,\"activated_at\":null,\"amount\":3,\"bonus_values\":[{\"value\":0.35,\"battle_type\":\"В Вылазках и Битвах за Укрепрайон\"}],\"x_level_only\":false},{\"status\":null,\"action_time\":null,\"active_till\":null,\"level\":7,\"activated_at\":null,\"amount\":3,\"bonus_values\":[{\"value\":0.4,\"battle_type\":\"В Вылазках и Битвах за Укрепрайон\"}],\"x_level_only\":false},{\"status\":null,\"action_time\":null,\"active_till\":null,\"level\":8,\"activated_at\":null,\"amount\":3,\"bonus_values\":[{\"value\":0.5,\"battle_type\":\"В Вылазках и Битвах за Укрепрайон\"}],\"x_level_only\":false},{\"status\":null,\"action_time\":null,\"active_till\":null,\"level\":9,\"activated_at\":null,\"amount\":3,\"bonus_values\":[{\"value\":0.55,\"battle_type\":\"В Вылазках и Битвах за Укрепрайон\"}],\"x_level_only\":false},{\"status\":null,\"action_time\":null,\"active_till\":null,\"level\":10,\"activated_at\":null,\"amount\":1,\"bonus_values\":[{\"value\":0.6,\"battle_type\":\"В Вылазках и Битвах за Укрепрайон\"}],\"x_level_only\":false}],\"type\":\"HIGH_CAPACITY_TRANSPORT\",\"icon\":\"https:\\/\\/wgsh-wotru-static.wgcdn.co\\/cdn_static\\/reserves\\/high-capacity-transport.png\"},{\"name\":\"Военные учения\",\"bonus_type\":\"к свободному опыту\",\"disposable\":false,\"in_stock\":[{\"status\":\"ready_to_activate\",\"action_time\":3600,\"active_till\":null,\"level\":9,\"activated_at\":null,\"amount\":2,\"bonus_values\":[{\"value\":2.5,\"battle_type\":\"Во всех боях\"}],\"x_level_only\":false},{\"status\":\"ready_to_activate\",\"action_time\":7200,\"active_till\":null,\"level\":10,\"activated_at\":null,\"amount\":1,\"bonus_values\":[{\"value\":2.5,\"battle_type\":\"Во всех боях\"}],\"x_level_only\":false}],\"type\":\"MILITARY_MANEUVERS\",\"icon\":\"https:\\/\\/wgsh-wotru-static.wgcdn.co\\/cdn_static\\/reserves\\/military-maneuvers.png\"},{\"name\":\"Реквизиция\",\"bonus_type\":\"к промресурсу\",\"disposable\":true,\"in_stock\":[{\"status\":null,\"action_time\":null,\"active_till\":null,\"level\":5,\"activated_at\":null,\"amount\":2,\"bonus_values\":[{\"value\":0.3,\"battle_type\":\"В Битвах за Укрепрайон\"}],\"x_level_only\":false},{\"status\":null,\"action_time\":null,\"active_till\":null,\"level\":6,\"activated_at\":null,\"amount\":3,\"bonus_values\":[{\"value\":0.35,\"battle_type\":\"В Битвах за Укрепрайон\"}],\"x_level_only\":false},{\"status\":null,\"action_time\":null,\"active_till\":null,\"level\":7,\"activated_at\":null,\"amount\":3,\"bonus_values\":[{\"value\":0.4,\"battle_type\":\"В Битвах за Укрепрайон\"}],\"x_level_only\":false},{\"status\":null,\"action_time\":null,\"active_till\":null,\"level\":8,\"activated_at\":null,\"amount\":3,\"bonus_values\":[{\"value\":0.5,\"battle_type\":\"В Битвах за Укрепрайон\"}],\"x_level_only\":false},{\"status\":null,\"action_time\":null,\"active_till\":null,\"level\":9,\"activated_at\":null,\"amount\":3,\"bonus_values\":[{\"value\":0.55,\"battle_type\":\"В Битвах за Укрепрайон\"}],\"x_level_only\":false},{\"status\":null,\"action_time\":null,\"active_till\":null,\"level\":10,\"activated_at\":null,\"amount\":1,\"bonus_values\":[{\"value\":0.6,\"battle_type\":\"В Битвах за Укрепрайон\"}],\"x_level_only\":false}],\"type\":\"REQUISITION\",\"icon\":\"https:\\/\\/wgsh-wotru-static.wgcdn.co\\/cdn_static\\/reserves\\/requisition.png\"},{\"name\":\"Тактическая подготовка\",\"bonus_type\":\"к боевому опыту\",\"disposable\":false,\"in_stock\":[{\"status\":\"cannot_be_activated\",\"action_time\":7200,\"active_till\":null,\"level\":6,\"activated_at\":null,\"amount\":1,\"bonus_values\":[{\"value\":0.2,\"battle_type\":\"Во всех боях\"}],\"x_level_only\":false},{\"status\":\"cannot_be_activated\",\"action_time\":3600,\"active_till\":null,\"level\":9,\"activated_at\":null,\"amount\":3,\"bonus_values\":[{\"value\":0.5,\"battle_type\":\"Во всех боях\"}],\"x_level_only\":false},{\"status\":\"cannot_be_activated\",\"action_time\":7200,\"active_till\":null,\"level\":10,\"activated_at\":null,\"amount\":1,\"bonus_values\":[{\"value\":0.5,\"battle_type\":\"Во всех боях\"}],\"x_level_only\":false}],\"type\":\"TACTICAL_TRAINING\",\"icon\":\"https:\\/\\/wgsh-wotru-static.wgcdn.co\\/cdn_static\\/reserves\\/tactical-training.png\"}]} {\"status\":\"ok\",\"meta\":{\"count\":8},\"data\":[{\"name\":\"Дополнительный инструктаж\",\"bonus_type\":\"к опыту экипажа\",\"disposable\":false,\"in_stock\":[{\"status\":\"ready_to_activate\",\"action_time\":3600,\"active_till\":null,\"level\":9,\"activated_at\":null,\"amount\":3,\"bonus_values\":[{\"value\":2.0,\"battle_type\":\"В клановых и турнирных боях\"},{\"value\":1.5,\"battle_type\":\"В случайных боях\"}],\"x_level_only\":false},{\"status\":\"ready_to_activate\",\"action_time\":7200,\"active_till\":null,\"level\":10,\"activated_at\":null,\"amount\":1,\"bonus_values\":[{\"value\":2.0,\"battle_type\":\"В клановых и турнирных боях\"},{\"value\":1.5,\"battle_type\":\"В случайных боях\"}],\"x_level_only\":false}],\"type\":\"ADDITIONAL_BRIEFING\",\"icon\":\"https:\\/\\/wgsh-wotru-static.wgcdn.co\\/cdn_static\\/reserves\\/additional-briefing.png\"},{\"name\":\"Авиаудар\",\"bonus_type\":\"к промресурсу\",\"disposable\":true,\"in_stock\":[{\"status\":null,\"action_time\":null,\"active_till\":null,\"level\":3,\"activated_at\":null,\"amount\":2,\"bonus_values\":[{\"value\":0.15,\"battle_type\":\"В Вылазках и Битвах за Укрепрайон\"}],\"x_level_only\":false},{\"status\":null,\"action_time\":null,\"active_till\":null,\"level\":5,\"activated_at\":null,\"amount\":2,\"bonus_values\":[{\"value\":0.3,\"battle_type\":\"В Вылазках и Битвах за Укрепрайон\"}],\"x_level_only\":false},{\"status\":null,\"action_time\":null,\"active_till\":null,\"level\":6,\"activated_at\":null,\"amount\":3,\"bonus_values\":[{\"value\":0.35,\"battle_type\":\"В Вылазках и Битвах за Укрепрайон\"}],\"x_level_only\":false},{\"status\":null,\"action_time\":null,\"active_till\":null,\"level\":7,\"activated_at\":null,\"amount\":3,\"bonus_values\":[{\"value\":0.4,\"battle_type\":\"В Вылазках и Битвах за Укрепрайон\"}],\"x_level_only\":false},{\"status\":null,\"action_time\":null,\"active_till\":null,\"level\":8,\"activated_at\":null,\"amount\":3,\"bonus_values\":[{\"value\":0.5,\"battle_type\":\"В Вылазках и Битвах за Укрепрайон\"}],\"x_level_only\":false},{\"status\":null,\"action_time\":null,\"active_till\":null,\"level\":9,\"activated_at\":null,\"amount\":3,\"bonus_values\":[{\"value\":0.55,\"battle_type\":\"В Вылазках и Битвах за Укрепрайон\"}],\"x_level_only\":false},{\"status\":null,\"action_time\":null,\"active_till\":null,\"level\":10,\"activated_at\":null,\"amount\":1,\"bonus_values\":[{\"value\":0.6,\"battle_type\":\"В Вылазках и Битвах за Укрепрайон\"}],\"x_level_only\":false}],\"type\":\"AIRSTRIKE\",\"icon\":\"https:\\/\\/wgsh-wotru-static.wgcdn.co\\/cdn_static\\/reserves\\/airstrike.png\"},{\"name\":\"Артобстрел\",\"bonus_type\":\"к промресурсу\",\"disposable\":true,\"in_stock\":[{\"status\":null,\"action_time\":null,\"active_till\":null,\"level\":5,\"activated_at\":null,\"amount\":2,\"bonus_values\":[{\"value\":0.3,\"battle_type\":\"В Вылазках и Битвах за Укрепрайон\"}],\"x_level_only\":false},{\"status\":null,\"action_time\":null,\"active_till\":null,\"level\":6,\"activated_at\":null,\"amount\":3,\"bonus_values\":[{\"value\":0.35,\"battle_type\":\"В Вылазках и Битвах за Укрепрайон\"}],\"x_level_only\":false},{\"status\":null,\"action_time\":null,\"active_till\":null,\"level\":7,\"activated_at\":null,\"amount\":3,\"bonus_values\":[{\"value\":0.4,\"battle_type\":\"В Вылазках и Битвах за Укрепрайон\"}],\"x_level_only\":false},{\"status\":null,\"action_time\":null,\"active_till\":null,\"level\":8,\"activated_at\":null,\"amount\":3,\"bonus_values\":[{\"value\":0.5,\"battle_type\":\"В Вылазках и Битвах за Укрепрайон\"}],\"x_level_only\":false},{\"status\":null,\"action_time\":null,\"active_till\":null,\"level\":9,\"activated_at\":null,\"amount\":3,\"bonus_values\":[{\"value\":0.55,\"battle_type\":\"В Вылазках и Битвах за Укрепрайон\"}],\"x_level_only\":false},{\"status\":null,\"action_time\":null,\"active_till\":null,\"level\":10,\"activated_at\":null,\"amount\":1,\"bonus_values\":[{\"value\":0.6,\"battle_type\":\"В Вылазках и Битвах за Укрепрайон\"}],\"x_level_only\":false}],\"type\":\"ARTILLERY_STRIKE\",\"icon\":\"https:\\/\\/wgsh-wotru-static.wgcdn.co\\/cdn_static\\/reserves\\/artillery-strike.png\"},{\"name\":\"Боевые выплаты\",\"bonus_type\":\"к кредитам\",\"disposable\":false,\"in_stock\":[{\"status\":\"active\",\"action_time\":7200,\"active_till\":1560622808,\"level\":6,\"activated_at\":1560615608,\"amount\":5,\"bonus_values\":[{\"value\":0.3,\"battle_type\":\"В клановых и турнирных боях\"},{\"value\":0.15,\"battle_type\":\"В случайных боях\"}],\"x_level_only\":false},{\"status\":\"cannot_be_activated\",\"action_time\":7200,\"active_till\":null,\"level\":9,\"activated_at\":null,\"amount\":2,\"bonus_values\":[{\"value\":0.5,\"battle_type\":\"В клановых и турнирных боях\"},{\"value\":0.25,\"battle_type\":\"В случайных боях\"}],\"x_level_only\":false},{\"status\":\"cannot_be_activated\",\"action_time\":7200,\"active_till\":null,\"level\":10,\"activated_at\":null,\"amount\":1,\"bonus_values\":[{\"value\":0.75,\"battle_type\":\"В клановых и турнирных боях\"},{\"value\":0.3,\"battle_type\":\"В случайных боях\"}],\"x_level_only\":false}],\"type\":\"BATTLE_PAYMENTS\",\"icon\":\"https:\\/\\/wgsh-wotru-static.wgcdn.co\\/cdn_static\\/reserves\\/battle-payments.png\"},{\"name\":\"Большегрузный транспорт\",\"bonus_type\":\"к промресурсу\",\"disposable\":true,\"in_stock\":[{\"status\":null,\"action_time\":null,\"active_till\":null,\"level\":5,\"activated_at\":null,\"amount\":2,\"bonus_values\":[{\"value\":0.3,\"battle_type\":\"В Вылазках и Битвах за Укрепрайон\"}],\"x_level_only\":false},{\"status\":null,\"action_time\":null,\"active_till\":null,\"level\":6,\"activated_at\":null,\"amount\":3,\"bonus_values\":[{\"value\":0.35,\"battle_type\":\"В Вылазках и Битвах за Укрепрайон\"}],\"x_level_only\":false},{\"status\":null,\"action_time\":null,\"active_till\":null,\"level\":7,\"activated_at\":null,\"amount\":3,\"bonus_values\":[{\"value\":0.4,\"battle_type\":\"В Вылазках и Битвах за Укрепрайон\"}],\"x_level_only\":false},{\"status\":null,\"action_time\":null,\"active_till\":null,\"level\":8,\"activated_at\":null,\"amount\":3,\"bonus_values\":[{\"value\":0.5,\"battle_type\":\"В Вылазках и Битвах за Укрепрайон\"}],\"x_level_only\":false},{\"status\":null,\"action_time\":null,\"active_till\":null,\"level\":9,\"activated_at\":null,\"amount\":3,\"bonus_values\":[{\"value\":0.55,\"battle_type\":\"В Вылазках и Битвах за Укрепрайон\"}],\"x_level_only\":false},{\"status\":null,\"action_time\":null,\"active_till\":null,\"level\":10,\"activated_at\":null,\"amount\":1,\"bonus_values\":[{\"value\":0.6,\"battle_type\":\"В Вылазках и Битвах за Укрепрайон\"}],\"x_level_only\":false}],\"type\":\"HIGH_CAPACITY_TRANSPORT\",\"icon\":\"https:\\/\\/wgsh-wotru-static.wgcdn.co\\/cdn_static\\/reserves\\/high-capacity-transport.png\"},{\"name\":\"Военные учения\",\"bonus_type\":\"к свободному опыту\",\"disposable\":false,\"in_stock\":[{\"status\":\"ready_to_activate\",\"action_time\":3600,\"active_till\":null,\"level\":9,\"activated_at\":null,\"amount\":2,\"bonus_values\":[{\"value\":2.5,\"battle_type\":\"Во всех боях\"}],\"x_level_only\":false},{\"status\":\"ready_to_activate\",\"action_time\":7200,\"active_till\":null,\"level\":10,\"activated_at\":null,\"amount\":1,\"bonus_values\":[{\"value\":2.5,\"battle_type\":\"Во всех боях\"}],\"x_level_only\":false}],\"type\":\"MILITARY_MANEUVERS\",\"icon\":\"https:\\/\\/wgsh-wotru-static.wgcdn.co\\/cdn_static\\/reserves\\/military-maneuvers.png\"},{\"name\":\"Реквизиция\",\"bonus_type\":\"к промресурсу\",\"disposable\":true,\"in_stock\":[{\"status\":null,\"action_time\":null,\"active_till\":null,\"level\":5,\"activated_at\":null,\"amount\":2,\"bonus_values\":[{\"value\":0.3,\"battle_type\":\"В Битвах за Укрепрайон\"}],\"x_level_only\":false},{\"status\":null,\"action_time\":null,\"active_till\":null,\"level\":6,\"activated_at\":null,\"amount\":3,\"bonus_values\":[{\"value\":0.35,\"battle_type\":\"В Битвах за Укрепрайон\"}],\"x_level_only\":false},{\"status\":null,\"action_time\":null,\"active_till\":null,\"level\":7,\"activated_at\":null,\"amount\":3,\"bonus_values\":[{\"value\":0.4,\"battle_type\":\"В Битвах за Укрепрайон\"}],\"x_level_only\":false},{\"status\":null,\"action_time\":null,\"active_till\":null,\"level\":8,\"activated_at\":null,\"amount\":3,\"bonus_values\":[{\"value\":0.5,\"battle_type\":\"В Битвах за Укрепрайон\"}],\"x_level_only\":false},{\"status\":null,\"action_time\":null,\"active_till\":null,\"level\":9,\"activated_at\":null,\"amount\":3,\"bonus_values\":[{\"value\":0.55,\"battle_type\":\"В Битвах за Укрепрайон\"}],\"x_level_only\":false},{\"status\":null,\"action_time\":null,\"active_till\":null,\"level\":10,\"activated_at\":null,\"amount\":1,\"bonus_values\":[{\"value\":0.6,\"battle_type\":\"В Битвах за Укрепрайон\"}],\"x_level_only\":false}],\"type\":\"REQUISITION\",\"icon\":\"https:\\/\\/wgsh-wotru-static.wgcdn.co\\/cdn_static\\/reserves\\/requisition.png\"},{\"name\":\"Тактическая подготовка\",\"bonus_type\":\"к боевому опыту\",\"disposable\":false,\"in_stock\":[{\"status\":\"cannot_be_activated\",\"action_time\":7200,\"active_till\":null,\"level\":6,\"activated_at\":null,\"amount\":1,\"bonus_values\":[{\"value\":0.2,\"battle_type\":\"Во всех боях\"}],\"x_level_only\":false},{\"status\":\"cannot_be_activated\",\"action_time\":3600,\"active_till\":null,\"level\":9,\"activated_at\":null,\"amount\":3,\"bonus_values\":[{\"value\":0.5,\"battle_type\":\"Во всех боях\"}],\"x_level_only\":false},{\"status\":\"cannot_be_activated\",\"action_time\":7200,\"active_till\":null,\"level\":10,\"activated_at\":null,\"amount\":1,\"bonus_values\":[{\"value\":0.5,\"battle_type\":\"Во всех боях\"}],\"x_level_only\":false}],\"type\":\"TACTICAL_TRAINING\",\"icon\":\"https:\\/\\/wgsh-wotru-static.wgcdn.co\\/cdn_static\\/reserves\\/tactical-training.png\"}]}";
              jsonRoot=new JSONObject(responseOfServer);
              statusOfRespnse= jsonRoot.getString("status");

          }catch (Exception ex){
              responseOfServer=errorString;
          }
          i++;
          if (responseOfServer.equals(errorString)||(!statusOfRespnse.equals("ok"))){ try{ TimeUnit.MILLISECONDS.sleep(delayMS); } catch (InterruptedException e) { e.printStackTrace(); }
          }else{ break;}

      }

      // processing a response from the server
      String statusStringLog="";

      if ((!responseOfServer.equals(errorString))||statusOfRespnse.equals("ok")){

          try {
              JSONArray jsonArray = jsonRoot.getJSONArray("data");

              JSONObject jsonDataRoot;
              String StrReservName="";
              String timeActiveTill;
              String timeActivatedAt;
              String action_time;

              JSONObject JsonReserveObject;

              for (int x = 0; x < jsonArray.length(); x++) {

                  jsonDataRoot = jsonArray.getJSONObject(x);
                  JSONArray InStockArray = jsonDataRoot.getJSONArray("in_stock");


                  for (int y = 0; y < InStockArray.length(); y++) {
                      JsonReserveObject = InStockArray.getJSONObject(y);
                      timeActiveTill = JsonReserveObject.getString("active_till");

                      if (timeActiveTill!= "null") {

                          timeActivatedAt = JsonReserveObject.getString("activated_at");

                          // If resourse banned then do not show notification (continue)
                          Boolean flagBanned=false;
                          for(String a:playerWotSingleton.bannedResources)
                            if(a.equals(timeActivatedAt)){ flagBanned=true;};
                            if(flagBanned){
                                statusStringLog="continue";
                                continue;};


                            action_time = JsonReserveObject.getString("action_time");



                          SimpleDateFormat s = new SimpleDateFormat("HH:mm:ss 'at' yyyy.MM.dd");
                          String timeString = s.format(new Date(Long.valueOf(timeActivatedAt) * 1000));

                          Integer timeStampActivated = Integer.parseInt(timeActivatedAt);
                          Long timeStampCurrent1 = System.currentTimeMillis() / 1000;
                          Integer timeStampCurrent = Integer.parseInt(String.valueOf(timeStampCurrent1));

                          Integer percentOfReserve = (int)((timeStampCurrent - timeStampActivated) / Float.parseFloat(action_time) * 100);

                          if (action_time.equals("7200")) {
                              action_time = "2 часа";
                          } else {
                              action_time = "1 час";
                          }
                          ;

                          StrReservName = jsonDataRoot.getString("name");

                          //Уведомление
                          nH.createNotification(x, "Резерв " + StrReservName + " активирован.", timeString + " на " + action_time,timeActivatedAt, percentOfReserve);
                          statusStringLog = " Резерв " + StrReservName + " активирован в "+timeString;

                      }
                  }
              }

              if (statusStringLog.equals("")){
                  statusStringLog= "Попыток: "+Integer.toString(i)+"  Резервы не активны";
              }

              if (statusStringLog.equals("continue")){
                  statusStringLog= "Уведомление о действующем ресурсе "+StrReservName+" смахнуто пользователем";
              }



          } catch (Exception s) {

              String Ex = s.toString();
          }


      }else if(!responseOfServer.equals(errorString)){
          statusStringLog=responseOfServer.substring(responseOfServer.indexOf("message"),responseOfServer.indexOf("code")-3);

      }else{

          statusStringLog=responseOfServer;
      }

      //write line to the log
      log="     "+currentDate+" "+statusStringLog+" \n"+log;
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

        return response.toString();

    }



    public static void scheduleJob() {
        new JobRequest.Builder(WwdJob.TAG)
                .setExecutionWindow(30_000L, 40_000L)
                .setUpdateCurrent(true)
                .build()
                .schedule();
    }


}
