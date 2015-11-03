package com.example.luca.androidapp;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.widget.TextView;


class MyCurrentLoctionListener implements LocationListener
{
    TextView lat, longit;

    public MyCurrentLoctionListener(TextView l, TextView lo)
    {
        lat=l; longit=lo;
    }
    @Override
    public void onLocationChanged(Location location) {
        if(location==null)
        {
            System.out.println("********************Impossibile rilevare la posizione");
            lat.setText("Lat: not available");
            longit.setText("Long: not available");
        }
        else {
            lat.setText("Lat: "+(float)location.getLatitude());
            longit.setText("Long: "+(float)location.getLongitude());
        }
    }


    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

}