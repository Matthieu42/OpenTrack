package org.mines_ales.opentrack

import android.Manifest
import android.location.Location
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.TextView
import org.mines_ales.opentrack.model.OpenTrackData
import org.mines_ales.opentrack.model.Trip
import pub.devrel.easypermissions.EasyPermissions
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity(), View.OnClickListener, LocationTrackingService.LocationListener {

    private lateinit var durationTextView: TextView
    private var trip: Trip = Trip("Default")
    private val mHandler: Handler = Handler()
    private lateinit var locationTrackingService: LocationTrackingService
    private var mPosition: Location? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        this.durationTextView = findViewById(R.id.durationTextView)
        this.locationTrackingService = LocationTrackingService(this,this)
    }


    override fun onClick(v: View) {
        when (v.id) {

            R.id.startButton -> {
                if (!trip.isRunning()) {
                    startTrip(v)

                } else {
                    stopTrip(v)
                }
            }
        }
    }

    fun startTrip(v: View){
        (v as Button).text = getString(R.string.stop)
        this.trip.startTrip()
        this.startTimerUpdater()
        this.locationTrackingService.onStart()
    }
    fun stopTrip(v: View){
        (v as Button).text = getString(R.string.start)
        this.trip.stopTrip()
        this.locationTrackingService.onPause()
        OpenTrackData.tripHistory.addTrip(this.trip)
        this.trip = Trip("new trip")
    }

    fun updateTimer(){
        this.durationTextView.text = SimpleDateFormat("HH:mm:ss").format(Date(this.trip.getCurrentTime()))
    }

    var mStatusChecker: Runnable = object : Runnable {
        override fun run() {
            try {
                if(trip.isRunning()){
                    updateTimer()
                }
            } finally {
                mHandler.postDelayed(this, 1000)
            }
        }
    }

    fun startTimerUpdater() {
        mStatusChecker.run()
    }
    override fun onLocationFound(location: Location?) {
        findViewById<TextView>(R.id.altitudeTextView).text = "OK"
        mPosition = location
        findViewById<TextView>(R.id.altitudeTextView).text = mPosition!!.altitude.toInt().toString()

        findViewById<TextView>(R.id.speedTextView).text = mPosition!!.speed.toString()
    }

    override fun locationError(msg: String) {
        findViewById<TextView>(R.id.altitudeTextView).text = "ERROR"
    }

    override fun checkRequiredLocationPermission(): Boolean {
        val perms = arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)
        return if (!EasyPermissions.hasPermissions(this, *perms)) {
            EasyPermissions.requestPermissions(
                    this, "File ton GPS STP",
                    LocationTrackingService.RUN_TIME_PERMISSION_CODE, *perms
            )
            false
        } else {
            true
        }
    }
}