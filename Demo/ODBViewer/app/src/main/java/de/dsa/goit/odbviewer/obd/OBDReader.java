package de.dsa.goit.odbviewer.obd;

import android.bluetooth.BluetoothSocket;
import android.util.Log;

import com.github.pires.obd.commands.ObdCommand;


/**
 * A  Thread, that opens a Bluetooth Connection and uses the given OBDCommand to send a Reqest.
 * When the Thread finished the result can be get by the getResult function.
 * Created by abr
 */

public class OBDReader {
    ObdCommand obdCommand;
    BluetoothSocket socket;

    /**
     * Constructor
     * @param socket the socket to connect to
     * @param command the OBDCommand to execute
     */
    public OBDReader(BluetoothSocket socket , ObdCommand command) {
        this.socket = socket;
        this.obdCommand = command;
    }

    /**
     * Will call the run method for the obdCommand, given in the constrcutor.
     */
    public void start() {
       try{
            obdCommand.run(socket.getInputStream(), socket.getOutputStream());
        } catch (Exception e) {
            Log.e("OBDReader", "Could not use lib.", e);
        }
    }
}
