package fr.benchaabane.datalayer.books

import fr.benchaabane.domainlayer.books.Book
import fr.benchaabane.domainlayer.books.INetworkBooksRepository
import io.reactivex.Single

class NetworkBooksRepository(private val api: BooksApi): INetworkBooksRepository {

    override fun retrieveBooks(): Single<List<Book>> {
        return api.getBooks().map { response -> response.books.map { it.toBook() } }
    }
}