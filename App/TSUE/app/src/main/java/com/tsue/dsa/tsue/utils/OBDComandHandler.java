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
    private final TextView timeTextView;
    private final TextView ambienTempTextView;
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
        this.rpmTextView = activity.findViewById(R.id.rpmTextView);
        this.speedTextView = activity.findViewById(R.id.speedTextView);
        this.throttlePosTextView = activity.findViewById(R.id.throttlePosTextView);
        this.coolantTempTextView = activity.findViewById(R.id.coolantTempTextView);
        this.tankTextView = activity.findViewById(R.id.tankTextView);
        this.distanceTextView = activity.findViewById(R.id.distanceTextView);
        this.timeTextView = activity.findViewById(R.id.timeTextView);
        this.ambienTempTextView = activity.findViewById(R.id.ambienTempTextView);
        this.engineLoadTextView = activity.findViewById(R.id.engineLoadTextView);

        this.speedProgressBar = activity.findViewById(R.id.speedProgressBar);
        this.rpmProgressBar = activity.findViewById(R.id.rpmProgressBar);
        this.coolantTempProgressBar = activity.findViewById(R.id.coolantTempProgressBar);
        this.tankProgressBar = activity.findViewById(R.id.tankProgressBar);
        this.engineLoadProgressBar = activity.findViewById(R.id.engineLoadProgressBar);
    }
*/
    public void createCommands() {
        MyOBDCommand[] commands = new MyOBDCommand[]{
                createSingleCommand(ModeOptions.THROTTLE_POS, 1, throttlePosTextView),
                createSingleCommand(ModeOptions.DISTANCE, 1, distanceTextView),
                createSingleCommand(ModeOptions.TIME, 1, timeTextView),
                createSingleCommand(ModeOptions.AMBIENT_TEMP, 1, ambienTempTextView),
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
