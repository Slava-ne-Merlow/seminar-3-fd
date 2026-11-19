package seminar.task04

abstract class Delivery(val destination: String) {
    init {
        require(destination.isNotBlank())
    }

    abstract fun cost(weightHg: Int): Int
    fun label(weightKg: Int): String {
        val price = cost(weightKg)
        return "Доставка в $destination: $price"
    }
}

class Pickup(destination: String) : Delivery(destination) {
    override fun cost(weightHg: Int): Int {
        require(weightHg > 0)
        return 0
    }
}

class CourierDelivery(destination: String) : Delivery(destination) {
    override fun cost(weightHg: Int): Int {
        require(weightHg > 0)
        return 300 + 50 * weightHg
    }
}

class ExpressDelivery(destination: String, val distanceKm: Int) : Delivery(destination) {
    init {
        require(distanceKm > 0)
    }
    override fun cost(weightHg: Int): Int {
        require(weightHg in 1..10)
        return 300 + 80 * weightHg + 20 * distanceKm
    }
}