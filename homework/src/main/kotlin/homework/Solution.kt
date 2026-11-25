package homework

enum class Genre(val label: String) {
    FANTASY("Фэнтези"),
    ADVENTURE("Приключения"),
    SCIENCE("Наука")
}

interface Notifier {
    fun send(message: String)
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
        require(id > 0) { "ID должен быть больше 0" }
        require(title.isNotBlank()) { "Название не может быть пустым" }
        require(author.isNotBlank()) { "Автор не может быть пустым" }
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
            result += "${book.id}. ${book.title} — ${book.author}: $status"
            if (i < books.size - 1) {
                result += "\n"
            }
        }
        return result
    }
}
