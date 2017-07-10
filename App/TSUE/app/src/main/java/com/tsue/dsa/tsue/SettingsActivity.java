package com.tsue.dsa.tsue;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;

import com.tsue.dsa.tsue.utils.BluetoothHelper;
import com.tsue.dsa.tsue.utils.OBDComandHandler;

import java.util.ArrayList;

/**
 * Created by dsa on 06.07.2017.
 */

public class SettingsActivity extends Activity {
    private Spinner bluetoothSpinner;
    private ArrayAdapter<String> spinnerAdapter;
    private ArrayList<String> listItems = new ArrayList<String>();
    private BluetoothHelper bluetoothHelper;
    private OBDComandHandler commandHandler;
    private BluetoothDevice bluetoothDevice;

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
        Setting setting = SettingsManager.loadSettings(getPreferences(Context.MODE_PRIVATE));
        soundSwitch.setChecked(setting.isEnableSound());
        benzin.setText(setting.getFuel()+"");
        engineLoad.setText(setting.getEngineLoad()+"");
        engineTemp.setText(setting.getEngineTemp()+"");
        speed.setText(setting.getSpeed()+"");
    }

    private void addBluetoothSpinnerListener() {
        bluetoothSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String item = listItems.get(position);
                bluetoothDevice = bluetoothHelper.getDevice(item);
                commandHandler.setBluetoothDevice(bluetoothDevice);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                bluetoothDevice = null;
            }
        });

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
        SettingsManager.saveSettings(this.getPreferences(Context.MODE_PRIVATE));
    }
}