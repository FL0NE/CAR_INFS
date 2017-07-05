package de.dsa.goit.odbviewer.utils;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.util.Log;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * Helps to request the BluetoothPermission, to get a List of available {@link BluetoothDevice}s and to connect to a {@link BluetoothDevice}.
 * Created by abr
 */

public class BluetoothHelper {
    private static final UUID MY_UUID = UUID.fromString("0000110E-0000-1000-8000-00805F9B34FB");
    public static final int REQUEST_ENABLE_BT = 564;
    private static BluetoothAdapter mBluetoothAdapter;
    private Map<String, BluetoothDevice> pairedDevices;


    /**
     * Checks if the Bluetooth Adapter is enabled.
     * If it is not enabled it will create a Intent to enable it.
     * If it is enabled the initBluetooth method will be called.
     *
     * @param activity the current activity, which will get the intent result, if bluetooth is not enabled.
     * @return true, if bluetooth is enabled and the initBluetooth method was successful, false otherwise.
     */
    public boolean requestBluetooth(Activity activity) {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            return false;
        }

        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            activity.startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        } else {
            initBluetooth();
            return true;
        }
        return false;
    }

    /**
     * Requestes all bonded bluetooth devices and creates a Map, where the device name and the mac adress is the key and the {@link BluetoothDevice} is the value.
     */
    public void initBluetooth() {
        Set<BluetoothDevice> devices = mBluetoothAdapter.getBondedDevices();
        pairedDevices = new HashMap<>();
        for (BluetoothDevice device : devices) {
            String deviceName = device.getName();
            String deviceHardwareAddress = device.getAddress(); // MAC address
            String name = deviceName + "  " + deviceHardwareAddress + "\n";
            pairedDevices.put(name, device);
        }
    }

    /**
     * @return an String ArrayList, which is the KeySet of the map of paired devices.
     */
    public ArrayList<String> getPairedDeviceNames() {
        ArrayList<String> retval = new ArrayList<>();
        retval.addAll(pairedDevices.keySet());
        return retval;
    }


    /**
     *
     * @param name the name of a bluetooth device.
     * @return the {@link BluetoothDevice}, if it is in the paired devices, null otherwise
     */
    public BluetoothDevice getDevice(String name) {
        return pairedDevices.get(name);
    }

    /**
     * Tries to connect to the given {@link BluetoothDevice}.
     * @param dev the {@link BluetoothDevice} to connect to.
     * @return the {@link BluetoothSocket}
     * @throws IOException if it was not possible to connect to the Socket.
     */
    public static BluetoothSocket connect(BluetoothDevice dev) throws IOException {
        BluetoothSocket sock = null;
        BluetoothSocket sockFallback;
        dev = mBluetoothAdapter.getRemoteDevice(dev.getAddress());
        Log.d("BluetoothStatus", "Start Bluetooth");
        try {
            sock = dev.createRfcommSocketToServiceRecord(MY_UUID);
            sock.connect();
        } catch (Exception e1) {
            Log.e("BluetoothStatus", "E1", e1);
            if (sock != null) {
                Class<?> clazz = sock.getRemoteDevice().getClass();
                Class<?>[] paramTypes = new Class<?>[]{Integer.TYPE};
                try {
                    Method m = clazz.getMethod("createRfcommSocket", paramTypes);
                    Object[] params = new Object[]{Integer.valueOf(1)};
                    sockFallback = (BluetoothSocket) m.invoke(sock.getRemoteDevice()
                            , params);
                    sockFallback.connect();
                    sock = sockFallback;
                } catch (Exception e2) {
                    Log.e("BluetoothStatus", "Couldn't fallback. E2", e2);
                    throw new IOException(e2.getMessage());
                }
            }
        }
        return sock;
    }


}
