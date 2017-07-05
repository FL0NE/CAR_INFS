package de.dsa.goit.odbviewer.obd;

import android.widget.TextView;

import com.github.pires.obd.commands.ObdCommand;
import com.github.pires.obd.enums.AvailableCommandNames;

import de.dsa.goit.odbviewer.model.ModeOptions;
import de.dsa.goit.odbviewer.model.Modes;
import de.dsa.goit.odbviewer.model.Unit;

/**
 * MyOBDCommand is a OBDCommand.
 * It can be used to send a Request via Bluetooth to a OBD Interface.
 * Created by abr
 */

public class MyOBDCommand extends ObdCommand{
    private String result = "";
    private double modifier = 0.0;
    private TextView viewToUpdate;
    Unit unit =null;

//    /**
//     * Constructs a OBDCommand.
//     * @param obdCommand the hexadecimal request, which will be send to the OBDDevice.
//     */
//    public MyOBDCommand(String obdCommand) {
//        super(obdCommand);
//    }

    /**
     * Constructs a OBDCommand.
     * @param mode the Mode of the request
     * @param option the mode option of the request
     */
    public MyOBDCommand(Modes mode, ModeOptions option, double modifier, TextView viewToUpdate) {
        super((mode.getValue())+" "+ option.getValue());
        this.modifier = modifier;
        this.viewToUpdate=viewToUpdate;
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
        if(unit != null){
            return result +" " + unit.getSymbol();
        }
        return result;
    }

    @Override
    public String getCalculatedResult() {
        if(modifier!=0.0){
            try{
                return ""+Integer.valueOf(result)*modifier;
            }catch(NumberFormatException nfe){
                return result;
            }
        }
        return result;
    }

    @Override
    public String getName() {
        return AvailableCommandNames.SPEED.getValue();
    }

    /**
     * Sets the return value of the getCalculatedResult to the text view, given in the constructor.
     */
    public void updateUI(){
        viewToUpdate.setText(getCalculatedResult());
    }
}
