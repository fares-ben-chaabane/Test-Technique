package fr.benchaabane.presentationlayer.ui.books.listing

import androidx.lifecycle.LiveData

interface IBookListViewModel {

    fun observeBookList(): LiveData<List<BookPreviewUi>>
    fun observeErrorState(): LiveData<Boolean>
    fun observeLoadingState(): LiveData<Boolean>
    fun observerRefreshingState(): LiveData<Boolean>

    fun fetchBooks()
    fun refreshBookList()
    fun sortBookList(isAsc: Boolean)
    fun filterBookList(showFavorites: Boolean)
    fun updateBook(uic: String, isFavorite: Boolean)
    fun retry()
    fun destroySelf()
}