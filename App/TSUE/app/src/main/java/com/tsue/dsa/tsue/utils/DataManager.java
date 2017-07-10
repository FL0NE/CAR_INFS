package com.tsue.dsa.tsue.utils;

import android.app.Activity;

import com.tsue.dsa.tsue.MainActivity;
import com.tsue.dsa.tsue.SpeedActivity;

import java.util.List;

/**
 * Created by DSA on 07.07.2017.
 */

public class DataManager {
    private static MainActivity mainActivity = null;
    private static SpeedActivity speedActivity = null;

    public static void subscribeMain(MainActivity activity) {
        mainActivity = activity;
    }

    public static void subscribeSpeed(SpeedActivity activity) {
        speedActivity = activity;
    }

    public static void onSpeedChanged(double speed) {
        if (mainActivity != null) {
            mainActivity.speedLoadChanged(speed);

        }
        if (speedActivity != null) {
            speedActivity.speedLoadChanged(speed);
        }

    }

}
