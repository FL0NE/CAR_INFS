package com.tsue.dsa.tsue.obd;

import android.bluetooth.BluetoothSocket;
import android.os.AsyncTask;
import android.util.Log;

/**
 * OBDConnector can send a OBDCommand and update a UI Element using the OBD Result.
 * Created by abr
 */

public class OBDConnector extends AsyncTask<MyOBDCommand, Integer, MyOBDCommand[]> {
    private com.tsue.dsa.tsue.obd.OBDReader odbReader;
    private BluetoothSocket socket = null;

    /**
     * Constructor
     */
    public OBDConnector(BluetoothSocket socket) {
        this.socket=socket;
    }

    @Override
    protected MyOBDCommand[] doInBackground(MyOBDCommand... commands) {
        try {
            for (MyOBDCommand command : commands) {
                odbReader = new OBDReader(socket, command);
                odbReader.start();
            }
        } catch (Exception e) {
            Log.e("OBDConnector", "Cannot communicate", e);
        }
        return commands;
    }

    /**
     * Will call the updateUI method on each MyOBDCCommand
     * @param commands the {@link MyOBDCommand} returned by the doInBackground
     */
    protected void onPostExecute(MyOBDCommand... commands) {
        for (MyOBDCommand command : commands) {
            command.updateUI();
        }
    }


}
