package com.example.mc2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;

import java.util.Timer;
import java.util.TimerTask;

public class SplashScreen extends AppCompatActivity {

        ProgressBar bar;
        int count= 0;
        Timer t;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        bar = findViewById(R.id.pBar);
        t = new Timer();
        TimerTask timerTask=new TimerTask() {
            @Override
            public void run() {
                count ++;
                bar.setProgress(count);
                if(count==100){
                    t.cancel();
                }
            }
        };

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreen.this, LoginActivity.class);
                startActivity(intent);
                finish();

            }
        }, 3000);
    }
}