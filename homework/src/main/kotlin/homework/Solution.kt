package homework

enum class Genre(val label: String) {
    FANTASY("Фэнтези"),
    ADVENTURE("Приключения"),
    SCIENCE("Наука")
}

object LibraryRules {
    fun loanDays(genre: Genre): Int = when (genre) {
        Genre.FANTASY -> 21
        Genre.SCIENCE -> 7
        Genre.ADVENTURE -> 14
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
        if(!isAvailable) return false
        isAvailable = false
        return true
    }

    fun returnToLibrary(): Boolean {
        if(isAvailable) return false
        isAvailable = true
        return true
    }
    fun info():BookInfo{
        return BookInfo(title, author, genre)
    }
}

data class BookInfo(val title: String, val author: String, val genre: Genre)




interface Notifier {
    fun send(message: String)
}

class ConsoleNotifier : Notifier {
    override fun send(message: String) = println(message)
}

class CountingNotifier : Notifier {
    var sentCount: Int =0
    private set
    var lastMessage: String = ""
    private set

    override fun send(message: String) {
        sentCount = sentCount + 1
        lastMessage = message
    }
}




class Library(private val books: Array<Book>, private val notifier: Notifier) {
    fun hasBook(id: Int): Boolean {
        return books.any { it.id == id }
    }

    fun borrowBook(id: Int): Boolean {
        return false
    }

    fun returnBook(id: Int): Boolean {
        return false
    }

    fun availableCount(): Int {
        return 0
    }

    fun countByGenre(genre: Genre): Int {
        return books.count { it.genre == genre }
    }

    fun report(): String {
        return "Total Books: ${books.size}"
    }
}
