package fr.benchaabane.presentationlayer.extensions

import fr.benchaabane.presentationlayer.tools.asSuccess
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.subjects.PublishSubject
import org.junit.Before
import org.junit.Test
import java.util.concurrent.Callable


class ObservableMapToStateWithRetryTest {

    private lateinit var retry: PublishSubject<Unit>
    private lateinit var observable: PublishSubject<Int>

    @Before
    fun setUp() {
        retry = PublishSubject.create<Unit>()
        observable = PublishSubject.create<Int>()
    }

    @Test
    fun `init with loading`() {
        // When
        val testObserver = observable.mapToStateWithRetry(retry).test()
        // Then
        testObserver.assertNotComplete()
                .assertNoErrors()
                .assertValueCount(1)
                .assertValue { it.isLoading }
    }

    @Test
    fun `map success`() {
        // Given
        val testObserver = observable.mapToStateWithRetry(retry).test()
        // When
        observable.onNext(1)
        // Then
        testObserver.assertNotComplete()
                .assertNoErrors()
                .assertValueCount(2)
                .assertValueAt(0) { it.isLoading }
                .assertValueAt(1) { it.isSuccess && it.asSuccess().data == 1 }
    }

    @Test
    fun `many events`() {
        // Given
        val testObserver = observable.mapToStateWithRetry(retry).test()
        // When
        observable.onNext(1)
        observable.onNext(2)
        observable.onNext(3)
        // Then
        testObserver.assertNotComplete()
                .assertNoErrors()
                .assertValueCount(4)
                .assertValueAt(0) { it.isLoading }
                .assertValueAt(1) { it.isSuccess && it.asSuccess().data == 1 }
                .assertValueAt(2) { it.isSuccess && it.asSuccess().data == 2 }
                .assertValueAt(3) { it.isSuccess && it.asSuccess().data == 3 }
    }

    @Test
    fun `map error`() {
        // Given
        val testObserver = observable.mapToStateWithRetry(retry).test()
        // When
        observable.onError(Exception())
        // Then
        testObserver.assertNotComplete()
                .assertNoErrors()
                .assertValueCount(2)
                .assertValueAt(0) { it.isLoading }
                .assertValueAt(1) { it.isError }
    }

    @Test
    fun `loading when retry`() {
        // Given
        val deferObservable = Observable.defer(object : Callable<ObservableSource<Int>> {
            var callCount = 0
            override fun call(): ObservableSource<Int> {
                callCount++
                return when (callCount) {
                    1 -> Observable.error(Exception())
                    else -> observable
                }
            }
        })
        val testObserver = deferObservable.mapToStateWithRetry(retry).test()
        // When
        retry.onNext(Unit)
        observable.onNext(1)
        // Then
        testObserver.assertNotComplete()
                .assertNoErrors()
                .assertValueCount(4)
                .assertValueAt(0) { it.isLoading }
                .assertValueAt(1) { it.isError }
                .assertValueAt(2) { it.isLoading }
                .assertValueAt(3) { it.isSuccess && it.asSuccess().data == 1 }
    }

}