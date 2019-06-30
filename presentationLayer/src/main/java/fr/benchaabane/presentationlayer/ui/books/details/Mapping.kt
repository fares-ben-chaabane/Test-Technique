package fr.benchaabane.presentationlayer.ui.books.details

import fr.benchaabane.domainlayer.books.Book
import fr.benchaabane.presentationlayer.R
import fr.benchaabane.presentationlayer.tools.Resources

internal fun Book.toBookDetailUi(resources: Resources) = BookDetailUi(title = title,
    author = "${resources.getString(R.string.author_intro)} $author",
    distribution = "${resources.getString(R.string.distribution_intro)} $distribution ${resources.getString(R.string.`in`)} $publishYear",
    coverUrl = coverUrl,
    summary = summary,
    pagesCount = "$pagesCount ${resources.getString(R.string.pages)}",
    rating = rate.toDouble())