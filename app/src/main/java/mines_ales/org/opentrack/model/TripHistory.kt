package mines_ales.org.opentrack.model

class TripHistory(){
    private val tripList: ArrayList<Trip> = arrayListOf()

    public fun addTrip(trip : Trip){
        this.tripList.add(trip)
    }

}