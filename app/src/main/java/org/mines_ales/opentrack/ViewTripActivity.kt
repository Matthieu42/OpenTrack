package org.mines_ales.opentrack

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.preference.PreferenceManager
import org.mines_ales.opentrack.model.OpenTrackData
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.BoundingBox
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Polyline
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.gestures.RotationGestureOverlay
import org.osmdroid.util.BoundingBox.fromGeoPoints








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
        //add your points here
        this.line = Polyline()   //see note below!
        this.geoPoints = OpenTrackData.tripHistory.getTrip().getGeopPoints()
        this.line.setPoints(this.geoPoints)
        map!!.overlayManager.add(this.line)
        Handler().postDelayed({
            zoomToTrip()
        }, 1500)
    }

    public override fun onResume() {
        super.onResume()
        //this will refresh the osmdroid configuration on resuming.
        //if you make changes to the configuration, use
        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this));
        zoomToTrip()
        map!!.onResume()
    }

    public override fun onPause() {
        super.onPause()
        //this will refresh the osmdroid configuration on resuming.
        //if you make changes to the configuration, use
        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //Configuration.getInstance().save(this, prefs);
        map!!.onPause()  //needed for compass, my location overlays, v6.0.0 and up
    }

    private fun zoomToTrip(){
        map!!.onResume() //needed for compass, my location overlays, v6.0.0 and up
        val bb = fromGeoPoints(this.line.points)
        map!!.zoomToBoundingBox(bb, true)
    }

}