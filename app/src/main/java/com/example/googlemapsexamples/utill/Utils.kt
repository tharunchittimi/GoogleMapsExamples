package com.example.googlemapsexamples.utill

import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.net.Uri

/**
 * Created by TharunKumarC on 13, October, 2020
 * Copyrights (c) 2020.
 */
class Utils {

    companion object {

        fun checkLocationFetchAccess(context: Context?): Boolean {
            val lm = context?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            var gpsEnabled = false
            var networkEnabled = false

            try {
                gpsEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER)
            } catch (ex: Exception) {

            }

            try {
                networkEnabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
            } catch (ex: Exception) {
                ex.printStackTrace()
            }

            return !(!gpsEnabled && !networkEnabled)
        }

        fun checkGPSStatus(context: Context?): Boolean {
            val lm = context?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            var gpsEnabled = false

            try {
                gpsEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER)
            } catch (ex: Exception) {

            }
            return gpsEnabled
        }

        fun turnGPSOn(context: Context?) {
            val lm = context?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            val gpsEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER)
            if (!gpsEnabled) {
                val poke = Intent()
                poke.setClassName(
                    "com.android.settings",
                    "com.android.settings.widget.SettingsAppWidgetProvider"
                )
                poke.addCategory(Intent.CATEGORY_ALTERNATIVE)
                poke.data = Uri.parse("3")
                context.sendBroadcast(poke)
            }
        }

        fun turnGPSOff(context: Context?) {
            val lm = context?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            val gpsEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER)
            if (gpsEnabled) {
                val poke = Intent()
                poke.setClassName(
                    "com.android.settings",
                    "com.android.settings.widget.SettingsAppWidgetProvider"
                )
                poke.addCategory(Intent.CATEGORY_ALTERNATIVE)
                poke.data = Uri.parse("3")
                context.sendBroadcast(poke)
            }
        }

        fun setGpsState(mContext: Context?, state: Boolean) {
            val intent = Intent("android.location.GPS_ENABLED_CHANGE")
            intent.putExtra("enabled", true);
            mContext?.sendBroadcast(intent)
        }
    }
}