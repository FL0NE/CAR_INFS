package com.tsue.dsa.tsue;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

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

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Log.d("lifecycle", "onCreate invoked");
        setContentView(R.layout.activity_options);

        bluetoothSpinner = (Spinner) findViewById(R.id.bluetooth_spinner);
        spinnerAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, listItems);
        bluetoothSpinner.setAdapter(spinnerAdapter);
        addBluetoothSpinnerListener();
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
}