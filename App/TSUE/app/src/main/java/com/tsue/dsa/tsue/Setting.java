package com.tsue.dsa.tsue;

/**
 * Created by DSA on 06.07.2017.
 */

public class Setting {
    private float fuel;
    private float engineLoad;
    private float engineTemp;
    private float speed;
    private boolean enableSound;

    public float getEngineTemp() {
        return engineTemp;
    }

    public void setEngineTemp(float engineTemp) {
        this.engineTemp = engineTemp;
    }

    public float getFuel() {
        return fuel;
    }

    public void setFuel(float fuel) {
        this.fuel = fuel;
    }

    public float getEngineLoad() {
        return engineLoad;
    }

    public void setEngineLoad(float engineLoad) {
        this.engineLoad = engineLoad;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public boolean isEnableSound() {
        return enableSound;
    }

    public void setEnableSound(boolean enableSound) {
        this.enableSound = enableSound;
    }
}
