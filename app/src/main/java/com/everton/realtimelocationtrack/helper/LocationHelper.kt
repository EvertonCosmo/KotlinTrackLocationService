package com.everton.realtimelocationtrack.helper

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.location.Location
import android.preference.PreferenceManager
import androidx.core.R
import androidx.core.app.NotificationCompat
import androidx.core.app.TaskStackBuilder
import com.everton.realtimelocationtrack.MainActivity
//import android.R
import java.text.DateFormat
import java.util.*
import kotlin.text.StringBuilder

@Suppress("JAVA_CLASS_ON_COMPANION")
class LocationHelper(private var mContext:Context, private  var locations: List<Location>) {

    private var KEY_LOCATIONS_RESULT = "location-update-results"
//    private var PRIMAY_CHANNEL = "locationService"
    companion object{
         var PRIMAY_CHANNEL = "locationService"
    }
//    private lateinit var mContext: Context = context
    private lateinit var notificationManager: NotificationManager

    private  fun getLocationResultString(): String {
        if (locations.isEmpty()) return "Location not received"

        val sb: StringBuilder = java.lang.StringBuilder()
        for (location in locations) {

            sb.append("[")
            sb.append(location.latitude)
            sb.append(",")
            sb.append(location.longitude)
            sb.append(" ]")
            sb.append("\n")
        }
        return sb.toString()
    }
    private fun getResultsTitle(): String {
        val result : String = "Reported"
        return result + " : " + DateFormat.getTimeInstance().format(Date())
    }

     fun saveLocations(){
            PreferenceManager.getDefaultSharedPreferences(mContext).edit().putString(KEY_LOCATIONS_RESULT,getResultsTitle()+"\n" + getLocationResultString()).apply()
    }
    fun showNotification(){
         val intent : Intent = Intent(mContext,MainActivity::class.java);

         val stackBuilder :TaskStackBuilder = TaskStackBuilder.create(mContext)
        stackBuilder.addParentStack(MainActivity::class.java)
        stackBuilder.addNextIntent(intent) // intent of notification

         val notificationPending : PendingIntent? = stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);

        val notificationBuilder : NotificationCompat.Builder = NotificationCompat.Builder(mContext,PRIMAY_CHANNEL)
            .setSmallIcon(com.everton.realtimelocationtrack.R.mipmap.ic_launcher)
            .setContentTitle(getResultsTitle())
            .setContentText(getLocationResultString())
            .setAutoCancel(true)
            .setContentIntent(notificationPending)
//            .setPriority(NotificationCompat.PRIORITY_MAX)
            getNotificationManager().notify(0,notificationBuilder.build())
//        getN
    }
    private fun getNotificationManager() : NotificationManager {
        return mContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

}