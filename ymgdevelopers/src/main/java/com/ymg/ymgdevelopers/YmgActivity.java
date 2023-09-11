package com.ymg.ymgdevelopers;

import android.animation.ArgbEvaluator;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.ColorInt;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public abstract class YmgActivity extends AppCompatActivity implements View.OnClickListener,ViewPager.OnPageChangeListener {


    private YmgOnboardingAdapter onboardingAdapter;
    LinearLayout linearLayout;
    ViewPager2 viewPager;
    Button btnNext;
    Button btnSkip;
    Button btnFinish;
    int colorAccent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);
        //setStatusBackgroundColor();
        //hideActionBar();

        Window window = getWindow(); // Replace with your Window instance
        int newStatusBarColor = getResources().getColor(R.color.colorStatusBar); // Replace with your color
        setStatusBarColor(window, newStatusBarColor);

        boolean isWhiteStatusBar = isStatusBarColorWhite(window);

        if (isWhiteStatusBar){
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            colorAccent = ContextCompat.getColor(this, R.color.colorAccent);
        }else {
            colorAccent = ContextCompat.getColor(this, R.color.black);
        }

        btnNext = findViewById(R.id.btnNext);
        btnSkip = findViewById(R.id.btnSkip);
        btnFinish = findViewById(R.id.btnFinish);

        btnNext.setOnClickListener(this);
        btnSkip.setOnClickListener(this);
        btnFinish.setOnClickListener(this);

        btnSkip.setBackgroundResource(R.drawable.btn_background_light);
        btnNext.setBackgroundResource(R.drawable.btn_background);
        btnFinish.setBackgroundResource(R.drawable.btn_background);

        btnSkip.setTextColor(colorAccent);
        btnSkip.setBackgroundTintList(ColorStateList.valueOf(colorAccent));

    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        int nextPageIndex = viewPager.getCurrentItem() + 1;
        if (i == R.id.btnNext) {
            viewPager.setCurrentItem(nextPageIndex, true);
        } else if (i == R.id.btnSkip) {
            viewPager.setCurrentItem(onboardingAdapter.getItemCount(),true);
        } else if (i == R.id.btnFinish) {
            onFinishButtonPressed();
        }
    }

    public void setupViewId(){
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setCurrentIndicator(position);
                if (position == onboardingAdapter.getItemCount()-1){
                    btnNext.setVisibility(View.GONE);
                    btnSkip.setVisibility(View.GONE);
                    btnFinish.setVisibility(View.VISIBLE);
                }else {
                    btnNext.setVisibility(View.VISIBLE);
                    btnSkip.setVisibility(View.VISIBLE);
                    btnFinish.setVisibility(View.GONE);
                }
            }
        });
    }

    public void setupViewIdInit(){
        linearLayout = findViewById(R.id.linearLayout);
        viewPager = findViewById(R.id.onBoardingViewPager);
        viewPager.setAdapter(onboardingAdapter);
    }


    public void setOnboardPagesReady(List<YmgOnboardItem> pages) {

        onboardingAdapter = new YmgOnboardingAdapter(pages);
    }

    public void setupIndicators(){
        ImageView[] indicators = new ImageView[onboardingAdapter.getItemCount()];
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(8,0,8,0);
        for (int i = 0; i < indicators.length; i++){
            indicators[i] = new ImageView(getApplicationContext());
            indicators[i].setImageDrawable(ContextCompat.getDrawable(
                    getApplicationContext(),
                    R.drawable.indicator_inactive
            ));

            indicators[i].setLayoutParams(layoutParams);
            linearLayout.addView(indicators[i]);
        }
    }

    public void setCurrentIndicator(int index){
        int childCount = linearLayout.getChildCount();
        for (int i  = 0; i < childCount; i++){
            ImageView imageView = (ImageView) linearLayout.getChildAt(i);
            if (i ==index){
                imageView.setImageDrawable(ContextCompat.getDrawable(
                        getApplicationContext(),
                        R.drawable.indicator_active
                ));
                int color = ContextCompat.getColor(this, R.color.colorPrimary);
                imageView.setImageTintList(ColorStateList.valueOf(color));
            }else{
                imageView.setImageDrawable(ContextCompat.getDrawable(
                        getApplicationContext(),
                        R.drawable.indicator_inactive
                ));
                imageView.setImageTintList(ColorStateList.valueOf(Color.GRAY));
            }
        }
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

    private int darkenColor(@ColorInt int color) {
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        hsv[2] *= 0.9f;
        return Color.HSVToColor(hsv);
    }

    public void setStatusBackgroundColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.black_transparent));
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private void hideActionBar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }



    abstract public void onFinishButtonPressed();

}
