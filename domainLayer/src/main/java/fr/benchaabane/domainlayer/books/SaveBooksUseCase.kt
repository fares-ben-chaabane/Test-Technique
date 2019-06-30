package fr.benchaabane.domainlayer.books

import fr.benchaabane.commons.os.OpenWhenTesting
import io.reactivex.Completable

@OpenWhenTesting
class SaveBooksUseCase(private val localBooksRepository: ILocalBooksRepository) {

    fun execute(books: List<Book>): Completable = localBooksRepository.saveBooks(books)

}