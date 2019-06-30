package fr.benchaabane.domainlayer.books

import fr.benchaabane.commons.os.OpenWhenTesting
import io.reactivex.Completable

@OpenWhenTesting
class AddBookToFavoriteUseCase(private val repository: INetworkBooksRepository,
                               private val localRepository: ILocalBooksRepository) {

    fun execute(book: BookUpdate): Completable = repository.updateBook(book).andThen(localRepository.updateBook(book))
}