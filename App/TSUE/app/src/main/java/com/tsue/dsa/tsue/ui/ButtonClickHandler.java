package com.tsue.dsa.tsue.ui;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import com.tsue.dsa.tsue.R;
import com.tsue.dsa.tsue.model.ModeOptions;
import com.tsue.dsa.tsue.model.Modes;
import com.tsue.dsa.tsue.obd.MyOBDCommand;
import com.tsue.dsa.tsue.obd.PeriodicOBDConnector;

/**
 * Manages the enabling/disabling, text changes and start/stop of the {@link PeriodicOBDConnector}.
 * Created by abr on 22.06.2017.
 */

public class ButtonClickHandler {
    private Activity activity;
    private int[] buttonIDs = {R.id.enginetempButton, R.id.airflowButton, R.id.speedButton, R.id.positionButton, R.id.rpmButton};
    private String textOfSelectedButton = "Stop";
    private int idOfSelectedButton = -1;
    private BluetoothDevice bluetoothDevice = null;
    private PeriodicOBDConnector odbConnector = null;


    /**
     * Constructor
     * @param activity the active activity. Needed to find and update UI elements.
     */
    public ButtonClickHandler(Activity activity){
        this.activity = activity;
    }

    /**
     * Creates a {@link MyOBDCommand} with the getCommandForButton method. For the clicked button.
     * @param button the button, which ID will be used.
     */
    public void updateUI(View button) {
        MyOBDCommand com = getCommandForButton(button.getId());
        updateButtonsAndStartOBDConnector(button, com);
    }

    /**
     * Creates a {@link MyOBDCommand} with the getCommandForButton method. For all buttons, which IDs are in the buttonIDs Array.
     * @param button the button which was clicked.
     */
    public void updateAllValues(View button) {
        List<MyOBDCommand> commands = new ArrayList<>();
        for (int buttonID : buttonIDs) {
            commands.add(getCommandForButton(buttonID));
        }
        MyOBDCommand[] commandsArr = new MyOBDCommand[commands.size()];
        commandsArr = commands.toArray(commandsArr);
        updateButtonsAndStartOBDConnector(button, commandsArr);
    }

    /**
     * Creates a OBDCommand based on the given button id.
     * @param buttonID The ID of the Button for which you want to generate the {@link MyOBDCommand}
     * @return the generated {@link MyOBDCommand}, can be null
     */
    private MyOBDCommand getCommand(ModeOptions option,double multiplicator,int id) {
        ProgressBar progressBar;
        MyOBDCommand command = new MyOBDCommand(Modes.SHOW_CURRENT_DATA,option,multiplicator,progressBar);
    }
    private  MyOBDCommand getCommandForButton(int buttonID) {
        MyOBDCommand com = null;
        TextView component;
        switch (buttonID) {
            case R.id.speedButton:
                component = ((TextView) activity.findViewById(R.id.speedDisplay));
                com = new MyOBDCommand(Modes.SHOW_CURRENT_DATA, ModeOptions.SPEED, 1, component);
                break;
            case R.id.rpmButton:
                component = ((TextView) activity.findViewById(R.id.rpmDisplay));
                com = new MyOBDCommand(Modes.SHOW_CURRENT_DATA, ModeOptions.RPM, 64.251, component);
                break;
            case R.id.positionButton:
                component = ((TextView) activity.findViewById(R.id.positionDisplay));
                com = new MyOBDCommand(Modes.SHOW_CURRENT_DATA, ModeOptions.THROTTLE_POS, 0.392, component);
                break;
            case R.id.enginetempButton:
                component = ((TextView) activity.findViewById(R.id.engineTempDisplay));
                com = new MyOBDCommand(Modes.SHOW_CURRENT_DATA, ModeOptions.COOLANT_TEMP, 0.843, component);
                break;
            case R.id.airflowButton:
                component = ((TextView) activity.findViewById(R.id.airflowDisplay));
                com = new MyOBDCommand(Modes.SHOW_CURRENT_DATA, ModeOptions.MAF, 2.568, component);
                break;
            default:
        }
        return com;
    }

    /**
     * Resets the button to enable, if the pressed button was the button, that was pressed last.
     * Otherwise all buttons, without the pressed one, will be disabled. The text of the pressed button will change to "Stop". Then the {@link PeriodicOBDConnector} will be created and executed.
     * @param button
     * @param commands
     */
    private void updateButtonsAndStartOBDConnector(View button, MyOBDCommand... commands) {
        if (idOfSelectedButton == button.getId()) {
            resetButton();
            idOfSelectedButton = -1;
            odbConnector.stopConnector();
            odbConnector = null;
            Toast toast = Toast.makeText(activity, "Stopped", Toast.LENGTH_LONG);
            toast.show();
        } else if (bluetoothDevice != null) {
            idOfSelectedButton = button.getId();
            textOfSelectedButton = ((Button) button).getText().toString();
            setEnableOfAllButtons(false);
            ((Button) button).setText("Stop");
            button.setEnabled(true);

            if (odbConnector != null) {
                odbConnector.stopConnector();
            }
            odbConnector = new PeriodicOBDConnector(bluetoothDevice, 5000, commands);
            odbConnector.start();
            Toast toast = Toast.makeText(activity, "Started", Toast.LENGTH_LONG);
            toast.show();
        } else {
            Toast toast = Toast.makeText(activity, "Please choose a bluetooth device!", Toast.LENGTH_LONG);
            toast.show();
        }
    }

    /**
     * Sets all buttons to enable and resets the text of the pressed button to textOfSelectedButton
     */
    private void resetButton() {
        if (idOfSelectedButton != -1) {
            Button button = (Button) activity.findViewById(idOfSelectedButton);
            button.setText(textOfSelectedButton);
        }

        setEnableOfAllButtons(true);
    }

    /**
     * calls the setEnable method for each of the buttons, which IDs are oin the buttonID array.
     * @param enable the value, that will be set to all buttons
     */
    private void setEnableOfAllButtons(boolean enable) {
        for (int id : buttonIDs) {
            activity.findViewById(id).setEnabled(enable);
        }
        activity.findViewById(R.id.updateAllButton).setEnabled(enable);
    }

    public void setBluetoothDevice(BluetoothDevice bluetoothDevice) {
        this.bluetoothDevice = bluetoothDevice;
    }
}
