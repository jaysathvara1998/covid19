package com.example.covid19;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.facebook.login.LoginManager;

public class Navegation extends AppCompatActivity {

    LoginManager loginManager;
    CardView news, track, statistics, donate, feedback, logout, positive_cases, feed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navegation);
        getSupportActionBar().setTitle("Dashboard");
        init();
        listener();
    }

    private void listener() {
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Navegation.this, MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });

        news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intohome = new Intent(Navegation.this, ScrollingActivity.class);
                loginManager.getInstance().logOut();
                startActivity(intohome);
            }
        });

        track.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intotrack = new Intent(Navegation.this, MapsActivity.class);
                startActivity(intotrack);
            }
        });
        statistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intostec = new Intent(Navegation.this, statistics.class);
                startActivity(intostec);
            }
        });

        donate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://rzp.io/l/2mXsriF";

                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intofeed = new Intent(Navegation.this, feedbackpage.class);
                startActivity(intofeed);
            }
        });

        feed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intofeed = new Intent(Navegation.this, FeedsActivity.class);
                startActivity(intofeed);
            }
        });

        positive_cases.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intofeed = new Intent(Navegation.this, PositiveCasesActivity.class);
                startActivity(intofeed);
            }
        });
    }

    private void init() {
        news = findViewById(R.id.news);
        track = findViewById(R.id.track);
        statistics = findViewById(R.id.statistics);
        donate = findViewById(R.id.donate);
        feedback = findViewById(R.id.feedback);
        feed = findViewById(R.id.feed);
        positive_cases = findViewById(R.id.cases);
        logout = findViewById(R.id.logout);
    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            LoginManager.getInstance().logOut();
            finishAffinity();
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Press BACK again to Exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    public void onStop() {
        LoginManager.getInstance().logOut();
        super.onStop();
    }

}
