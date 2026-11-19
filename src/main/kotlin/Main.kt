import seminar.task01.transform as transformTask01
import seminar.task02.transform as transformTask02
import seminar.task03.TravelCard

// Импорты для задач 04–05 добавьте после создания соответствующих классов.
// Например: import seminar.task04.CourierDelivery

fun main() {

    class TravelCard(initialTrips: Int) {
        var remainingTrips: Int = initialTrips
            private set

        init {
            require(initialTrips >= 0)

        fun topUp(trips: Int) {
            require(trips > 0)
            remainingTrips += trips
        }

        fun ride(): Boolean {
            if (remainingTrips > 0) {
                remainingTrips--
                return true
            }
            return false
        }
    }
        }

    println("Здесь можно вызывать функции и классы из заданий.")

    // Раскомментируйте нужный вызов после реализации функции.
    // println(transformTask01(129))
    // println(transformTask02(131))

    // val card = TravelCard(2)
    // println(card.ride())
    // println(card.remainingTrips)
}
