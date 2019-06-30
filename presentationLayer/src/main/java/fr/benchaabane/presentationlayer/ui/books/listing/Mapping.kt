package fr.benchaabane.presentationlayer.ui.books.listing

import fr.benchaabane.domainlayer.books.Book
import fr.benchaabane.presentationlayer.R
import fr.benchaabane.presentationlayer.tools.Resources

internal fun Book.toBookPreviewUi(resources: Resources) = BookPreviewUi(title = title,
    author = "${resources.getString(R.string.author_intro)} $author",
    distribution = "${resources.getString(R.string.distribution_intro)} $distribution ${resources.getString(R.string.`in`)} $publishYear",
    coverUrl = coverUrl,
    uic = uic,
    publishYear = publishYear.toInt(),
    isFavorite = isFavorite)