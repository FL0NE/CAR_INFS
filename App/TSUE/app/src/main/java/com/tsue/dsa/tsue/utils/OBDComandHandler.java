package com.tsue.dsa.tsue.utils;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.tsue.dsa.tsue.R;
import com.tsue.dsa.tsue.model.ModeOptions;
import com.tsue.dsa.tsue.model.Modes;
import com.tsue.dsa.tsue.obd.ModifierCalculator;
import com.tsue.dsa.tsue.obd.MyOBDCommand;
import com.tsue.dsa.tsue.obd.PeriodicOBDConnector;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DSA on 06.07.2017.
 */

public class OBDComandHandler {

    private final Activity activity;
    private final TextView speedTextView;
    private final TextView throttlePosTextView;
    private final TextView coolantTempTextView;
    private final TextView tankTextView;
    //    private final TextView timeTextView;
    private final TextView ambientTempTextView;
    private final TextView engineLoadTextView;
    private final ProgressBar speedProgressBar;
    private final ProgressBar rpmProgressBar;
    private final ProgressBar coolantTempProgressBar;
    private final ProgressBar tankProgressBar;
    private final ProgressBar engineLoadProgressBar;
    private final ProgressBar throttleProgressBar;
    private int idOfSelectedButton;
    private TextView rpmTextView;
    private BluetoothDevice bluetoothDevice;
    private PeriodicOBDConnector odbConnector;
    private String textOfSelectedButton;

    public OBDComandHandler(Activity activity) {
        this.activity = activity;

        this.rpmTextView = (TextView) activity.findViewById(R.id.rpm_wert);
        this.speedTextView = (TextView) activity.findViewById(R.id.kmh_wert);
        this.throttlePosTextView = (TextView) activity.findViewById(R.id.gas_wert);
        this.coolantTempTextView = (TextView) activity.findViewById(R.id.aussenTemp_wert);
        this.tankTextView = (TextView) activity.findViewById(R.id.tank_wert);
//        this.timeTextView = activity.findViewById(R.id.txt_);
        this.ambientTempTextView = (TextView) activity.findViewById(R.id.aussenTemp_wert);
        this.engineLoadTextView = (TextView) activity.findViewById(R.id.motortemp_wert);

        this.throttleProgressBar = (ProgressBar) activity.findViewById(R.id.pbr_gas);
        this.speedProgressBar = (ProgressBar) activity.findViewById(R.id.pbr_Spd);
        this.rpmProgressBar = (ProgressBar) activity.findViewById(R.id.pbr_RPM);
        this.coolantTempProgressBar = (ProgressBar) activity.findViewById(R.id.pbr_Mtemp);
        this.tankProgressBar = (ProgressBar) activity.findViewById(R.id.pbr_Tank);
        this.engineLoadProgressBar = (ProgressBar) activity.findViewById(R.id.pbr_Auslstng);

    }

    public void createCommands() {
        ModifierCalculator percentage = new ModifierCalculator() {
            @Override
            public double calculateModifier(double resultA, double resultB) {
                return resultA * 100 / 255.0;
            }
        };
        ModifierCalculator distance = new ModifierCalculator() {
            @Override
            public double calculateModifier(double resultA, double resultB) {
                return 256 * resultA + resultB;
            }
        };
        ModifierCalculator temp = new ModifierCalculator() {
            @Override
            public double calculateModifier(double resultA, double resultB) {
                return resultA - 40;
            }
        };
        ModifierCalculator rpm = new ModifierCalculator() {
            @Override
            public double calculateModifier(double resultA, double resultB) {
                return (256 * resultA + resultB) * 0.25;
            }
        };
        ModifierCalculator speed = new ModifierCalculator() {
            @Override
            public double calculateModifier(double resultA, double resultB) {
                return resultA;
            }
        };

        MyOBDCommand[] commands = new MyOBDCommand[]{
                createSingleCommand(ModeOptions.THROTTLE_POS, percentage, throttlePosTextView, throttleProgressBar),
//                createSingleCommand(ModeOptions.TIME, distance, timeTextView),
                createSingleCommand(ModeOptions.AMBIENT_TEMP, temp, ambientTempTextView),
                createSingleCommand(ModeOptions.RPM, rpm, rpmTextView, rpmProgressBar),
                createSingleCommand(ModeOptions.SPEED, speed, speedTextView, speedProgressBar),
                createSingleCommand(ModeOptions.TANK, percentage, tankTextView, tankProgressBar),
                createSingleCommand(ModeOptions.COOLANT_TEMP, temp, coolantTempTextView, coolantTempProgressBar),
                createSingleCommand(ModeOptions.ENGINE_LOAD, percentage, engineLoadTextView, engineLoadProgressBar)
        };
        connectToBluetooth(commands);
    }

    private MyOBDCommand createSingleCommand(ModeOptions option, ModifierCalculator modifier, TextView textView, ProgressBar progressBar) {
        MyOBDCommand result = new MyOBDCommand(activity, Modes.SHOW_CURRENT_DATA, option, modifier, textView, progressBar);
        return result;
    }

    private MyOBDCommand createSingleCommand(ModeOptions option, ModifierCalculator modifier, TextView textView) {
        MyOBDCommand result = new MyOBDCommand(activity, Modes.SHOW_CURRENT_DATA, option, modifier, textView);
        return result;
    }

    public void setBluetoothDevice(BluetoothDevice bluetoothDevice) {
        this.bluetoothDevice = bluetoothDevice;
    }

    private void connectToBluetooth(MyOBDCommand... commands) {

        if (bluetoothDevice != null) {
            odbConnector = new PeriodicOBDConnector(bluetoothDevice, 500, commands);
            odbConnector.start();
            Toast toast = Toast.makeText(activity, "Started", Toast.LENGTH_LONG);
            toast.show();
        } else {
            Toast toast = Toast.makeText(activity, "Please choose a bluetooth device!", Toast.LENGTH_LONG);
            toast.show();
        }
    }
}
