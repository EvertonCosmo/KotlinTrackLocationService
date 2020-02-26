package com.everton.realtimelocationtrack

import android.app.Service
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.os.IBinder

class BackgroundLocationService : Service() {
    private var locationManager: LocationManager? = null;

    override fun onBind(intent: Intent?): IBinder? = null;

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
        return START_STICKY
    }

    override fun onCreate() {
        super.onCreate()
        if (locationManager == null)
            locationManager =
                applicationContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        try {
            locationManager?.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,INTERVAL,DISTANCE,locationListeners)
        }

    }
}