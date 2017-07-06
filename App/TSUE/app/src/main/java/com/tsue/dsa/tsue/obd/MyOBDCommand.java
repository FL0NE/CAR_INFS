package com.tsue.dsa.tsue.obd;

import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.pires.obd.commands.ObdCommand;
import com.github.pires.obd.enums.AvailableCommandNames;
import com.tsue.dsa.tsue.Setting;
import com.tsue.dsa.tsue.SettingsManager;
import com.tsue.dsa.tsue.model.ModeOptionValues;
import com.tsue.dsa.tsue.model.ModeOptions;
import com.tsue.dsa.tsue.model.Modes;
import com.tsue.dsa.tsue.model.Unit;

/**
 * MyOBDCommand is a OBDCommand.
 * It can be used to send a Request via Bluetooth to a OBD Interface.
 * Created by abr
 */

public class MyOBDCommand extends ObdCommand {
    private final ProgressBar progressbarUpdate;
    private String result = "";
    private double modifier = 0.0;
    private TextView textViewUpdate;
    private ModeOptions option;
    Unit unit = null;

//    /**
//     * Constructs a OBDCommand.
//     * @param obdCommand the hexadecimal request, which will be send to the OBDDevice.
//     */
//    public MyOBDCommand(String obdCommand) {
//        super(obdCommand);
//    }

    /**
     * Constructs a OBDCommand.
     *
     * @param mode   the Mode of the request
     * @param option the mode option of the request
     */
    public MyOBDCommand(Modes mode, ModeOptions option, double modifier, TextView textViewUpdate, ProgressBar progressbarUpdate) {
        super((mode.getValue()) + " " + option.getValue());
        this.modifier = modifier;
        this.textViewUpdate = textViewUpdate;
        this.option = option;
        this.progressbarUpdate = progressbarUpdate;
    }

    public MyOBDCommand(Modes mode, ModeOptions option, double modifier, TextView textView) {
        this(mode, option, modifier, textView, null);
    }

//    /**
//     * Constructs a OBDCommand.
//     * @param mode the Mode of the request
//     * @param option the mode option of the request
//     * @param unit the Unit of the return value. Will be added to the formatted result.
//     */
//    public MyOBDCommand(Modes mode, ModeOptions option, Unit unit) {
//        super((mode.getValue())+" "+ option.getValue());
//        this.unit = unit;
//    }

    @Override
    protected void performCalculations() {
        result = buffer.get(2).toString();
    }

    @Override
    public String getFormattedResult() {
        if (unit != null) {
            return result + " " + unit.getSymbol();
        }
        return result;
    }

    @Override
    public String getCalculatedResult() {
        if (modifier != 0.0) {
            try {
                return "" + (int) Math.round(Integer.valueOf(result) * modifier);
            } catch (NumberFormatException nfe) {
                return result;
            }
        }
        return result;
    }

    private int getCalculatedPercentage() {
        double finalResult = 0;
        if (modifier != 0.0) {
            int integer = Integer.valueOf(result);
            finalResult = integer * (int) modifier;
        }
        Integer currentValue = 1;
        for (ModeOptionValues value : ModeOptionValues.values()) {
            if (value.getOption() == option) {
                currentValue = value.getValue();
            }
        }
        return (int) ((finalResult / currentValue) * 100);
    }

    @Override
    public String getName() {
        return AvailableCommandNames.SPEED.getValue();
    }

    /**
     * Sets the return value of the getCalculatedResult to the text view, given in the constructor.
     */
    public void updateUI() {
<<<<<<< HEAD
        textViewUpdate.setText(getCalculatedResult());
        if (option == ModeOptions.SPEED || option == ModeOptions.RPM || option == ModeOptions.COOLANT_TEMP || option == ModeOptions.TANK || option == ModeOptions.ENGINE_LOAD || option == ModeOptions.THROTTLE_POS || option == ModeOptions.COOLANT_TEMP) {
            progressbarUpdate.setProgress(getCalculatedPercentage());
=======
        String value = getCalculatedResult();
        try {
            if (value == null || value.equals("") || value.isEmpty() || textViewUpdate == null) {
                return;
            }
            Setting setting = SettingsManager.getSetting();
            Log.i("info", "fuel -> " + setting.getFuel());
            textViewUpdate.setText(value);
            if (option == ModeOptions.SPEED || option == ModeOptions.RPM || option == ModeOptions.COOLANT_TEMP || option == ModeOptions.TANK || option == ModeOptions.ENGINE_LOAD) {
                progressbarUpdate.setProgress(getCalculatedPercentage());
            }
        } catch (Exception e) {
            e.printStackTrace();
>>>>>>> a64e2900a10bc72357938b4fcfbc0d65787549c3
        }

    }
}
