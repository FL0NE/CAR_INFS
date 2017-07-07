package com.tsue.dsa.tsue;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.tsue.dsa.tsue.utils.DataManager;
import com.tsue.dsa.tsue.utils.OnDataChangedListener;

public class SpeedActivity extends AppCompatActivity implements OnDataChangedListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speed);
        DataManager.subscribeSpeed(this);
    }

    @Override
    public void engineLoadChanged(double load) {

    }

    @Override
    public void throttleLoadChanged(double load) {

    }

    @Override
    public void fuelLoadChanged(double load) {

    }

    @Override
    public void rpmLoadChanged(double load) {

    }

    @Override
    public void speedLoadChanged(double load) {

    }

    @Override
    public void engineTempChanged(double load) {

    }
}
