package com.tsue.dsa.tsue;

/**
 * Created by DSA on 06.07.2017.
 */

public class Setting {
    private String fuel;
    private String engineLoad;
    private String engineTemp;
    private String speed;
    private boolean enableSound;

    public String getEngineTemp() {
        return engineTemp;
    }

    public void setEngineTemp(String engineTemp) {
        this.engineTemp = engineTemp;
    }

    public String getFuel() {
        return fuel;
    }

    public void setFuel(String fuel) {
        this.fuel = fuel;
    }

    public String getEngineLoad() {
        return engineLoad;
    }

    public void setEngineLoad(String engineLoad) {
        this.engineLoad = engineLoad;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public boolean isEnableSound() {
        return enableSound;
    }

    public void setEnableSound(boolean enableSound) {
        this.enableSound = enableSound;
    }
}
