package com.tsue.dsa.tsue.utils;

/**
 * Created by DSA on 07.07.2017.
 */

public interface OnDataChangedListener {
    void engineLoadChanged(Double load);

    void throttleLoadChanged(Double load);

    void fuelLoadChanged(Double load);

    void rpmLoadChanged(Double load);

    void speedLoadChanged(Double load);

    void engineTempChanged(Double load);

}
