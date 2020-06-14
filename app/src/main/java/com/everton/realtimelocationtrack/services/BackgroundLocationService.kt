package com.everton.realtimelocationtrack.services

import android.Manifest
import android.app.Notification
import android.app.Service
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.IBinder
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import com.everton.realtimelocationtrack.helper.LocationHelper
import com.everton.realtimelocationtrack.util.BackgroundUtil
import com.google.android.gms.location.*


class BackgroundLocationService : Service(){
        private val TAG: Class<BackgroundLocationService> = BackgroundLocationService::class.java
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback
    private lateinit var fusedLocationProvider:FusedLocationProviderClient


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG.toString(),"onStartCommand: called")
        startForeground(1001,getNotification())
        locationsUpdates()
        return START_NOT_STICKY
    }
    private fun getNotification(): Notification? {
        var notificationBuilder: NotificationCompat.Builder? = null
        notificationBuilder = NotificationCompat.Builder(
            applicationContext,
            LocationHelper.PRIMAY_CHANNEL
        )
            .setContentTitle("Location Notification")
            .setContentText("Location service is running in the background.")
            .setSmallIcon(com.everton.realtimelocationtrack.R.drawable.ic_launcher_background)
            .setAutoCancel(true)
        return notificationBuilder.build()
    }

    override fun onCreate() {
        super.onCreate()
        fusedLocationProvider = LocationServices.getFusedLocationProviderClient(this)
        createLocationCallBack()

    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    private fun locationsUpdates(){
        locationRequest = LocationRequest().apply { priority = LocationRequest.PRIORITY_HIGH_ACCURACY; fastestInterval = 5*1000; interval = 4*1000 }

        val permission = BackgroundUtil.hasPermission(this)
        if(!permission){
            Log.e("PERMISSION","Not have permission")
            return
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        fusedLocationProvider.requestLocationUpdates(locationRequest,locationCallback,Looper.myLooper())
    }

    private fun createLocationCallBack() {
        locationCallback = object :LocationCallback(){
            override fun onLocationResult(result: LocationResult?) {
                if(result == null) {
                    Log.d("onLocationResult: " ,"error in location")
                    return
                }
                Log.d("location:","Lat is:" + result.lastLocation.latitude + "," + "Lng is: " + result.lastLocation.latitude)
                val locations : List<Location> =result.locations
                val helper: LocationHelper = LocationHelper(applicationContext,locations)
                helper.showNotification()
                helper.saveLocations()
                Toast.makeText(applicationContext, "Location received: " + locations.size, Toast.LENGTH_SHORT).show()
                super.onLocationResult(result)

            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG.toString(),"onDestroy: called")
        stopForeground(true)
        fusedLocationProvider.removeLocationUpdates(locationCallback)
    }
}