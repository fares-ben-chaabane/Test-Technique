package fr.benchaabane.presentationlayer.extensions

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.subjects.PublishSubject
import org.junit.Before
import org.junit.Test
import java.util.concurrent.Callable


class CompletableMapToStateWithRetryTest {

    private lateinit var retry: PublishSubject<Unit>

    @Before
    fun setUp() {
        retry = PublishSubject.create<Unit>()
    }

    @Test
    fun `map success`() {
        // Given
        val completable = Completable.complete()
        // When
        val testObserver = completable.mapToStateWithRetry(retry).test()
        // Then
        testObserver.assertComplete()
                .assertNoErrors()
                .assertValueCount(2)
                .assertValueAt(0) { it.isLoading }
                .assertValueAt(1) { it.isSuccess }
    }

    @Test
    fun `map error`() {
        // Given
        val completable = Completable.error(Exception())
        // When
        val testObserver = completable.mapToStateWithRetry(retry).test()
        // Then
        testObserver.assertNotComplete()
                .assertNoErrors()
                .assertValueCount(2)
                .assertValueAt(0) { it.isLoading }
                .assertValueAt(1) { it.isError }
        retry.onComplete()
        testObserver.assertComplete()
                .assertNoErrors()
                .assertValueCount(2)
                .assertValueAt(0) { it.isLoading }
                .assertValueAt(1) { it.isError }
    }

    @Test
    fun `loading when retry`() {
        // Given
        val completable = Observable.defer(object : Callable<ObservableSource<Int>> {
            var callCount = 0
            override fun call(): ObservableSource<Int> {
                callCount++
                return when (callCount) {
                    1 -> Observable.error(Exception())
                    else -> Completable.complete().toObservable()
                }
            }
        }).ignoreElements()
        val testObserver = completable.mapToStateWithRetry(retry).test()
        // When
        retry.onNext(Unit)
        // Then
        testObserver.assertComplete()
                .assertNoErrors()
                .assertValueCount(4)
                .assertValueAt(0) { it.isLoading }
                .assertValueAt(1) { it.isError }
                .assertValueAt(2) { it.isLoading }
                .assertValueAt(3) { it.isSuccess }
    }

}