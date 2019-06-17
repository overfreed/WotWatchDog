package com.example.myapplication;

public class PlayerWotSingleton {

    public String status="";
    public String access_token="";
    public String nickname="";
    public String account_id="";
    public String expires_at="";



    public static PlayerWotSingleton instance;

    private  PlayerWotSingleton(){

    }

    public static synchronized PlayerWotSingleton  getInstance(){ // #3
        if(instance == null){		//если объект еще не создан
            instance = new PlayerWotSingleton();	//создать новый объект
        }
        return instance;		// вернуть ранее созданный объект
    }


}
