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
        } else {
            return false
        }
    }

    fun returnToLibrary(): Boolean {
        if (!isAvailable) {
            isAvailable = true
            return true
        } else {
            return false
        }
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
        ++sentCount
        lastMessage = message
    }
}


class Library(private val books: Array<Book>, private val notifier: Notifier) {
    fun hasBook(id: Int): Boolean {
        if (id <= 0) return false
        for (b in books) {
            if (b.id == id) return true
        }
        return false
    }

    fun borrowBook(id: Int): Boolean {
        if (id <= 0) return false
        for (b in books) {
            if (b.id == id) {
                if (b.borrow()) {
                    notifier.send("Выдана книга: ${b.title}")
                    return true
                }
                return false
            }
        }
        return false
    }

    fun returnBook(id: Int): Boolean {
        if (id <= 0) return false
        for (b in books) {
            if (b.id == id) {
                if (b.returnToLibrary()) {
                    notifier.send("Возвращена книга: ${b.title}")
                    return true
                }
                return false
            }
        }
        return false
    }

    fun availableCount(): Int {
        if (books.isEmpty()) return 0
        var cnt = 0
        for (b in books) {
            if (b.isAvailable) ++cnt
        }
        return cnt
    }

    fun countByGenre(genre: Genre): Int {
        var cnt = 0
        for (b in books) {
            if (b.genre == genre) ++cnt
        }
        return cnt
    }

    fun report(): String {
        if (books.isEmpty()) return "Библиотека пуста"
        var s = ""
        for (b in books) {
            if (s.isNotBlank()) s += "\n"
            s += "${b.id}. ${b.title} — ${b.author}: "
            if (b.isAvailable) {
                s += "доступна"
            } else s += "выдана"
        }
        return s
    }
}

data class BookInfo(val title: String, val author: String, val genre: Genre)