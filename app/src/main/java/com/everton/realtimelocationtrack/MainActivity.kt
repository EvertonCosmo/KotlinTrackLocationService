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
import com.everton.realtimelocationtrack.services.BackgroundLocationService
import com.google.android.gms.location.*
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private lateinit var OutputViewText : TextView
    private lateinit var foregroundStartButton: Button

    private lateinit var foregroundStopButton : Button
    private lateinit var fusedLocationProviderClient : FusedLocationProviderClient; // Foreground usage
    private lateinit var locationCallback : LocationCallback; // Foreground usage
    private lateinit var locationRequest : LocationRequest;
    private final val MY_PERMISSIONS_REQUEST_ACESS_FINE_LOCATION = 1;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        foregroundStartButton = findViewById(R.id.foreground_start_button)
        foregroundStopButton = findViewById(R.id.foreground_stop_button)
//        OutputViewText = findViewById(R.id.output_text_view)
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
        }
            Log.d("permission", "OK")

            foregroundStartButton.setOnClickListener{ view -> startLocationService()}
            foregroundStopButton.setOnClickListener{ view -> stopLocationService()}


    }

    private fun logResultsToScreen(output:String) {
        val outputWithPreviousLogs = "$output\n${OutputViewText.text}"
        OutputViewText.text = outputWithPreviousLogs
    }
    override fun onResume() {
        super.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
//        stopLocationService()
    }

    private fun startLocationService (){
        val intent: Intent  = Intent(this, BackgroundLocationService::class.java)
        ContextCompat.startForegroundService(this,intent)
        Toast.makeText(this,"Service  started",Toast.LENGTH_SHORT)
    }
    private fun stopLocationService(){
        val intent: Intent  = Intent(this, BackgroundLocationService::class.java)
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
