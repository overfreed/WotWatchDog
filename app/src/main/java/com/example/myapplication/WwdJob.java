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
    public static String log = "log";
    private PlayerWotSingleton  playerWotSingleton      = PlayerWotSingleton.getInstance();
   NotificationHelper nH;

    @Override
    @NonNull
    protected Result onRunJob(Params params) {
        nH= new  NotificationHelper(getContext());

    if (playerWotSingleton.flagJobExecute) {
        wotRequestJob();
        scheduleJob();
    }
        return Result.SUCCESS;
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
              String StrReservName;
              String timeActiveTill;
              String timeActivatedAt;

              JSONObject JsonReserveObject;

              for (int x = 0; x < jsonArray.length(); x++) {

                  jsonDataRoot = jsonArray.getJSONObject(x);
                  StrReservName = jsonDataRoot.getString("name");
                  JSONArray InStockArray = jsonDataRoot.getJSONArray("in_stock");


                  for (int y = 0; y < InStockArray.length(); y++) {
                      JsonReserveObject = InStockArray.getJSONObject(y);
                      timeActiveTill = JsonReserveObject.getString("active_till");

                      if (timeActiveTill!= "null") {

                          timeActivatedAt=JsonReserveObject.getString("activated_at");
                          SimpleDateFormat s = new SimpleDateFormat("yyyy.MM.dd 'at' HH:mm:ss");
                          String timeString = s.format(new Date(Long.valueOf(timeActivatedAt) * 1000));

                          //Уведомление
                          nH.createNotification(x, "Резерв "+StrReservName + " активирован.", timeString + " " + Integer.toString(i) + " time");
                          statusStringLog= timeString + " " + Integer.toString(i) + " time "+ ", Резерв "+StrReservName+" активирован \n";
                      }
                  }
              }

              if (statusStringLog.equals("")){
                  statusStringLog= "Попыток: "+Integer.toString(i)+"  Резервы не активны";
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
      log=currentDate+" "+statusStringLog+" \n"+log;
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
