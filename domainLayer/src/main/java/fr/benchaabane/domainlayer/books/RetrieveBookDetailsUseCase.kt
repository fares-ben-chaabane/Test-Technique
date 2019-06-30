package fr.benchaabane.domainlayer.books

import fr.benchaabane.commons.os.OpenWhenTesting

@OpenWhenTesting
class RetrieveBookDetailsUseCase(private val repository: ILocalBooksRepository) {
    fun execute(uic: String) = repository.retrieveBookDetails(uic).toObservable()
}