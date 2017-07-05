package com.tsue.dsa.tsue;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    //VARIABLEN

    TextView hohe = (TextView) findViewById(R.id.hohe_wert);
    TextView entfernung = (TextView) findViewById(R.id.entf_wert);
    TextView gang = (TextView) findViewById(R.id.gang_wert);



    //ENDE VARIABLEN


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Settings_Buton

        Button btn_einstellungen = (Button) findViewById(R.id.btn_enstllngn);
        btn_einstellungen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent settingsIntent = new Intent(MainActivity.this, options.class);
                startActivity(settingsIntent);
            }
        });


         // TEST ENDE



    }
}
