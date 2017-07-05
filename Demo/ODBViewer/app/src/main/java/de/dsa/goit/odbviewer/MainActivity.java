package de.dsa.goit.odbviewer;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import de.dsa.goit.odbviewer.model.ModeOptions;
import de.dsa.goit.odbviewer.model.Modes;
import de.dsa.goit.odbviewer.obd.MyOBDCommand;
import de.dsa.goit.odbviewer.obd.OBDConnector;
import de.dsa.goit.odbviewer.obd.PeriodicOBDConnector;
import de.dsa.goit.odbviewer.ui.ButtonClickHandler;
import de.dsa.goit.odbviewer.utils.BluetoothHelper;


/**
 * MainActivity, start on startup.
 */
public class MainActivity extends Activity {

    private ArrayAdapter<String> spinnerAdapter;
    private ArrayList<String> listItems = new ArrayList<String>();
    private Spinner bluetoothSpinner;
    private BluetoothHelper bluetoothHelper;
    private ButtonClickHandler buttonClickHandler;
    private BluetoothDevice bluetoothDevice;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("lifecycle", "onCreate invoked");
        setContentView(R.layout.activity_main);

        //init bluetooth selection spinner
        bluetoothSpinner = (Spinner) findViewById(R.id.bluetooth_spinner);
        spinnerAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, listItems);
        bluetoothSpinner.setAdapter(spinnerAdapter);
        addBluetoothSpinnerListener();

        buttonClickHandler = new ButtonClickHandler(this);

        //request bluetooth access
        bluetoothHelper = new BluetoothHelper();
        requestBluetooth();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //To-Do close socket
        Log.d("lifecycle", "onDestroy invoked");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //Request access again or update spinner with list of Bluetooth devices
        if (requestCode == BluetoothHelper.REQUEST_ENABLE_BT && resultCode == RESULT_OK) {
            bluetoothHelper.initBluetooth();
            updateAdapter(spinnerAdapter);
        } else if (requestCode == BluetoothHelper.REQUEST_ENABLE_BT) {
            Toast toast = Toast.makeText(this, "Need bluetooth to work!", Toast.LENGTH_LONG);
            toast.show();
            requestBluetooth();
        }
    }

    /**
     * Called by UI Buttons.
     * Just forwarding to {@link ButtonClickHandler} updateUI method.
     *
     * @param button the clicked button
     */
    public void updateUI(View button) {
        buttonClickHandler.updateUI(button);
    }

    /**
     * Called by UI Buttons.
     * Just forwarding to {@link ButtonClickHandler} updateAllValues method.
     *
     * @param button the clicked button
     */
    public void updateAllValues(View button) {
        buttonClickHandler.updateAllValues(button);
    }


    /**
     * Used the bluetoothHelper to request bluetooth access
     */
    private void requestBluetooth() {
        if (bluetoothHelper.requestBluetooth(this)) {
            updateAdapter(spinnerAdapter);
        }
    }

    /**
     * Updates the given ArrayAdapter
     *
     * @param adapter the Adapter that should be updated.
     */
    private void updateAdapter(ArrayAdapter adapter) {
        adapter.clear();
        adapter.addAll(bluetoothHelper.getPairedDeviceNames());
        adapter.notifyDataSetChanged();
    }


    /**
     * Adds a {@link android.widget.AdapterView.OnItemSelectedListener} to the bluetooth spinner.
     * The {@link android.widget.AdapterView.OnItemSelectedListener} will update the the bluetoothDevice and the {@link ButtonClickHandler}, whenever a item in the spinner was selected/deselected.
     */
    private void addBluetoothSpinnerListener() {
        bluetoothSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String item = listItems.get(position);
                bluetoothDevice = bluetoothHelper.getDevice(item);
                buttonClickHandler.setBluetoothDevice(bluetoothDevice);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                bluetoothDevice = null;
            }
        });
    }
}