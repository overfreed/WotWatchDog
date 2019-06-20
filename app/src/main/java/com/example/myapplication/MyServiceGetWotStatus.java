package com.example.myapplication;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
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

    int mStartMode;       // indicates how to behave if the service is killed
    IBinder mBinder;      // interface for clients that bind
    boolean mAllowRebind; // indicates whether onRebind should be used
    String application_id;
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
                s = doGet("https://api.worldoftanks.ru/wot/stronghold/clanreserves/?application_id="+application_id+"&access_token="+playerWotSingleton.access_token);
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
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {

                String str="";
                Date currentDate=new Date();
                String status="";
                String error="";
                DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
                String dateText = dateFormat.format(currentDate);
                int Attempt=0;


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
                       error= jsonRoot.getString("message");
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


                                    //Уведомление
                                    notic(x, true, new Date(Integer.parseInt(timeActivatedAt)*1000).toString() + " " + Integer.toString(Attempt) + " time", StrReservName + ", РЕЗЕРВ АКТИВИРОВАН!!!");
                                    logString+= new Date(Integer.parseInt(timeActivatedAt)*1000).toString() + " " + Integer.toString(Attempt) + " time "+ ", Резерв "+StrReservName+" активирован \n";
                                }
                            }
                        }
                        ;


                    } catch (Exception s) {

                        String Ex = s.toString();
                    }



                }




//Уведомление, Если резервы не активны
              if (Attempt!=MaxAttempt) {
                  logString+= dateText + " " + Integer.toString(Attempt) + " time "+status + ", Резервы не активны \n";
              }else{
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




        application_id=(String) intent.getExtras().get("application_id");


        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("WotStatus")
                .setContentText("my string");
        int a=1;
        a=2;
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
