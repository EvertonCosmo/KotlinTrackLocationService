package com.everton.realtimelocationtrack.util

import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.content.ContextCompat
import com.everton.realtimelocationtrack.MainActivity
import java.util.jar.Manifest

open class BackgroundUtil {
    companion object {
        fun hasPermission(context: Context) :Boolean{
            val granted = PackageManager.PERMISSION_GRANTED
            Log.d("PERMISSION" ,granted.toString())
            return ContextCompat.checkSelfPermission(context,android.Manifest.permission.ACCESS_FINE_LOCATION) == granted;

        }}




}