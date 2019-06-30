package fr.benchaabane.domainlayer.books

import io.reactivex.Completable
import io.reactivex.Single

interface INetworkBooksRepository {
    fun retrieveBooks(): Single<List<Book>>
    fun updateBook(book: BookUpdate): Completable
}