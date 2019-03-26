package mines_ales.org.opentrack

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.TextView
import mines_ales.org.opentrack.model.Trip

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private var textView: TextView? = null
    private val trip: Trip = Trip("Default")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textView = findViewById(R.id.durationTextView)
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

    }
}


