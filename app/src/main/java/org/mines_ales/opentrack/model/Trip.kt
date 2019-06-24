package org.mines_ales.opentrack.model

import org.osmdroid.util.GeoPoint
import java.lang.StringBuilder
import java.lang.System.currentTimeMillis
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class Trip(nameP : String){

    private var name = nameP
    private var startTime : Long = 0
    private var currentTime: Long = 0
    private var started:Boolean = false
    private var stopped:Boolean = false
    private var pointList: ArrayList<GeoPoint> = ArrayList()

    fun startTrip(){
        this.started = true
        this.startTime = currentTimeMillis()
    }

    fun stopTrip(){
        this.stopped = true
    }

    fun setCurrentTime() {
        this.currentTime = currentTimeMillis() - this.startTime
    }

    fun getCurrentTime() : Long{
        if(isRunning())
            this.setCurrentTime()
        return currentTime
    }

    fun isStopped() : Boolean{
        return this.stopped
    }

    fun isRunning(): Boolean {
        return this.started && !this.isStopped()
    }

    fun addGeoPoint(latitude: Double, longitude:Double){
        pointList.add(GeoPoint(latitude,longitude))
    }

    fun getGeoPoints(): ArrayList<GeoPoint> {
        return this.pointList
    }

    override fun toString(): String {
        return Date(this.startTime).toString() + Date(currentTime).toString()
    }

    fun getStartDate(): String{
        val dateFormat = SimpleDateFormat("dd MMMM yyyy HH:mm:ss",
            Locale.getDefault())
        dateFormat.timeZone = TimeZone.getDefault()
        return dateFormat.format(this.startTime)
    }

    fun getTotalTime(): String{

        val minutes = this.currentTime / 1000 / 60
        val seconds = this.currentTime  / 1000 % 60

        return String.format("%d minutes and %d seconds.", minutes, seconds)

    }
}