package homework

enum class Genre(val label: String) {
    FANTASY("Фэнтези"),
    ADVENTURE("Приключения"),
    SCIENCE("Наука")
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
    init {
        require(id > 0)
        require(title.isNotBlank())
        require(author.isNotBlank())
    }

    var isAvailable = true
        private set

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

    fun info(): BookInfo{
        return BookInfo(title, author, genre)
    }
}

interface Notifier{
    fun send(message: String)
}

class ConsoleNotifier : Notifier{
    override fun send(message: String) {
        println(message)
    }
}

class CountingNotifier : Notifier{
    var sentCount = 0
    private set
    var lastMessage = ""
    private set
    override fun send(message: String) {
        sentCount++
        lastMessage = message
    }
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

class Library(
    val books: Array<Book>,
    val notifier: Notifier,
){
    fun hasBook(id: Int): Boolean {
        for (book in books) {
            if (book.id == id) {
                return true
            }
        }
        return false
    }

    fun borrowBook(id: Int): Boolean{
        for (book in books) {
            if (book.id == id) {
                if (book.borrow()){
                    notifier.send("Выдана книга: ${book.title}")
                    return true
                } else {
                    return false
                }
            }
        }
        return false
    }

    fun returnBook(id: Int): Boolean{
        for (book in books) {
            if (book.id == id) {
                if (book.returnToLibrary()){
                    notifier.send("Возвращена книга: ${book.title}")
                    return true
                } else {
                    return false
                }
            }
        }
        return false
    }

    fun availableCount(): Int {
        var count = 0
        for (book in books) {
            if (book.isAvailable){
                count++
            }
        }
        return count
    }

    fun countByGenre(genre: Genre): Int {
        var count = 0
        for (book in books) {
            if (book.genre == genre){
                count++
            }
        }
        return count
    }

    fun report(): String{
        var message = ""
        if (books.isEmpty()){
            message = "Библиотека пуста"
        } else {
            for (i in 0 until books.size){
                val book = books[i]
                var status = ""
                if (book.isAvailable){
                    status = "доступна"
                } else {
                    status = "выдана"
                }
                message += "${book.id}. ${book.title} — ${book.author}: $status"
                if (i < books.size - 1){
                    message += "\n"
                }
            }
        }
        return message
    }
}