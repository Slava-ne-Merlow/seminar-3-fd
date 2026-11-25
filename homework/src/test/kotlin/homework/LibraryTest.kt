package homework

import java.io.ByteArrayOutputStream
import java.io.PrintStream
import kotlin.test.*

class LibraryTest {
    @Test fun `новая книга доступна и имеет жанр по умолчанию`() {
        val book = Book(1, "Хоббит", "Толкин")
        assertTrue(book.isAvailable)
        assertEquals(Genre.FANTASY, book.genre)
    }

    @Test fun `книга отвергает неверные данные`() {
        assertFailsWith<IllegalArgumentException> { Book(0, "Книга", "Автор") }
        assertFailsWith<IllegalArgumentException> { Book(1, " ", "Автор") }
        assertFailsWith<IllegalArgumentException> { Book(1, "Книга", "") }
    }

    @Test fun `книга управляет выдачей и возвратом`() {
        val book = Book(1, "Хоббит", "Толкин")
        assertFalse(book.returnToLibrary())
        assertTrue(book.borrow())
        assertFalse(book.isAvailable)
        assertFalse(book.borrow())
        assertTrue(book.returnToLibrary())
        assertTrue(book.isAvailable)
    }

    @Test fun `состояние книги нельзя присвоить снаружи`() {
        for (method in Book::class.java.methods) {
            assertFalse(method.name == "setAvailable" || method.name == "setIsAvailable")
        }
        for (field in Book::class.java.fields) {
            assertFalse(field.name == "isAvailable")
        }
    }

    @Test fun `карточка книги содержит её данные`() {
        val book = Book(1, "Космос", "Саган", Genre.SCIENCE)
        assertEquals(createBookInfo("Космос", "Саган", Genre.SCIENCE), book.javaClass.getMethod("info").invoke(book))
    }

    @Test fun `карточки сравниваются по данным`() {
        val first = createBookInfo("Хоббит", "Толкин", Genre.FANTASY)
        val second = createBookInfo("Хоббит", "Толкин", Genre.FANTASY)
        assertEquals(first, second)
        assertEquals(first.hashCode(), second.hashCode())
    }

    @Test fun `уведомитель считает сообщения и запоминает последнее`() {
        val notifier = createCountingNotifier()
        assertEquals(0, notifier.sentCount)
        assertEquals("", notifier.lastMessage)
        notifier.send("Первое")
        notifier.send("Второе")
        assertEquals(2, notifier.sentCount)
        assertEquals("Второе", notifier.lastMessage)
    }

    @Test fun `консольное уведомление печатает сообщение`() {
        val buffer = ByteArrayOutputStream()
        val original = System.out
        val stream = PrintStream(buffer, true, "UTF-8")
        try {
            System.setOut(stream)
            createConsoleNotifier().send("Привет")
        } finally {
            System.setOut(original)
            stream.close()
        }
        assertEquals("Привет${System.lineSeparator()}", buffer.toString("UTF-8"))
    }

    @Test fun `библиотека проверяет наличие книги по id`() {
        val book = Book(7, "Хоббит", "Толкин")
        val library = Library(arrayOf(book), createCountingNotifier())
        assertTrue(library.hasBook(7))
        assertFalse(library.hasBook(1))
    }

    @Test fun `выдача и возврат уведомляют только при успехе`() {
        val notifier = createCountingNotifier()
        val book = Book(1, "Хоббит", "Толкин")
        val library = Library(arrayOf(book), notifier)
        assertTrue(library.borrowBook(1))
        assertEquals("Выдана книга: Хоббит", notifier.lastMessage)
        assertFalse(library.borrowBook(1))
        assertFalse(library.borrowBook(99))
        assertTrue(library.returnBook(1))
        assertFalse(library.returnBook(1))
        assertEquals(2, notifier.sentCount)
        assertEquals("Возвращена книга: Хоббит", notifier.lastMessage)
    }

    @Test fun `библиотека принимает другую реализацию интерфейса`() {
        val book = Book(1, "Хоббит", "Толкин")
        val notifier = StateNotifier(book)
        val library = Library(arrayOf(book), notifier)
        library.borrowBook(1)
        assertEquals(1, notifier.calls)
        assertFalse(notifier.availableWhenSent)
    }

    @Test fun `доступность считается по состоянию книг`() {
        val first = Book(1, "Хоббит", "Толкин")
        val second = Book(2, "Космос", "Саган", Genre.SCIENCE)
        val library = Library(arrayOf(first, second), createCountingNotifier())
        library.borrowBook(1)
        assertEquals(1, library.availableCount())
        assertFalse(first.isAvailable)
        assertTrue(second.isAvailable)
    }

    @Test fun `подсчёт по жанру включает выданные книги`() {
        val science = Book(2, "Космос", "Саган", Genre.SCIENCE)
        val library = Library(arrayOf(science), createCountingNotifier())
        library.borrowBook(2)
        assertEquals(1, library.countByGenre(Genre.SCIENCE))
        assertEquals(0, library.countByGenre(Genre.FANTASY))
    }

    @Test fun `срок выдачи зависит от жанра`() {
        assertEquals(21, LibraryRules.loanDays(Genre.FANTASY))
        assertEquals(14, LibraryRules.loanDays(Genre.ADVENTURE))
        assertEquals(7, LibraryRules.loanDays(Genre.SCIENCE))
    }

    @Test fun `пустая библиотека`() {
        val library = Library(emptyArray(), createCountingNotifier())
        assertFalse(library.hasBook(1))
        assertFalse(library.borrowBook(1))
        assertFalse(library.returnBook(1))
        assertEquals(0, library.availableCount())
        assertEquals(0, library.countByGenre(Genre.FANTASY))
        assertEquals("Библиотека пуста", library.report())
    }

    @Test fun `отчёт содержит id и состояние книги`() {
        val library = Library(arrayOf(Book(7, "Хоббит", "Толкин")), createCountingNotifier())
        library.borrowBook(7)
        assertEquals("7. Хоббит — Толкин: выдана", library.report())
    }
}

private class StateNotifier(private val book: Book) : Notifier {
    var calls = 0
    var availableWhenSent = true

    override fun send(message: String) {
        calls++
        availableWhenSent = book.isAvailable
    }
}

// Самостоятельно создаваемые классы загружаются по имени, чтобы заготовка компилировалась.
private fun createBookInfo(title: String, author: String, genre: Genre): Any {
    return Class.forName("homework.BookInfo")
        .getConstructor(String::class.java, String::class.java, Genre::class.java)
        .newInstance(title, author, genre)
}

private fun createConsoleNotifier(): Notifier {
    return Class.forName("homework.ConsoleNotifier").getConstructor().newInstance() as Notifier
}

private fun createCountingNotifier(): CountingNotifierProbe {
    val instance = Class.forName("homework.CountingNotifier").getConstructor().newInstance() as Notifier
    return CountingNotifierProbe(instance)
}

private class CountingNotifierProbe(private val instance: Notifier) : Notifier {
    val sentCount: Int
        get() = instance.javaClass.getMethod("getSentCount").invoke(instance) as Int
    val lastMessage: String
        get() = instance.javaClass.getMethod("getLastMessage").invoke(instance) as String

    override fun send(message: String) {
        instance.send(message)
    }
}
