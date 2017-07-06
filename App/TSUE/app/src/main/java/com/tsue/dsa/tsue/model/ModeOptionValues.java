package com.tsue.dsa.tsue.model;

import android.graphics.PorterDuff;

/**
 * Created by DSA on 06.07.2017.
 */

public enum ModeOptionValues {
    SPEEDVALUE(ModeOptions.SPEED, 300),
    RPMVALUE(ModeOptions.RPM, 10000),
    THROTTLE_POS_VALUE(ModeOptions.THROTTLE_POS, 0),
    COOLANT_TEMP_VALUE(ModeOptions.COOLANT_TEMP, 0),
    MAF_VALUE(ModeOptions.MAF, 0),
    TANK_VALUE(ModeOptions.TANK, 0),
    DISTANCE_VALUE(ModeOptions.DISTANCE, 0),
    TIME_VALUE(ModeOptions.TIME, 0),
    AMBIENT_TEMP_VALUE(ModeOptions.AMBIENT_TEMP, 0),
    ENGINE_LOAD_VALUE(ModeOptions.ENGINE_LOAD, 0);
    private ModeOptions option;
    private Integer value;

    private ModeOptionValues(ModeOptions option, Integer value) {
        this.option = option;
        this.value = value;
    }
    public ModeOptions getOption() {
        return option;
    }
    public Integer getValue() {
        return value;
    }
}
