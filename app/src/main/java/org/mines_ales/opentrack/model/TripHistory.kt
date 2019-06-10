package org.mines_ales.opentrack.model


class TripHistory(){
    private val tripList: ArrayList<Trip> = arrayListOf()

    public fun addTrip(trip : Trip){
        this.tripList.add(trip)
    }

}