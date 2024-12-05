package com.ymg.ymgtools;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ymg.ymgdevelopers.YmgTools;

import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    RelativeLayout relativeLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        YmgTools.getBack(MainActivity.this);
        TextView textView = findViewById(R.id.textView);
        relativeLayout = findViewById(R.id.relativeLayout);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uuid = UUID.randomUUID().toString();
               String tt = YmgTools.jZpTkLg(MainActivity.this, uuid);
                Log.d("ymgs", tt+"");

            }
        });
    }
}