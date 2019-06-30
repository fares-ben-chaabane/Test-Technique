package fr.benchaabane.presentationlayer.test

import fr.benchaabane.domainlayer.books.Book
import fr.benchaabane.domainlayer.books.BookCategory
import fr.benchaabane.presentationlayer.ui.books.details.BookDetailUi

class DataBook {

    val ui by lazy { Ui() }
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
            publishYear = "2000",
            isFavorite = false
        )
    }

    class Ui {
        val bookUi = BookDetailUi(title = "title",
            author = "Written by author",
            distribution = "Distributed by distribution in 2000",
            summary = "summary",
            pagesCount = "5 Pages",
            coverUrl = "cover",
            rating = 2.toDouble())
    }
}