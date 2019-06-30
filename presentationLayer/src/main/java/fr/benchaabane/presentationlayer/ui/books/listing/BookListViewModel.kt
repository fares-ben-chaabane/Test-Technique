package fr.benchaabane.presentationlayer.ui.books.listing

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import fr.benchaabane.commons.tools.Logger
import fr.benchaabane.domainlayer.books.*
import fr.benchaabane.presentationlayer.extensions.subscribeAsyncToState
import fr.benchaabane.presentationlayer.extensions.subscribeAsyncToStateWithRetry
import fr.benchaabane.presentationlayer.tools.Resources
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.subjects.PublishSubject

class BookListViewModel(private val retrieveLocalBooksUseCase: RetrieveLocalBooksUseCase,
                        private val retrieveNetworkBooksUseCase: RetrieveNetworkBooksUseCase,
                        private val saveBooksUseCase: SaveBooksUseCase,
                        private val addBookToFavoriteUseCase: AddBookToFavoriteUseCase,
                        private val resources: Resources) : ViewModel(), IBookListViewModel {

    private val disposables = CompositeDisposable()
    private val retry = PublishSubject.create<Unit>()

    private val booksList = MutableLiveData<List<BookPreviewUi>>()
    private val errorState = MutableLiveData<Boolean>()
    private val loadingState = MutableLiveData<Boolean>()
    private val refreshingState = MutableLiveData<Boolean>()


    /* ------------------------------------- */
    /*                Override               */
    /* ------------------------------------- */

    override fun observeBookList() = booksList
    override fun observeErrorState() = errorState
    override fun observeLoadingState() = loadingState
    override fun observerRefreshingState() = refreshingState

    override fun fetchBooks() {
        disposables += retrieveLocalBooksUseCase.execute()
            .subscribeAsyncToStateWithRetry(
                onSuccess = {
                    booksList.postValue(it.map { book -> book.toBookPreviewUi(resources) })
                    loadingState.postValue(false)
                    errorState.postValue(false)
                },
                onLoading = {
                    loadingState.postValue(true)
                    errorState.postValue(false)
                },
                onError = {
                    loadingState.postValue(false)
                    errorState.postValue(true)
                },
                retry = retry
            )
    }

    override fun refreshBookList() {
        disposables += retrieveNetworkBooksUseCase.execute()
            .subscribeAsyncToStateWithRetry(
                onSuccess = {
                    booksList.postValue(it.map { book -> book.toBookPreviewUi(resources) })
                    refreshingState.postValue(false)
                    saveBooks(it)
                },
                onLoading = { refreshingState.postValue(true) },
                onError = { refreshingState.postValue(false) },
                retry = retry
            )
    }

    override fun sortBookList(isAsc: Boolean) {
        booksList.value = if (isAsc) booksList.value?.sortedBy { it.publishYear } else booksList.value?.sortedByDescending { it.publishYear }
    }

    override fun filterBookList(showFavorites: Boolean) {
        if (showFavorites) booksList.value = booksList.value?.filter { it.isFavorite } else fetchBooks()
    }

    override fun updateBook(uic: String, isFavorite: Boolean) {
        disposables += addBookToFavoriteUseCase.execute(BookUpdate(uic, isFavorite))
            .subscribeAsyncToState(
                onSuccess = {
                    booksList.value = booksList.value?.map { if (it.uic == uic) it.copy(isFavorite = isFavorite) else it }
                },
                onError = { Logger.e(it) },
                onLoading = {}
            )
    }

    override fun retry() {
        retry.onNext(Unit)
    }

    override fun destroySelf() {
        disposables.clear()
    }


    private fun saveBooks(books: List<Book>) {
        disposables += saveBooksUseCase.execute(books)
            .subscribeAsyncToState(
                onSuccess = {},
                onLoading = {},
                onError = {}
            )
    }
}