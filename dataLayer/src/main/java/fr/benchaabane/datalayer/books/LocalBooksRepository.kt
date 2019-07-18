package fr.benchaabane.datalayer.books

import com.vicpin.krealmextensions.queryAll
import com.vicpin.krealmextensions.queryFirst
import com.vicpin.krealmextensions.saveAll
import fr.benchaabane.commons.os.Optional
import fr.benchaabane.domainlayer.books.Book
import fr.benchaabane.domainlayer.books.ILocalBooksRepository
import io.reactivex.Completable
import io.reactivex.Single

class LocalBooksRepository: ILocalBooksRepository {

    override fun saveBooks(books: List<Book>): Completable {
        return Completable.fromCallable { books.map { it.toBookEntity() }.saveAll() }
    }

    override fun retrieveBooks(): Single<List<Book>> {
      return Single.just(BookEntity().queryAll().map { it.toBook() })
    }

    override fun retrieveBookDetails(uic: String): Single<Optional<Book>> {
        return Single.just(Optional(BookEntity().queryFirst { equalTo("uic", uic) }?.toBook()))
    }

}