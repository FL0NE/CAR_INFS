package com.tsue.dsa.tsue.model;

/**
 * Created by abr on 08.06.2017.
 */

public enum ModeOptions {
    SPEED("0D"),
    RPM("0C"),
    THROTTLE_POS("11"),
    COOLANT_TEMP("05"),
    MAF("10"),
    TANK("03"),
    Distance("31"),
    TIME("4E"),
    AMBIENT_TEMP("46"),
    ENGINE_LOAD("04");



    private String value;

    private ModeOptions(String value) {
        this.value = value;
    }

    public String getValue(){
        return value;
    }

}