package homework

enum class Genre(val label: String) {
    FANTASY("Фэнтези"),
    ADVENTURE("Приключения"),
    SCIENCE("Наука")
}

object LibraryRules {
    fun loanDays(genre: Genre): Int {
        return when (genre) {
            Genre.FANTASY -> 21
            Genre.ADVENTURE -> 14
            Genre.SCIENCE -> 7
        }
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
        if (!isAvailable) {
            return false
        }
        isAvailable = false
        return true
    }

    fun returnToLibrary(): Boolean {
        if (isAvailable) {
            return false
        }
        isAvailable = true
        return true
    }

    fun info(): BookInfo {
        return BookInfo(title, author, genre)
    }
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
        sentCount++
        lastMessage = message
    }
}

class Library(private val books: Array<Book>, private val notifier: Notifier) {
    private fun findBookIndex(id: Int): Int {
        if (id <= 0) {
            return -1
        }
        for (i in books.indices) {
            if (books[i].id == id) {
                return i
            }
        }
        return -1
    }

    fun hasBook(id: Int): Boolean {
        return findBookIndex(id) != -1
    }

    fun borrowBook(id: Int): Boolean {
        val index = findBookIndex(id)
        if (index == -1) {
            return false
        }
        val book = books[index]
        if (book.borrow()) {
            notifier.send("Выдана книга: ${book.title}")
            return true
        }
        return false
    }

    fun returnBook(id: Int): Boolean {
        val index = findBookIndex(id)
        if (index == -1) {
            return false
        }
        val book = books[index]
        if (book.returnToLibrary()) {
            notifier.send("Возвращена книга: ${book.title}")
            return true
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
            if (book.genre == genre) {
                count++
            }
        }
        return count
    }

    fun report(): String {
        if (books.isEmpty()) {
            return "Библиотека пуста"
        }

        var result = ""
        for (i in books.indices) {
            val book = books[i]
            val status = if (book.isAvailable) "доступна" else "выдана"
            result += "${book.id}. ${book.title} — ${book.author}: $status"
            if (i < books.lastIndex) {
                result += "\n"
            }
        }
        return result
    }
}


