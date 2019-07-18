package fr.benchaabane.domainlayer.books

data class Book(
    val uic: String,
    val title: String,
    val author: String,
    val distribution: String,
    val coverUrl: String,
    val pagesCount: Int,
    val rate: Int,
    val summary: String,
    val category: BookCategory,
    val publishYear: String)

enum class BookCategory {
    NOVEL, HISTORY, FICTION, WAR, COMEDY
}