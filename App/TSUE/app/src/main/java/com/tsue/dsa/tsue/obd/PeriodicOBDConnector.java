package com.tsue.dsa.tsue.obd;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import com.github.pires.obd.commands.protocol.EchoOffCommand;
import com.github.pires.obd.commands.protocol.ObdResetCommand;
import com.github.pires.obd.commands.protocol.SelectProtocolCommand;
import com.github.pires.obd.enums.ObdProtocols;
import com.tsue.dsa.tsue.utils.BluetoothHelper;

import java.io.IOException;



/**
 * The {@link PeriodicOBDConnector} opens a Bluetooth Socket once. Then it configures the OBD connection.
 *  You can configure a Array of {@link com.tsue.dsa.tsue.obd.MyOBDCommand}, which will be executed as often and as fast as configured.
 * Created by abr
 */

public class PeriodicOBDConnector extends Thread {

    private BluetoothDevice device;
    private BluetoothSocket socket = null;
    private OBDConnector obdConnector;
    private boolean run = true;
    private Integer periods = null;
    private long sleep;
    private MyOBDCommand[] commands;

    /**
     * Constrcutor
     * @param device the {@link BluetoothDevice} to communicate with
     * @param sleep the time to sleep during the request, in ms
     * @param periods the number of requests per {@link com.tsue.dsa.tsue.obd.MyOBDCommand} (null for infinity)
     * @param commands the Array of {@link com.tsue.dsa.tsue.obd.MyOBDCommand}s that will be executed in each periode.
     */
    public PeriodicOBDConnector(BluetoothDevice device, long sleep, Integer periods, com.tsue.dsa.tsue.obd.MyOBDCommand... commands) {
        this.device = device;
        this.periods = periods;
        this.sleep=sleep;
        this.commands = commands;
    }

    /**
     * Constrcutor
     * @param device the {@link BluetoothDevice} to communicate with
     * @param sleep the time to sleep during the request, in ms
     * @param commands the Array of {@link com.tsue.dsa.tsue.obd.MyOBDCommand}s that will be executed in each periode.
     */
    public PeriodicOBDConnector(BluetoothDevice device, long sleep, com.tsue.dsa.tsue.obd.MyOBDCommand... commands) {
        this.device = device;
        this.sleep=sleep;
        this.commands = commands;
    }

    /**
     * To start the Thread.
     * Will run, until the number of periods is 0. Or it will be stopped with the stopConnector method.
     * Will create a {@link BluetoothSocket}, if there is no socket.
     * Will create a {@link com.tsue.dsa.tsue.obd.OBDConnector} in each loop and executes it with the {@link com.tsue.dsa.tsue.obd.MyOBDCommand}s, given in the constructor.
     * Will then wait for the amount of ms, given in the constructor.
     */
    public void run() {
        while (run){
            if (socket == null || !socket.isConnected()) {
                socket = initConnection();
            }

            if(socket!=null){
                obdConnector = new OBDConnector(socket);
                obdConnector.execute(commands);
            }

            if(periods != null) {
                periods--;
                if(periods<=0){
                    break;
                }
            }
            try {
                sleep(sleep);
            } catch (InterruptedException e) {
                Log.e("PeriodicOBDConnector","Could not sleep",e);
            }
        }
        stopConnector();
    }

    /**
     * Stops the loop in the run method and closes the {@link BluetoothSocket}.
     */
    public void stopConnector(){
        run = false;
        closeBluetoothSocket();
    }

    /**
     * If there is an {@link BluetoothSocket} it will be closed and nulled.
     */
    public void closeBluetoothSocket() {
        if (socket != null) {
            try {
                socket.close();
                socket = null;
            } catch (IOException e) {
                Log.e("PeriodicOBDConnector","Could not close Socket",e);
            }
        }
    }

    /**
     * Inits the {@link BluetoothSocket} using the {@link BluetoothHelper}.
     * If the socket can connect to the {@link BluetoothDevice}, the OBD Connection will be configured:
     * <li>Reset</li>
     * <li>EchoOff</li>
     * <li>Protocol:Auto</li>
     * See the EML227 definition for detailed information about those commands.
     * @return the connected and for OBD configured Bluetooth Socket, or null.
     */
    private BluetoothSocket initConnection() {
        try {
            BluetoothSocket socket = BluetoothHelper.connect(device);
            new ObdResetCommand().run(socket.getInputStream(), socket.getOutputStream());
            new EchoOffCommand().run(socket.getInputStream(), socket.getOutputStream());
            new SelectProtocolCommand(ObdProtocols.AUTO).run(socket.getInputStream(), socket.getOutputStream());
            return socket;
        } catch (IOException ioe) {
            Log.e("PeriodicOBDConnector", "Could not connect!",ioe);
            socket = null;
        } catch (InterruptedException e) {
            Log.e("PeriodicOBDConnector", "Could not send init codes",e);
            socket = null;
        }
        return null;
    }

}
