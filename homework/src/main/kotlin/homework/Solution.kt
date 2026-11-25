package homework

enum class Genre(val label: String) {
    FANTASY("Фэнтези"),
    ADVENTURE("Приключения"),
    SCIENCE("Наука")
}

object LibraryRules {
    fun loanDays(genre: Genre): Int {
        val day = when (genre) {
            Genre.FANTASY -> 21
            Genre.SCIENCE -> 7
            Genre.ADVENTURE -> 14
        }
        return day;
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
        if (isAvailable) {isAvailable= false; return true };
        return false;
    }

    fun returnToLibrary(): Boolean {
        if (isAvailable) {return false }
        isAvailable = true;
        return true;
    }

    fun info(): BookInfo{
        val Bookinfo = BookInfo(title, author, genre);
        return Bookinfo;
    }
}

interface Notifier {
    fun send(message: String) {
        println(message)
    }
}

class ConsoleNotifier: Notifier {
    override fun send(message: String) {
        println(message);
    }
}

class CountingNotifier: Notifier {
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
    fun hasBook(id: Int): Boolean {
        if (books.any { it.id == id }) {
            return true;
        }
        return false;
    }

    fun borrowBook(id: Int): Boolean {
        if (!hasBook(id)) {
            return false;
        }
        if (books.find { it.id == id }?.borrow() == false) {
            return false;
        } else {
            notifier.send("Выдана книга: ${books.find { it.id == id }?.title}");
            return true;
        }
    }

    fun returnBook(id: Int): Boolean {
        if (!hasBook(id)) {
            return false;
        }
        if (books.find { it.id == id }?.returnToLibrary() == false) {
            return false;
        } else {
            notifier.send("Возвращена книга: ${books.find { it.id == id }?.title}");
            return true;
        }
    }

    fun availableCount(): Int {
        if (books.size == 0) {
            return 0;
        }
        return books.count { it.isAvailable == true };
    }

    fun countByGenre(genre: Genre): Int {
        if (books.size == 0) {
            return 0;
        }
        return books.count { it.genre == genre };
    }

    fun report(): String {
        if (books.size == 0) {
            return "Библиотека пуста"
        } else {
            var message = "";
            for (book in books) {
                message += "${book.id}";
                message += ". ";
                message += book.title;
                message += " — ";
                message += book.author;
                message += ": ";
                if (book.isAvailable) {
                    message += "доступна"
                } else {
                    message += "выдана"
                }
                message += "\n";
            }
            return message.dropLast(1);
        }
    }
}

data class BookInfo(
    val title: String,
    val author: String,
    val genre: Genre
)
