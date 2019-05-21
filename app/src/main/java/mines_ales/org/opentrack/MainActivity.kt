package mines_ales.org.opentrack

import android.location.Location
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.TextView
import mines_ales.org.opentrack.model.OpenTrackData
import mines_ales.org.opentrack.model.Trip
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity(), View.OnClickListener,LocationTrackingService.LocationListener {

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
    }
    fun stopTrip(v: View){
        (v as Button).text = getString(R.string.start)
        this.trip.stopTrip()
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
        locationTrackingService.stopLocationUpdates()
        mPosition = location
        findViewById<TextView>(R.id.avgSpeedTextView).text = location.toString()
    }

    override fun locationError(msg: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun checkRequiredLocationPermission(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}