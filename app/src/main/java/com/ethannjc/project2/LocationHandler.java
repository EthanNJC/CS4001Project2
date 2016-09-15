package com.ethannjc.project2;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import java.util.Observable;

public class LocationHandler extends Observable implements LocationListener {

    // Ethan Cox - Location Handler

    LocationManager locationManager;
    private long prevTime = 0;
    public Double[] location;

    public LocationHandler(MainActivity act) {
        locationManager = (LocationManager) act.getSystemService(Context.LOCATION_SERVICE);
        location = new Double[2];

        // Permission Check/Request
        if (ActivityCompat.checkSelfPermission(act, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(act, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(act, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 0);
            Toast.makeText(act, "Permission FAILED", Toast.LENGTH_LONG).show();
        } else Toast.makeText(act, "Permission PASS", Toast.LENGTH_LONG).show();


        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);

        // Attempt to give the app some initial data (seems to only work sometimes)
        Location loc = locationManager.getLastKnownLocation(locationManager.NETWORK_PROVIDER);
        if (loc != null) pushChange(loc);
    }

    public void pushChange(Location loc) {
        setChanged();
        location[0] = loc.getLatitude();
        location[1] = loc.getLongitude();
        notifyObservers(location);
    }

    @Override
    public void onLocationChanged(Location loc) {
        long curr_time = System.currentTimeMillis();
        if (curr_time - prevTime > 250) {
            prevTime = curr_time;
            pushChange(loc);
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) { }
    @Override
    public void onProviderEnabled(String provider) { }
    @Override
    public void onProviderDisabled(String provider) { }
}
