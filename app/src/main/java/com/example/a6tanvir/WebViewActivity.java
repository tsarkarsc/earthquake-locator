package com.example.a6tanvir;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebViewActivity extends AppCompatActivity {

    private WebView webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        Intent intent = getIntent();
        String lon = intent.getStringExtra("lon");
        String lat = intent.getStringExtra("lat");

        StringBuffer url = new StringBuffer();
        url.append("https://www.openstreetmap.org/");
        url.append("?mlat=" + lat);
        url.append("&mlon=" + lon);

        webview =(WebView)findViewById(R.id.web_view_id);
        webview.setWebViewClient(new WebViewClient());
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setDomStorageEnabled(true);
        webview.setOverScrollMode(WebView.OVER_SCROLL_NEVER);
        webview.loadUrl(url.toString());
    }
}
