package mines_ales.org.opentrack.model

import org.mines_ales.opentrack.model.Trip

class TripHistory(){
    private val tripList: ArrayList<Trip> = arrayListOf()

    public fun addTrip(trip : Trip){
        this.tripList.add(trip)
    }

}