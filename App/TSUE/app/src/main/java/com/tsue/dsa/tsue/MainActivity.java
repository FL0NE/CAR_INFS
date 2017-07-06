package com.tsue.dsa.tsue;

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

import com.tsue.dsa.tsue.model.ModeOptions;
import com.tsue.dsa.tsue.model.Modes;
import com.tsue.dsa.tsue.obd.MyOBDCommand;
import com.tsue.dsa.tsue.obd.OBDConnector;
import com.tsue.dsa.tsue.obd.PeriodicOBDConnector;
import com.tsue.dsa.tsue.ui.ButtonClickHandler;
import com.tsue.dsa.tsue.utils.BluetoothHelper;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;


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
		
		
        //VARIABLEN

        TextView hohe = (TextView) findViewById(R.id.hohe_wert);
        TextView entfernung = (TextView) findViewById(R.id.entf_wert);
        TextView gang = (TextView) findViewById(R.id.gang_wert);

        /*

        hohe.setText(HEIGHT);
        entfernung.setText(DISTANCE);
        gang.setText(THROTTLE_POS);

        */

        ProgressBar aussenTemp_bar = (ProgressBar) findViewById(R.id.pbr_Atemp);
        ProgressBar speed_bar = (ProgressBar) findViewById(R.id.pbr_Spd);
        ProgressBar rpm_bar = (ProgressBar) findViewById(R.id.pbr_RPM);
        ProgressBar gas_bar = (ProgressBar) findViewById(R.id.pbr_Gsdrkng);
        ProgressBar motorTemp_bar = (ProgressBar) findViewById(R.id.pbr_Mtemp);
        ProgressBar tankFuel_bar = (ProgressBar) findViewById(R.id.pbr_Tnkfllng);
        ProgressBar auslastung_bar = (ProgressBar) findViewById(R.id.pbr_Auslstng);

        //SPEED
        //RPM
        //THROTTLE_POS
        //COOLANT_TEMP
        //MAF -> luft flow
        //TANK
        //Distance
        //Time
        //ENGINE_LOAD
/*

        int maxAussenTemp = 40;
        int aussenTemp = (AMBIENT_TEMP / maxAussenTemp)*100;

        int maxSpeed = 200;
        int speed_prozent = (SPEED / maxSpeed) * 100;

        int rpm_max = 0;
        int rpm_prozent = (RPM / rpm_max) * 100;

        int maxMotor = 0;



        aussenTemp_bar.setProgress(AMBIENT_TEMP);
        speed_bar.setProgress(SPEED);
        rpm_bar.setProgress(RPM);
        gas_bar.setProgress(0);
        motorTemp_bar.setProgress(COOLANT_TEMP);
        tankFuel_bar.setProgress(TANK);
        auslastung_bar.setProgress(ENGINE_LOAD);

*/
        //ENDE VARIABLEN



        // Settings_Buton

        Button btn_einstellungen = (Button) findViewById(R.id.btn_enstllngn);
        btn_einstellungen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent settingsIntent = new Intent(MainActivity.this, options.class);
                startActivity(settingsIntent);
            }
        });
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