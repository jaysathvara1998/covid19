package com.example.covid19;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class ScrollingActivity extends AppCompatActivity {

    WebView wb1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        wb1=findViewById(R.id.wb1);
        wb1.setWebViewClient(new WebViewClient());
        wb1.clearCache(true);

        wb1.getSettings().setDomStorageEnabled(true);
        wb1.getSettings().setJavaScriptEnabled(true);
//        webSettings.setJavaScriptEnabled(true);
//        webSettings.setAppCacheEnabled(true);
        wb1.loadUrl("https://timesofindia.indiatimes.com/india/coronavirus-in-india-live-updates-death-toll-rises-to-3435-in-india-cases-climb-to-112359/liveblog/75837508.cms");

    }
}
