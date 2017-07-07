package com.tsue.dsa.tsue;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by DSA on 06.07.2017.
 */

public class SettingsManager {

    private static Setting setting;

    public static void setSetting(Setting setting1) {
        setting = setting1;
    }

    public static final String SOUND_ENABLED_KEY = "sound_enabled";
    public static final String ENGINE_LOAD_KEY = "engine_load";
    public static final String ENGINE_TEMP_KEY = "engine_temp";
    public static final String FUEL_KEY = "fuel_key";
    public static final String SPEED = "SPEED2";

    public static Setting getSetting() {
        if (setting == null) {
            setting = new Setting();
            setting.setEnableSound(false);
            setting.setEngineLoad(0.0);
            setting.setEngineTemp(0.0);
            setting.setFuel(0.0);
            setting.setSpeed(0.0);
        }
        return setting;
    }

    public static void saveSettings(SharedPreferences preferences) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(SOUND_ENABLED_KEY, setting.isEnableSound());
        editor.putString(ENGINE_LOAD_KEY, setting.getEngineLoad() + "");
        editor.putString(ENGINE_TEMP_KEY, setting.getEngineTemp() + "");
        editor.putString(FUEL_KEY, setting.getFuel() + "");
        editor.putString(SPEED, setting.getSpeed() + "");
        editor.apply();
    }

    public static Setting loadSettings(SharedPreferences preferences) {
        Setting setting = new Setting();
        setting.setEnableSound(preferences.getBoolean(SettingsManager.SOUND_ENABLED_KEY, false));
        setting.setEngineLoad(Double.valueOf(preferences.getString((SettingsManager.ENGINE_LOAD_KEY), 0.0 + "")));
        setting.setEngineTemp(Double.valueOf(preferences.getString(SettingsManager.ENGINE_TEMP_KEY, 0.0 + "")));
        setting.setFuel(Double.valueOf(preferences.getString(SettingsManager.FUEL_KEY, 0.0 + "")));
        setting.setSpeed(Double.valueOf(preferences.getString(SettingsManager.SPEED, 0.0 + "")));
        return setting;
    }
}
