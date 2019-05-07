package mines_ales.org.opentrack.model

import java.lang.System.currentTimeMillis

class Trip(nameP : String){

    private var name = nameP
    private var startTime : Long = 0
    private var currentTime: Long = 0
    private var started:Boolean = false
    private var stopped:Boolean = false


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
}