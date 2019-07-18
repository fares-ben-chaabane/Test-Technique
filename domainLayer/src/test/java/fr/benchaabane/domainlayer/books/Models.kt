package fr.benchaabane.domainlayer.books

class DataBook {

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
}



