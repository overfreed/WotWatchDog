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

public class OpenIdActivity extends AppCompatActivity {

    WebView webView;
    String accessTokenWG;
    String application_id;
    PlayerWotSingleton playerWotSingleton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_id);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle arguments = getIntent().getExtras();
        application_id = arguments.get("application_id").toString();


    }


    public void buttonTry(View view) throws Exception {

        System.out.println("666!");
        setContentView(R.layout.activity_open_id);

         webView = (WebView) findViewById(R.id.webView);

        webView.setWebViewClient(new WebViewClient() {

            public void onPageFinished(WebView view, String url)
            {
                 String title=webView.getTitle();
                 String urlString=webView.getUrl();

                 if (urlString.contains("wot/?&status=ok&access_token=")){

                     accessTokenWG=urlString.substring(urlString.indexOf("access_token=")+13,urlString.indexOf("access_token=")+53);

                     putIdInMyActivity();




            };



            }
        });

        WebSettings webSettings = webView.getSettings();

        webSettings.setJavaScriptEnabled(true);





        webView.loadUrl("https://api.worldoftanks.ru/wot/auth/login/?application_id="+application_id+"&redirect_uri=https%3A%2F%2Fapi.worldoftanks.ru%2Fwot%2F%2Fblank%2Fwot%2F");


    }


//    private class MyWebViewClient extends WebViewClient {
//        @Override public boolean shouldOverrideUrlLoading(WebView view, String url) { if (Uri.parse(url).getHost().equals("www.yandex.ru"))
//        { // Designate Urls that you want to load in WebView still. return false;
//            } // Otherwise, give the default behavior (open in browser)
//            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//        startActivity(intent); return true; }}





  void  putIdInMyActivity(){
      Intent intent = new Intent(this, MainActivity.class);
// передача объекта с ключом "hello" и значением "Hello World"
      intent.putExtra("accessTokenWG", accessTokenWG);
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



        webView.loadUrl("https://api.worldoftanks.ru/wot/auth/login/?application_id="+application_id+"&redirect_uri=https%3A%2F%2Fapi.worldoftanks.ru%2Fwot%2F%2Fblank%2Fwot%2F");


    }





}


