package fr.benchaabane.domainlayer.books

import fr.benchaabane.commons.os.Optional
import io.reactivex.Completable
import io.reactivex.Single

interface ILocalBooksRepository {
    fun saveBooks(books: List<Book>): Completable
    fun retrieveBooks(): Single<List<Book>>
    fun retrieveBookDetails(uic: String): Single<Optional<Book>>
}