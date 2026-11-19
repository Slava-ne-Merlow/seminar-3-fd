package seminar.task04


abstract class Delivery(
    val destination: String
) {
    init {
        require(destination.isNotBlank())
    }

    abstract fun cost(weightKg: Int): Int

    fun label(weightKg: Int): String {
        return "Доставка в $destination: ${cost(weightKg)}"
    }
}

class PickUp(
    destination: String
) : Delivery(destination) {
    override fun cost(weightKg: Int): Int {
        require(weightKg > 0)
        return 0
    }
}


class CourierDeivery(
    destination: String
) : Delivery(destination) {
    override fun cost(weightKg: Int): Int {
        require(weightKg > 0)
        return 200 + 50 * weightKg
    }
}

class ExpressDelivery(
    destination: String,
    private val distance: Int
): Delivery(destination) {
    init {
        require(distance > 0)
    }
    override fun cost(weightKg: Int): Int {
        require(weightKg in 1..10)
        return 300 + 80 * weightKg + 20 * distance
    }
}