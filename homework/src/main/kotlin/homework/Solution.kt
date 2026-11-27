package homework

enum class Genre(val label: String) {
    FANTASY("Фэнтези"),
    ADVENTURE("Приключения"),
    SCIENCE("Наука")
}

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
        sentCount += 1
        lastMessage = message
    }
}

data class BookInfo(
    val title: String,
    val author: String,
    val genre: Genre
)

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
        if (!isAvailable) return false
        isAvailable = false
        return true
    }

    fun returnToLibrary(): Boolean {
        if (isAvailable) return false
        isAvailable = true
        return true
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

class Library(
    private val books: Array<Book>,
    private val notifier: Notifier
) {
    fun hasBook(id: Int): Boolean {
        if (id <= 0) return false
        for (book in books) {
            if (book.id == id) return true
        }
        return false
    }

    fun borrowBook(id: Int): Boolean {
        if (id <= 0) return false
        for (book in books) {
            if (book.id == id) {
                if (book.borrow()) {
                    notifier.send("Выдана книга: ${book.title}")
                    return true
                }
                return false
            }
        }
        return false
    }

    fun returnBook(id: Int): Boolean {
        if (id <= 0) return false
        for (book in books) {
            if (book.id == id) {
                if (book.returnToLibrary()) {
                    notifier.send("Возвращена книга: ${book.title}")
                    return true
                }
                return false
            }
        }
        return false
    }

    fun availableCount(): Int {
        var count = 0
        for (book in books) {
            if (book.isAvailable) {
                count += 1
            }
        }
        return count
    }

    fun countByGenre(genre: Genre): Int {
        var count = 0
        for (book in books) {
            if (book.genre == genre) {
                count += 1
            }
        }
        return count
    }

    fun report(): String {
        if (books.isEmpty()) return "Библиотека пуста"

        var result = ""
        for (i in books.indices) {
            val book = books[i]
            val status = if (book.isAvailable) "доступна" else "выдана"
            result += "${book.id}. ${book.title} — ${book.author}: $status"
            if (i < books.size - 1) {
                result += "\n"
            }
        }
        return result
    }
}

