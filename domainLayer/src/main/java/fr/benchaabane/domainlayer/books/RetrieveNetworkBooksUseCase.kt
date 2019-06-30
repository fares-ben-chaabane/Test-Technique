package fr.benchaabane.domainlayer.books

import fr.benchaabane.commons.os.OpenWhenTesting
import io.reactivex.Observable

@OpenWhenTesting
class RetrieveNetworkBooksUseCase(private val repository: INetworkBooksRepository) {

    fun execute(): Observable<List<Book>> = repository.retrieveBooks().toObservable()
}