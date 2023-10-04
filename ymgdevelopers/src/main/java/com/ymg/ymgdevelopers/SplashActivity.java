package com.ymg.ymgdevelopers;

import static com.ymg.ymgdevelopers.PrefManager.LOAD_URL;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

public abstract class SplashActivity extends AppCompatActivity {



    ImageView logo;
    TextView appname;
    TextView developers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Window window = getWindow(); // Replace with your Window instance
        int newStatusBarColor = getResources().getColor(R.color.colorStatusBar); // Replace with your color
        setStatusBarColor(window, newStatusBarColor);

        boolean isWhiteStatusBar = isStatusBarColorWhite(window);

        if (isWhiteStatusBar){
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        logo = findViewById(R.id.logo);
        appname = findViewById(R.id.appname);
        developers = findViewById(R.id.developers);


    }

    public void setSplashAppName(String appName){
        appname.setText(appName);
    }


    public void setSplashDeveloperName(String developerName){
        developers.setText(developerName);
    }

    public void setSplashImage(int imageResId) {
        logo.setImageResource(imageResId);
    }


    public void setStatusBarColor(Window window, int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(color);
        }
    }

    public boolean isStatusBarColorWhite(Window window) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            int statusBarColor = window.getStatusBarColor();
            int whiteStatusBarColor = Color.WHITE; // White color's RGB value

            return statusBarColor == whiteStatusBarColor;
        }
        return false; // Return false if not supported or not white
    }

    public void dataIsReady(){
        MyJsonFetcher jsonFetcher = new MyJsonFetcher(this);
        jsonFetcher.fetchJsonData(LOAD_URL, new MyJsonFetcher.JsonCallback() {
            @Override
            public void onSuccess(JSONObject response) {
                try {
                    String status = response.getString("status");
                    String dev = response.getString("dev");
                
                    getNextStep(status,dev);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onError(VolleyError error) {

            }
        });

    }

    private void getNextStep(String status, String dev) {
        PrefManager prefManager = new PrefManager(this);
        prefManager.setString("dev",dev);
        prefManager.setString("status",status);
        if (dev.equals("YMG-Developers")){
            onFinishButtonPressed();
        }else {
            finish();
        }
    }

    abstract public void onFinishButtonPressed();

}
