package com.tsue.dsa.tsue;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.tsue.dsa.tsue.utils.BluetoothHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DSA on 06.07.2017.
 */

public class StartActivity extends Activity {

    private Button startButton;
    private Spinner bluetoothDeviceSpinner;
    private BluetoothDevice currentSelectedDevice;
    private List<String> listItems = new ArrayList<String>();
    private ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>()
    private BluetoothHelper bluetoothHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        this.startButton = (Button) findViewById(R.id.btn_strt);
        this.bluetoothDeviceSpinner = (Spinner) findViewById(R.id.bluetooth_spinner_startscreen);
        bluetoothHelper = new BluetoothHelper();
        initAdapter();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == BluetoothHelper.REQUEST_ENABLE_BT && resultCode == RESULT_OK) {
            bluetoothHelper.initBluetooth();
            spinnerAdapter(spinnerAdapter);
        } else if (requestCode == BluetoothHelper.REQUEST_ENABLE_BT) {
            Toast toast = Toast.makeText(this, "Need bluetooth to work!", Toast.LENGTH_LONG);
            toast.show();
            requestBluetooth();
        }
    }

    private void initAdapter() {
        arrayAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, listItems);
        bluetoothDeviceSpinner.setAdapter(arrayAdapter);
    }

    private void initListener() {
        this.startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
