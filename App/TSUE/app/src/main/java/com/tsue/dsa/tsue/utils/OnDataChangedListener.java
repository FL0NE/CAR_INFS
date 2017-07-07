package com.tsue.dsa.tsue.utils;

/**
 * Created by DSA on 07.07.2017.
 */

public interface OnDataChangedListener {
    void engineLoadChanged(double load);

    void throttleLoadChanged(double load);

    void fuelLoadChanged(double load);

    void rpmLoadChanged(double load);

    void speedLoadChanged(double load);

    void engineTempChanged(double load);

}
