package fr.benchaabane.datalayer.books

import fr.benchaabane.domainlayer.books.Book
import fr.benchaabane.domainlayer.books.BookCategory
import fr.benchaabane.domainlayer.books.BookUpdate

internal fun BookJson.toBook() = Book(uic = uic,
    title = title,
    author = author,
    distribution = distribution?: "",
    category = category.toBookCategory(),
    coverUrl = coverUrl?: "",
    pagesCount = pagesCount,
    publishYear = publishYear,
    rate = rate,
    summary = summary?: "",
    isFavorite = isFavorite)

internal fun BookEntity.toBook() = Book(
    uic = uic,
    title = title?: "",
    author = author?: "",
    distribution = distribution?: "",
    category = category?.toBookCategory()?: BookCategory.NOVEL,
    coverUrl = coverUrl?: "",
    pagesCount = pagesCount,
    publishYear = publishYear?: "",
    rate = rate,
    summary = summary?: "",
    isFavorite = isFavorite)

internal fun Book.toBookEntity() = BookEntity(uic = uic,
    title = title,
    author = author,
    distribution = distribution,
    category = category.toCategory(),
    coverUrl = coverUrl,
    pagesCount = pagesCount,
    publishYear = publishYear,
    rate = rate,
    summary = summary,
    isFavorite = isFavorite)

internal fun BookUpdate.toBookJson() = BookUpdateJson(uic = uic,
    isFavorite = isFavorite)

typealias Category = String
internal fun Category.toBookCategory(): BookCategory {
    return when {
        this == "War" -> BookCategory.WAR
        this == "Novel" -> BookCategory.NOVEL
        this == "History" -> BookCategory.HISTORY
        this == "Fiction" -> BookCategory.FICTION
        else -> BookCategory.COMEDY
    }
}
internal fun BookCategory.toCategory(): Category {
    return when {
        this == BookCategory.WAR -> "War"
        this == BookCategory.NOVEL -> "Novel"
        this == BookCategory.HISTORY -> "History"
        this == BookCategory.FICTION -> "Fiction"
        else -> "Comedy"
    }
}