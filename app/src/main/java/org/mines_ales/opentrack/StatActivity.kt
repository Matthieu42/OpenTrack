package org.mines_ales.opentrack

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_stat.*
import org.mines_ales.opentrack.model.OpenTrackData
import org.osmdroid.util.GeoPoint
import kotlin.math.roundToLong

class StatActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stat)

        val currentTrip = OpenTrackData.tripHistory.getTrip(intent.getIntExtra("id", -1))

        var distance = 0.0
        var maxDistance = 0.0
        var firstPoint = 0
        var lastPoint: GeoPoint? = null
        for (geoItem in currentTrip.getGeoPoints()) {
            if (firstPoint == 0) {
                firstPoint = 1
                lastPoint = geoItem
            }
            else {
                distance += geoItem.distanceToAsDouble(lastPoint)
                if (geoItem.distanceToAsDouble(lastPoint) > maxDistance) maxDistance = geoItem.distanceToAsDouble(lastPoint)
                lastPoint = geoItem
            }
        }
        val aveSpeed = (distance / 1000) / (currentTrip.getCurrentTime().toDouble() / 1000 / 60 / 60)
        val maxSpeed = (maxDistance / 1000.0) / (1.0 / 3600.0)

        txtDistance.text = ("%.2f".format(distance).toDouble()).toString() + " meters"
        txtAveSpeed.text = ("%.2f".format(aveSpeed).toDouble()).toString() + " km/h"
        txtMaxSpeed.text = ("%.2f".format(maxSpeed).toDouble()).toString() + " km/h"



    }
}
