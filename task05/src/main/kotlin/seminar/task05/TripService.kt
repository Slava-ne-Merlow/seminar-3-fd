package seminar.task05

interface TravelPass{
    fun pay(passengers: Int) : Boolean
}
class TravelCard(initialTrips:Int) : TravelPass {
    init {
        require(initialTrips >= 0)
    }

    var remainingTrips : Int = initialTrips
        private set

    override fun pay(passengers: Int): Boolean {
        require(passengers > 0)
        if (passengers > remainingTrips) return false
        remainingTrips -= passengers
        return true
    }
}
class UnlimitedPass : TravelPass {
    override fun pay(passengers: Int): Boolean {
        require(passengers > 0)
        return true
    }
}


interface Notifier {
    fun send(message : String) : Unit
}
class TripService(val notifier: Notifier){
    fun travel(pass : TravelPass, passengers : Int) : Boolean{
        require(passengers > 0)
        if(!pass.pay(passengers)) return false
        notifier.send("Поездка оплачена. Пассажиров: $passengers")
        return true
    }
}