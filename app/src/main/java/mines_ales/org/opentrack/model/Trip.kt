package mines_ales.org.opentrack.model

import java.lang.System.currentTimeMillis

class Trip(nameP : String){

    private var name = nameP
    private var startTime : Long = 0
    private var currentTime: Long = 0


    public fun startTrip(){

        this.startTime = currentTimeMillis()
    }

    public fun pauseTrip(){

    }

    public fun  stopTrip(){

    }

    public fun setCurrentTime() {
        this.currentTime = currentTimeMillis() - this.startTime
    }

    public fun getCurrentTime() : Long{
        this.setCurrentTime()
        return currentTime
    }
}