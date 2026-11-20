package seminar.task05

interface TravelPass {
    fun pay(passengers: Int): Boolean
}

class TravelCard(initialTrips: Int) : TravelPass {
    var remainingTrips: Int = initialTrips
        private set
    init {
        require(initialTrips >= 0)
    }
    override fun pay(passengers: Int): Boolean {
        require(passengers > 0)
        if (remainingTrips >= passengers) {
            remainingTrips -= passengers
            return true
        }
        return false
    }
}

class UnlimitedPass : TravelPass {
    override fun pay(passengers: Int) : Boolean {
        require(passengers > 0)
        return true
    }
}

interface Notifier {
    fun send(message: String): Unit
}

class TripService(private val notifier: Notifier) {
    fun travel(pass: TravelPass, passengers: Int): Boolean {
        require(passengers > 0)
        if (pass.pay(passengers)) {
            notifier.send("Поездка оплачена. Пассажиров: $passengers")
            return true
        }
        else {
            return false
        }
    }
}