package com.example.covid19;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class statistics extends AppCompatActivity {

    WebView wb1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        getSupportActionBar().hide();

        wb1=findViewById(R.id.webView);
        wb1.setWebViewClient(new WebViewClient());
        wb1.clearCache(true);

        wb1.getSettings().setDomStorageEnabled(true);
        wb1.getSettings().setJavaScriptEnabled(true);
//        webSettings.setJavaScriptEnabled(true);
//        webSettings.setAppCacheEnabled(true);
        wb1.loadUrl("https://www.covid19india.org");
    }
}
