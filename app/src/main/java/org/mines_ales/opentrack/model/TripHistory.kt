package org.mines_ales.opentrack.model


class TripHistory {
    private val tripList: ArrayList<Trip> = arrayListOf()

    fun addTrip(trip : Trip){
        this.tripList.add(trip)
    }

    fun getTrip(i:Int): Trip {
        return tripList[i]
    }

    fun getTrips(): ArrayList<Trip> {
        return tripList
    }

}