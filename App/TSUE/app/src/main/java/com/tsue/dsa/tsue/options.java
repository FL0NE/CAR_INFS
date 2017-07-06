package com.tsue.dsa.tsue;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.ImageButton;
import android.widget.TextView;

public class options extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        ImageButton reload_settings = (ImageButton) findViewById(R.id.reload_settings);
        Switch sound = (Switch) findViewById(R.id.sound_switch);
        TextView test = (TextView) findViewById(R.id.test_txt);


            if(sound.isChecked()) {
                test.setText("ON");
            }
            else {
                test.setText("OFF");
            }




    }
}
