package mines_ales.org.opentrack

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.TextView
import mines_ales.org.opentrack.model.Trip
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var textView: TextView
    private val trip: Trip = Trip("Default")
    private val mHandler: Handler = Handler()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        this.textView = findViewById(R.id.durationTextView)
    }


    override fun onClick(v: View) {
        when (v.id) {
            R.id.startButton -> {
                startTrip(v)
            }
            else -> {

            }
        }
    }

    fun startTrip(v: View){
        (v as Button).setText("Stop")
        this.trip.startTrip()
        this.startTimerUpdater()
    }

    fun updateTimer(){
        this.textView.setText(SimpleDateFormat("mm:ss").format(Date(this.trip.getCurrentTime())))
    }

    var mStatusChecker: Runnable = object : Runnable {
        override fun run() {
            try {
                updateTimer()
            } finally {
                mHandler.postDelayed(this, 1000)
            }
        }
    }

    fun startTimerUpdater() {
        mStatusChecker.run()
    }
}


