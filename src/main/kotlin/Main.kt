import seminar.task01.transform as transformTask01
import seminar.task02.transform as transformTask02
import seminar.task03.TravelCard

// Импорты для задач 04–05 добавьте после создания соответствующих классов.
// Например: import seminar.task04.CourierDelivery

fun main() {
     val N=readln()
     N.toString(2)
     String b="00"
    String c="11"
    if (N%2==0){
        N+=b

    } else{
        N+=c

    }
    N.toString(10)
    println(N)


    println("Здесь можно вызывать функции и классы из заданий.")

    // Раскомментируйте нужный вызов после реализации функции.
    // println(transformTask01(129))
    // println(transformTask02(131))

    // val card = TravelCard(2)
    // println(card.ride())
    // println(card.remainingTrips)
}
