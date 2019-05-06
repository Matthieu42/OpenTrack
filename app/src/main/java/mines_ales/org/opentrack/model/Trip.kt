package mines_ales.org.opentrack.model

import java.lang.System.currentTimeMillis

class Trip(nameP : String){

    private var name = nameP
    private var startTime : Long = 0
    private var currentTime: Long = 0
    private var paused:Boolean = true
    private var stopped:Boolean = false


    public fun startTrip(){
        this.paused = false
        this.startTime = currentTimeMillis()
    }

    public fun pauseTrip(){
        this.paused = true

    }

    public fun unPauseTrip(){
        this.paused = false

    }

    public fun stopTrip(){
        pauseTrip()
        this.stopped = true
    }

    public fun setCurrentTime() {
        this.currentTime = currentTimeMillis() - this.startTime
    }

    public fun getCurrentTime() : Long{
        this.setCurrentTime()
        return currentTime
    }
    public fun isPaused() : Boolean{
        return this.paused
    }
    public fun isStopped() : Boolean{
        return this.stopped
    }

    public fun isRunning(): Boolean {
        return !this.isPaused() && !this.isStopped()
    }
}