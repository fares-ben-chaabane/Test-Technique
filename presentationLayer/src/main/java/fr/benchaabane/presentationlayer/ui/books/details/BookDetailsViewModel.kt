package fr.benchaabane.presentationlayer.ui.books.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import fr.benchaabane.domainlayer.books.RetrieveBookDetailsUseCase
import fr.benchaabane.presentationlayer.extensions.subscribeAsyncToStateWithRetry
import fr.benchaabane.presentationlayer.tools.Resources
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.subjects.PublishSubject

class BookDetailsViewModel(private val retrieveBookDetailsUseCase: RetrieveBookDetailsUseCase,
                           private val resources: Resources) : ViewModel(), IBookDetailsViewModel {

    private val disposables = CompositeDisposable()
    private val retry = PublishSubject.create<Unit>()

    private val bookDetail = MutableLiveData<BookDetailUi>()
    private val errorState = MutableLiveData<Boolean>()
    private val loadingState = MutableLiveData<Boolean>()

    /* ------------------------------------- */
    /*                Override               */
    /* ------------------------------------- */

    override fun observeBookDetail() = bookDetail
    override fun observerLoadingState() = loadingState
    override fun observeErrorState() = errorState

    override fun fetchBookDetails(uic: String) {
        disposables += retrieveBookDetailsUseCase.execute(uic)
            .subscribeAsyncToStateWithRetry(
                onSuccess = {
                    if (it.isPresent) {
                        bookDetail.postValue(it.get().toBookDetailUi(resources))
                        errorState.postValue(false)
                        loadingState.postValue(false)
                    } else {
                        loadingState.postValue(false)
                        errorState.postValue(true)
                    }
                },
                onLoading = {
                    errorState.postValue(false)
                    loadingState.postValue(true)
                },
                onError = {
                    errorState.postValue(true)
                    loadingState.postValue(false)
                },
                retry = retry
            )
    }

    override fun retry() {
        retry.onNext(Unit)
    }

    override fun destroySelf() {
        disposables.clear()
    }
}