package fr.benchaabane.presentationlayer.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import fr.benchaabane.commons.tools.Logger
import fr.benchaabane.domainlayer.books.Book
import fr.benchaabane.domainlayer.books.RetrieveLocalBooksUseCase
import fr.benchaabane.domainlayer.books.RetrieveNetworkBooksUseCase
import fr.benchaabane.domainlayer.books.SaveBooksUseCase
import fr.benchaabane.presentationlayer.extensions.subscribeAsyncToState
import fr.benchaabane.presentationlayer.extensions.subscribeAsyncToStateWithRetry
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.subjects.PublishSubject

class SplashViewModel(private val saveBooksUseCase: SaveBooksUseCase,
                      private val retrieveNetworkBooksUseCase: RetrieveNetworkBooksUseCase,
                      private val retrieveLocalBooksUseCase: RetrieveLocalBooksUseCase) : ViewModel(), ISplashViewModel {

    private val disposables = CompositeDisposable()
    private val retry = PublishSubject.create<Unit>()

    private val errorState = MutableLiveData<Boolean>()
    private val successState = MutableLiveData<Boolean>()


    /* ------------------------------------- */
    /*                Override               */
    /* ------------------------------------- */

    override fun observeErrorState(): LiveData<Boolean> = errorState
    override fun observeSuccessState(): LiveData<Boolean> = successState

    override fun fetchBooks() {
        disposables += retrieveNetworkBooksUseCase.execute()
            .subscribeAsyncToStateWithRetry(
                onSuccess = { saveBooks(it) },
                onError = {
                    Logger.e(it)
                    fetchLocalBooks()
                },
                onLoading = { },
                retry = retry
            )
    }

    override fun retry() {
        retry.onNext(Unit)
    }

    override fun destroySelf() {
        disposables.clear()
    }


    /* ------------------------------------- */
    /*              Processing               */
    /* ------------------------------------- */

    private fun saveBooks(books: List<Book>) {
        disposables += saveBooksUseCase.execute(books)
            .subscribeAsyncToState(
                onSuccess = { successState.postValue(true) },
                onError = {
                    Logger.e(it)
                    errorState.postValue(true)
                },
                onLoading = { })
    }

    private fun fetchLocalBooks() {
        disposables += retrieveLocalBooksUseCase.execute()
            .subscribeAsyncToState(
                onSuccess = { if (!it.isNullOrEmpty()) successState.postValue(true) else errorState.postValue(true)},
                onError = {
                    Logger.e(it)
                    errorState.postValue(true)
                },
                onLoading = {})
    }


}