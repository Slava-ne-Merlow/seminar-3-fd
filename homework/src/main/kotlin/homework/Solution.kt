package homework

enum class Genre(val label: String) {
    FANTASY("Фэнтези"),
    ADVENTURE("Приключения"),
    SCIENCE("Наука")
}

object LibraryRules {
    fun loanDays(genre: Genre): Int {
        return when(genre) {
            Genre.FANTASY -> 21
            Genre.ADVENTURE -> 14
            Genre.SCIENCE -> 7
        }
    }
}

class Book(
    val id: Int,
    val title: String,
    val author: String,
    val genre: Genre = Genre.FANTASY,
) {
    var isAvailable: Boolean = true
        private set

    init {
        require(id > 0)
        require(title.isNotBlank())
        require(author.isNotBlank())
    }

    fun borrow(): Boolean {
        if (isAvailable) {
            isAvailable=false
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
data class BookInfo(
    val title: String,
    val author: String,
    val genre: Genre,
){
    init{
        require(title.isNotBlank())
        require(author.isNotBlank())
    }

}
interface Notifier {
    fun send(message: String)
}
class ConsoleNotifier(): Notifier{
    override fun send(message: String) = println(message)
}
class CountingNotifier(): Notifier{
    var sentCount: Int = 0
        private set
    var lastMessage: String = ""
        private set
    override fun send(message: String){
        sentCount++
        lastMessage = message
    }
}

class Library(private val books: Array<Book>, private val notifier: Notifier) {
    fun hasBook(id: Int): Boolean {
        for (book in books) {
            if (book.id == id) {
                return true
            }
        }
        return false
    }

    fun borrowBook(id: Int): Boolean {
        for (book in books) {
            if (book.id == id) {
                val isBorrowed = book.borrow()
                if (isBorrowed) {
                    notifier.send("Выдана книга: ${book.title}")
                }
                return isBorrowed
            }
        }
        return false
    }

    fun returnBook(id: Int): Boolean {
        for (book in books) {
            if (book.id == id) {
                val Return = book.returnToLibrary()
                if (Return) {
                    notifier.send("Возвращена книга: ${book.title}")
                }
                return Return
            }
        }
        return false
    }

    fun availableCount(): Int {
        var count = 0
        for (book in books) {
            if (book.isAvailable) {
                count++
            }
        }
        return count
    }

    fun countByGenre(genre: Genre): Int {
        var count = 0
        for (book in books) {
            if (genre == book.genre) {
                count++
            }
        }
        return count
    }

    fun report(): String {
        if (books.isEmpty()) return "Библиотека пуста"
        var res = ""
        for (book in books) {
            val st = if (book.isAvailable) "доступна" else "выдана"
            val r = "${book.id}. ${book.title} — ${book.author}: ${st}"
            if (res.isNotEmpty()) {
                res += "\n"
            }
            res += r
        }
        return res
    }
}
