package homework

fun main() {
    val book = Book(1, "Хоббит", "Джон Толкин")
    val library = Library(arrayOf(book), ConsoleNotifier())

    println(library.borrowBook(1))
    println(library.report())
}
