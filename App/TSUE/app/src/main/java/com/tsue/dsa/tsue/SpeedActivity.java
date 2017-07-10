package com.tsue.dsa.tsue;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.github.anastr.speedviewlib.base.Speedometer;
import com.tsue.dsa.tsue.utils.DataManager;
import com.tsue.dsa.tsue.utils.OnDataChangedListener;

public class SpeedActivity extends AppCompatActivity implements OnDataChangedListener {

    private Speedometer speedometer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speed);
        speedometer = (Speedometer) findViewById(R.id.pointerSpeedometer);
        DataManager.subscribeSpeed(this);
        SettingsManager.loadSettings(getPreferences(MODE_PRIVATE));
    }


    @Override
    public void engineLoadChanged(Double load) {

    }

    @Override
    public void throttleLoadChanged(Double load) {

    }

    @Override
    public void fuelLoadChanged(Double load) {

    }

    @Override
    public void rpmLoadChanged(Double load) {

    }

    @Override
    public void speedLoadChanged(Double load) {
        Log.i("test", load + "");
//        speedometer.speedTo(Float.valueOf(load + ""));
        Double speed = SettingsManager.getSetting().getSpeed();
        speedometer.setMaxSpeed(10);
    }

    @Override
    public void engineTempChanged(Double load) {

    }
}
