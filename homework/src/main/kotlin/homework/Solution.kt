package homework

enum class Genre(val label: String) {
    FANTASY("Фэнтези"),
    ADVENTURE("Приключения"),
    SCIENCE("Наука")
}

object LibraryRules {
    fun loanDays(genre: Genre): Int = when(genre){
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

    fun info(): BookInfo = BookInfo(title,author,genre)
}

data class BookInfo(val title: String, val author: String, val genre: Genre)

interface Notifier {
    fun send(message: String)
}

class ConsoleNotifier : Notifier{
    override fun send(message: String){
        println(message)
    }
}

class CountingNotifier : Notifier{
    var sentCount: Int=0
        private set
    var lastMessage: String=""
        private set

    override fun send(message: String){
        sentCount++
        lastMessage=message
    }
}
class Library(private val books: Array<Book>, private val notifier: Notifier) {
    fun hasBook(id: Int): Boolean {
        for(book in books){
            if(book.id==id) return true
        }
        return false
    }

    fun borrowBook(id: Int): Boolean {
        for(book in books){
            if (id==book.id) {
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
        for(book in books){
            if(book.id==id){
                if(book.returnToLibrary()){
                    notifier.send("Возвращена книга: ${book.title}")
                    return true
                }
                return false
            }
        }
        return false
    }

    fun availableCount(): Int {
        var count=0
        for(book in books){
            if(book.isAvailable)count++
        }
        return count
    }

    fun countByGenre(genre: Genre): Int {
        var count = 0
        for(book in books){
            if(book.genre==genre) count++
        }
        return count
    }

    fun report(): String {
        if(books.isEmpty()) return "Библиотека пуста"
        var result=""
        for (book in books){
            var b=""
            b+="${book.id}. ${book.title} — ${book.author}: "
            if(book.isAvailable) b+="доступна"
            else b+="выдана"
            result+=b+"\n"
        }
        return result.dropLast(1)
    }
}
