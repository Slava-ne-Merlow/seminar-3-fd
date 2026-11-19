//import seminar.task01.transform as transformTask01
//import seminar.task02.transform as transformTask02
//import seminar.task03.TravelCard
//
//// Импорты для задач 04–05 добавьте после создания соответствующих классов.
//// Например: import seminar.task04.CourierDelivery
//
package seminar.task01

fun transform(n: Int): Int{
    var s = n.toString(2)

    if(n%2==0){
        s+="00"
    } else {
        s+="11"
    }

    return s.toInt(2)
}

fun maxNBelow(limit: Int): Int {
    TODO("Найдите максимальное N, для которого R < limit")
}
fun main() {
    println("Здесь можно вызывать функции и классы из заданий.")

    // Раскомментируйте нужный вызов после реализации функции.
     println(transform(129))
    // println(transformTask02(131))

    // val card = TravelCard(2)
    // println(card.ride())
    // println(card.remainingTrips)
}
