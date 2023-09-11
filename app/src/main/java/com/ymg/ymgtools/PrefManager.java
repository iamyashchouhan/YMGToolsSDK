package com.ymg.ymgtools;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefManager {


    private static final String PREF_NAME = "ymg_tool";
    public static final String LOAD_URL = "https://iamyashchouhan.github.io/checkdev/prime-tv.php";
    Context _context;
    SharedPreferences.Editor editor;
    SharedPreferences pref;


    public PrefManager(Context context) {
        this._context = context;
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, 0);
        this.pref = sharedPreferences;
        this.editor = sharedPreferences.edit();
    }

    public void setBoolean(String str, Boolean bool) {
        this.editor.putBoolean(str, bool.booleanValue());
        this.editor.commit();
    }

    public void setString(String str, String str2) {
        this.editor.putString(str, str2);
        this.editor.commit();
    }

    public void setInt(String str, int i) {
        this.editor.putInt(str, i);
        this.editor.commit();
    }

    public boolean getBoolean(String str) {
        return this.pref.getBoolean(str, true);
    }

    public void remove(String str) {
        if (this.pref.contains(str)) {
            this.editor.remove(str);
            this.editor.commit();
        }
    }

    public String getString(String str) {
        return this.pref.contains(str) ? this.pref.getString(str, null) : "";
    }

    public int getInt(String str) {
        return this.pref.getInt(str, 2);
    }




    public void updateWallpaperColumns(int columns) {
        editor.putInt("wallpaper_columns", columns);
        editor.apply();
    }

    public Integer getAppOpenToken() {
        return pref.getInt("app_open_token", 0);
    }

    public void updateAppOpenToken(int value) {
        editor.putInt("app_open_token", value);
        editor.apply();
    }

    public Integer getInAppReviewToken() {
        return pref.getInt("in_app_review_token", 0);
    }

    public void updateInAppReviewToken(int value) {
        editor.putInt("in_app_review_token", value);
        editor.apply();
    }


    //save
    public void setNightModeState(Boolean state){
        SharedPreferences.Editor editor= pref.edit();
        editor.putBoolean("NightMode",state);
        editor.commit();
    }

    //load
    public Boolean loadNightModeState(){
        Boolean state = pref.getBoolean("NightMode",true);
        return state;
    }

}
