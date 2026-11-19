import seminar.task01.transform as transformTask01
import seminar.task02.transform as transformTask02
import seminar.task03.TravelCard

// Импорты для задач 04–05 добавьте после создания соответствующих классов.
// Например: import seminar.task04.CourierDelivery
package seminar.task01
fun transform(n:Int): Int {
    var s = n.toString(radix=2)
    if (n%2 == 0) {
        s += "00"
    } else {
        s += "11"
    }
    return s.toInt(radix=2)
}
fun main() {
    println("Здесь можно вызывать функции и классы из заданий.")

    // Раскомментируйте нужный вызов после реализации функции.
    // println(transformTask01(129))
    // println(transformTask02(131))

    // val card = TravelCard(2)
    // println(card.ride())
    // println(card.remainingTrips)
}
