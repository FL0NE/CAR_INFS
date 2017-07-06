package com.tsue.dsa.tsue;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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
        Switch sound_switch = (Switch) findViewById(R.id.sound_switch);






        reload_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        TextView test = (TextView) findViewById(R.id.test_txt);
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.test_sound);

      /*      if(sound_switch.isChecked()) {
                int SoundStatus = 1;
            }
            else {
                int SoundStatus = 0;
            }
*/


    }



}
