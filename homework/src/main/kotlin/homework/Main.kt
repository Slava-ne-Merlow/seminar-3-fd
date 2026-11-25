package homework

fun main() {
    // Раскомментируйте после создания ConsoleNotifier и реализации методов.

    val book = Book(1, "Хоббит", "Джон Толкин")
    val book2 = Book(2, "2233", "ewwew")
    val library = Library(arrayOf(book, book2), ConsoleNotifier())


    println(library.borrowBook(1))
    println(library.report())

}
