package homework

fun sum(a: Int, b: Int): Int {
    return a+b
enum class Genre(val label: String) {
    FANTASY("Фэнтези"),
    ADVENTURE("Приключения"),
    SCIENCE("Наука")
}
data class BookInfo(val title: String, val author: String, val genre: Genre)
class Book(
    val id: Int,
    val title: String,
    val author: String,
    val genre: Genre = Genre.FANTASY
) {
    var isAvailable: Boolean = true
        private set
    init {
        require(id > 0) { "id must be > 0" }
        require(title.isNotBlank()) { "title must not be blank" }
        require(author.isNotBlank()) { "author must not be blank" }
    }
    fun borrow(): Boolean {
        if (isAvailable) {
            isAvailable = false
            return true
        }
        return false
    }
    fun returnToLibrary(): Boolean {
        if (!isAvailable) {
            isAvailable = true
            return true
        }
        return false
    }
    fun info(): BookInfo {
        return BookInfo(title, author, genre)
    }
}
object LibraryRules {
    fun loanDays(genre: Genre): Int = when (genre) {
        Genre.FANTASY -> 21
        Genre.ADVENTURE -> 14
        Genre.SCIENCE -> 7
    }
}
interface Notifier {
    fun send(message: String)
}
class ConsoleNotifier {
    fun send(message: String) {
        println(message)
    }
}
class CountingNotifier {
    var sentCount: Int = 0
        private set

    var lastMessage: String = ""
        private set

    fun send(message: String) {
        sentCount++
        lastMessage = message
    }
}
class Library(private val books: Array<Book>, private val notifier: Notifier) {

    fun hasBook(id: Int): Boolean {
        for (book in books) {
            if (book.id == id) return true
        }
        return false
    }
    fun borrowBook(id: Int): Boolean {
        if (id <= 0) return false
        for (book in books) {
            if (book.id == id) {
                val borrowed = book.borrow()
                if (borrowed) {
                    notifier.send("Выдана книга: ${book.title}")
                }
                return borrowed
            }
        }
        return false
    }
    fun returnBook(id: Int): Boolean {
        if (id <= 0) return false
        for (book in books) {
            if (book.id == id) {
                val returned = book.returnToLibrary()
                if (returned) {
                    notifier.send("Возвращена книга: ${book.title}")
                }
                return returned
            }
        }
        return false
    }
    fun availableCount(): Int {
        var count = 0
        for (book in books) {
            if (book.isAvailable) count++
        }
        return count
    }
    fun countByGenre(genre: Genre): Int {
        var count = 0
        for (book in books) {
            if (book.genre == genre) count++
        }
        return count
    }
    fun report(): String {
        if (books.isEmpty()) return "Библиотека пуста"
        var result = ""
        for (i in books.indices) {
            val book = books[i]
            val status = if (book.isAvailable) "доступна" else "выдана"
            val line = "${book.id}. ${book.title} — ${book.author}: $status"
            if (i == 0) {
                result = line
            } else {
                result = result + "\n" + line
            }
        }
        return result
    }
}

