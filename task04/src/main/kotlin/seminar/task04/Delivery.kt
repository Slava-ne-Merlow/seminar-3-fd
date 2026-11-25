package seminar.task04

abstract class Delivery(
    val destination: String) {
    init {
       require(destination.isNotBlank())
    }

    abstract fun cost(weightKg: Int): Int

    fun label(weightKg: Int): String {
        return "Доставка в ${destination}: ${cost(weightKg)} руб."
    }
}

class Pickup(destination: String) : Delivery(destination) {
    override fun cost(weightKg: Int): Int{
        require(weightKg > 0)
        return 0
    }
}

class CourierDelivery(destination: String) : Delivery(destination) {
    override fun cost(weightKg: Int): Int {
        require(weightKg > 0)
        return 200 + (weightKg * 50)
    }
}

class ExpressDelivery(
    destination: String,
    private val distanceKm: Int) : Delivery(destination) {
    init {
        require(distanceKm > 0)
    }
    override fun cost(weightKg: Int): Int {
        require(weightKg in 1..10)
        return 300 + (weightKg * 80) + (distanceKm * 20)
    }
}