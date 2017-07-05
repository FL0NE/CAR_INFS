package de.dsa.goit.odbviewer.model;

/**
 * Created by abr on 08.06.2017.
 */

public enum Modes {
    SHOW_CURRENT_DATA,
    SHOW_FREEZE_FRAME_DATA,
    SHOW_DIAGNOSTIC_TROUBLE_CODES,
    CLEAR_TROUBLE_CODES,
    TEST_RESULT_OXYGEN_SENSOR,
    TEST_RESULT_MONITORING,
    SHOW_PENDING_TROUBLE_CODES,
    SPECIAL_CONTROL_MODE,
    REQUEST_VEHICLE_INFORMATION,
    REQUEST_PERMANENT_TROUBLE_CODES;

    public String getValue(){
        int ord = this.ordinal() + 1;
        String hex = Integer.toHexString(ord);
        return hex.length() == 1?"0"+hex:hex;
    }
}
