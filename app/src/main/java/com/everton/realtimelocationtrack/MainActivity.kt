package com.everton.realtimelocationtrack


import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.everton.realtimelocationtrack.helper.LocationHelper
import com.google.android.gms.location.*
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private lateinit var textView : TextView
    private lateinit var buttonOn : Button
    private lateinit var buttonOff : Button
    private lateinit var fusedLocationProviderClient : FusedLocationProviderClient; // Foreground usage
    private lateinit var locationCallback : LocationCallback; // Foreground usage
    private lateinit var locationRequest : LocationRequest;
    private final val MY_PERMISSIONS_REQUEST_ACESS_FINE_LOCATION = 1;

    //    companion object {
//
//    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textView = findViewById(R.id.textLat)
        buttonOff = findViewById(R.id.btnOff)
        buttonOn = findViewById(R.id.btnOn)
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

            } else {

                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                    MY_PERMISSIONS_REQUEST_ACESS_FINE_LOCATION
                )


            }
        } else {
//            startLocationService()
            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

            locationCallback = object :LocationCallback() {
                override fun onLocationResult(result: LocationResult) {
                    super.onLocationResult(result)
                    Log.d("location:","Lat in foreground:" + result.lastLocation.latitude + "," + "Lng in foreground: " + result.lastLocation.latitude);
                    textView.text = " Results : { " + result.lastLocation.latitude + " ," +result.lastLocation.longitude + "}"
                }
            }
            Log.d("permission", "OK")

            buttonOn.setOnClickListener{ view -> startLocationService()}
            buttonOff.setOnClickListener{ view -> stopLocationService()}
        }

    }


    override fun onResume() {
        super.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        stopLocationService()
    }

    private fun startLocationService (){
        val intent: Intent  = Intent(this,BackgroundLocationService::class.java)
        ContextCompat.startForegroundService(this,intent)
        Toast.makeText(this,"Service  started",Toast.LENGTH_SHORT)
    }
    private fun stopLocationService(){
        val intent: Intent  = Intent(this,BackgroundLocationService::class.java)
        stopService(intent)
        Toast.makeText(this,"Service  stopped",Toast.LENGTH_SHORT)
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_ACESS_FINE_LOCATION -> if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            } else Toast.makeText(this, "Give me permissions", Toast.LENGTH_LONG).show()
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    //
//    private fun startServiceLocation() {
//        Log.d("state:", "INIT");
//        val intent = Intent(this, BackgroundLocationService::class.java)
//        startService(intent)
//
//    }


}
