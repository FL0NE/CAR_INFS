package com.tsue.dsa.tsue;

import android.app.Activity;

import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;

import java.util.ArrayList;

//import com.tsue.dsa.tsue.ui.ButtonClickHandler;
import com.tsue.dsa.tsue.utils.BluetoothDeivceManager;
import com.tsue.dsa.tsue.utils.BluetoothHelper;
import com.tsue.dsa.tsue.utils.DataManager;
import com.tsue.dsa.tsue.utils.OBDCommandHandler;
import com.tsue.dsa.tsue.utils.OnDataChangedListener;


/**
 * MainActivity, start on startup.
 */
public class MainActivity extends Activity implements OnDataChangedListener {

    private ArrayAdapter<String> spinnerAdapter;
    private ArrayList<String> listItems = new ArrayList<String>();
    private Spinner bluetoothSpinner;
    private BluetoothHelper bluetoothHelper;
    private OBDCommandHandler commandHandler;
    private BluetoothDevice bluetoothDevice;
    private Setting setting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("lifecycle", "onCreate invoked");
        setContentView(R.layout.activity_main);
        commandHandler = new OBDCommandHandler(this);
        bluetoothDevice = BluetoothDeivceManager.getBluetoothDevice();
        commandHandler.setBluetoothDevice(bluetoothDevice);
        commandHandler.createCommands();
        ImageButton btn_einstellungen = (ImageButton) findViewById(R.id.btn_settings);
        btn_einstellungen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent settingsIntent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(settingsIntent);
            }
        });



     


        //TEST BUTTON

        Button tstbtn = (Button) findViewById(R.id.btn_test);
        tstbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tstIntent = new Intent(MainActivity.this, SpeedActivity.class);
                startActivity(tstIntent);
            }
        });

        //TEST ENDE
        Setting setting = SettingsManager.loadSettings(PreferenceManager.getDefaultSharedPreferences(this));
        SettingsManager.setSetting(setting);

//        SharedPreferences preferences = getPreferences(Context.MODE_PRIVATE);
//        Setting setting = new Setting();
//        setting.setEnableSound(preferences.getBoolean(SettingsManager.SOUND_ENABLED_KEY, false));
//        setting.setEngineLoad(Double.valueOf(preferences.getString((SettingsManager.ENGINE_LOAD_KEY), 0.0 + "")));
//        setting.setEngineTemp(Double.valueOf(preferences.getString(SettingsManager.ENGINE_TEMP_KEY, 0.0 + "")));
//        setting.setFuel(Double.valueOf(preferences.getString(SettingsManager.FUEL_KEY, 0.0 + "")));
//        setting.setSpeed(Double.valueOf(preferences.getString(SettingsManager.SPEED, 0.0 + "")));
//        SettingsManager.setSetting(setting);
        
    }

    private void registerOnDataChangedListener() {
        DataManager.subscribeMain(this);
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
    @Override
    public void engineLoadChanged(Double load) {

    }

    @Override
    public void throttleLoadChanged(Double load) {

    }

    @Override
    public void fuelLoadChanged(Double load) {

    }

    @Override
    public void rpmLoadChanged(Double load) {

    }

    @Override
    public void speedLoadChanged(Double load) {

    }

    @Override
    public void engineTempChanged(Double load) {

    }
}