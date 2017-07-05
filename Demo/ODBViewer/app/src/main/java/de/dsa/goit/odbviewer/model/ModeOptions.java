package de.dsa.goit.odbviewer.model;

/**
 * Created by abr on 08.06.2017.
 */

public enum ModeOptions {
    SPEED("0D"),
    RPM("0C"),
    THROTTLE_POS("11"),
    COOLANT_TEMP("05"),
    MAF("10");


    private String value;

    private ModeOptions(String value) {
        this.value = value;
    }

    public String getValue(){
        return value;
    }

}
