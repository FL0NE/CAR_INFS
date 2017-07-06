package com.tsue.dsa.tsue.utils;

import android.bluetooth.BluetoothDevice;

/**
 * Created by DSA on 06.07.2017.
 */

public class BluetoothDeivceManager {
    private static BluetoothDevice bluetoothDevice;

    public static void setBluetoothDevice(BluetoothDevice device) {
        bluetoothDevice = device;
    }

    public static BluetoothDevice getBluetoothDevice() {
        return bluetoothDevice;
    }
}
