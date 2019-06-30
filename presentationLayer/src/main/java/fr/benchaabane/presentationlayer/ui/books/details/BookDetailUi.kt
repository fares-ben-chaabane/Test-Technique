package fr.benchaabane.presentationlayer.ui.books.details

data class BookDetailUi(val title: String,
                        val author: String,
                        val distribution: String,
                        val summary: String,
                        val pagesCount: String,
                        val rating: Double,
                        val coverUrl: String)