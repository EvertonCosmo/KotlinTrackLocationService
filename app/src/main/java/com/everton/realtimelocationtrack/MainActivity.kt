package com.everton.realtimelocationtrack

import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.util.jar.Manifest

class MainActivity : AppCompatActivity() {
    private final val MY_PERMISSIONS_REQUEST_ACESS_FINE_LOCATION = 1;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            )
            != PackageManager.PERMISSION_GRANTED
        ) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) {

                startServiceLocation();
            } else {

                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                    MY_PERMISSIONS_REQUEST_ACESS_FINE_LOCATION
                )


            }
        } else {
            Log.d("permission", "OK")
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_ACESS_FINE_LOCATION -> if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startServiceLocation();
            } else Toast.makeText(this, "Give me permissions", Toast.LENGTH_LONG).show()
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun startServiceLocation() {
        Log.d("state:", "INIT");
        val intent = Intent(this, LocationService::class.java)
        startService(intent)

    }


}
