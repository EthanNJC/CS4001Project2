package com.ethannjc.project2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Observable;
import java.util.Observer;

public class MainActivity extends AppCompatActivity implements Observer {

    // Ethan Cox - Project 2 Main Activity

    private TextView xText, yText, zText, latText, lonText;
    private AccelerometerHandler ah;
    private LocationHandler lh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        xText = (TextView) findViewById(R.id.x_text_view);
        yText = (TextView) findViewById(R.id.y_text_view);
        zText = (TextView) findViewById(R.id.z_text_view);
        latText = (TextView) findViewById(R.id.lat_text_view);
        lonText = (TextView) findViewById(R.id.lon_text_view);
    }

    @Override
    protected void onStart() {
        super.onStart();
        ah = new AccelerometerHandler(this);
        ah.addObserver(this);
        lh = new LocationHandler(this);
        lh.addObserver(this);
    }

    @Override
    public void update(Observable observable, Object o) {
        if (observable instanceof AccelerometerHandler) {
            float [] xyz = (float []) o;
            xText.setText(String.format("%.3f", xyz[0]));
            yText.setText(String.format("%.3f", xyz[1]));
            zText.setText(String.format("%.3f", xyz[2]));
        } else if (observable instanceof LocationHandler) {
            Double[] location = (Double[]) o;
            latText.setText(String.format("%.6f", location[0]));
            lonText.setText(String.format("%.6f", location[1]));
        }
    }
}
