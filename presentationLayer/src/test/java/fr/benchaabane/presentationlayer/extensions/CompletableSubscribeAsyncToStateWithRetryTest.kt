package fr.benchaabane.presentationlayer.extensions


import com.nhaarman.mockitokotlin2.then
import com.nhaarman.mockitokotlin2.times
import fr.benchaabane.commons_android.tools.TestSchedulers
import fr.benchaabane.commons_android.tools.initForTest
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.subjects.PublishSubject
import org.amshove.kluent.any
import org.amshove.kluent.mock
import org.junit.Before
import org.junit.Test
import java.util.concurrent.Callable

class CompletableSubscribeAsyncToStateWithRetryTest {

    private lateinit var onSuccess: () -> Unit
    private lateinit var onLoading: () -> Unit
    private lateinit var onError: (Throwable) -> Unit
    private lateinit var retry: PublishSubject<Unit>

    @Before
    fun setUp() {
        TestSchedulers.initForTest()
        onSuccess = mock()
        onLoading = mock()
        onError = mock()
        retry = PublishSubject.create()
    }

    @Test
    fun `on success`() {
        // Given
        val completable = Completable.complete()
        // When
        completable.subscribeAsyncToStateWithRetry(onSuccess = onSuccess,
                                                   onLoading = onLoading,
                                                   onError = onError,
                                                   retry = retry)
        // Then
        then(onSuccess).should(times(1)).invoke()
        then(onLoading).should(times(1)).invoke()
        then(onError).should(times(0)).invoke(any())
    }

    @Test
    fun `on error`() {
        // Given
        val completable = Completable.error(Exception())
        // When
        completable.subscribeAsyncToStateWithRetry(onSuccess = onSuccess,
                                                   onLoading = onLoading,
                                                   onError = onError,
                                                   retry = retry)
        // Then
        then(onSuccess).should(times(0)).invoke()
        then(onLoading).should(times(1)).invoke()
        then(onError).should(times(1)).invoke(any())
    }

    @Test
    fun `loading when retry`() {
        // Given
        val completable = Observable.defer(object : Callable<ObservableSource<String>> {
            var callCount = 0
            override fun call(): ObservableSource<String> {
                callCount++
                return when (callCount) {
                    1 -> Observable.error(Exception())
                    else -> Completable.complete().toObservable()
                }
            }
        }).ignoreElements()
        completable.subscribeAsyncToStateWithRetry(onSuccess = onSuccess,
                                                   onLoading = onLoading,
                                                   onError = onError,
                                                   retry = retry)
        // When
        retry.onNext(Unit)
        // Then
        then(onSuccess).should(times(1)).invoke()
        then(onLoading).should(times(2)).invoke()
        then(onError).should(times(1)).invoke(any())
    }


}