package com.tsue.dsa.tsue;

import android.app.Activity;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

//import com.tsue.dsa.tsue.ui.ButtonClickHandler;
import com.tsue.dsa.tsue.utils.BluetoothDeivceManager;
import com.tsue.dsa.tsue.utils.BluetoothHelper;
import com.tsue.dsa.tsue.utils.OBDComandHandler;


/**
 * MainActivity, start on startup.
 */
public class MainActivity extends Activity {

    private ArrayAdapter<String> spinnerAdapter;
    private ArrayList<String> listItems = new ArrayList<String>();
    private Spinner bluetoothSpinner;
    private BluetoothHelper bluetoothHelper;
    private OBDComandHandler commandHandler;
    private BluetoothDevice bluetoothDevice;
    private Setting setting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("lifecycle", "onCreate invoked");
        setContentView(R.layout.activity_main);
        commandHandler = new OBDComandHandler(this);
        bluetoothDevice = BluetoothDeivceManager.getBluetoothDevice();
        commandHandler.setBluetoothDevice(bluetoothDevice);
        commandHandler.createCommands();
        TextView hohe = (TextView) findViewById(R.id.hohe_wert);
        TextView entfernung = (TextView) findViewById(R.id.entf_wert);
        TextView gang = (TextView) findViewById(R.id.gang_wert);
        ImageButton btn_einstellungen = (ImageButton) findViewById(R.id.btn_settings);
        btn_einstellungen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent settingsIntent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(settingsIntent);
            }
        });
        SharedPreferences preferences = getPreferences(Context.MODE_PRIVATE);
        Setting setting = new Setting();
        setting.setEnableSound(preferences.getBoolean(SettingsManager.SOUND_ENABLED_KEY, false));
        setting.setEngineLoad(Double.valueOf(preferences.getString((SettingsManager.ENGINE_LOAD_KEY), 0.0 + "")));
        setting.setEngineTemp(Double.valueOf(preferences.getString(SettingsManager.ENGINE_TEMP_KEY, 0.0 + "")));
        setting.setFuel(Double.valueOf(preferences.getString(SettingsManager.FUEL_KEY, 0.0 + "")));
        setting.setSpeed(Double.valueOf(preferences.getString(SettingsManager.SPEED, 0.0 + "")));
        SettingsManager.setSetting(setting);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //To-Do close socket
        Log.d("lifecycle", "onDestroy invoked");
    }


    /**
     * Adds a {@link android.widget.AdapterView.OnItemSelectedListener} to the bluetooth spinner.
     * The {@link android.widget.AdapterView.OnItemSelectedListener} will update the the bluetoothDevice and the {@link //ButtonClickHandler}, whenever a item in the spinner was selected/deselected.
     */
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
}