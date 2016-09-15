package com.ethannjc.project2;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import java.util.Observable;

public class AccelerometerHandler extends Observable implements SensorEventListener {

    // Ethan Cox - Project 2 Accelerometer Handler

    private SensorManager sensorManager;
    private Sensor accel;
    private long prevTime = 0;

    public AccelerometerHandler(MainActivity act) {
        sensorManager = (SensorManager) act.getSystemService(Activity.SENSOR_SERVICE);
        accel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, accel, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        long curr_time = System.currentTimeMillis();
        if (curr_time - prevTime > 250) {
            prevTime = curr_time;
            setChanged();
            notifyObservers(event.values);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) { }

}
