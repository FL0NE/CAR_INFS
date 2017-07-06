package com.tsue.dsa.tsue;

import android.content.SharedPreferences;

/**
 * Created by DSA on 06.07.2017.
 */

public class SettingsManager {

    private static Setting setting;

    public static void setSetting(Setting setting1) {
        setting = setting1;
    }
    public static Setting getSetting() {
        return setting;
    }
}
