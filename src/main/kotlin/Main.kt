package homework

import seminar.task01.transform as transformTask01
import seminar.task02.transform as transformTask02
import seminar.task03.TravelCard
import seminar.task04.CourierDelivery
import seminar.task05.TripService

fun main() {
    println("Здесь можно вызывать функции и классы из заданий.")
    val book = Book(1, "Хоббит", "Джон Толкин")
    val library = Library(arrayOf(book), ConsoleNotifier())
    println(library.borrowBook(1))
    println(library.report())
}
