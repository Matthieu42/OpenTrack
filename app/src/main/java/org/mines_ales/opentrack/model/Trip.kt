package org.mines_ales.opentrack.model

import org.osmdroid.util.GeoPoint
import java.lang.System.currentTimeMillis

class Trip(nameP : String){

    private var name = nameP
    private var startTime : Long = 0
    private var currentTime: Long = 0
    private var started:Boolean = false
    private var stopped:Boolean = false
    private var pointList: ArrayList<GeoPoint> = ArrayList()

    public fun startTrip(){
        this.started = true
        this.startTime = currentTimeMillis()
    }

    public fun stopTrip(){
        this.stopped = true
    }

    public fun setCurrentTime() {
        this.currentTime = currentTimeMillis() - this.startTime
    }

    public fun getCurrentTime() : Long{
        this.setCurrentTime()
        return currentTime
    }
    public fun isStopped() : Boolean{
        return this.stopped
    }

    public fun isRunning(): Boolean {
        return this.started && !this.isStopped()
    }
    public fun addGeoPoint(latitude: Double, longitude:Double){
        pointList.add(GeoPoint(latitude,longitude))
    }
    public fun getGeopPoints(): ArrayList<GeoPoint> {
        return this.pointList
    }
}