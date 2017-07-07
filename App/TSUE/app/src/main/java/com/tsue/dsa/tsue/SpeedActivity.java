package com.tsue.dsa.tsue;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SpeedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speed);


        Gauge speedometer = (Gauge) findViewById(R.id.pointerSpeedometer);
        speedometer.speedTo(50);
        speedometer.setMaxSpeed(200);





    }
}
