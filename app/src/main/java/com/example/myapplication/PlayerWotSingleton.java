package com.example.myapplication;

import android.content.Context;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayDeque;

import static android.content.Context.MODE_PRIVATE;

public class PlayerWotSingleton {

    public String status="";
    public String access_token="";
    public String nickname="";
    public String account_id="";
    public String expires_at="";
    public Boolean flagJobExecute=false;
    public ArrayDeque<String> bannedResources;



    public static PlayerWotSingleton instance;

    private  PlayerWotSingleton(){

    }

    public static synchronized PlayerWotSingleton  getInstance(){ // #3
        if(instance == null){		//если объект еще не создан
            instance = new PlayerWotSingleton();	//создать новый объект
        }
        return instance;		// вернуть ранее созданный объект
    }

    public static void serializePlayerWot(Context context) {

        try{
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(context.openFileOutput(App.nameOfFilePlayerWotObj, MODE_PRIVATE)));
            // пишем данные
            Gson gson=new Gson();
            String GsonObject=gson.toJson(instance);
            bw.write(GsonObject);
            // закрываем поток
            bw.close();}catch (Exception ex){};

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

    public void resourseBanned(String bannedTimeArrivedAt){
        bannedResources.add(bannedTimeArrivedAt);
    }


}
