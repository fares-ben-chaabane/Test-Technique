package fr.benchaabane.presentationlayer.ui.books.details

import androidx.lifecycle.LiveData

interface IBookDetailsViewModel {

    fun observeBookDetail(): LiveData<BookDetailUi>
    fun observerLoadingState(): LiveData<Boolean>
    fun observeErrorState(): LiveData<Boolean>

    fun fetchBookDetails(uic: String)
    fun retry()
    fun destroySelf()
}