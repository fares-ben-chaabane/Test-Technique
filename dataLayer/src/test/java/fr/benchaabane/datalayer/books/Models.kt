package fr.benchaabane.datalayer.books

import fr.benchaabane.domainlayer.books.Book
import fr.benchaabane.domainlayer.books.BookCategory
import fr.benchaabane.domainlayer.books.BookUpdate

class DataBook {

    val json by lazy { Json() }
    val domain by lazy { Domain() }

    class Domain {
        val book = Book(
            uic = "uic",
            title = "title",
            author = "author",
            distribution = "distribution",
            coverUrl = "cover",
            pagesCount = 5,
            rate = 2,
            summary = "summary",
            category = BookCategory.COMEDY,
            publishYear = "2000")
    }

    class Json {
        val bookJson = BookJson(uic = "uic",
            title = "title",
            author = "author",
            distribution = "distribution",
            coverUrl = "cover",
            pagesCount = 5,
            rate = 2,
            summary = "summary",
            category = "Comedy",
            publishYear = "2000")

        val bookJsonWrapper = BookWrapperJson(books = listOf(bookJson, bookJson.copy(uic = "2", rate = 4)))
    }
}