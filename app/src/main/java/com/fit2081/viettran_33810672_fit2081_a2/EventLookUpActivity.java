package com.fit2081.viettran_33810672_fit2081_a2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.fit2081.viettran_33810672_fit2081_a1.R;

public class EventLookUpActivity extends AppCompatActivity {

    Toolbar webToolbar;
    // get event name as provided by the user
    String eventName;
    WebView webView;


    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_look_up);

        // Set up the toolbar and back button
        webToolbar = findViewById(R.id.web_toolbar);
        setSupportActionBar(webToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // get reference to web view
        webView = findViewById(R.id.webView);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // get event name from Intent
        eventName = getIntent().getExtras().getString("eventName", "unknown event");


        // compile the Wikipedia URL, which will be used to load into WebView
        String googlePageURL = "https://www.google.com/search?q=" + eventName;

        // set new WebView Client for the WebView
        // This gives the WebView ability to be load the URL in the current WebView
        // instead of navigating to default web browser of the device
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(googlePageURL);

        // Go back to previous page if the phone back button was clicked
        webView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    WebView webView = (WebView) v;

                    switch(keyCode) {
                        case KeyEvent.KEYCODE_BACK:
                            if (webView.canGoBack()) {
                                webView.goBack();
                                return true;
                            }
                            break;
                    }
                }

                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


}