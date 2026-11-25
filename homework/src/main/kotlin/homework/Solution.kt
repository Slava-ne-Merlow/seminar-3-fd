package homework

enum class Genre(val label: String) {
    FANTASY("Фэнтези"),
    ADVENTURE("Приключения"),
    SCIENCE("Наука")
}

object LibraryRules {
    fun loanDays(genre: Genre): Int = when (genre) {
        Genre.FANTASY -> 21
        Genre.ADVENTURE -> 14
        Genre.SCIENCE -> 7
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

    fun info(): BookInfo = BookInfo(title, author, genre)
}

data class BookInfo(
    val title: String,
    val author: String,
    val genre: Genre,
)

interface Notifier {
    fun send(message: String)
}

class ConsoleNotifier : Notifier {
    override fun send(message: String) {
        println(message)
    }
}

class CountingNotifier : Notifier {
    var sentCount: Int = 0
        private set
    var lastMessage: String = ""
        private set

    override fun send(message: String) {
        sentCount++
        lastMessage = message
    }
}

class Library(private val books: Array<Book>, private val notifier: Notifier) {
    private fun findBook(id: Int): Book? {
        for (book in books) {
            if (book.id == id) return book
        }
        return null
    }

    fun hasBook(id: Int): Boolean = findBook(id) != null

    fun borrowBook(id: Int): Boolean {
        val book = findBook(id) ?: return false
        if (book.borrow()) {
            notifier.send("Выдана книга: ${book.title}")
            return true
        }
        return false
    }

    fun returnBook(id: Int): Boolean {
        val book = findBook(id) ?: return false
        if (book.returnToLibrary()) {
            notifier.send("Возвращена книга:  ${book.title}")
            return true
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
        if (books.isEmpty()) return "В библиотеке нет книг"
        var result = ""
        for (i in books.indices) {
            val book = books[i]
            if (i > 0) result += "\n"
            val status = if (book.isAvailable) "доступна" else "выдана"
            result += "${book.id}. ${book.title} - ${book.author}: $status"
        }
        return result
    }
}
