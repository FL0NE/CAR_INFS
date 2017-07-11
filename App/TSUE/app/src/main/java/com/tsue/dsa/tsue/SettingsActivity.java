package com.tsue.dsa.tsue;

import android.app.Activity;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.EditText;
import android.widget.Switch;

/**
 * Created by dsa on 06.07.2017.
 */

public class SettingsActivity extends Activity {
    private EditText benzin;
    private EditText engineLoad;
    private EditText engineTemp;
    private EditText speed;
    private Switch soundSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("lifecycle", "onCreate invoked");
        setContentView(R.layout.activity_options);
        benzin = (EditText) findViewById(R.id.num_benzin);
        engineLoad = (EditText) findViewById(R.id.editText);
        engineTemp = (EditText) findViewById(R.id.num_Mtemp);
        speed = (EditText) findViewById(R.id.num_maxSpeed);
        soundSwitch = (Switch) findViewById(R.id.sound_switch);
        Setting setting = SettingsManager.loadSettings(PreferenceManager.getDefaultSharedPreferences(this));
        soundSwitch.setChecked(setting.isEnableSound());
        benzin.setText(setting.getFuel()+"");
        engineLoad.setText(setting.getEngineLoad()+"");
        engineTemp.setText(setting.getEngineTemp()+"");
        speed.setText(setting.getSpeed()+"");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Setting setting = new Setting();
        setting.setFuel(benzin.getText().length() > 0 ? Double.valueOf(benzin.getText().toString()) : 0.0);
        setting.setEngineLoad(engineLoad.getText().length() > 0 ? Double.valueOf(engineLoad.getText().toString()) : 0.0);
        setting.setEngineTemp(engineTemp.getText().length() > 0 ? Double.valueOf(engineTemp.getText().toString()) : 0.0);
        setting.setSpeed(speed.getText().length() > 0 ? Double.valueOf(speed.getText().toString()) : 0.0);
        setting.setEnableSound(soundSwitch.isChecked());
        SettingsManager.setSetting(setting);
        SettingsManager.saveSettings(PreferenceManager.getDefaultSharedPreferences(this));
    }
}