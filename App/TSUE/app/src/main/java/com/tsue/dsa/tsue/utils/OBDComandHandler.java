package com.tsue.dsa.tsue.utils;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.graphics.PorterDuff;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.tsue.dsa.tsue.R;
import com.tsue.dsa.tsue.model.ModeOptions;
import com.tsue.dsa.tsue.model.Modes;
import com.tsue.dsa.tsue.obd.MyOBDCommand;
import com.tsue.dsa.tsue.obd.PeriodicOBDConnector;

/**
 * Created by DSA on 06.07.2017.
 */

public class OBDComandHandler {

    private final Activity activity;
    private final TextView speedTextView;
    private final TextView throttlePosTextView;
    private final TextView coolantTempTextView;
    private final TextView tankTextView;
    private final TextView distanceTextView;
//    private final TextView timeTextView;
    private final TextView ambientTempTextView;
    private final TextView engineLoadTextView;
    private final ProgressBar speedProgressBar;
    private final ProgressBar rpmProgressBar;
    private final ProgressBar coolantTempProgressBar;
    private final ProgressBar tankProgressBar;
    private final ProgressBar engineLoadProgressBar;
    private int idOfSelectedButton;

    private TextView rpmTextView;
    private BluetoothDevice bluetoothDevice;
    private PeriodicOBDConnector odbConnector;
    private String textOfSelectedButton;

    public OBDComandHandler(Activity activity) {
        this.activity = activity;
        this.rpmTextView = (TextView) activity.findViewById(R.id.txt_RPM);
        this.speedTextView = (TextView) activity.findViewById(R.id.txt_Spd);
        this.throttlePosTextView = (TextView) activity.findViewById(R.id.txt_Gngschltng);
        this.coolantTempTextView = (TextView) activity.findViewById(R.id.txt_Mtemp);
        this.tankTextView = (TextView) activity.findViewById(R.id.txt_Tnkfllng);
        this.distanceTextView = (TextView) activity.findViewById(R.id.txt_Entfrng);
//        this.timeTextView = activity.findViewById(R.id.txt_);
        this.ambientTempTextView = (TextView) activity.findViewById(R.id.txt_Atemp);
        this.engineLoadTextView = (TextView) activity.findViewById(R.id.txt_Alastung);

        this.speedProgressBar = (ProgressBar) activity.findViewById(R.id.pbr_Spd);
        this.rpmProgressBar = (ProgressBar) activity.findViewById(R.id.pbr_RPM);
        this.coolantTempProgressBar = (ProgressBar) activity.findViewById(R.id.pbr_Mtemp);
        this.tankProgressBar = (ProgressBar) activity.findViewById(R.id.pbr_Tank);
        this.engineLoadProgressBar = (ProgressBar) activity.findViewById(R.id.pbr_Auslstng);
    }

    public void createCommands() {
        MyOBDCommand[] commands = new MyOBDCommand[]{
                createSingleCommand(ModeOptions.THROTTLE_POS, 1, throttlePosTextView),
                createSingleCommand(ModeOptions.DISTANCE, 1, distanceTextView),
//                createSingleCommand(ModeOptions.TIME, 1, timeTextView),
                createSingleCommand(ModeOptions.AMBIENT_TEMP, 1, ambientTempTextView),
                createSingleCommand(ModeOptions.RPM, 64.251, rpmTextView, rpmProgressBar),
                createSingleCommand(ModeOptions.SPEED, 1, speedTextView, speedProgressBar),
                createSingleCommand(ModeOptions.TANK, 1, tankTextView, tankProgressBar),
                createSingleCommand(ModeOptions.COOLANT_TEMP, 1, coolantTempTextView, coolantTempProgressBar),
                createSingleCommand(ModeOptions.ENGINE_LOAD, 1, engineLoadTextView, engineLoadProgressBar)
        };
        connectToBluetooth(commands);
    }

    private MyOBDCommand createSingleCommand(ModeOptions option, double modifier, TextView textView, ProgressBar progressBar) {
        MyOBDCommand result = new MyOBDCommand(Modes.SHOW_CURRENT_DATA, option, modifier, textView, progressBar);
        return result;
    }

    private MyOBDCommand createSingleCommand(ModeOptions option, double modifier, TextView textView) {
        MyOBDCommand result = new MyOBDCommand(Modes.SHOW_CURRENT_DATA, option, modifier, textView);
        return result;
    }

    public void setBluetoothDevice(BluetoothDevice bluetoothDevice) {
        this.bluetoothDevice = bluetoothDevice;
    }

    private void connectToBluetooth(MyOBDCommand... commands) {

        if (bluetoothDevice != null) {
            odbConnector = new PeriodicOBDConnector(bluetoothDevice, 5000, commands);
            odbConnector.start();
            Toast toast = Toast.makeText(activity, "Started", Toast.LENGTH_LONG);
            toast.show();
        } else {
            Toast toast = Toast.makeText(activity, "Please choose a bluetooth device!", Toast.LENGTH_LONG);
            toast.show();
        }
    }
}
