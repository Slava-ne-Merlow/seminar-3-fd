package seminar.task03

class TravelCard(initialTrips: Int) {
    var remainingTrips: Int = initialTrips
        private set

    init {
        require(initialTrips > 0)
    }

    fun topUp(trips: Int) {
        require(trips > 0)
        remainingTrips += trips
    }

    fun ride(): Boolean {
        if (remainingTrips > 0) {
            remainingTrips--
            return true
        } else {
            return false
        }
    }
}
