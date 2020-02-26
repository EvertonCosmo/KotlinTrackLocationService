package com.everton.realtimelocationtrack

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.os.Looper
import android.util.Log
import com.google.android.gms.location.*

class LocationService : Service () {
    private lateinit var fusedLocationProviderClient : FusedLocationProviderClient;
    private lateinit var locationCallback : LocationCallback ;
    private lateinit var locationRequest : LocationRequest;
    override fun onBind(intent: Intent?): IBinder? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        return null;
    }

    override fun onCreate() {
        super.onCreate()
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        locationCallback = object :LocationCallback() {
           override fun onLocationResult(result: LocationResult) {
               super.onLocationResult(result)
               Log.d("location:","Lat is:" + result.lastLocation.latitude + "," + "Lng is: " + result.lastLocation.latitude);
           }
       }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        requestLocation();
        return super.onStartCommand(intent, flags, startId)
    }

    private fun requestLocation() {
        locationRequest = LocationRequest();
        locationRequest.priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
        locationRequest.interval = 5000
        fusedLocationProviderClient.requestLocationUpdates(locationRequest,locationCallback,Looper.myLooper());
    }
}