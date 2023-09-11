package com.ymg.ymgtools;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;

import com.ymg.ymgdevelopers.YmgActivity;
import com.ymg.ymgdevelopers.YmgOnboardItem;

import java.util.ArrayList;
import java.util.List;

public class OnBoardActivity extends YmgActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PrefManager prefManager = new PrefManager(this);
        if (prefManager.loadNightModeState()==true){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        List<YmgOnboardItem> onboardItems = new ArrayList<>();

        YmgOnboardItem onboardItemList = new YmgOnboardItem();
        onboardItemList.setTitle("Seamless Payments with Our Secure Wallet");
        onboardItemList.setDescription("Say goodbye to hassle and hello to seamless transaction with Coffee's secure wallet. Making payments has never been easier");
        onboardItemList.setImage(R.drawable.screen_1);

        YmgOnboardItem onboardItemList2 = new YmgOnboardItem();
        onboardItemList2.setTitle("Seamless Payments with Our Secure Wallet");
        onboardItemList2.setDescription("Say goodbye to hassle and hello to seamless transaction with Coffee's secure wallet. Making payments has never been easier");
        onboardItemList2.setImage(R.drawable.best);

        YmgOnboardItem onboardItemList3 = new YmgOnboardItem();
        onboardItemList3.setTitle("No Login Required");
        onboardItemList3.setDescription("Login is not Required in our new app");
        onboardItemList3.setImage(R.mipmap.ic_launcher);

        onboardItems.add(onboardItemList);
        onboardItems.add(onboardItemList2);
        onboardItems.add(onboardItemList3);

        setOnboardPagesReady(onboardItems);

        //setupOnBoardingItems();
        setupViewIdInit();
        setupIndicators();
        setCurrentIndicator(0);
        setupViewId();
    }

    @Override
    public void onFinishButtonPressed() {
        startActivity(new Intent(this,SplashActivity.class));
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }
}