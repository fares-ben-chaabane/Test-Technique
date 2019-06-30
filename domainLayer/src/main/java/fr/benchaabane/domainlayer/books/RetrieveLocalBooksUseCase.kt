package fr.benchaabane.domainlayer.books

import fr.benchaabane.commons.os.OpenWhenTesting
import io.reactivex.Observable

@OpenWhenTesting
class RetrieveLocalBooksUseCase(private val repository: ILocalBooksRepository)  {

    fun execute(): Observable<List<Book>> = repository.retrieveBooks().toObservable()
}