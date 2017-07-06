package com.tsue.dsa.tsue;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;

/**
 * Created by dsa on 05.07.2017.
 */

public class Calc extends Activity {
    public static final String PREFS_NAME = "TSUE";

    @Override
    protected void onCreate(Bundle state){
        super.onCreate(state);
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        int maxMotor = settings.getInt("maxMotor", 0);
        int minBenzin = settings.getInt("minBenzin", 0);
        int maxAuslastung = settings.getInt("maxAuslastung", 0);
    }

    @Override
    protected void onStop(){
        super.onStop();
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("maxMotor", 0);
        editor.putInt("minBenzin", 0);
        editor.putInt("maxAuslastung", 0);
        editor.commit();
    }
}
