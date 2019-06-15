package org.mines_ales.opentrack

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.location.Location
import android.support.v4.app.ActivityCompat
import android.util.Log
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import pub.devrel.easypermissions.EasyPermissions

class LocationTrackingService(private val mActivity: Activity, private val mListener: LocationListener) {
    private val GPS_REQUEST_CODE: Int = 999
    private var mLocationCallback: LocationCallback? = null
    private var mFusedLocationClient: FusedLocationProviderClient? = null
    private var mLocationRequest: LocationRequest? = null
    // initiliaze th class for create instances ans set callback listeners
    init {
        try {
            mFusedLocationClient = LocationServices.getFusedLocationProviderClient(mActivity)
            mFusedLocationClient!!.lastLocation
                    .addOnSuccessListener(mActivity) { location ->
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            mListener.onLocationFound(location)
                        }
                    }
            createLocationRequest()
            mLocationCallback = object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult?) {
                    for (location in locationResult!!.locations) {
                        mListener.onLocationFound(location)
                    }
                }

            }
        } catch (ex: SecurityException) {
            ex.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        checkGpsSetting()
    }

    //Here, we assigned required minimum and maximum duration that explained above
    private fun createLocationRequest() {
        mLocationRequest = LocationRequest.create()
        mLocationRequest!!.interval = UPDATE_INTERVAL.toLong()
        mLocationRequest!!.fastestInterval = FASTEST_INTERVAL.toLong()
        mLocationRequest!!.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        //mLocationRequest!!.smallestDisplacement = DISPLACEMENT.toFloat()
    }

    //In the start method, we checking permissions. It'will invoke a permision required pop if required permission not given
    fun onStart() {
        if (!checkRequiredLocationPermission()) {
            getLastLocation()
        } else {
            startLocationUpdates()
        }
    }

    fun stopLocationUpdates() {
        mFusedLocationClient!!.removeLocationUpdates(mLocationCallback!!)
    }

    fun onPause() {
        stopLocationUpdates()
    }

    private fun checkRequiredLocationPermission(): Boolean {
        val perms = arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)
        if (!EasyPermissions.hasPermissions(mActivity, *perms)) {
            mListener.checkRequiredLocationPermission()
            return false
        } else {
            return true
        }
    }

    /**
     * Check Gps setting IN/OFF state. If GPS OFF it'll show on popup.
     */
    private  fun checkGpsSetting() {

        val builder = LocationSettingsRequest.Builder()
        builder.addLocationRequest(mLocationRequest!!)
        val task = LocationServices.getSettingsClient(mActivity).checkLocationSettings(builder.build())

        task.addOnCompleteListener { result ->
            try {
                result.getResult(ApiException::class.java)
                // All location settings are satisfied. The client can initialize location
                // requests here.
            } catch (exception: ApiException) {
                when (exception.statusCode) {
                    LocationSettingsStatusCodes.RESOLUTION_REQUIRED ->{
                        val resolvable = exception as ResolvableApiException
                        resolvable.startResolutionForResult(mActivity, GPS_REQUEST_CODE)

                    }
                    LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> {
                    }
                }
            }
        }
    }

    private fun getLastLocation() {
        if (ActivityCompat.checkSelfPermission(mActivity,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mActivity,Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return
        }
        mFusedLocationClient!!.lastLocation
                .addOnCompleteListener(mActivity) { task ->
                    if (task.isSuccessful && task.result != null) {
                        mListener.onLocationFound(task.result)
                    } else {
                        Log.w("", "no_location_detected", task.exception)
                    }
                }
    }

    private fun startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(mActivity,
                        Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        mActivity,Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED) {
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            return
        }
        mFusedLocationClient!!.requestLocationUpdates(mLocationRequest, mLocationCallback!!, null)
    }

    interface LocationListener {
        fun onLocationFound(location: Location?)
        fun locationError(msg: String)
        fun checkRequiredLocationPermission(): Boolean
    }

    companion object {
        private const val UPDATE_INTERVAL = 1000
        private const val FASTEST_INTERVAL = 1000
        private const val DISPLACEMENT = 10
        const  val RUN_TIME_PERMISSION_CODE = 999
    }
}
