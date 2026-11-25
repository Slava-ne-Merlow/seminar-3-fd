package homework

class Book(
    val id: Int,
    val title: String,
    val author: String,
    val genre: Genre = Genre.FANTASY
) {
    init {
        require(id > 0)
        require(title.isNotBlank())
        require(author.isNotBlank())
    }

    var isAvailable: Boolean = true
        private set

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
    val genre: Genre
)

object LibraryRules {
    fun loanDays(genre: Genre): Int = when (genre) {
        Genre.FANTASY -> 21
        Genre.ADVENTURE -> 14
        Genre.SCIENCE -> 7
    }
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
        sentCount += 1
        lastMessage = message
    }
}

class Library(books: Array<Book>, notifier: Notifier) {
    private val books: Array<Book> = books
    private val notifier: Notifier = notifier

    private fun indexOf(id: Int): Int {
        if (id <= 0) return -1
        for (i in books.indices) {
            if (books[i].id == id) return i
        }
        return -1
    }

    fun hasBook(id: Int): Boolean = indexOf(id) != -1

    fun borrowBook(id: Int): Boolean {
        val idx = indexOf(id)
        if (idx == -1) return false
        val book = books[idx]
        if (!book.borrow()) return false
        notifier.send("Выдана книга: ${book.title}")
        return true
    }

    fun returnBook(id: Int): Boolean {
        val idx = indexOf(id)
        if (idx == -1) return false
        val book = books[idx]
        if (!book.returnToLibrary()) return false
        notifier.send("Возвращена книга: ${book.title}")
        return true
    }

    fun availableCount(): Int {
        var count = 0
        for (book in books) {
            if (book.isAvailable) count += 1
        }
        return count
    }

    fun countByGenre(genre: Genre): Int {
        var count = 0
        for (book in books) {
            if (book.genre == genre) count += 1
        }
        return count
    }

    fun report(): String {
        if (books.isEmpty()) return "Библиотека пуста"
        var result = ""
        for (i in books.indices) {
            if (i > 0) result += "\n"
            val book = books[i]
            val status = if (book.isAvailable) "доступна" else "выдана"
            result += "${book.id}. ${book.title} — ${book.author}: $status"
        }
        return result
    }
}
