package org.mines_ales.opentrack.model


class TripHistory {
    private val tripList: ArrayList<Trip> = arrayListOf()

    public fun addTrip(trip : Trip){
        this.tripList.add(trip)
    }

    public fun getTrip(i:Int): Trip {
        return tripList[i]
    }
    public fun getTrips(): ArrayList<Trip> {
        return tripList
    }

}