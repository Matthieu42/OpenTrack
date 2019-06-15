package org.mines_ales.opentrack

import android.os.Bundle
import android.os.Handler
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatActivity
import org.mines_ales.opentrack.model.OpenTrackData
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.BoundingBox.fromGeoPoints
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Polyline

class ViewTripActivity : AppCompatActivity() {
    private var map: MapView? = null
    private lateinit var geoPoints:ArrayList<GeoPoint>
    private lateinit var line: Polyline

    override fun onCreate(savedInstanceState: Bundle?) {
        val ctx = applicationContext
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx))
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_trip)
        map = findViewById(R.id.map)
        map!!.setTileSource(TileSourceFactory.MAPNIK)
        map!!.setMultiTouchControls(true)
        this.line = Polyline()

        this.geoPoints = OpenTrackData.tripHistory.getTrip(intent.extras["id"] as Int).getGeoPoints()
        this.line.setPoints(this.geoPoints)
        map!!.overlayManager.add(this.line)

        Handler().postDelayed({
            zoomToTrip()
        }, 1500) //FIX because zoomToBoundingBox not working when in onCreate
    }

    public override fun onResume() {
        super.onResume()
        zoomToTrip()
        map!!.onResume()
    }

    public override fun onPause() {
        super.onPause()
        map!!.onPause()
    }

    private fun zoomToTrip(){
        map!!.onResume()
        val bb = fromGeoPoints(this.line.points)
        map!!.zoomToBoundingBox(bb, true)
    }

}