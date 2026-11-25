package homework

enum class Genre(val label: String) {
    FANTASY("Фэнтези"),
    ADVENTURE("Приключения"),
    SCIENCE("Наука")
}

object LibraryRules {
    fun loanDays(genre: Genre): Int = when (genre) {
        (Genre.ADVENTURE) -> 14
        (Genre.FANTASY) -> 21
        (Genre.SCIENCE) -> 7

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
        require(id>0)
        require(title.isNotBlank())
        require(author.isNotBlank())
    }

    fun borrow(): Boolean {
        if (isAvailable == false) return false
        else {
            isAvailable = false
            return true
        }
    }

    fun returnToLibrary(): Boolean {
        if (isAvailable == true) return false
        else {
            isAvailable = true
            return true
        }
    }
    fun info(): BookInfo =  BookInfo(title, author, genre)
}
data class BookInfo(val title: String, val author: String, val genre: Genre)

interface Notifier {
    fun send(message: String)
}

class ConsoleNotifier : Notifier{
    override fun send(message: String) {
        println(message)
    }
}

class CountingNotifier : Notifier{
    var sentCount: Int = 0
    private set
    var lastMessage: String =""
    private set
    override fun send(message: String) {
        sentCount = sentCount+1
        lastMessage = message
    }
}
class Library(private val books: Array<Book>, private val notifier: Notifier) {
    fun hasBook(id: Int): Boolean {
        for (book in books) {
            if (id == book.id) return true
        }
        return false
    }

    fun borrowBook(id: Int): Boolean {
        if (id <= 0) return false
        for (book in books) {
            if (id == book.id) {
                if (book.borrow() == false) return false
                notifier.send("Выдана книга: $book.title")
                return true
            }
        }
        return false
    }

    fun returnBook(id: Int): Boolean {
        if (id <= 0) return false
        for (book in books) {
            if (id == book.id) {
                if (book.returnToLibrary() == false) return false
                notifier.send("Возвращена книга: $book.title")
                return true
            }
        }
        return false
    }

    fun availableCount(): Int {
        var count = 0
        for (book in books) {
            if (book.isAvailable == true) count++
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
        if (books.isEmpty() == true) return "Библиотека пуста"
        var availability = ""
        for (i in books.indices) {
            val book = books[i]
            availability += "${book.id}. ${book.title} — ${book.author}:"
            if (book.isAvailable == true) availability += " доступна\n"
            else availability += " выдана\n"
        }
        return availability
    }
}