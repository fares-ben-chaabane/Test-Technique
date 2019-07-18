package fr.benchaabane.domainlayer.books

import io.reactivex.Single

interface INetworkBooksRepository {
    fun retrieveBooks(): Single<List<Book>>
}