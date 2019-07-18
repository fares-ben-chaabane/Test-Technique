package fr.benchaabane.presentationlayer.ui.books.listing

data class BookPreviewUi(
    val uic: String,
    val title: String,
    val author: String,
    val distribution: String,
    val publishYear: Int,
    val coverUrl: String)