package com.example.covid19;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import com.github.barteksc.pdfviewer.PDFView;

public class emengercy extends AppCompatActivity {

    PDFView pdfViewer,pdfViewer2;
    Button addinfo,mainbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emengercy);

        addinfo=findViewById(R.id.button10);
        pdfViewer=(PDFView) findViewById(R.id.pdfView);
        pdfViewer.fromAsset("coronvavirushelplinenumber.pdf").load();

        pdfViewer2=(PDFView) findViewById(R.id.pdfView2);
        pdfViewer2.setVisibility(View.INVISIBLE);

        mainbtn=findViewById(R.id.button11);
        mainbtn.setEnabled(false);

        addinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pdfViewer.setVisibility(View.INVISIBLE);
                pdfViewer2.setVisibility(View.VISIBLE);
                pdfViewer2.fromAsset("additionnal info.pdf").load();
                addinfo.setEnabled(false);
                mainbtn.setEnabled(true);
            }
        });

        mainbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intoreg=new Intent(emengercy.this,register.class);
                startActivity(intoreg);
            }
        });

    }

    boolean doubleBackToExitPressedOnce = false;
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        pdfViewer.setVisibility(View.VISIBLE);
        pdfViewer2.setVisibility(View.INVISIBLE);
        addinfo.setEnabled(true);
        mainbtn.setEnabled(false);
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}
