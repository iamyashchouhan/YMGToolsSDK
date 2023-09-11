package com.ymg.ymgtools;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ymg.ymgdevelopers.YmgActivity;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends com.ymg.ymgdevelopers.SplashActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setSplashAppName("Hello World");
        setSplashDeveloperName("YMG DEV");
        setSplashImage(R.drawable.ic_menu_post);

        Timer myTimer = new Timer();
        myTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                // If you want to modify a view in your Activity
                SplashActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dataIsReady();

                    }
                });
            }
        }, 3000);
    }

    @Override
    public void onFinishButtonPressed() {
        Toast.makeText(this, "YMG DEVELOPERS", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this,MainActivity.class));
    }
}