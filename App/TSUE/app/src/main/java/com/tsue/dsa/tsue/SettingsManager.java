package com.tsue.dsa.tsue;

import android.content.SharedPreferences;

/**
 * Created by DSA on 06.07.2017.
 */

public class SettingsManager {


    public void saveSettings(Setting setting, SharedPreferences preferences) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("maxTemp", setting.getMaxTemp());
        editor.putInt("minTemp", setting.getMinTemp());
        editor.apply();
    }

    public Setting getSettings(SharedPreferences preferences) {
        Setting result = new Setting();

        result.setMaxTemp(preferences.getInt("maxTemp", 1));
        result.setMinTemp(preferences.getInt("minTemp", 1));
        return result;
    }
}
