package com.example.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;

public class OpenIdActivity extends AppCompatActivity {

    WebView webView;
    PlayerWotSingleton playerWotSingleton=PlayerWotSingleton.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_id);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        try {
            View view = findViewById(R.id.editText2);
            buttonTry(view);
        }catch(Exception ex){

        }




    }


    public void buttonTry(View view) throws Exception {


        setContentView(R.layout.activity_open_id);

         webView = (WebView) findViewById(R.id.webView);

        webView.setWebViewClient(new WebViewClient() {

            public void onPageFinished(WebView view, String url)
            {
                 String title=webView.getTitle();
                 String urlString=webView.getUrl();

                 if (urlString.contains("wot/?&status=ok&access_token=")){



                     playerWotSingleton.status      =getValue(urlString, "status");
                     playerWotSingleton.access_token=getValue(urlString, "access_token");
                     playerWotSingleton.nickname    =getValue(urlString, "nickname");
                     playerWotSingleton.account_id  =getValue(urlString, "account_id");
                     playerWotSingleton.expires_at  =getValue(urlString, "expires_at");

                     playerWotSingleton.serializePlayerWot(OpenIdActivity.this);

                     putIdInMyActivity();
            };

            }
        });

        WebSettings webSettings = webView.getSettings();

        webSettings.setJavaScriptEnabled(true);





        webView.loadUrl("https://api.worldoftanks.ru/wot/auth/login/?application_id="+App.application_id+"&redirect_uri=https%3A%2F%2Fapi.worldoftanks.ru%2Fwot%2F%2Fblank%2Fwot%2F");


    }


//    private class MyWebViewClient extends WebViewClient {
//        @Override public boolean shouldOverrideUrlLoading(WebView view, String url) { if (Uri.parse(url).getHost().equals("www.yandex.ru"))
//        { // Designate Urls that you want to load in WebView still. return false;
//            } // Otherwise, give the default behavior (open in browser)
//            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//        startActivity(intent); return true; }}





  void  putIdInMyActivity(){
      Intent intent = new Intent(this, MainActivity.class);


// запуск SecondActivity
      startActivity(intent);

  };


    public void buttonExit(View view) throws Exception {

        CookieSyncManager cookieSyncMngr=CookieSyncManager.createInstance(this);
        cookieSyncMngr.startSync();
        CookieManager cookieManager=CookieManager.getInstance();
        cookieManager.removeAllCookie();
        cookieManager.removeSessionCookie();
        cookieSyncMngr.stopSync();
        cookieSyncMngr.sync();

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.loadUrl("https://api.worldoftanks.ru/wot/auth/login/?application_id="+App.application_id+"&redirect_uri=https%3A%2F%2Fapi.worldoftanks.ru%2Fwot%2F%2Fblank%2Fwot%2F");
    }


    public String getValue(String str, String value) {

        String result = "";
        String obrez = str.substring(str.indexOf(value) + value.length() + 1);

        if (obrez.contains("&")) {
            result = obrez.substring(0, obrez.indexOf("&"));
        } else {
            result = obrez;
        }
        return result;

    }


}


